package parking.facility;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import parking.ParkingLot;
import vehicle.Car;
import vehicle.Size;

import static org.junit.jupiter.api.Assertions.*;

public class GateTest {

    @Test
    public void testFindAnyAvailableSpaceForCar() {
        ParkingLot lot = new ParkingLot(2, 3);
        Gate gate = new Gate(lot);
        Car car = new Car("CAR123", Size.SMALL, 0);
        assertNotNull(gate.findAnyAvailableSpaceForCar(car));
    }

    @ParameterizedTest
    @CsvSource({
        "CAR1, SMALL, 1",
        "CAR2, LARGE, 2"
    })
    public void testFindPreferredAvailableSpaceForCar(String plate, Size size, int floor) {
        ParkingLot lot = new ParkingLot(3, 4);
        Gate gate = new Gate(lot);
        Car car = new Car(plate, size, floor);
        assertNotNull(gate.findPreferredAvailableSpaceForCar(car));
    }

    @ParameterizedTest
    @CsvSource({
        "CAR10, SMALL, 0",
        "CAR20, LARGE, 1"
    })
    public void testRegisterCar(String plate, Size size, int floor) {
        ParkingLot lot = new ParkingLot(2, 4);
        Gate gate = new Gate(lot);
        Car car = new Car(plate, size, floor);
        boolean registered = gate.registerCar(car);
        assertTrue(registered);
        assertNotNull(car.getTicketId());
        assertTrue(car.getTicketId().startsWith(plate));
    }

    @ParameterizedTest
    @CsvSource({
        "CARX, SMALL, 0",
        "CARY, LARGE, 1"
    })
    public void testDeRegisterCar(String plate, Size size, int floor) {
        ParkingLot lot = new ParkingLot(2, 4);
        Gate gate = new Gate(lot);
        Car car = new Car(plate, size, floor);
        assertTrue(gate.registerCar(car));
        gate.deRegisterCar(car.getTicketId());

        for (Space[] level : lot.getFloorPlan()) {
            for (Space space : level) {
                assertFalse(plate.equals(space.getCarLicensePlate()));
            }
        }
    }
}
