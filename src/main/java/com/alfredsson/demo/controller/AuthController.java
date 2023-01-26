package com.alfredsson.demo.controller;

import com.alfredsson.demo.repo.AccountRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("login")
    public String login(HttpSession session, @RequestParam String username, @RequestParam String password) {

        if(AccountRepository.login(username, password)){
            session.setMaxInactiveInterval(60*30);
            session.setAttribute("username", username);

            return "redirect:/invoice/read";
        }  else {

            Object loginAttempts = session.getAttribute("login-attempts");
            if (loginAttempts == null) {
                loginAttempts = 0;
            }

            session.setAttribute("login-attempts", (int) loginAttempts + 1);

            return "redirect:error/login?attempts=" + ((int) loginAttempts + 1);
        }
    }

    @PostMapping("logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/index.html";
    }

}
