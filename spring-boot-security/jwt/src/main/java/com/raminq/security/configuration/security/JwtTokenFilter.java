package com.raminq.security.configuration.security;

import com.raminq.security.domain.entity.security.Permission;
import com.raminq.security.domain.entity.security.Role;
import com.raminq.security.domain.entity.security.User;
import com.raminq.security.repository.security.UserRepo;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.List.of;
import static java.util.Optional.ofNullable;
import static org.springframework.util.StringUtils.hasText;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepo userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!hasText(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        final String token = header.split(" ")[1].trim();
        final Claims claims = jwtTokenUtil.validateAndParseToken(token);
        if (claims == null) {
            chain.doFilter(request, response);
            return;
        }

        final User user = User.builder()
                .id(Long.valueOf(claims.getSubject().split(",")[0]))
                .username(claims.getSubject().split(",")[1])
                .fullName(String.valueOf(claims.get("fullName")))
                .role(Role.builder()
                        .name(String.valueOf(claims.get("role")))
                        .permissions((Set<Permission>) claims.get("permissions", ArrayList.class).stream()
                                        .map(pName -> Permission.builder().name(String.valueOf(pName)).build()).collect(Collectors.toSet())
                        ).build())
                .build();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user, null,
                ofNullable(user).map(UserDetails::getAuthorities).orElse(of())
        );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
