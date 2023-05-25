package com.example.appbeta;

import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TransferThread extends Thread {
    private final BluetoothSocket socket;
    private Utils utils;

    private final InputStream input;
    private final OutputStream output;
    private byte[] mmBuffer; // mmBuffer store for the stream

    public TransferThread(Utils utils) {
        this.socket = utils.getBluetoothSocket();
        this.utils = utils;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams; using temp objects because
        // member streams are final.
        try {
            tmpIn = socket.getInputStream();
        } catch (IOException e) {
            utils.setConnected(false);
            Log.d("TransferThread:: constructor","error getting input stream");
        }
        try {
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
            utils.setConnected(false);
            Log.d("TransferThread:: constructor","error getting output stream");
        }

        input = tmpIn;
        output = tmpOut;
        Log.d("TransferThread","Thread started succefully");
        utils.setConnected(true);
    }

    public void run() {
        mmBuffer = new byte[100];
        int numBytes; // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs.
        while (true) {
            try {
                // Read from the InputStream.
                numBytes = input.read(mmBuffer);
                utils.setData(mmBuffer);
                // Send the obtained bytes to the UI activity.
            } catch (IOException e) {
                utils.setConnected(false);
            }
        }
    }

    // Call this from the main activity to send data to the remote device.
    public void write(byte number) {
        try {
            output.write(number);
            Log.d("TransferThread::write","message sent : "+number);

            // Share the sent message with the UI activity.

        } catch (IOException e) {
            Log.d("TransferThread::write","error while sending");
            utils.setConnected(false);
            // Send a failure message back to the activity.

        }
    }
    public byte[] getData(){
        return mmBuffer;
    }
    public void cancel() {

    }
}
