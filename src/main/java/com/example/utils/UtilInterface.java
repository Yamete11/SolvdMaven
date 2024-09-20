package com.example.utils;

@FunctionalInterface
public interface UtilInterface<T, R> {
    R apply(T t);
}
