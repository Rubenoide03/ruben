package dev.ruben.web.funkos.controllers;

import dev.ruben.funkos.services.FunkoService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/*
@Controller
@RequestMapping("/v1/funkos")
@Slf4j
public class WebFunkoController {
    private final FunkoService funkoService;
    private final MessageSource messageSource;
    @Autowired
    public WebFunkoController(FunkoService funkoService, MessageSource messageSource) {
        this.funkoService = funkoService;
        this.messageSource = messageSource;
    }
    @GetMapping("/login")
    public String login(HttpSession session) {
        log.info("Login GET");
        if (isLoggedAndSessionIsActive(session)) {
            log.info("Si está logueado volvemos al index");
            return "redirect:/productos";
        }
        return "productos/login";
    }


}

 */
