package com.webapp.project.Controllers;

import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.webapp.project.models.UserDto;
import com.webapp.project.models.UsersTable;
import com.webapp.project.models.repositories.UserRepository;

import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping("/register")
    public String register(Model model) {
        UserDto userDto = new UserDto();
        userDto.setStatus("ACTIVE");
        model.addAttribute("userDto", userDto);
        model.addAttribute("success", false);
        // return "clients/Clients_create";
        return "register";
    }

    @PostMapping("/register")
    public String register(Model model, @Valid @ModelAttribute UserDto userDto, BindingResult result) {

        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            result.addError(new FieldError("userDto", "confirmPassword", userDto.getConfirmPassword(),
                    false, null, null, "Password does not match"));

        }

        if (userRepo.findByEmail(userDto.getEmail()) != null) {
            result.addError(new FieldError("userDto", "email", userDto.getEmail(),
                    false, null, null, "Email already exists"));
        }
        if (result.hasErrors()) {
            System.out.println(result);
            return "register";
        }
        var bCryptPasswordEncoder = new BCryptPasswordEncoder();
        UsersTable newUser = new UsersTable();
        newUser.setFirstName(userDto.getFirstName());
        newUser.setLastName(userDto.getLastName());
        newUser.setEmail(userDto.getEmail());
        newUser.setPhone(userDto.getPhone());
        newUser.setAddress(userDto.getAddress());
        newUser.setStatus(userDto.getStatus());
        newUser.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        // newUser.setPassword(userDto.getPassword());
        newUser.setCreatedAt(new Date());

        userRepo.save(newUser);
        model.addAttribute("success", true);

        return "register";
    }

}
