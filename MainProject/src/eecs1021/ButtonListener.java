package eecs1021;

import org.firmata4j.IOEvent;
import org.firmata4j.Pin;
import org.firmata4j.IODeviceEventListener;
import org.firmata4j.ssd1306.SSD1306;

import java.io.IOException;

public class ButtonListener implements IODeviceEventListener {

    private final Pin button;
    private final Pin led;
    private final SSD1306 display;

    public ButtonListener(Pin button, Pin led, SSD1306 display){
        this.button = button;
        this.led = led;
        this.display = display;
    }

    private int systemState = 1;

    @Override
    public void onPinChange(IOEvent event){

        if(button.getValue() == 1){
            systemState = (systemState == 1) ? 0 : 1;

        }

        if(systemState == 0){
            try {
                led.setValue(0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    @Override
    public void onStart(IOEvent event) {}
    @Override
    public void onStop(IOEvent event) {}
    @Override
    public void onMessageReceive(IOEvent event, String message) {}


}
