package com.example.demo;
@FunctionalInterface
public interface Functions<T,R> {
   public R getValue(T t1,T t2);
}
