package ddane.recipes.repository;

import ddane.recipes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	@Override
	boolean existsById(String id);

	User findUserByUsername(String username);
}
