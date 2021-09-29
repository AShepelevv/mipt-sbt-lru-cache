package ru.ashepelev.lru.concurrent;

import lombok.RequiredArgsConstructor;
import ru.ashepelev.lru.LRUCache;
import ru.ashepelev.lru.utils.Pair;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import static java.time.Instant.now;
import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
public class ConcurrentLRUCache<K, V> implements LRUCache<K, V> {
    private final int size;
    private final ConcurrentHashMap<K, Instant> timeByKey;
    private final ConcurrentHashMap<K, V> valueByKey;
    private final ConcurrentSkipListSet<Pair<Instant, K>> keys;

    @Override
    public void put(K key, V value) {
        getTimeByKey(key).ifPresent((__) -> updateTime(new Pair<>(__, key)));

        //Проверить, что не превысили размер... Если превысили, убрать самый старый
        while (valueByKey.size() == size) {
            var oldestKey = getOldestKey();
            while (valueByKey.containsKey(oldestKey)) {
                valueByKey.remove(oldestKey);
            }
        }

        //Вставить новое значение и обновить метки времени
        var time = now();
        keys.add(new Pair<>(time, key));
        valueByKey.put(key, value);
        timeByKey.put(key, time);
    }

    @Override
    public Optional<V> get(K key) {
        return Optional.empty();
    }

    private synchronized Optional<Instant> getTimeByKey(K key) {
        var time = ofNullable(timeByKey.get(key));
        time.ifPresent((__) -> timeByKey.remove(key));
        return time;
    }

    private synchronized void updateTime(Pair<Instant, K> pair) {
        ofNullable(keys.lower(pair)).ifPresent(keys::remove);
    }

    private synchronized K getOldestKey() {
        var first = ofNullable(keys.first());
        first.ifPresent(keys::remove);
        return first.map(Pair::getSecond).orElseThrow();
    }
}
