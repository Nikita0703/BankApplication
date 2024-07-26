package com.example.bankaccounts.aspects;

import com.example.bankaccounts.entity.User;
import com.example.bankaccounts.entity.enums.ERole;
import com.example.bankaccounts.payload.response.MessageResponse;
import com.example.bankaccounts.service.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AccessControlAspectUser {
    private final UserService userService;
    @Pointcut("execution(* com.example.bankaccounts.controller.UserController.findByEmail(..)) || " +
            "execution(* com.example.bankaccounts.controller.UserController.findByFio(..)) || " +
            "execution(* com.example.bankaccounts.controller.UserController.findByTel(..)) ||" +
            "execution(* com.example.bankaccounts.controller.UserController.filterForBirthday(..))")
    public void serviceMethods() {}

    @Around("serviceMethods()")
    public Object checkAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User user = userService.getUserByUsername(authentication.getName());
            boolean flag = false;

            for (ERole role:user.getRoles()) {
                if ("ROLE_ADMIN".equals(role.name())) {
                    flag = true;
                }
            }

            if(!flag){
                return new MessageResponse("You dont have access");
            }
        }
        return joinPoint.proceed();
    }
}
