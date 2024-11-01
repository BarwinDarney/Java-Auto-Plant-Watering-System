package eecs1021;

import org.firmata4j.Pin;
import org.firmata4j.ssd1306.SSD1306;

import java.io.IOException;
import java.util.TimerTask;

public class MoistureTask extends TimerTask {

    private final Pin moistureSensor;
    private final Pin waterPump;
    private final SSD1306 display;
    private final Pin led;

    //Constructor
    public MoistureTask(Pin moistureSensor, Pin waterPump, SSD1306 display, Pin led){
        this.moistureSensor = moistureSensor;
        this.waterPump = waterPump;
        this.display = display;
        this.led = led;

    }




    //Object of Main class
    Main mainObj = new Main();


    @Override
    public void run(){
            if(led.getValue() == 1) {
                StateMachine();
            }

            else if(led.getValue() == 0){
                display.getCanvas().clear();
                display.display();

                display.getCanvas().setTextsize(1);
                display.getCanvas().drawString(0,20,"System Powering Off");
                display.display();
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //Exit program
                display.clear();
                display.display();
                System.exit(0);

            }
        }

        private double analogReading;
        private double voltageOffset = 0.2;
        private double conversionValue;

        public void StateMachine(){

            analogReading = (double)moistureSensor.getValue() * 5/1023;
            conversionValue = (analogReading + voltageOffset);

            mainObj.sensorValuesList.add(conversionValue);

            Double val = conversionValue;

            if (conversionValue > 3.3) {
                System.out.println("Soil is dry " + conversionValue);

                display.getCanvas().setTextsize(1);
                display.getCanvas().drawString(5, 0, "Soil is dry");
                display.display();

                display.getCanvas().setTextsize(2);
                display.getCanvas().drawString(5, 15, val.toString());
                display.display();

                try {

                    waterPump.setValue(1);

                } catch (IOException e) {

                    throw new RuntimeException(e);

                }
            } else if (analogReading <= 3.3) {
                System.out.println("Soil is wet " + conversionValue);

                display.getCanvas().setTextsize(1);
                display.getCanvas().drawString(5, 0, "Soil is wet");
                display.display();

                display.getCanvas().setTextsize(2);
                display.getCanvas().drawString(5, 15, val.toString());
                display.display();


                try {

                    waterPump.setValue(0);

                } catch (IOException e) {

                    throw new RuntimeException(e);
                }

            }

        }

    }



