package com.example.impl;

import com.example.interfaces.SomeInterface;

public class OtherImpl implements SomeInterface {
    @Override
    public void doSomething() {
        System.out.print("B");
    }
}