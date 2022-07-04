package ddane.recipes.controller.impl;

import ddane.recipes.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ddane.recipes.controller.IRegistrationController;
import ddane.recipes.model.dto.UserDto;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/register")
public class RegistrationController implements IRegistrationController {
	private final UserServiceImpl userService;

	@Override
	public void register(@Valid @RequestBody UserDto userDto) {
		boolean created = userService.addUser(userDto);
		if (!created) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					"User already exists!");
		}
	}
}
