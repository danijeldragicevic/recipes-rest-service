package ddane.recipes.service;

import ddane.recipes.exception.ForbiddenException;
import ddane.recipes.exception.NotFoundException;
import ddane.recipes.model.dto.RecipeDto;
import ddane.recipes.model.dto.RecipeIdDto;

import java.util.List;

public interface IRecipeService {
	RecipeIdDto createRecipe(RecipeDto recipeDto, String createdBy);

	RecipeDto getRecipeById(long id) throws NotFoundException;

	void deleteRecipeById(String username, long id) throws NotFoundException, ForbiddenException;

	RecipeDto updateRecipeById(String username, long id, RecipeDto recipeDto) throws NotFoundException, ForbiddenException;

	List<RecipeDto> getAllRecipesByCategoryOrName(String category, String name);
}
