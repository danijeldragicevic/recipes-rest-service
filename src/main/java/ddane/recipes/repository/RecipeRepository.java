package ddane.recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ddane.recipes.model.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	@Override
	boolean existsById(Long id);

	List<Recipe> findAllByCategoryIgnoreCase(String category);

	List<Recipe> findRecipeByNameContainingIgnoreCase(String name);
}
