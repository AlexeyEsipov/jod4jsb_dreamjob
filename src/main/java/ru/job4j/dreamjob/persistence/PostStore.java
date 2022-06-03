package ru.job4j.dreamjob.persistence;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

@ThreadSafe
@Repository
public class PostStore {

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(3);

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "Junior", new City(1), true));
        posts.put(2, new Post(2, "Middle Java Job", "Middle", new City(2), false));
        posts.put(3, new Post(3, "Senior Java Job", "Senior", new City(3), true));
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        post.setId(id.incrementAndGet());
        posts.put(post.getId(), post);
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void update(Post post) {
        posts.replace(post.getId(), post);
    }

    public void delete(int id) {
        posts.remove(id);
    }
}
