package com.bolshakovk.favthingsservice.aop;

import com.bolshakovk.favthingsservice.dto.LogDto;
import com.bolshakovk.favthingsservice.service.LogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAdvice {
    private final LogService logService;
    @Pointcut("@annotation(com.bolshakovk.favthingsservice.annotation.Loggable)")
    public void loggableMethods() {
    }
    @Pointcut("execution(* com.bolshakovk.favthingsservice.controllers.*.*(..))")
    public void allMethodsPackageController() {
    }
    @Around("allMethodsPackageController()")
    public Object applicationLogger(ProceedingJoinPoint joinPoint) throws JsonProcessingException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ObjectMapper mapper = new ObjectMapper();
        String className = joinPoint.getTarget().getClass().toString();
        String methodName = joinPoint.getSignature().getName();
        Object[] array = joinPoint.getArgs();
        log.info("Вызван метод : " + className + ":" + methodName + "с аргументами" + mapper.writeValueAsString(array) +
                ": имя пользователя: " + username);
        logService.addLogInDB(new LogDto(LocalDateTime.now(),
                        "INFO",
                        log.getName() + "." + methodName,
                        "Вызван метод : " + className + ":" + methodName + "с аргументами" +
                                mapper.writeValueAsString(array),
                        username));
        Object obj = null;
        try {
            obj = joinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        log.info(className + ":" + methodName + "() " + "Response: " + mapper.writeValueAsString(obj) +
                ": имя пользователя: " + username);
        logService.addLogInDB(new LogDto(LocalDateTime.now(),
                "INFO",
                log.getName() + "." + methodName,
                className + ":" + methodName + "Response: " +
                        mapper.writeValueAsString(obj),
                username)
        );
        return obj;
    }
}
