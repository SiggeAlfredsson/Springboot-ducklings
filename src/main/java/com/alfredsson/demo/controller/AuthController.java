package com.alfredsson.demo.controller;

import com.alfredsson.demo.exception.InvalidPasswordOrUsernameException;
import com.alfredsson.demo.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private AccountService accountService;

    public AuthController() {
        accountService = new AccountService();
    }

    @ExceptionHandler(InvalidPasswordOrUsernameException.class)
    public String handleInvalidPassword(InvalidPasswordOrUsernameException ex, HttpSession session, HttpServletRequest req) {

        Object loginAttempts = session.getAttribute("loginAttempts");
        if (loginAttempts == null) {
            loginAttempts = 0;
        }

        session.setAttribute("loginAttempts", (int) loginAttempts + 1);

        return "redirect:login?error=" + ex.getMessage();
    }


    @PostMapping("login")
    public String login(HttpSession session, RedirectAttributes redirect, @RequestParam String username, @RequestParam String password) throws SQLException {

        if (session.getAttribute("username") != null) {

            return "redirect:/invoice/read";
        } else {

            if (accountService.usernameExists(username) && password.equals("") && accountService.getPasswordByUsername(username).equals("")) {
                    System.out.println("wrong");
                    session.setMaxInactiveInterval(60 * 30);
                    session.setAttribute("username", username);
                    return "addPasswordPage";
                }

                if (accountService.login(username, password)) {
                    session.setMaxInactiveInterval(60 * 30);
                    session.setAttribute("username", username);

                    return "redirect:/invoice/read";
                } else {
                    throw new InvalidPasswordOrUsernameException("Invalid login attempt", username);

                }
            }
    }






   @GetMapping("login")
    public String showLoginPage(HttpSession session) {
        if (session.getAttribute("username") != null) {
            return "redirect:/invoice/read";
        }
        return "loginPage";
    }


    @PostMapping("addPassword")
    public String addPassword(HttpSession session, @RequestParam String password, @RequestParam String confirmpassword) {
        if(confirmpassword.equals(password)){
            String username = (String) session.getAttribute("username");
            accountService.updatePassword(username, password);
            return "redirect:/invoice/read";
        }
        System.out.println("afsdasfd");
        return "redirect:error";
    }

    @PostMapping("logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/index.html";
    }

}
