package ru.ashepelev.lru.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Pair<K extends Comparable<K>, V> implements Comparable<Pair<K, V>>{
    private final K first;
    private final V second;

    @Override
    public int compareTo(Pair<K, V> o) {
        return this.getFirst().compareTo(o.getFirst());
    }
}
