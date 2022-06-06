package ru.job4j.dreamjob.controller;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class PostControlTest {
    @Test
    public void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(1, "New post"),
                new Post(2, "New post")
        );
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        when(postService.findAll()).thenReturn(posts);
        CityService cityService = mock(CityService.class);
        HttpSession session = mock(HttpSession.class);
        PostControl postController = new PostControl(postService, cityService);
        String page = postController.posts(model, session);
        verify(model).addAttribute("posts", posts);
        assertThat(page, is("posts"));
    }

    @Test
    public void whenAddPost() {
        List<City> cities = Arrays.asList(
                new City(),
                new City()
        );
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostControl postController = new PostControl(postService, cityService);
        when(cityService.getAllCities()).thenReturn(cities);
        String page = postController.addPost(model, session);
        verify(model).addAttribute("cities", cities);
        assertThat(page, is("addPost"));
    }

    @Test
    public void whenFormUpdatePost1() {
        List<City> cities = Arrays.asList(
                new City(),
                new City()
        );
        Post post = new Post(1, "New post");
        User user = new User();
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostControl postController = new PostControl(postService, cityService);
        when(session.getAttribute("user")).thenReturn(user);
        when(cityService.getAllCities()).thenReturn(cities);
        when(postService.findById(1)).thenReturn(post);
        String page = postController.formUpdatePost(model, 1, session);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("post", post);
        verify(model).addAttribute("cities", cities);
        assertThat(page, is("updatePost"));
    }

    @Test
    public void whenUpdatePost() {
        Post update = new Post(1, "Update post");
        City city = new City();
        update.setCity(city);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostControl postController = new PostControl(postService, cityService);
        when(cityService.findById(1)).thenReturn(city);
        String page = postController.updatePost(update);
        verify(postService).update(update);
        assertThat(page, is("redirect:/posts"));
    }

    @Test
    public void whenCreatePost() {
        Post create = new Post(1, "Create post");
        City city = new City();
        create.setCity(city);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostControl postController = new PostControl(postService, cityService);
        when(cityService.findById(1)).thenReturn(city);
        String page = postController.createPost(create);
        verify(postService).add(create);
        assertThat(page, is("redirect:/posts"));
    }

    @Test
    public void whenDeletePost() {
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostControl postController = new PostControl(postService, cityService);
        String page = postController.deletePost(1);
        verify(postService).delete(1);
        assertThat(page, is("redirect:/posts"));
    }
}