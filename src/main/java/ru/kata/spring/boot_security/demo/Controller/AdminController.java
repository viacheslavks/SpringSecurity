package ru.kata.spring.boot_security.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/")
    public String showAllUsers(Model model) {
        model.addAttribute("allUsers", userService.findAll());
        return "admin";

    }

    @GetMapping("/addForm")
    public String addForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "/new";

    }

    @PostMapping(value = "/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping(value = "/editForm")
    public String editForm(@RequestParam("userId") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
//        model.addAttribute("id", id);
        return "update";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/";
    }
//    @ModelAttribute("id") Long id

    @GetMapping(value = "/deleteUser")
    public String deleteUser(@RequestParam("userId") Long id) {
        userService.delete(id);
        return "redirect:/";
    }
}