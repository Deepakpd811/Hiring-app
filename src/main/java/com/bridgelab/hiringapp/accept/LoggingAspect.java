package com.bridgelab.hiringapp.accept;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    // Before any method in service package
    @Before("execution(* com.bridgelab.hiringapp.service..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info(" Entering method: {} with arguments: {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    // After method returns successfully
    @AfterReturning(pointcut = "execution(* com.bridgelab.hiringapp.service..*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info(" Method returned: {} with result: {}", joinPoint.getSignature(), result);
    }

    // After method throws exception
    @AfterThrowing(pointcut = "execution(* com.bridgelab.hiringapp.service..*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        log.error("Method threw exception: {} with message: {}", joinPoint.getSignature(), ex.getMessage());
    }

    // After any method (whether success or exception)
    @After("execution(* com.bridgelab.hiringapp.service..*(..))")
    public void logAfter(JoinPoint joinPoint) {
        log.info(" Exiting method: {}", joinPoint.getSignature());
    }
}
