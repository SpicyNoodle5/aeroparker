package aeroparker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/register")
public class RegisterController {
    
    @Autowired
    private RegisterService service;

    @GetMapping
    public String form(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }
    
    @PostMapping
    public String register(@ModelAttribute Customer customer, Model model) {
        model.addAttribute("customer", customer);

        String outcome = service.register(customer);
        if(outcome.equals("success")) {
            return "success";
        }

        model.addAttribute("error", "Error: " + outcome);
        return "register";
    }

}
