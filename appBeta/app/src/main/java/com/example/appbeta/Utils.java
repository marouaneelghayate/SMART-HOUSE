package com.example.appbeta;

import android.bluetooth.BluetoothSocket;
import android.content.Context;

public class Utils {
    private BluetoothSocket bluetoothSocket=null;
    private static boolean connected = false;
    private static Utils instance=null;
    private static Commands commands;
    private static byte[] data;
    public byte[] getData(){return data;}
    public void setData(byte[] date){ data=date;}

    public TransferThread getTransferThread() {
        return transferThread;
    }

    public void setTransferThread(TransferThread transferThread) {
        Utils.transferThread = transferThread;
    }

    private static TransferThread transferThread;


    private Utils(Context context){
        commands = Commands.getInstance(context);
    }
    public static Utils getInstance(Context context){
        if(instance == null){
            instance = new Utils(context);
        }
        return instance;
    }

    public BluetoothSocket getBluetoothSocket() {
        return bluetoothSocket;
    }

    public void setBluetoothSocket(BluetoothSocket bluetoothSocket) {
        this.bluetoothSocket = bluetoothSocket;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
    public Commands getCommands(){return  commands;}
}
