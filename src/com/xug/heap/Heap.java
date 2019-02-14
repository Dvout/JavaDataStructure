package com.xug.heap;

public interface Heap<E extends Comparable<E>> {
    void add(E e);

    E remove();

    int getSize();

    boolean isEmpty();

    E getFront();
}
