package parking;

import parking.facility.Space;
import vehicle.Car;
import vehicle.Size;

public class ParkingLot {
    private final Space[][] floorPlan;

    public ParkingLot(int floorNumber, int spaceNumber) {
        if (floorNumber < 1 || spaceNumber < 1) {
            throw new IllegalArgumentException("Floor and space numbers must be >= 1");
        }

        floorPlan = new Space[floorNumber][spaceNumber];
        for (int i = 0; i < floorNumber; i++) {
            for (int j = 0; j < spaceNumber; j++) {
                floorPlan[i][j] = new Space(i, j);
            }
        }
    }

    public Space[][] getFloorPlan() {
        return floorPlan;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Space[] floor : floorPlan) {
            for (int i = 0; i < floor.length; i++) {
                Space space = floor[i];

                if (!space.isTaken()) {
                    sb.append("X ");
                } else {
                    Size size = space.getOccupyingCarSize();
                    if (size == Size.LARGE) {
                        sb.append("L ");
                    } else {
                        sb.append("S ");
                    }
                }
            }
            sb.setLength(sb.length() - 1);
            sb.append("\n");
        }

        return sb.toString().trim();
    }
}
