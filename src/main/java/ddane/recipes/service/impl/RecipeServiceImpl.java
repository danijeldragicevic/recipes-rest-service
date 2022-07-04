package ddane.recipes.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import ddane.recipes.exception.ForbiddenException;
import ddane.recipes.exception.NotFoundException;
import ddane.recipes.model.Recipe;
import ddane.recipes.model.RecipeId;
import ddane.recipes.model.User;
import ddane.recipes.model.dto.RecipeDto;
import ddane.recipes.model.dto.RecipeIdDto;
import ddane.recipes.repository.RecipeRepository;
import ddane.recipes.repository.UserRepository;
import ddane.recipes.service.IRecipeService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements IRecipeService {
	private final ModelMapper modelMapper;
	private final RecipeRepository recipeRepository;
	private final UserRepository userRepository;

	@Override
	public RecipeIdDto createRecipe(RecipeDto recipeDto, String username) {
		User user = userRepository.findUserByUsername(username);

		Recipe recipe = mapToEntity(recipeDto);
		recipe.setDate(LocalDateTime.now());
		recipe.setCreatedBy(user);

		Recipe saved = recipeRepository.save(recipe);

		RecipeId recipeId = new RecipeId();
		recipeId.setId(saved.getId());

		RecipeIdDto recipeIdDto = mapToDto(recipeId);

		return recipeIdDto;
	}


	@Override
	public RecipeDto getRecipeById(long id) throws NotFoundException {
		Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new NotFoundException());

		return mapToDto(recipe);
	}

	@Override
	public void deleteRecipeById(String username, long id) throws NotFoundException, ForbiddenException {
		Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new NotFoundException());

		if (recipe.getCreatedBy().getUsername().equalsIgnoreCase(username)) {
			recipeRepository.deleteById(id);
		} else {
			throw new ForbiddenException();
		}
	}

	@Override
	public RecipeDto updateRecipeById(String username, long id, RecipeDto recipeDto) throws NotFoundException, ForbiddenException{
		Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new NotFoundException());

		if (recipe.getCreatedBy().getUsername().equalsIgnoreCase(username)) {
			recipe.setName(recipeDto.getName());
			recipe.setDate(LocalDateTime.now());
			recipe.setCategory(recipeDto.getCategory());
			recipe.setDescription(recipeDto.getDescription());
			recipe.setIngredients(recipeDto.getIngredients());
			recipe.setDirections(recipeDto.getDirections());
			recipeRepository.save(recipe);

			return mapToDto(recipe);
		} else {
			throw new ForbiddenException();
		}
	}

	@Override
	public List<RecipeDto> getAllRecipesByCategoryOrName(String category, String name) {
		if (!category.isBlank() && name.isBlank()) {
			return recipeRepository.findAllByCategoryIgnoreCase(category)
					.stream()
					.map(this::mapToDto)
					.sorted(Comparator.comparing(RecipeDto::getDate).reversed())
					.collect(Collectors.toList());

		} else if (category.isBlank() && !name.isBlank()) {
			return recipeRepository.findRecipeByNameContainingIgnoreCase(name)
					.stream()
					.map(this::mapToDto)
					.sorted(Comparator.comparing(RecipeDto::getDate).reversed())
					.collect(Collectors.toList());

		} else {
			return List.of();
		}
	}

	private Recipe mapToEntity(RecipeDto recipeDto) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		return modelMapper.map(recipeDto, Recipe.class);
	}

	private RecipeDto mapToDto(Recipe recipe) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		return modelMapper.map(recipe, RecipeDto.class);
	}

	private RecipeIdDto mapToDto(RecipeId recipeId) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		return modelMapper.map(recipeId, RecipeIdDto.class);
	}
}
