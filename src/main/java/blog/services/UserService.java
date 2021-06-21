package blog.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import blog.models.ERole;
import blog.models.Role;
import blog.models.User;
import blog.payload.request.SignupRequest;
import blog.repository.role.RoleRepository;
import blog.repository.user.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	public User cadastrar(SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			// TODO Criar Exception
			// return ResponseEntity.badRequest().body(new MessageResponse("Erro: Usuário já
			// existe!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			// TODO Criar Exception
			// return ResponseEntity.badRequest().body(new MessageResponse("Erro: E-mail já
			// está em uso!"));
		}

		User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Erro: Permissão não encontrada."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Erro: Permissão não encontrada."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Erro: Permissão não encontrada."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Erro: Permissão não encontrada."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		User savedUser = userRepository.save(user);

		return savedUser;
	}

	public User buscar(long id) {

		Optional<User> user = userRepository.findById(id);

		if (!user.isPresent()) {
			// TODO Criar Exception
			// throw new NotFoundException("Not found!");
		}

		return user.get();
	}

	public List<User> buscarTodos() {

		List<User> users = userRepository.findAll();

		return users;
	}

}
