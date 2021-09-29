package ru.ashepelev.lru;

import java.util.Optional;

public interface LRUCache<K, V> {
    void put(K key, V value);

    Optional<V> get(K key);
}
