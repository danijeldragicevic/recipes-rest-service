package ddane.recipes.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ddane.recipes.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeDto {
	@NotBlank
	private String name;

	@NotBlank
	private String category;

	private LocalDateTime date;

	@NotBlank
	private String description;

	@NotNull
	@Size(min = 1)
	private List<String> ingredients;

	@NotNull
	@Size(min = 1)
	private List<String> directions;

	@JsonIgnore
	private User createdBy;
}
