package com.webapp.project.Controllers;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.webapp.project.models.UserDto;
import com.webapp.project.models.UsersTable;
import com.webapp.project.models.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/users")
public class ClientController {
    @Autowired
    private UserRepository userRepo;


    @GetMapping({"", "/"})
    public String getClients(Model model) {
        var users = userRepo.findAll(Sort.by(Sort.Direction.DESC, "Id"));
        model.addAttribute("users", users);
        return "users/Users_index";
    }

    @GetMapping("/create")
    public String createClient(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("UserDto", userDto);
        return "users/Users_create";
    }
    @PostMapping("/create")
    public String createClient(@Valid @ModelAttribute UserDto userDto, BindingResult result) {
        if (userRepo.findByEmail(userDto.getEmail()) != null) {
            result.addError(new FieldError("UserDto", "email", userDto.getEmail(),
             false, null, null, "Email already exists"));
        }
        if(result.hasErrors()) {
            return "users/Users_create";
        }
        UsersTable user = new UsersTable();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhone(userDto.getPhone());
        user.setAddress(userDto.getAddress());
        user.setStatus(userDto.getStatus());
        //new Date() is used by java.util.Date and new java.sql.Date() is used by java.sql.Date
        user.setCreatedAt(new Date());//their are diffrent types of data imports, java.util.Date is used here. the other one is java.sql.Date


        userRepo.save(user);
        return "redirect:/users";
    }
    
    //@GetMapping("/edit")  
    @GetMapping("/edit/{id}")
    public String editClient(Model model, @PathVariable int id /*@RequestParam int id*/) {
        UsersTable user = userRepo.findById(id).orElse(null);//Varible name mattters here, it should be the same as the one in the repository
        // for .orElse(null) to work, the return type of findById should be Optional, meaning that method findById should not exist in the repository
        if(user == null) {
            return "redirect:/users";
        }

        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setPhone(user.getPhone());
        userDto.setAddress(user.getAddress());
        userDto.setStatus(user.getStatus());

        model.addAttribute("user", user);//attribute name should be the same as the one in the html file
        model.addAttribute("userDto", userDto);

        return "users/Users_edit";
    }


    @PostMapping("/edit/{id}")
    public String editClient(Model model, /*@RequestParam int id*/ @PathVariable int id, 
    @Valid @ModelAttribute UserDto userDto, BindingResult result) {
        UsersTable user = userRepo.findById(id) .orElse(null);
        if(user == null) {
            return "redirect:/users";
        }

        model.addAttribute("user", user);
        
        if(result.hasErrors()) {
            return "users/Users_edit";
        }

        user.setFirstName(userDto.getFirstName());//
        user.setLastName(userDto.getLastName());//
        user.setEmail(userDto.getEmail());//
        user.setPassword(userDto.getPassword());//
        user.setPhone(userDto.getPhone());//
        user.setAddress(userDto.getAddress());//
        user.setStatus(userDto.getStatus());//


        try {
            userRepo.save(user);

        } catch (Exception e) {
            result.addError(new FieldError("userDto", "email", userDto.getEmail(),
             false, null, null, "Email already exists"));

            return "users/Users_edit";
        }

        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable int id) {
        UsersTable User = userRepo.findById(id).orElse(null);
        if(User != null) {
            userRepo.delete(User);
        }
        return "redirect:/users";
    }



    
}
