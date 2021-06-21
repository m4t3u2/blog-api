package blog.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import blog.models.User;
import blog.payload.request.SignupRequest;
import blog.services.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping
	public ResponseEntity<Void> cadastrar(@Valid @RequestBody SignupRequest signUpRequest) {

		User savedUser = userService.cadastrar(signUpRequest);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> buscar(@PathVariable("id") long id) {

		User user = userService.buscar(id);

		return ResponseEntity.ok(user);
	}

	@GetMapping
	public ResponseEntity<List<User>> buscarTodos() {

		List<User> users = userService.buscarTodos();

		return ResponseEntity.ok(users);
	}

}
