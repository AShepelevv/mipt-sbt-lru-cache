package ru.ashepelev.lru.concurrent;

import lombok.RequiredArgsConstructor;
import ru.ashepelev.lru.LRUCache;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@RequiredArgsConstructor
public class ConcurrentLRUCache<K, V> implements LRUCache<K, V> {
    private final int size;
    private final ConcurrentLinkedQueue<K> keys = new ConcurrentLinkedQueue<K>();
    private final ConcurrentHashMap<K, V> values = new ConcurrentHashMap<K, V>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public Optional<V> get(K key) {
        lock.readLock().lock();
        try {
            Optional<V> returnValue = empty();
            if (values.containsKey(key)) {
                keys.remove(key);
                returnValue = of(values.get(key));
                keys.add(key);
            }
            return returnValue;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void put(K key, V value) {
        lock.writeLock().lock();
        try {
            keys.remove(key);
            if (values.size() == size) values.remove(keys.poll());
            keys.add(key);
            values.put(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }
}
