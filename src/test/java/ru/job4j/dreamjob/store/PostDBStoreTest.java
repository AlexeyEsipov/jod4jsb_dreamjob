package ru.job4j.dreamjob.store;

import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import static org.junit.Assert.*;

public class PostDBStoreTest {

    @Test
    public void whenCreatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        City city = new City(1);
        Post post = new Post(0, "Java Job", city);
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertEquals(postInDb.getName(), post.getName());
    }

    @Test
    public void whenUpdatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        City city = new City(1);
        Post post = new Post(0, "Java Job", city);
        store.add(post);
        post.setName("New Java Job");
        store.update(post);
        Post postInDb = store.findById(post.getId());
        assertEquals(postInDb.getName(), post.getName());
    }

    @Test
    public void whenDeletePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        City city = new City(1);
        Post post = new Post(0, "Java Job", city);
        store.add(post);
        store.delete(post.getId());
        assertNull(store.findById(post.getId()));
    }
}