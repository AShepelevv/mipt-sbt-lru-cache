package ru.ashepelev.lru.sequential;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ashepelev.lru.LRUCache;

import java.util.List;
import java.util.function.Supplier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SequentialLRUCacheTest {
    @Spy
    private final LRUCache<Integer, Integer> cache = new SequentialLRUCache<>(5);
    @Mock
    private final Supplier<Integer> supplierMock = () -> 0;

    @Test
    void test() {
        var keys = List.of(1, 2, 3, 2, 3, 4, 1, 1, 1, 1, 1,  5, 6, 7, 8, 9);
        for (int key : keys) if (cache.get(key).isEmpty()) cache.put(key, key * key);
        verify(cache, times(9)).put(any(), any());
        verify(cache, times(16)).get(any());
    }

    @Test
    void testDeleteDublicates() {
        var keys = List.of(1, 1, 1, 1, 1, 1, 1, 2);
        for (int key : keys) if (cache.get(key).isEmpty()) cache.put(key, key * key);
        verify(cache, times(2)).put(any(), any());
        verify(cache, times(8)).get(any());
    }
}
