package ru.kata.spring.boot_security.demo.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RegistrationService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserValidator userValidator;
    private final UserService userService;
    private final RegistrationService registrationService;



    public AdminController(UserValidator userValidator, UserService userService, RegistrationService registrationService) {
        this.userValidator = userValidator;
        this.userService = userService;
        this.registrationService = registrationService;
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("users", userService.getAllUsers());
        return "show";
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }
    @GetMapping("/new")
    public String newUser(Model model){
        model.addAttribute("user", new User());

        return "new";

    }
    @PostMapping
    public String create(@ModelAttribute("user") User user, BindingResult bindingResult){
        userValidator.validate(user,bindingResult);

        if (bindingResult.hasErrors()) {
            return "/new";
        }
        registrationService.register(user);

        return "redirect:/admin";

    }
    @GetMapping("/{id}/edit")
    public String edit(Model model,@PathVariable("id") long id){
        model.addAttribute("user",userService.getUserById(id));
        return "edit";

    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user,@PathVariable("id") long id){
        userService.updateUser(id,user);
        return "redirect:/admin";
    }
    @DeleteMapping("/{id}")
    private String delete(@PathVariable("id") long id){
        userService.deleteUser(id);
        return "redirect:/admin";
    }


}

