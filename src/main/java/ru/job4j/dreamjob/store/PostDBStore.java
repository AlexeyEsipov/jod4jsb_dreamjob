package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;

@Repository
public class PostDBStore {
    private final BasicDataSource pool;

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Collection<Post> findAll() {
        Collection<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    int cityId = it.getInt("city_id");
                    CityService cityService = new CityService();
                    City city = cityService.findById(cityId);
                    posts.add(new Post(it.getInt("id"), it.getString("name"),
                            it.getString("description"), city,
                            it.getBoolean("visible")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public Post add(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO post(name, description, visible, city_id) VALUES (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setBoolean(3, post.isVisible());
            ps.setInt(4, post.getCity().getId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    public void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
         "UPDATE post SET name = ?, description = ?, visible = ?, city_id = ? WHERE id = ?")) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setBoolean(3, post.isVisible());
            ps.setInt(4, post.getCity().getId());
            ps.setInt(5, post.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Post findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    int cityId = it.getInt("city_id");
                    CityService cityService = new CityService();
                    City city = cityService.findById(cityId);
                    return new Post(it.getInt("id"), it.getString("name"),
                            it.getString("description"), city,
                            it.getBoolean("visible"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM post WHERE id = ?")) {
            ps.setInt(1, id);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}