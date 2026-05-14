package com.example.impl;

import com.example.interfaces.SomeInterface;
/**
 * Первая реализация интерфейса {@link SomeInterface}.
 * 
 * Выводит символ "A" при вызове метода.
 */
public class SomeImpl implements SomeInterface {
    @Override
    public void doSomething() {
        System.out.print("A");
    }
}