package ddane.recipes.service.impl;

import ddane.recipes.model.User;
import ddane.recipes.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ddane.recipes.configuration.BeansConfig;
import ddane.recipes.model.dto.UserDto;
import ddane.recipes.repository.UserRepository;
import ddane.recipes.security.impl.UserDetailsImpl;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
	private final UserRepository userRepository;
	private final BeansConfig beansConfig;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Not found: " + username);
		}

		return new UserDetailsImpl(user);
	}

	public boolean addUser(UserDto userDto) {
		userDto.setPassword(beansConfig.getEncoder().encode(userDto.getPassword()));
		User user = mapToUser(userDto);

		boolean existingUser = userRepository.existsById(user.getUsername());
		if (!existingUser) {
			userRepository.save(user);
			return true;
		} else {
			return false;
		}
	}

	private User mapToUser(UserDto userDto) {
		User user = new User();
		user.setPassword(userDto.getPassword());
		user.setUsername(userDto.getEmail());
		user.setRole("ROLE_USER");

		return user;
	}
}
