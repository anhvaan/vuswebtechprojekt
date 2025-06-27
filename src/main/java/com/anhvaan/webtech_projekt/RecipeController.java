package com.anhvaan.webtech_projekt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "http://localhost:5173")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public List<Recipe> getAllRecipes() {
        // Für Demo - alle Rezepte ohne User-Filter
        return recipeService.getAllRecipesByUserId("1");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        Optional<Recipe> recipe = recipeService.getRecipeById(id);
        return recipe.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        return recipeService.createRecipe(recipe);
    }

    @PutMapping("/{id}")
    public Recipe updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        return recipeService.updateRecipe(id, recipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/favorite")
    public ResponseEntity<Recipe> toggleFavorite(@PathVariable Long id, @RequestBody Map<String, Boolean> favoriteData) {
        Recipe recipe = recipeService.toggleFavorite(id, favoriteData.get("isFavorite"));
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/favorites")
    public List<Recipe> getFavoriteRecipes(@RequestParam String userId) {
        return recipeService.getFavoriteRecipes(userId);
    }
}