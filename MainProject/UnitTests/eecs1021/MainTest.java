package eecs1021;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    @Test
    void testSensorValuesList() {
        // Create a sample list of sensor values
        ArrayList<Double> expectedValues = new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0));

        // Call the method that populates the sensorValuesList
        Main.populateSensorValues(expectedValues);

        // Get the sensorValuesList
        ArrayList<Double> actualValues = Main.getSensorValuesList();

        // Assert that the actual values match the expected values
        assertEquals(expectedValues, actualValues, "SensorValuesList should match expected values");
    }
}
