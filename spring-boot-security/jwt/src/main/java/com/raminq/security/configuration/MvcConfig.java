package com.raminq.security.configuration;

import com.raminq.security.configuration.interceptor.RequestProcessingTimeInterceptor;
import com.raminq.security.service.common.PropertiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class MvcConfig implements WebMvcConfigurer {

    private final RequestProcessingTimeInterceptor requestProcessingTimeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestProcessingTimeInterceptor);
    }

    // Remove the default ROLE_ prefix
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }


    @Bean
    public PropertiesService properties(@Value("${jwt.Secret}") String secret,
                                        @Value("${jwt.ExpirationMs}") String expirationMs,
                                        @Value("${jwt.RefreshExpirationMs}") String refreshExpirationMs) {
        return new PropertiesService(secret, Long.valueOf(expirationMs), Long.valueOf(refreshExpirationMs));
    }
}
