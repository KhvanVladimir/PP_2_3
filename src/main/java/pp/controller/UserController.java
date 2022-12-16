package pp.controller;

import net.bytebuddy.matcher.StringMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pp.dao.UserDao;
import pp.dao.UserDaoImp;
import pp.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import pp.service.UserService;
import pp.service.UserServiceImp;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService a;

    public UserController() {
    }

    @GetMapping ()
    public String getUsers(Model model){
        model.addAttribute("users", a.listUsers());
        return "users";
    }

    @GetMapping ("/{id}")
    public String getUser(@PathVariable("id") int id, Model model){
        model.addAttribute("user", a.getUser(id));
        return "user";
    }

    @GetMapping ("/new")
    public String getNew(Model model){
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping()
    public String create(@ModelAttribute User user){
        a.add(user);
        return "redirect:/users/";
    }

    @GetMapping ("/{id}/edit")
    public String editUser(@PathVariable("id") int id, Model model){
        model.addAttribute("user", a.getUser(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String edit(@ModelAttribute("user") User user) {
        a.update(user);
        return "redirect:/users/";
    }

    @GetMapping ("/{id}/delete")
    public String deleteUser(@PathVariable("id") int id, Model model){
        a.delete(id);
        return "redirect:/users/";
    }
}
