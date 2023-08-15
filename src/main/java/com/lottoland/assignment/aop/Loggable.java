package com.lottoland.assignment.aop;

import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Component
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Loggable {
    LogLevel value();
}