package com.anhvaan.webtech_projekt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecipeController.class)
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Recipe testRecipe;

    @BeforeEach
    void setUp() {
        testRecipe = new Recipe();
        testRecipe.setId(1L);
        testRecipe.setTitle("Test Recipe");
        testRecipe.setDescription("Test Description");
        testRecipe.setIngredients(Arrays.asList("Ingredient 1", "Ingredient 2"));
        testRecipe.setInstructions(Arrays.asList("Step 1", "Step 2"));
        testRecipe.setPrepTime(15);
        testRecipe.setCookTime(30);
        testRecipe.setServings(4);
        testRecipe.setUserId("1");
        testRecipe.setIsFavorite(false);
    }

    @Test
    void testGetAllRecipes() throws Exception {
        // Given
        List<Recipe> recipes = Arrays.asList(testRecipe);
        when(recipeService.getAllRecipesByUserId("1")).thenReturn(recipes);

        // When & Then
        mockMvc.perform(get("/api/recipes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Recipe"))
                .andExpect(jsonPath("$[0].userId").value("1"));

        verify(recipeService).getAllRecipesByUserId("1");
    }

    @Test
    void testGetRecipeById() throws Exception {
        // Given
        when(recipeService.getRecipeById(1L)).thenReturn(Optional.of(testRecipe));

        // When & Then
        mockMvc.perform(get("/api/recipes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Recipe"));

        verify(recipeService).getRecipeById(1L);
    }

    @Test
    void testGetRecipeByIdNotFound() throws Exception {
        // Given
        when(recipeService.getRecipeById(1L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/recipes/1"))
                .andExpect(status().isNotFound());

        verify(recipeService).getRecipeById(1L);
    }

    @Test
    void testCreateRecipe() throws Exception {
        // Given
        when(recipeService.createRecipe(any(Recipe.class))).thenReturn(testRecipe);

        // When & Then
        mockMvc.perform(post("/api/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRecipe)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Recipe"));

        verify(recipeService).createRecipe(any(Recipe.class));
    }

    @Test
    void testUpdateRecipe() throws Exception {
        // Given
        when(recipeService.updateRecipe(eq(1L), any(Recipe.class))).thenReturn(testRecipe);

        // When & Then
        mockMvc.perform(put("/api/recipes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRecipe)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Recipe"));

        verify(recipeService).updateRecipe(eq(1L), any(Recipe.class));
    }

    @Test
    void testDeleteRecipe() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/recipes/1"))
                .andExpect(status().isOk());

        verify(recipeService).deleteRecipe(1L);
    }

    @Test
    void testToggleFavorite() throws Exception {
        // Given
        testRecipe.setIsFavorite(true);
        when(recipeService.toggleFavorite(1L, true)).thenReturn(testRecipe);

        // When & Then
        mockMvc.perform(patch("/api/recipes/1/favorite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"isFavorite\": true}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isFavorite").value(true));

        verify(recipeService).toggleFavorite(1L, true);
    }

    @Test
    void testGetFavoriteRecipes() throws Exception {
        // Given
        testRecipe.setIsFavorite(true);
        List<Recipe> favoriteRecipes = Arrays.asList(testRecipe);
        when(recipeService.getFavoriteRecipes("1")).thenReturn(favoriteRecipes);

        // When & Then
        mockMvc.perform(get("/api/recipes/favorites")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].isFavorite").value(true));

        verify(recipeService).getFavoriteRecipes("1");
    }
}