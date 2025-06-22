package com.anhvaan.webtech_projekt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository repo;

    public Recipe saveRecipe(Recipe recipe) {
        return repo.save(recipe);
    }

    public Recipe getRecipeById(Long id) {
        return repo.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Recipe> getAllRecipes() {
        Iterable<Recipe> iterator = repo.findAll();
        List<Recipe> recipes = new ArrayList<>();
        for (Recipe recipe : iterator) {
            recipes.add(recipe);
        }
        return recipes;
    }

    public void deleteRecipe(Long id) {
        repo.deleteById(id);
    }
}
