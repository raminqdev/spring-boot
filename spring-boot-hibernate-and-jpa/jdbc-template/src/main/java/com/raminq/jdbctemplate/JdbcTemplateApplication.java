package com.raminq.jdbctemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.*;

@SpringBootApplication
public class JdbcTemplateApplication {
    public static void main(String[] args) {
        SpringApplication.run(JdbcTemplateApplication.class, args);
    }
}

@org.springframework.core.annotation.Order(1)
@Log4j2
@Component
class Startup implements ApplicationRunner {
    private final CustomerRepo customerRepo;

    Startup(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (customerRepo.count() <= 0) {
            Set<Customer> customers = Set.of(
                    Customer.builder().name("ramin").email("ramin@ramin.com").orders(
                            Set.of(Order.builder().sku("ARG").build(),
                                    Order.builder().sku("MFH").build()
                            )).build(),
                    Customer.builder().name("jack").email("jack@jack.com").orders(
                            Set.of(Order.builder().sku("OPI").build(),
                                    Order.builder().sku("HTR").build(),
                                    Order.builder().sku("SEW").build()
                            )).build(),
                    Customer.builder().name("mary").email("mary@mary.com").build());
            customerRepo.saveAll(customers);
        }
    }
}


@org.springframework.core.annotation.Order(2)
@Log4j2
@Component
class QueryCustomerAndOrderCount implements ApplicationRunner {
    private final JdbcTemplate jdbcTemplate;

    QueryCustomerAndOrderCount(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        StringUtils.line();

        Collection<CustomerOrderReport> cor = jdbcTemplate
                .query("select c.* , (select count(o.id) from orders o where o.customer_fk = c.id) as count from customers c",
                        (rs, i) ->
                                new CustomerOrderReport(rs.getLong("id"), rs.getString("name"),
                                        rs.getString("email"), rs.getInt("count")));

        cor.forEach(log::info);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomerOrderReport {
        private Long id;
        private String name, email;
        private int orderCount;
    }
}


@org.springframework.core.annotation.Order(3)
@Log4j2
@Component
class QueryCustomersAndOrders implements ApplicationRunner {
    private final JdbcTemplate jdbcTemplate;

    QueryCustomersAndOrders(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    ResultSetExtractor<Collection<Customer>> rse = rs -> {
        Map<Long, Customer> customerMap = new HashMap<>();
        Customer currentCustomer = null;
        while (rs.next()) {
            long cid = rs.getLong("cid");
            if (currentCustomer == null || currentCustomer.getId() != cid) {
                currentCustomer = new Customer();
                currentCustomer.setId(cid);
                currentCustomer.setName(rs.getString("name"));
                currentCustomer.setEmail(rs.getString("email"));
            }
            if (rs.getString("oid") != null) {
                Order order = new Order(rs.getLong("oid"), rs.getString("sku"));
                currentCustomer.getOrders().add(order);
            }
            customerMap.put(currentCustomer.getId(), currentCustomer);
        }
        return customerMap.values();
    };

    @Override
    public void run(ApplicationArguments args) throws Exception {
        StringUtils.line();

        Collection<Customer> customers = jdbcTemplate
                .query("select c.id as cid, o.id as oid, c.*, o.* from customers c left join " +
                        " orders o on c.id = o.customer_fk order by cid", rse);

        customers.forEach(log::info);
    }

}


@org.springframework.core.annotation.Order(4)
@Log4j2
@Component
class QueryCustomersAndOrdersSimpleFlatMapper implements ApplicationRunner {
    private final JdbcTemplate jdbcTemplate;

    QueryCustomersAndOrdersSimpleFlatMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    ResultSetExtractorImpl<Customer> rse = JdbcTemplateMapperFactory.newInstance()
            .addKeys("id", "orders_id")
            .newResultSetExtractor(Customer.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        StringUtils.line("without PreparedStatement");

        Collection<Customer> customers1 = jdbcTemplate
                .query("select c.*, o.id as orders_id, o.sku as orders_sku " +
                        " from customers c left join orders o on c.id = o.customer_fk order by o.id", rse);
        customers1.forEach(log::info);

        StringUtils.line("PreparedStatement");

        List<Customer> customers = jdbcTemplate.query(connection ->
                connection.prepareStatement("select c.*, o.id as orders_id, o.sku as orders_sku " +
                        " from customers c left join orders o on c.id = o.customer_fk order by o.id"), rse);

        customers.forEach(log::info);

    }

}


@org.springframework.core.annotation.Order(5)
@Log4j2
@Component
class JdbcTemplateWriter implements ApplicationRunner {
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRepo customerRepo;

    JdbcTemplateWriter(JdbcTemplate jdbcTemplate, CustomerRepo customerRepo) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRepo = customerRepo;
    }

    private Long insert(String name, String email) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            //PreparedStatement tells sql engine to cache this statement
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into customers(name, email) values (?,?) ", new String[]{"id"});
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            return preparedStatement;
        }, generatedKeyHolder);

        long idOfNewCustomer = generatedKeyHolder.getKey().longValue();
        return idOfNewCustomer;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        Long newCustomerId = insert("new Customer", "new Customer@gmail.com");

        StringUtils.line();
        StringUtils.line();
        customerRepo.findById(newCustomerId).ifPresent(log::info);
    }

}


@Log4j2
abstract class StringUtils {
    public static void line() {
        log.info("=======================================");
    }

    public static void line(String header) {
        log.info("=================" + header + "======================");
    }
}