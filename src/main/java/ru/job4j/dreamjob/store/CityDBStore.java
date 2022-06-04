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

@Repository
public class CityDBStore {
    private final BasicDataSource pool;

    public CityDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Collection<City> getAllCities() {
        Collection<City> cities = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM city")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    cities.add(new City(it.getInt("city_id"), it.getString("city_name")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

    public City findById(int id) {
        City city = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM city WHERE city_id = ?")) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    city = new City(it.getInt("city_id"), it.getString("city_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }
}