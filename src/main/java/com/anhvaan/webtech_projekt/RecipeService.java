package com.anhvaan.webtech_projekt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Transactional
    public List<Recipe> getAllRecipesByUserId(String userId) {
        return recipeRepository.findByUserId(userId);
    }

    @Transactional
    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    @Transactional
    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Transactional
    public Recipe updateRecipe(Long id, Recipe recipe) {
        recipe.setId(id);
        return recipeRepository.save(recipe);
    }

    @Transactional
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    @Transactional
    public Recipe toggleFavorite(Long id, Boolean isFavorite) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        recipe.setIsFavorite(isFavorite);
        return recipeRepository.save(recipe);
    }

    @Transactional
    public List<Recipe> getFavoriteRecipes(String userId) {
        return recipeRepository.findByUserIdAndIsFavoriteTrue(userId);
    }
}