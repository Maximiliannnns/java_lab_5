package com.example.impl;

import com.example.interfaces.SomeOtherInterface;
/**
 * Реализация интерфейса {@link SomeOtherInterface}.
 * 
 * Выводит символ "C" при вызове метода.
 */
public class SODoer implements SomeOtherInterface {
    @Override
    public void doSomeOther() {
        System.out.print("C");
    }
}