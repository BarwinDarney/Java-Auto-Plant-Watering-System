package eecs1021;

import edu.princeton.cs.introcs.StdDraw;
import org.firmata4j.I2CDevice;
import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;
import org.firmata4j.ssd1306.SSD1306;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class Main {



    public static ArrayList<Double> sensorValuesList = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException {

        Arduino();
    }

    private static final String COMPORT = "COM5";
    public static IODevice arduino = new FirmataDevice(COMPORT);

    public static void Arduino() throws IOException, InterruptedException {

        //Arduino Setup
        arduino.start();
        arduino.ensureInitializationIsDone();

        //Pins
        Pin moistureSensor = arduino.getPin(15);
        Pin waterPump = arduino.getPin(2);
        Pin button = arduino.getPin(6);
        Pin led = arduino.getPin(4);

        //Set Pin Modes
        moistureSensor.setMode(Pin.Mode.ANALOG);
        waterPump.setMode(Pin.Mode.OUTPUT);
        button.setMode(Pin.Mode.INPUT);
        led.setMode(Pin.Mode.OUTPUT);

        //Display setup
        I2CDevice i2cObject = arduino.getI2CDevice((byte) 0x3C);
        SSD1306 display = new SSD1306(i2cObject, SSD1306.Size.SSD1306_128_64);
        display.init();

        led.setValue(1);

        arduino.addEventListener(new ButtonListener(button,led, display));


            Timer timer = new Timer();
            TimerTask moistureTask = new MoistureTask(moistureSensor, waterPump, display, led);
            timer.schedule(moistureTask, 0, 1000);


            Graph();

    }

    public static void Graph() throws InterruptedException {
        int seconds = 0;
        int i = 0;

        //Graph setup
        StdDraw.setXscale(-15,50);
        StdDraw.setYscale(-15,20);
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.line(0,0,0,5);
        StdDraw.line(0,0,50,0);
        StdDraw.text(-10,3, "Moisture Value (V)");
        StdDraw.text(25,-3, "Time (s)");
        StdDraw.text(25,15,"Time vs Moisture Readings");
        StdDraw.text(-5,0,"0");
        StdDraw.text(-5,5,"5");

        while (true) {

                Thread.sleep(1100);

                double moistureReading = sensorValuesList.get(i);
                StdDraw.setPenRadius(0.005);
                StdDraw.setPenColor(StdDraw.BOOK_BLUE);
                StdDraw.text((double) seconds, moistureReading, "*");

                i++;
                if (seconds < 50) {
                    seconds++;
                } else {
                    seconds = 1;
                }


        }

    }

    public static void populateSensorValues(ArrayList<Double> values) {
        sensorValuesList.clear();
        sensorValuesList.addAll(values);
    }

    public static ArrayList<Double> getSensorValuesList() {
        return sensorValuesList;
    }





}
