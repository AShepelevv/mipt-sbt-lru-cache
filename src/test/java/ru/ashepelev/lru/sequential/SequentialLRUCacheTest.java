package ru.ashepelev.lru.sequential;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ashepelev.lru.LRUCache;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SequentialLRUCacheTest {
    private final static int CACHE_SIZE = 5;
    @Spy
    private final LRUCache<Integer, Integer> cache = new SequentialLRUCache<>(CACHE_SIZE);

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

        for (int i = 0; i < CACHE_SIZE; ++i) {
            cache.put(i, i);
        }

        assertEquals(empty(), cache.get(10));
    }
}
