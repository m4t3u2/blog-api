package blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blog.models.Post;
import blog.payload.response.MessageResponse;
import blog.repository.PostRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/posts")
public class PostController {
	@Autowired
	PostRepository postRepository;

	@Autowired
	PasswordEncoder encoder;

	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody Post post) {
		postRepository.save(post);
		return ResponseEntity.ok(new MessageResponse("Post registrado!"));
	}

	@GetMapping("/todos")
	public ResponseEntity<List<Post>> buscarTodos() {
		// TODO Tratar quando não tem nenhum encontrado.
		return ResponseEntity.ok(postRepository.findAll());
	}

}
