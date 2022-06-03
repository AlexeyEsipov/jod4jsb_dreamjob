package ru.job4j.dreamjob.persistence;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

@ThreadSafe
@Repository
public class CandidateStore {

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(3);

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Andrew", "Big", new City(1), true));
        candidates.put(2, new Candidate(2, "Petr", "Small", new City(2), false));
        candidates.put(3, new Candidate(3, "Tom", "Middle", new City(3), true));
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public void add(Candidate candidate) {
        candidate.setId(id.incrementAndGet());
        candidates.put(candidate.getId(), candidate);
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public void update(Candidate candidate) {
        candidates.replace(candidate.getId(), candidate);
    }

    public void delete(int id) {
        candidates.remove(id);
    }
}