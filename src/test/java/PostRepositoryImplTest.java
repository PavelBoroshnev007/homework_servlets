import model.Post;
import org.junit.jupiter.api.Test;
import repository.PostRepository;
import repository.PostRepositoryImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostRepositoryImplTest {

    @Test
    void testConcurrency() throws InterruptedException {
        PostRepository repository = new PostRepositoryImpl();
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 1000; i++) {
            executorService.execute(() -> {
                Post post = new Post(0, "Content");
                repository.save(post);
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        assertEquals(1000, repository.all().size());
    }
}
