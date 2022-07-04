package ddane.recipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipes")
public class Recipe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private String category;

	private LocalDateTime date;

	private String description;

	@ElementCollection
	private List<String> ingredients;

	@ElementCollection
	private List<String> directions;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private User createdBy;
}
