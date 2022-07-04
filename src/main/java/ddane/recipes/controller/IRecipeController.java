package ddane.recipes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ddane.recipes.model.dto.RecipeDto;

import java.util.Optional;

//@RequestMapping("/default")
public interface IRecipeController {

	@PostMapping("/new")
	public ResponseEntity createRecipe(UserDetails userDetails, RecipeDto recipeDto);

	@GetMapping("/{id}")
	public ResponseEntity getRecipeById(long id);

	@DeleteMapping("/{id}")
	public ResponseEntity deleteRecipeById(UserDetails userDetails, long id);

	@PutMapping("/{id}")
	public ResponseEntity updateRecipeById(UserDetails userDetails, long id, RecipeDto recipeDto);

	@GetMapping("/search")
	public ResponseEntity getAllRecipesByCategoryOrName(Optional<String> category, Optional<String> name);
}
