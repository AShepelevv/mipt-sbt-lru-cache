package ru.ashepelev.lru.sequential;

import lombok.RequiredArgsConstructor;
import ru.ashepelev.lru.LRUCache;
import ru.ashepelev.lru.utils.Pair;

import java.time.Instant;
import java.util.*;

import static java.time.Instant.now;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toCollection;

@RequiredArgsConstructor
public class SequentialLRUCache<K, V> implements LRUCache<K, V> {
    private final Integer size;
    private final Map<K, V> values = new HashMap<>();
    private Queue<Pair<Instant, K>> keys = new PriorityQueue<>();

    @Override
    public void put(K key, V value) {
        if (values.size() == size) {
            var peekKey = keys.poll().getSecond();
            values.remove(peekKey);
        }
        updateKey(key);
        values.put(key, value);
    }

    @Override
    public Optional<V> get(K key) {
        var returnValue = ofNullable(values.get(key));
        returnValue.ifPresent((__) -> updateKey(key));
        return returnValue;
    }

    private void updateKey(K key) {
        removeKey(key);
        addKey(key);
    }

    private void removeKey(K key) {
        keys = keys.stream().filter(pair -> pair.getSecond() != key).collect(toCollection(PriorityQueue::new));
    }

    public void addKey(K key) {
        keys.add(new Pair<>(now(), key));
    }
}
