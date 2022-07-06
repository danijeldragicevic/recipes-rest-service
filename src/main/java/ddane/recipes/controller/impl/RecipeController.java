package ddane.recipes.controller.impl;

import ddane.recipes.service.IRecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ddane.recipes.controller.IRecipeController;
import ddane.recipes.exception.ForbiddenException;
import ddane.recipes.exception.NotFoundException;
import ddane.recipes.model.dto.RecipeDto;
import ddane.recipes.model.dto.RecipeIdDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipe")
public class RecipeController implements IRecipeController {
	private final IRecipeService recipeService;

	@Override
	public ResponseEntity createRecipe(
			@AuthenticationPrincipal UserDetails userDetails,
			@Valid @RequestBody RecipeDto recipeDto) {

		RecipeIdDto recipeIdDto = recipeService.createRecipe(recipeDto, userDetails.getUsername());

		return ResponseEntity.ok(recipeIdDto);
	}

	@Override
	public ResponseEntity<RecipeDto> getRecipeById(
			@PathVariable long id) {

		RecipeDto recipeDto = null;
		try {
			recipeDto = recipeService.getRecipeById(id);
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(recipeDto);
	}

	@Override
	public ResponseEntity<HttpStatus> deleteRecipeById(
			@AuthenticationPrincipal UserDetails userDetails,
			@PathVariable long id) {

		try {
			recipeService.deleteRecipeById(userDetails.getUsername(), id);
			return ResponseEntity.noContent().build();
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (ForbiddenException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@Override
	public ResponseEntity updateRecipeById(
			@AuthenticationPrincipal UserDetails userDetails,
			@PathVariable long id,
			@Valid @RequestBody RecipeDto recipeDto) {

		try {
			recipeService.updateRecipeById(userDetails.getUsername(), id, recipeDto);
			return ResponseEntity.noContent().build();
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (ForbiddenException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@Override
	public ResponseEntity<List<RecipeDto>> getAllRecipesByCategoryOrName(
			@RequestParam(required = false) Optional<String> category,
			@RequestParam(required = false) Optional<String> name) {

		if (category.isEmpty() && name.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		if (category.isPresent() && name.isPresent()) {
			return ResponseEntity.badRequest().build();
		}

		String categoryToUse = category.orElse("");
		String nameToUse = name.orElse("");
		List<RecipeDto> recipeDtoList = recipeService.getAllRecipesByCategoryOrName(categoryToUse, nameToUse);

		return ResponseEntity.ok(recipeDtoList);
	}
}
