package parking;

import org.junit.jupiter.api.Test;
import parking.facility.Gate;
import vehicle.Car;
import vehicle.Size;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotTest {

    @Test
    public void testConstructorWithInvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(0, 2));
        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(3, 0));
        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(0, 0));
    }

    @Test
    public void testTextualRepresentation() {
        ParkingLot lot = new ParkingLot(3, 5);
        Gate gate = new Gate(lot);

        gate.registerCar(new Car("S1", Size.SMALL, 0));  
        gate.registerCar(new Car("S2", Size.SMALL, 1));  
        gate.registerCar(new Car("L1", Size.LARGE, 1));  
        gate.registerCar(new Car("S3", Size.SMALL, 2));  
        gate.registerCar(new Car("L2", Size.LARGE, 2)); 

        String[] lines = lot.toString().split("\n");
        assertEquals(3, lines.length);

        assertTrue(lines[0].contains("S") || lines[1].contains("S") || lines[2].contains("S"));
        assertTrue(lines[1].contains("L") || lines[2].contains("L"));
    }
}
