package com.xug.map;

public interface Map<K,V> {
    void add(K key, V value);

    V remvoe(K key);

    void set(K key, V value);

    boolean contain(K key);

    V get(K key);

    int getSize();

    boolean isEmpty();
}
