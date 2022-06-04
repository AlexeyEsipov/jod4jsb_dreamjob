package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import java.util.Collection;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.PostDBStore;

@ThreadSafe
@Service
public class PostService {
    private final PostDBStore store;

    public PostService(PostDBStore store) {
        this.store = store;
    }

    public void add(Post post) {
        store.add(post);
    }

    public Post findById(int id) {
        return store.findById(id);
    }

    public void update(Post post) {
        store.update(post);
    }

    public Collection<Post> findAll() {
        return store.findAll();
    }

    public void delete(int id) {
        store.delete(id);
    }
}