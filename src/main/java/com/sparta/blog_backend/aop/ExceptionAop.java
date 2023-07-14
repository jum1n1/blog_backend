package com.sparta.blog_backend.aop;

import com.sparta.blog_backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j(topic = "ExceptionAop")
@Aspect
@Component
@RequiredArgsConstructor
public class ExceptionAop {

    private final UserService userService;

    @Pointcut("execution(* com.sparta.blog_backend.user.controller.*(..))")
    private void user() {}

    @Pointcut("execution(* com.sparta.blog_backend.user.jwt.*(..))")
    private void jwtAuthorizationFilter() {}

    @Pointcut("execution(* com.sparta.blog_backend.blog.controller.*(..))")
    private void blog() {}

    @Around("user()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
         return null;
    }
}
