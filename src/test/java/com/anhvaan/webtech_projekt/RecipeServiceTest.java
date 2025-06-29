package com.anhvaan.webtech_projekt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService recipeService;

    private Recipe testRecipe;

    @BeforeEach
    void setUp() {
        testRecipe = new Recipe();
        testRecipe.setId(1L);
        testRecipe.setTitle("Test Recipe");
        testRecipe.setUserId("1");
        testRecipe.setIsFavorite(false);
    }

    @Test
    void testGetAllRecipesByUserId() {
        // Given
        String userId = "1";
        List<Recipe> expectedRecipes = Arrays.asList(testRecipe);
        when(recipeRepository.findByUserId(userId)).thenReturn(expectedRecipes);

        // When
        List<Recipe> result = recipeService.getAllRecipesByUserId(userId);

        // Then
        assertEquals(expectedRecipes, result);
        verify(recipeRepository).findByUserId(userId);
    }

    @Test
    void testGetRecipeById() {
        // Given
        Long id = 1L;
        when(recipeRepository.findById(id)).thenReturn(Optional.of(testRecipe));

        // When
        Optional<Recipe> result = recipeService.getRecipeById(id);

        // Then
        assertTrue(result.isPresent());
        assertEquals(testRecipe, result.get());
        verify(recipeRepository).findById(id);
    }

    @Test
    void testCreateRecipe() {
        // Given
        when(recipeRepository.save(testRecipe)).thenReturn(testRecipe);

        // When
        Recipe result = recipeService.createRecipe(testRecipe);

        // Then
        assertEquals(testRecipe, result);
        verify(recipeRepository).save(testRecipe);
    }

    @Test
    void testUpdateRecipe() {
        // Given
        Long id = 1L;
        when(recipeRepository.save(testRecipe)).thenReturn(testRecipe);

        // When
        Recipe result = recipeService.updateRecipe(id, testRecipe);

        // Then
        assertEquals(id, result.getId());
        assertEquals(testRecipe, result);
        verify(recipeRepository).save(testRecipe);
    }

    @Test
    void testDeleteRecipe() {
        // Given
        Long id = 1L;

        // When
        recipeService.deleteRecipe(id);

        // Then
        verify(recipeRepository).deleteById(id);
    }

    @Test
    void testToggleFavorite() {
        // Given
        Long id = 1L;
        Boolean newFavoriteStatus = true;
        when(recipeRepository.findById(id)).thenReturn(Optional.of(testRecipe));
        when(recipeRepository.save(testRecipe)).thenReturn(testRecipe);

        // When
        Recipe result = recipeService.toggleFavorite(id, newFavoriteStatus);

        // Then
        assertTrue(result.getIsFavorite());
        verify(recipeRepository).findById(id);
        verify(recipeRepository).save(testRecipe);
    }

    @Test
    void testToggleFavoriteRecipeNotFound() {
        // Given
        Long id = 1L;
        when(recipeRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            recipeService.toggleFavorite(id, true);
        });
        verify(recipeRepository).findById(id);
        verify(recipeRepository, never()).save(any());
    }

    @Test
    void testGetFavoriteRecipes() {
        // Given
        String userId = "1";
        testRecipe.setIsFavorite(true);
        List<Recipe> expectedFavorites = Arrays.asList(testRecipe);
        when(recipeRepository.findByUserIdAndIsFavoriteTrue(userId)).thenReturn(expectedFavorites);

        // When
        List<Recipe> result = recipeService.getFavoriteRecipes(userId);

        // Then
        assertEquals(expectedFavorites, result);
        verify(recipeRepository).findByUserIdAndIsFavoriteTrue(userId);
    }
}