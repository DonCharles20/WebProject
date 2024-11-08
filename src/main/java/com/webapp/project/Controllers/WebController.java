package com.webapp.project.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*@Controller is used to mark a class as a Spring MVC controller. 
It is typically used in combination with @RequestMapping to handle web requests and return a view (usually a JSP or HTML page).
@RestController is a specialized version of @Controller. It is a convenience annotation that combines @Controller and @ResponseBody, 
meaning that it handles web requests and returns the response body directly in JSON or XML format, rather than rendering a view.
*/

@Controller//there are two types of controllers in Spring: @Controller and @RestController
public class WebController {  // Rename the class to avoid conflicts with Spring's built-in `Controller` class
    @RequestMapping("/webproject") // Maps to http://localhost:8080/webproject
    public String hello(){
        return "Hello World";  // This will display raw text since it’s not a view name
    }
    
    @GetMapping("/") // Maps to http://localhost:8080/
    public String home(Model model) {
        //model.addAttribute("name", "to");
        return "index"; // Looks for src/main/resources/templates/index.html
    }

}

