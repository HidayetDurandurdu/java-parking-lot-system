package parking.facility;

import parking.ParkingLot;
import vehicle.Car;
import vehicle.Size;

import java.util.ArrayList;

public class Gate {
    private final ArrayList<Car> cars = new ArrayList<>();
    private final ParkingLot parkingLot;

    public Gate(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    private Space findTakenSpaceByCar(Car c) {
        for (Space[] floor : parkingLot.getFloorPlan()) {
            for (Space space : floor) {
                if (space.isTaken() && c.equals(space.getCarLicensePlate() != null ? c : null)) {
                    if (space.getCarLicensePlate().equals(c.getLicensePlate())) {
                        return space;
                    }
                }
            }
        }
        return null;
    }

    private Space findAvailableSpaceOnFloor(int floor, Car c) {
        Space[] spaces = parkingLot.getFloorPlan()[floor];
        if (c.getSpotOccupation() == Size.SMALL) {
            for (Space space : spaces) {
                if (!space.isTaken()) {
                    return space;
                }
            }
        } else {
            for (int i = 1; i < spaces.length; i++) {
                if (!spaces[i].isTaken() && !spaces[i - 1].isTaken()) {
                    return spaces[i];
                }
            }
        }
        return null;
    }

    public Space findAnyAvailableSpaceForCar(Car c) {
        Space[][] plan = parkingLot.getFloorPlan();
        for (int i = 0; i < plan.length; i++) {
            Space found = findAvailableSpaceOnFloor(i, c);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    public Space findPreferredAvailableSpaceForCar(Car c) {
        int preferred = c.getPreferredFloor();
        Space[][] plan = parkingLot.getFloorPlan();
        int total = plan.length;
        if (preferred >= total) return null;

        int offset = 0;
        while (offset < total) {
            int floorDown = preferred - offset;
            if (floorDown >= 0) {
                Space s = findAvailableSpaceOnFloor(floorDown, c);
                if (s != null) return s;
            }

            int floorUp = preferred + offset;
            if (offset != 0 && floorUp < total) {
                Space s = findAvailableSpaceOnFloor(floorUp, c);
                if (s != null) return s;
            }

            offset++;
        }
        return null;
    }

    public boolean registerCar(Car c) {
        Space space = findPreferredAvailableSpaceForCar(c);
        if (space == null) return false;

        c.setTicketId(c.getLicensePlate() + "-T");
        int floor = space.getFloorNumber();
        int num = space.getSpaceNumber();

        if (c.getSpotOccupation() == Size.SMALL) {
            parkingLot.getFloorPlan()[floor][num].addOccupyingCar(c);
        } else {
            parkingLot.getFloorPlan()[floor][num - 1].addOccupyingCar(c);
            parkingLot.getFloorPlan()[floor][num].addOccupyingCar(c);
        }

        cars.add(c);
        return true;
    }

    public void registerCars(Car... cars) {
        for (Car c : cars) {
            Space space = findAnyAvailableSpaceForCar(c);
            if (space == null) {
                System.err.println("No space found for car: " + c.getLicensePlate());
                continue;
            }

            c.setTicketId(c.getLicensePlate() + "-T");
            int floor = space.getFloorNumber();
            int num = space.getSpaceNumber();

            if (c.getSpotOccupation() == Size.SMALL) {
                parkingLot.getFloorPlan()[floor][num].addOccupyingCar(c);
            } else {
                parkingLot.getFloorPlan()[floor][num - 1].addOccupyingCar(c);
                parkingLot.getFloorPlan()[floor][num].addOccupyingCar(c);
            }

            this.cars.add(c);
        }
    }

    public void deRegisterCar(String ticketId) {
        Car target = null;
        for (Car c : cars) {
            if (ticketId.equals(c.getTicketId())) {
                target = c;
                break;
            }
        }
        if (target == null) return;

        for (Space[] floor : parkingLot.getFloorPlan()) {
            for (Space space : floor) {
                if (space.isTaken() && target.getLicensePlate().equals(space.getCarLicensePlate())) {
                    space.removeOccupyingCar();
                }
            }
        }
        cars.remove(target);
    }
}
