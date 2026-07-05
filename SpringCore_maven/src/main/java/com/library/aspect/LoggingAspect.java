package com.library.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Cross-cutting concern: logs the execution time of every method
 * in the service and repository layers, without modifying those classes.
 */
@Aspect
public class LoggingAspect {

    /**
     * Wraps every method call inside com.library.service and com.library.repository
     * packages (and their sub-packages) and logs how long each call took.
     */
    @Around("execution(* com.library.service..*(..)) || execution(* com.library.repository..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        long start = System.nanoTime();

        Object result = joinPoint.proceed(); // actually invoke the target method

        long end = System.nanoTime();
        double durationMs = (end - start) / 1_000_000.0;

        System.out.printf("[AOP] %s.%s() executed in %.3f ms%n",
                className, methodName, durationMs);

        return result;
    }
}
