package repository;

import model.Post;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepositoryImpl implements PostRepository {
    private final List<Post> posts = new CopyOnWriteArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public List<Post> all() {
        return Collections.unmodifiableList(posts);
    }

    @Override
    public Optional<Post> getById(long id) {
        return posts.stream().filter(post -> post.getId() == id).findFirst();
    }

    @Override
    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(idCounter.getAndIncrement());
            posts.add(post);
        } else {
            Optional<Post> existingPost = getById(post.getId());
            existingPost.ifPresent(p -> {
                p.setContent(post.getContent());
            });
        }
        return post;
    }

    @Override
    public void removeById(long id) {
        posts.removeIf(post -> post.getId() == id);
    }
}
