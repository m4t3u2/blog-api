package blog.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import blog.models.Post;
import blog.services.PostService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	PostService postService;

	@PostMapping
	public ResponseEntity<Void> salvar(@Valid @RequestBody Post post) {

		//TODO Não permitir save publico.
		Post savedPost = postService.salvar(post);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPost.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@GetMapping
	public ResponseEntity<List<Post>> buscarTodos() {

		List<Post> posts = postService.buscarTodos();

		return ResponseEntity.ok(posts);
	}

}
