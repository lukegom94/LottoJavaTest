package com.lottoland.assignment.aop;

import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;

public class Logger {

    public static void log(Class clazz, LogLevel logLevel, String message) {
        var logger = LoggerFactory.getLogger(clazz);
        switch (logLevel) {
            case DEBUG -> logger.debug(message);
            case INFO -> logger.info(message);
            case WARN -> logger.warn(message);
            case ERROR -> logger.error(message);
            default -> logger.debug(message);
        }
    }
}