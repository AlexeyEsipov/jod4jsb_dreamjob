package ru.job4j.dreamjob.store;

import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import static org.junit.Assert.*;

public class CandidateDBStoreTest {

    @Test
    public void whenCreateCandidate() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        City city = new City(1);
        Candidate candidate = new Candidate(0, "Junior", city);
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertEquals(candidateInDb.getName(), candidate.getName());
    }

    @Test
    public void whenUpdateCandidate() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        City city = new City(1);
        Candidate candidate = new Candidate(0, "Junior", city);
        store.add(candidate);
        candidate.setName("New Junior");
        store.update(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertEquals(candidateInDb.getName(), candidate.getName());
    }

    @Test
    public void whenDeleteCandidate() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        City city = new City(1);
        Candidate candidate = new Candidate(0, "Junior", city);
        store.add(candidate);
        store.delete(candidate.getId());
        assertNull(store.findById(candidate.getId()));
    }
}