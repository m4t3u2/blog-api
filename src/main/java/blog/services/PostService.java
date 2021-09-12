package blog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.models.Post;
import blog.repository.post.PostRepository;

@Service
public class PostService {

	@Autowired
	PostRepository postRepository;

	public Post salvar(Post post) {

		Post savedPost = postRepository.save(post);

		return savedPost;
	}

	public List<Post> buscarTodos() {

		List<Post> posts = postRepository.findAllByOrderByDateDesc();

		return posts;
	}

}
