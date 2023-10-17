package service;

import exception.NotFoundException;
import model.Post;
import repository.PostRepository;

import java.util.List;
import java.util.Optional;

public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        return repository.all();
    }

    public Post getById(long id) {
        Optional<Post> postOptional = repository.getById(id);
        if (postOptional.isEmpty()) {
            throw new NotFoundException("Post with id " + id + " not found");
        }
        return postOptional.get();
    }

    public Post save(Post post) {
        return repository.save(post);
    }

    public boolean removeById(long id) {
        return repository.removeById(id);
    }
}
