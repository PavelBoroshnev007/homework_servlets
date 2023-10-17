package repository;

import model.Post;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepositoryImpl implements PostRepository {
    private final ConcurrentHashMap<Long, Post> postMap = new ConcurrentHashMap<>();
    private final AtomicLong postIdCounter = new AtomicLong(0);

    @Override
    public List<Post> all() {
        return new CopyOnWriteArrayList<>(postMap.values());
    }

    @Override
    public Optional<Post> getById(long id) {
        return Optional.ofNullable(postMap.get(id));
    }

    @Override
    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(postIdCounter.incrementAndGet());
        }
        postMap.put(post.getId(), post);
        return post;
    }

    @Override
    public boolean removeById(long id) {
        return postMap.remove(id) != null;
    }
}
