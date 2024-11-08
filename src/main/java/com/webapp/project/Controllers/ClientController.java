package com.webapp.project.Controllers;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import com.webapp.project.models.Clients;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.webapp.project.models.ClientDto;
import com.webapp.project.models.repositories.ClientRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientRepository clientRepo;


    @GetMapping({"", "/"})
    public String getClients(Model model) {
        var clients = clientRepo.findAll(Sort.by(Sort.Direction.DESC, "Id"));
        model.addAttribute("clients", clients);
        return "clients/Clients_index";
    }

    @GetMapping("/create")
    public String createClient(Model model) {
        ClientDto clientDto = new ClientDto();
        model.addAttribute("clientDto", clientDto);
        return "clients/Clients_create";
    }
    @PostMapping("/create")
    public String createClient(@Valid @ModelAttribute ClientDto clientDto, BindingResult result) {
        if (clientRepo.findByEmail(clientDto.getEmail()) != null) {
            result.addError(new FieldError("clientDto", "email", clientDto.getEmail(),
             false, null, null, "Email already exists"));
        }
        if(result.hasErrors()) {
            return "clients/Clients_create";
        }
        Clients client = new Clients();
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setEmail(clientDto.getEmail());
        client.setPassword(clientDto.getPassword());
        client.setPhone(clientDto.getPhone());
        client.setAddress(clientDto.getAddress());
        client.setStatus(clientDto.getStatus());
        //new Date() is used by java.util.Date and new java.sql.Date() is used by java.sql.Date
        client.setCreatedAt(new Date());//their are diffrent types of data imports, java.util.Date is used here. the other one is java.sql.Date


        clientRepo.save(client);
        return "redirect:/clients";
    }
    
    //@GetMapping("/edit")  
    @GetMapping("/edit/{id}")
    public String editClient(Model model, @PathVariable int id /*@RequestParam int id*/) {
        Clients clients = clientRepo.findById(id).orElse(null);//Varible name mattters here, it should be the same as the one in the repository
        // for .orElse(null) to work, the return type of findById should be Optional, meaning that method findById should not exist in the repository
        if(clients == null) {
            return "redirect:/clients";
        }

        ClientDto clientDto = new ClientDto();
        clientDto.setFirstName(clients.getFirstName());
        clientDto.setLastName(clients.getLastName());
        clientDto.setEmail(clients.getEmail());
        clientDto.setPassword(clients.getPassword());
        clientDto.setPhone(clients.getPhone());
        clientDto.setAddress(clients.getAddress());
        clientDto.setStatus(clients.getStatus());

        model.addAttribute("clients", clients);//attribute name should be the same as the one in the html file
        model.addAttribute("clientDto", clientDto);

        return "clients/Clients_edit";
    }


    @PostMapping("/edit/{id}")
    public String editClient(Model model, /*@RequestParam int id*/ @PathVariable int id, 
    @Valid @ModelAttribute ClientDto clientDto, 
    BindingResult result) {
        Clients clients = clientRepo.findById(id) .orElse(null);
        if(clients == null) {
            return "redirect:/clients";
        }
        model.addAttribute("clients", clients);
        
        if(result.hasErrors()) {
            return "clients/Clients_edit";
        }
        clients.setFirstName(clientDto.getFirstName());
        clients.setLastName(clientDto.getLastName());
        clients.setEmail(clientDto.getEmail());
        clients.setPassword(clientDto.getPassword());
        clients.setPhone(clientDto.getPhone());
        clients.setAddress(clientDto.getAddress());
        clients.setStatus(clientDto.getStatus());
        try {
            clientRepo.save(clients);

        } catch (Exception e) {
            result.addError(new FieldError("ClientDto", "email", clientDto.getEmail(),
             false, null, null, "Email already exists"));

            return "clients/Clients_edit";
        }

        return "redirect:/clients";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable int id) {
        Clients clients = clientRepo.findById(id).orElse(null);
        if(clients != null) {
            clientRepo.delete(clients);
        }
        return "redirect:/clients";
    }



    
}
