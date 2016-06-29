package com.example;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import jssc.SerialPortTimeoutException;

/**
 * Created by rodrigosol on 6/28/16.
 */
public class Serial {

    public static void main(String[] args) throws InterruptedException {
        SerialPort serialPort = new SerialPort("/dev/tty.usbmodem1421");
        try {
            serialPort.openPort();//Open serial port
            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);

            Thread.sleep(2000);
            System.out.println(serialPort.writeBytes("3".getBytes()));
//            serialPort.writeInt(1);
//            serialPort.writeString("\n");
//            serialPort.writeInt(1);
//            serialPort.writeInt(1);
//            serialPort.writeInt(1);
//            serialPort.writeInt(1);
//            serialPort.writeInt(2);
//            serialPort.writeInt(2);
//            serialPort.writeInt(2);
//            serialPort.writeInt(2);

//            while (true){
//                Thread.sleep(10);
//            }
            //System.out.println(serialPort.readString());

            //serialPort.writeInt(1);
//            serialPort.writeBytes("abc\0".getBytes());
//            serialPort.writeBytes("abc\0".getBytes());
//            serialPort.writeInt(1);
//            while (serialPort.read)
//            System.out.println(serialPort.readString());
            serialPort.closePort();//Close serial port
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
            }
    }
}
