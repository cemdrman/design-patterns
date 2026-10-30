package designpatterns.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * The Spring container itself is a big collection of patterns: a Factory for beans, Singleton
 * registry, Dependency Injection, Proxies for {@code @Cacheable}/{@code @Transactional},
 * Observer for application events, Template Method in {@code JdbcTemplate}...
 */
@SpringBootApplication
@EnableCaching
public class SpringPatternsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPatternsApplication.class, args);
    }
}
