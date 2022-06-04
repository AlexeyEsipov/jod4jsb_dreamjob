package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.store.CityDBStore;

import java.util.*;
@ThreadSafe
@Service
public class CityService {
    private final CityDBStore store;

    public CityService(CityDBStore store) {
        this.store = store;
    }

    public Collection<City> getAllCities() {
        return store.getAllCities();
    }

    public City findById(int id) {
        return store.findById(id);
    }
}