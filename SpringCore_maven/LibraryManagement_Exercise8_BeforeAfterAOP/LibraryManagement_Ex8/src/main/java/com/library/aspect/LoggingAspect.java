package com.library.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Cross-cutting concern: logs a message before and after every
 * method call in the service and repository layers, without
 * modifying those classes' code.
 */
@Aspect
public class LoggingAspect {

    // Reusable pointcut expression: every method in service/repository packages
    private static final String TARGET_METHODS =
            "execution(* com.library.service..*(..)) || execution(* com.library.repository..*(..))";

    /**
     * Advice that runs BEFORE the target method executes.
     */
    @Before(TARGET_METHODS)
    public void logBeforeMethod(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        System.out.println("[AOP - BEFORE] About to execute: " + className + "." + methodName + "()");
    }

    /**
     * Advice that runs AFTER the target method completes
     * (whether it returns normally or throws an exception).
     */
    @After(TARGET_METHODS)
    public void logAfterMethod(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        System.out.println("[AOP - AFTER] Finished executing: " + className + "." + methodName + "()");
    }
}
