package org.example.rolestask.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.example.rolestask.web.model.response.RoleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class WebLogger
 */
@Slf4j
@Aspect
@Component
public class WebLogger {
    Logger logger = LoggerFactory.getLogger(WebLogger.class);

    @Pointcut("execution(public * org.example.rolestask.web.controller.RoleController.*(..))")
    public void callAtUserController() { }

    @Pointcut("execution(public * org.example.rolestask.web.controller.RoleController.findByIdOrDie(..))")
    public void findByIdOrDie() { }

    @Pointcut("execution(public * org.example.rolestask.web.controller.RoleController.commit(..))")
    public void commit() { }

    @Pointcut("execution(public * org.example.rolestask.web.controller.RoleController.findAll())")
    public void findAll() { }

    @Before("callAtUserController()")
    public void beforeCallAtMethod(JoinPoint jp) {
        LocalDateTime time = LocalDateTime.now();
        String args = "";

        if(jp.getArgs().length>0){
            args = " insert argument "+ Arrays.toString(jp.getArgs());
        }
        logger.info(jp.toShortString() +  args +" time-" +time.getHour()+":"+time.getMinute());
    }

    @AfterReturning(
            pointcut = "findByIdOrDie()",
            returning = "result")
    public void findByIdOrDie(RoleResponse result) {
        logger.info("findUserById - "+result.toString());
    }

    @AfterReturning(
            pointcut = "commit()",
            returning = "result")
    public void commit(RoleResponse result) {
        logger.info("commit - "+result.toString());
    }

    @AfterReturning(
            pointcut = "findAll()",
            returning = "result")
    public void findAll(List<RoleResponse> result) {
        logger.info("findAll - "+result.toString());
    }
}
