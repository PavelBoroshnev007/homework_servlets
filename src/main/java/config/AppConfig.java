package config;

import org.springframework.context.annotation.Bean;
import controller.PostController;
import org.springframework.context.annotation.Configuration;
import repository.PostRepository;
import repository.PostRepositoryImpl;
import service.PostService;

@Configuration
public class AppConfig {

    @Bean
    public PostRepository postRepository() {
        return new PostRepositoryImpl();
    }

    @Bean
    public PostService postService(PostRepository repository) {
        return new PostService(repository);
    }

    @Bean
    public PostController postController(PostService service) {
        return new PostController(service);
    }
}
