package com.references;

public class BigObject {
    private int value;
    public BigObject(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("BigObject (" + value + ") finalize()");
    }
}
