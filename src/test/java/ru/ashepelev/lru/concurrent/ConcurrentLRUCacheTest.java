package ru.ashepelev.lru.concurrent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ashepelev.lru.LRUCache;

import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.Optional.empty;
import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
class ConcurrentLRUCacheTest {
    private final static int CACHE_SIZE = 5;
    private final LRUCache<Integer, Integer> cache = new ConcurrentLRUCache<>(5);

    @Test
    void test() {
        cache.put(1, 1);

        assertEquals(empty(), cache.get(0));
        assertEquals(Optional.of(1), cache.get(1));

        for (int i = 0; i < CACHE_SIZE; ++i) {
            cache.put(2, 2);
        }

        assertEquals(Optional.of(1), cache.get(1));
        assertEquals(Optional.of(2), cache.get(2));

        cache.put(10, 10);
        Executor executor = new ThreadPoolExecutor(10, 10, 100, MICROSECONDS, new ArrayBlockingQueue<>(100));
        for (int i = 0; i < 100; ++i) {
            int value = i % CACHE_SIZE;
            executor.execute(() -> cache.put(value, value));
        }

        assertEquals(empty(), cache.get(10));
        for (int i = 0; i < CACHE_SIZE; i++) {
            assertEquals(Optional.of(i), cache.get(i));
        }
    }
}
