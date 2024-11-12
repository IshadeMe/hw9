package org.example;

import java.io.IOException;

@FunctionalInterface
public interface Supp<K, T, O> {
    void apply(K b, K c, T t, O o, K d) throws IOException;
}
