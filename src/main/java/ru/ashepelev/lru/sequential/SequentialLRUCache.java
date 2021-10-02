package ru.ashepelev.lru.sequential;

import lombok.RequiredArgsConstructor;
import ru.ashepelev.lru.LRUCache;

import java.util.*;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@RequiredArgsConstructor
public class SequentialLRUCache<K, V> implements LRUCache<K, V> {
    private final int size;
    private final Queue<K> keys = new LinkedList<>();
    private final Map<K, V> values = new HashMap<>();

    @Override
    public void put(K key, V value) {
        keys.remove(key);
        if (values.size() == size) values.remove(keys.poll());
        keys.add(key);
        values.put(key, value);
    }

    @Override
    public Optional<V> get(K key) {
        if (keys.remove(key)) {
            keys.add(key);
            return of(values.get(key));
        }
        return empty();
    }
}
