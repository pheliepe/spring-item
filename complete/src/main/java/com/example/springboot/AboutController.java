package com.example.springboot;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/* Dieser Controller demonstriert, wie html mittels eines 
 * Templates erzeugt werden kann.
 * Das Template liegt in src/main/resources/templates/aboutTemplate.html
 */
@Controller 
public class AboutController {    
    @GetMapping("/about")
    public String about(Model model) {        
        model.addAttribute(
            "time", 
            new java.util.Date().toString());                
        return "aboutTemplate";
    }
}
