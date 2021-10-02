package ru.ashepelev.lru.concurrent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ashepelev.lru.LRUCache;

import static java.util.List.of;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
class ConcurrentLRUCacheTest {
    @Spy
    private final LRUCache<Integer, Integer> cache = new ConcurrentLRUCache<>(5);

    @Test
    void test() {
        var keys = of(1, 2, 3, 2, 3, 4, 1, 1, 1, 1, 1, 5, 6, 7, 8, 9, 1);
        for (int key : keys) if (cache.get(key).isEmpty()) cache.put(key, key * key);
        verify(cache, times(10)).put(anyInt(), anyInt());
        verify(cache, times(17)).get(anyInt());
    }
}
