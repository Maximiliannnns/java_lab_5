package com.example.impl;

import com.example.interfaces.SomeInterface;
/**
 * Альтернативная реализация интерфейса {@link SomeInterface}.
 * 
 * Выводит символ "B" при вызове метода.
 * Используется для демонстрации возможности смены реализации
 * через конфигурационный файл без изменения кода.
 */
public class OtherImpl implements SomeInterface {
    @Override
    public void doSomething() {
        System.out.print("B");
    }
}