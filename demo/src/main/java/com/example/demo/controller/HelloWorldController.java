package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@RestController
public class HelloWorldController {

    @GetMapping("/user")
    public String showForm(Model model) {
        // Create a new User object to bind the form data
        User user = new User();
        model.addAttribute("user", user);
        return "form";
    }

    @PostMapping("/user")
    public String submitForm(@ModelAttribute("user") User user, Model model) {
        // The user object contains the form data
        model.addAttribute("user", user);
        return "success";
    }

}