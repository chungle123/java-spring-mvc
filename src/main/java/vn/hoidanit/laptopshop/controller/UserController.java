package vn.hoidanit.laptopshop.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UserService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        List<User> users=this.userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/user/table-user";
    }
    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model,@PathVariable long id) {
        User user=this.userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        return "admin/user/show";
    }
    @RequestMapping("/admin/user/create") //GET
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }
   @RequestMapping("/admin/user/update/{id}") //GET
    public String getUpdateUserPage(Model model,@PathVariable long id) {
        User user=this.userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user/update";
    }
    @PostMapping(value = "/admin/user/update")
    public String postUpdateUserPage(Model model ,@ModelAttribute ("user") User user) {
        User currentUser=this.userService.getUserById(user.getId());
        if(currentUser !=null){
            currentUser.setAddress(user.getAddress());
            currentUser.setFullName(user.getFullName());
            currentUser.setPhone(user.getPhone());
            this.userService.handleSaveUser(currentUser);
        }
        return "redirect:/admin/user";
    }
    @RequestMapping(value = "/admin/user/create",method = RequestMethod.POST)
    public String postUpdateUser(Model model ,@ModelAttribute ("newUser") User user) {
        this.userService.handleSaveUser(user);
        return "redirect:/admin/user";
    }
    @GetMapping(value = "/admin/user/delete/{id}")
    public String deleteUserPage(Model model ,@PathVariable long id) 
    {
        User user=new User();
        user.setId(id);
        model.addAttribute("id", id);
         model.addAttribute("user", user);
        return "admin/user/delete";
    }
    @PostMapping(value = "/admin/user/delete")
    public String postDeleteUser(Model model ,@ModelAttribute ("user") User user) {
        this.userService.deleteUser(user.getId());
        return "redirect:/admin/user";
    }
}
