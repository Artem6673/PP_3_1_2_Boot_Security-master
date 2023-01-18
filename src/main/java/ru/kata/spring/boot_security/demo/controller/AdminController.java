package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserValidator userValidator;
    private final UserService userService;

    private final RoleService roleService;



    public AdminController(UserValidator userValidator, UserService userService, RoleService roleService) {
        this.userValidator = userValidator;
        this.userService = userService;
        this.roleService = roleService;
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
        System.out.println(roleService.getAllRoles());
        System.out.println(userService.getAllUsers());
        return "index";

    }
    @GetMapping("/new")
    public String newUser(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("roles",roleService.getAllRoles());
        model.addAttribute("users",userService.getAllUsers());

        return "new";

    }
    @PostMapping
    public String create(Model model, @ModelAttribute("user") User user, BindingResult bindingResult){
        userValidator.validate(user,bindingResult);

        if (bindingResult.hasErrors()) {
            return "/new";
        }
        userService.saveUser(user);
        model.addAttribute("users", userService.getAllUsers());

        return "redirect:/admin";

    }
    @GetMapping("/{id}/edit")
    public String edit(Model model,@PathVariable("id") long id){
        model.addAttribute("user",userService.getUserById(id));
        model.addAttribute("roles",roleService.getAllRoles());
        model.addAttribute("users", userService.getAllUsers());
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

