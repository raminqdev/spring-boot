package com.raminq.security.configuration.interceptor;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class RequestProcessingTimeInterceptor implements HandlerInterceptor {

    private final Logger logger;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startTime = (long) request.getAttribute("startTime");
        long requestProcessDuration = System.currentTimeMillis() - startTime;
        if (requestProcessDuration > 500) {
            logger.info("Request URL::" + request.getRequestURL().toString() + ":: Time Taken=" + requestProcessDuration);
        }
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
