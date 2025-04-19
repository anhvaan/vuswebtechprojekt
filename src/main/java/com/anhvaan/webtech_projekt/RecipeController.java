package com.anhvaan.webtech_projekt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecipeController {

    @GetMapping("/hello")
    public String hello() {
        return "Willkommen zur Fojo-App!";
    }

    @GetMapping("/recipes")
    public List<String> getAllRecipes() {
        return List.of("Spaghetti Bolognese", "Tomatensuppe", "Veganes Curry");
    }
}
