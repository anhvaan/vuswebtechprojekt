package com.anhvaan.webtech_projekt;

import com.anhvaan.webtech_projekt.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByUserId(String userId);

    List<Recipe> findByUserIdAndIsFavoriteTrue(String userId);
}
