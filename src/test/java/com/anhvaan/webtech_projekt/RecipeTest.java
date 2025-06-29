package com.anhvaan.webtech_projekt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

class RecipeTest {

    private Recipe recipe;

    @BeforeEach
    void setUp() {
        recipe = new Recipe();
    }

    @Test
    void testRecipeCreation() {
        // Given
        String title = "Test Recipe";
        String description = "Test Description";
        List<String> ingredients = Arrays.asList("Ingredient 1", "Ingredient 2");
        List<String> instructions = Arrays.asList("Step 1", "Step 2");

        // When
        recipe.setTitle(title);
        recipe.setDescription(description);
        recipe.setIngredients(ingredients);
        recipe.setInstructions(instructions);
        recipe.setPrepTime(15);
        recipe.setCookTime(30);
        recipe.setServings(4);
        recipe.setUserId("1");

        // Then
        assertEquals(title, recipe.getTitle());
        assertEquals(description, recipe.getDescription());
        assertEquals(ingredients, recipe.getIngredients());
        assertEquals(instructions, recipe.getInstructions());
        assertEquals(15, recipe.getPrepTime());
        assertEquals(30, recipe.getCookTime());
        assertEquals(4, recipe.getServings());
        assertEquals("1", recipe.getUserId());
        assertFalse(recipe.getIsFavorite()); // Default value
    }

    @Test
    void testFavoriteToggle() {
        // Given
        recipe.setIsFavorite(false);

        // When
        recipe.setIsFavorite(true);

        // Then
        assertTrue(recipe.getIsFavorite());
    }

    @Test
    void testDefaultValues() {
        // Then
        assertNull(recipe.getId());
        assertNull(recipe.getTitle());
        assertNull(recipe.getDescription());
        assertFalse(recipe.getIsFavorite());
        assertNull(recipe.getCreatedAt());
        assertNull(recipe.getUpdatedAt());
    }

    @Test
    void testPreUpdateCallback() {
        // Given
        recipe.onCreate();
        LocalDateTime originalCreatedAt = recipe.getCreatedAt();

        // When
        recipe.onUpdate();

        // Then
        assertEquals(originalCreatedAt, recipe.getCreatedAt());
        assertNotNull(recipe.getUpdatedAt());
    }
}