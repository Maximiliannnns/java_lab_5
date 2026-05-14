package com.example.impl;

import com.example.interfaces.SomeOtherInterface;

public class SODoer implements SomeOtherInterface {
    @Override
    public void doSomeOther() {
        System.out.print("C");
    }
}