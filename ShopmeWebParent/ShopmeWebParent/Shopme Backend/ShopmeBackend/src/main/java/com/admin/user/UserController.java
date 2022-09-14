package com.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public String listAll(Model model) {
        List<User> listUsers = userService.listAllUsers();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        List<Role> roleList = userService.listAllRoles();
        User user = new User();
        user.setEnabled(true);
        model.addAttribute("user", user);
        model.addAttribute("roleList", roleList);
        model.addAttribute("pageTitle", "Create New User");
        return "user_form";
    }

    @PostMapping("/users/save/")
    public String saveUser(User user, RedirectAttributes redirectAttributes) {
        userService.save(user);

        redirectAttributes.addAttribute("message", "The user has been saved successfully");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.get(id);
            List<Role> roleList = userService.listAllRoles();
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
            model.addAttribute("roleList", roleList);

            return "user_form";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/users";
        }


    }

}
