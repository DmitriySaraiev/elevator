package domain;

public class Passenger {

    private Floor currentFloor;
    private Floor destinationFloor;
    private boolean isMovingUp;

    public Passenger(Floor currentFloor) {
        this.currentFloor = currentFloor;
        destinationFloor = Building.getInstance().getFloors().get(generateDestinationFloorNumber());
        isMovingUp = (destinationFloor.getNumber() > currentFloor.getNumber());
        pressDirectionButton(currentFloor);
    }


    /**
     * After arriving on destination floor,
     * new random currentFloor and destinationFloor values are assigned to passenger
     */
    public void updateAfterArrival() {
        currentFloor = Building.getInstance().getFloors().get((int) (Math.random() * Building.getInstance().getNumberOfFloors()));
        destinationFloor = Building.getInstance().getFloors().get(generateDestinationFloorNumber());
        isMovingUp = (destinationFloor.getNumber() > currentFloor.getNumber());
        if (isMovingUp)
            currentFloor.getMovingUpPassengers().add(this);
        else
            currentFloor.getMovingDownPassengers().add(this);
        pressDirectionButton(currentFloor);
    }

    private void pressDirectionButton(Floor floor) {
        if (isMovingUp)
            floor.setUpButtonPressed(true);
        else
            floor.setDownButtonPressed(true);
    }

    private int generateDestinationFloorNumber() {
        int destinationFloorNumber;
        do {
            destinationFloorNumber = (int) (Math.random() * Building.getInstance().getNumberOfFloors());
        }
        while (destinationFloorNumber == currentFloor.getNumber());
        return destinationFloorNumber;
    }

    public Floor getCurrentFloor() {
        return currentFloor;
    }

    public Floor getDestinationFloor() {
        return destinationFloor;
    }

    public boolean isMovingUp() {
        return isMovingUp;
    }

    public void setCurrentFloor(Floor currentFloor) {
        this.currentFloor = currentFloor;
    }

    public void setDestinationFloor(Floor destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public void setMovingUp(boolean movingUp) {
        isMovingUp = movingUp;
    }

    @Override
    public String toString() {
        return "\ndomain.Passenger{" +
                "currentFloorNumber=" + currentFloor.getNumber() +
                ", destinationFloorNumber=" + destinationFloor.getNumber() +
                ", isMovingUp=" + isMovingUp +
                '}';
    }
}
