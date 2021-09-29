package ru.ashepelev.lru;

import ru.ashepelev.lru.sequential.SequentialLRUCache;

import java.util.List;

public class Main {
    public static void main(String[] args) {

    }

    private static void testSequential() {
        LRUCache<Integer, Integer> cache = new SequentialLRUCache<>(5);
        var keys = List.of(1, 2, 3, 2, 3, 4, 1, 1, 1, 1, 1,  5, 6, 7, 8, 9);
        for (int key : keys) if (cache.get(key).isEmpty()) {
            System.out.println(key);
            cache.put(key, key * key);
        }
    }
}
