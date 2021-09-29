package ru.ashepelev.lru.concurrent;

import lombok.RequiredArgsConstructor;
import ru.ashepelev.lru.LRUCache;

import java.util.Optional;

@RequiredArgsConstructor
public class ConcurrentLRUCache<K, V> implements LRUCache<K, V> {
    @Override
    public void put(K key, V value) {

    }

    @Override
    public Optional<V> get(K key) {
        return Optional.empty();
    }
}
