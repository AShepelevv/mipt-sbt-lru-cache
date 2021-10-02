package ru.ashepelev.lru.sequential;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ashepelev.lru.LRUCache;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SequentialLRUCacheTest {
    @Spy
    private final LRUCache<Integer, Integer> cache = new SequentialLRUCache<>(5);

    @Test
    void test() {
        var keys = List.of(1, 2, 3, 2, 3, 4, 1, 1, 1, 1, 1, 5, 6, 7, 8, 9, 1);
        for (int key : keys) if (cache.get(key).isEmpty()) cache.put(key, key * key);
        verify(cache, times(10)).put(anyInt(), anyInt());
        verify(cache, times(17)).get(anyInt());
    }
}
