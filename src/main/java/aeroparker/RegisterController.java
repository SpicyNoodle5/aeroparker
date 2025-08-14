package aeroparker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegisterController {
    
    @Autowired
    private RegisterService service;

    @GetMapping("/register")
    public String form(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }
    
    @PostMapping("/register")
    public String register(@ModelAttribute Customer customer, Model model) {
        model.addAttribute("customer", customer);

        boolean success = service.register(customer);
        if(success) {
            return "success";
        }
        return "register";
    }

}
