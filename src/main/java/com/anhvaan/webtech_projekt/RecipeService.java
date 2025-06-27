package com.anhvaan.webtech_projekt;

import com.anhvaan.webtech_projekt.Recipe;
import com.anhvaan.webtech_projekt.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    public List<Recipe> getAllRecipesByUserId(String userId) {
        return recipeRepository.findByUserId(userId);
    }

    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Recipe updateRecipe(Long id, Recipe recipe) {
        recipe.setId(id);
        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    public List<Recipe> getFavoriteRecipes(String userId) {
        return recipeRepository.findByUserIdAndIsFavoriteTrue(userId);
    }
}