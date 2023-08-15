package com.lottoland.assignment.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
public class LoggableAspect {

    @Around("@annotation(Loggable)")
    public void logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        Loggable loggableMethod = method.getAnnotation(Loggable.class);
        LogLevel logLevel = loggableMethod.value();

        Object[] args = joinPoint.getArgs();
        StringBuilder sb = new StringBuilder();

        sb.append("Starting method " + method.getName() + "() with parameters: " + Arrays.toString(args));
        Object result = joinPoint.proceed();
        sb.append(". Return value: " + ((Objects.isNull(result)) ? " void" : result));

        Logger.log(joinPoint.getSignature().getDeclaringType(), logLevel, sb.toString());
    }
}
