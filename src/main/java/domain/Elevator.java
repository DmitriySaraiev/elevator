package domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Elevator {

    private final static int ELEVATOR_CAPACITY = 5;
    private Floor currentFloor;
    private Floor finalFloor;
    private List<Passenger> passengers;
    private boolean isMovingUp = true;

    public Elevator() {
        passengers = new ArrayList<>(ELEVATOR_CAPACITY);
        currentFloor = Building.getInstance().getFloors().get(0);
    }

    public void loadPassengers() {
        if (passengers.isEmpty() &&
                currentFloor.getMovingUpPassengers().isEmpty() &&
                !currentFloor.getMovingDownPassengers().isEmpty())
            isMovingUp = false;
        else if (passengers.isEmpty() &&
                currentFloor.getMovingDownPassengers().isEmpty() &&
                !currentFloor.getMovingUpPassengers().isEmpty())
            isMovingUp = true;
        if (isMovingUp) {
            while (passengers.size() < ELEVATOR_CAPACITY && currentFloor.getMovingUpPassengers().size() > 0) {
                passengers.add(currentFloor.getMovingUpPassengers().get(0));
                currentFloor.getMovingUpPassengers().remove(0);
            }
        } else {
            while (passengers.size() < ELEVATOR_CAPACITY && currentFloor.getMovingDownPassengers().size() > 0) {
                passengers.add(currentFloor.getMovingDownPassengers().get(0));
                currentFloor.getMovingDownPassengers().remove(0);
            }
        }
        if (currentFloor.getMovingUpPassengers().size() == 0)
            currentFloor.setUpButtonPressed(false);
        if (currentFloor.getMovingDownPassengers().size() == 0)
            currentFloor.setDownButtonPressed(false);
        calculateFinalFloor();
    }

    public void unloadPassengers() {
        Iterator iterator = passengers.iterator();
        while (iterator.hasNext()) {
            Passenger passenger = (Passenger) iterator.next();
            if (passenger.getDestinationFloor().getNumber() == currentFloor.getNumber()) {
                passenger.updateAfterArrival();
                iterator.remove();
            }
        }
    }

    public boolean shouldStop() {
        for (Passenger passenger : passengers) {
            if (passenger.getDestinationFloor().equals(currentFloor))
                return true;
        }
        if (!isFull()) {
            if (isMovingUp) {
                return (currentFloor.getMovingUpPassengers().size() > 0);
            } else {
                return (currentFloor.getMovingDownPassengers().size() > 0);
            }
        }
        return false;
    }

    private void calculateFinalFloor() {
        int finalFloorNumber = currentFloor.getNumber();
        if (isMovingUp) {
            for (Passenger passenger : passengers) {
                if (finalFloorNumber < passenger.getDestinationFloor().getNumber()) {
                    finalFloorNumber = passenger.getDestinationFloor().getNumber();
                    finalFloor = passenger.getDestinationFloor();
                }
            }
        } else {
            for (Passenger passenger : passengers) {
                if (finalFloorNumber > passenger.getDestinationFloor().getNumber()) {
                    finalFloorNumber = passenger.getDestinationFloor().getNumber();
                    finalFloor = passenger.getDestinationFloor();
                }
            }
        }
    }

    private boolean isFull() {
        return passengers.size() >= ELEVATOR_CAPACITY;
    }

    public void moveUp() {
        setCurrentFloor(Building.getInstance().getFloors().get(currentFloor.getNumber() + 1));
    }

    public void moveDown() {
        setCurrentFloor(Building.getInstance().getFloors().get(currentFloor.getNumber() - 1));
    }

    public Floor getCurrentFloor() {
        return currentFloor;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public boolean isMovingUp() {
        return isMovingUp;
    }

    public Floor getFinalFloor() {
        return finalFloor;
    }

    public void setCurrentFloor(Floor currentFloor) {
        this.currentFloor = currentFloor;
    }

    public void setMovingUp(boolean movingUp) {
        isMovingUp = movingUp;
    }

    public void setFinalFloor(Floor finalFloor) {
        this.finalFloor = finalFloor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Elevator elevator = (Elevator) o;
        return isMovingUp == elevator.isMovingUp &&
                currentFloor.equals(elevator.currentFloor) &&
                passengers.equals(elevator.passengers) &&
                finalFloor.equals(elevator.finalFloor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentFloor, passengers, isMovingUp, finalFloor);
    }
}
