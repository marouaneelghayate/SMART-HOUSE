package com.example.appbeta;
public class Value {
    private byte byteValue;
    private boolean state;
    public byte getByteValue() {
        return byteValue;
    }

    public void setByteValue(byte byteValue) {
        this.byteValue = byteValue;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }


    public Value(byte byteValue, boolean state) {
        this.byteValue = byteValue;
        this.state = state;
    }

}
