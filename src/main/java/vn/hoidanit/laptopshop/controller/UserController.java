package vn.hoidanit.laptopshop.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService , UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }
     
    
    @RequestMapping("/")
    public String getHomePage(Model model) {
        model.addAttribute("eric", "test");
        return "hello";
    }
    
    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        return "admin/user/table-user";
    }
    @RequestMapping("/admin/user/create") //GET
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create",method = RequestMethod.POST)
    public String createUserPage(Model model ,@ModelAttribute ("newUser") User user) {
        System.out.println(user);
        this.userService.handleSaveUser(user);
        return "hello";
    }
}
