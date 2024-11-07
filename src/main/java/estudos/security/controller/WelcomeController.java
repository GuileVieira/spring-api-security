package estudos.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping
    public String welcome() {
        return "Hello, World";
    }

    @GetMapping("/users")
    public String users() {
        return "Authorized User";
    }

    @GetMapping("/managers")
    public String managers() {
        return "Authorized Manager";
    }
}
