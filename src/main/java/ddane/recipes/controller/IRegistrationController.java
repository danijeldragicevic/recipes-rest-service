package ddane.recipes.controller;

import org.springframework.web.bind.annotation.PostMapping;
import ddane.recipes.model.dto.UserDto;

public interface IRegistrationController {

	@PostMapping
	public void register(UserDto userDto);
}
