package com.raminq.security.configuration.aspect;

import com.raminq.security.domain.dto.enums.PermissionLevel;
import com.raminq.security.domain.entity.security.Permission;
import com.raminq.security.domain.entity.security.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.raminq.security.domain.dto.ErrorCodes.USER_HAS_NO_PERMISSION_TO_VIEW_THIS_RESOURCE;
import static com.raminq.security.domain.dto.ErrorCodes.USER_NOT_FOUND_IN_SECURITY_CONTEXT;

/*
Aspect : Authorization is our aspect
 */
@Aspect
@Component
public class PermissionAuthorizeAspect {

    //Pointcut : PermissionAuthorize annotation is our pointcut(says where this aspect should be enabled)
    @Pointcut("@annotation(PermissionAuthorize)")
    private void executePermissionAuthorizeOnMethods() {
    }

    @Pointcut("@within(PermissionAuthorize)")
    private void executePermissionAuthorizeOnClasses() {
    }

    @Before("executePermissionAuthorizeOnMethods() || executePermissionAuthorizeOnClasses()")
    public void authorize(JoinPoint joinPoint) throws Throwable {
        //Advice : body of this method is our advice

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            throw new BadCredentialsException(String.valueOf(USER_NOT_FOUND_IN_SECURITY_CONTEXT));
        }

        Stream<String> permissions = Arrays.stream(((MethodSignature) joinPoint.getSignature())
                .getMethod().getAnnotation(PermissionAuthorize.class).value())
                .mapToObj(p -> {
                    PermissionLevel permissionLevel = PermissionLevel.getByValue(p);
                    return permissionLevel != null ? permissionLevel.toString() : "";
                }).filter(StringUtils::hasText).distinct();

        Set<String> allowedPermissions = user.getRole().getPermissions().stream().map(Permission::getName).collect(Collectors.toSet());

        boolean authorized = permissions.allMatch(allowedPermissions::contains);
        if (!authorized) {
            throw new AccessDeniedException(String.valueOf(USER_HAS_NO_PERMISSION_TO_VIEW_THIS_RESOURCE));
        }
    }
}
