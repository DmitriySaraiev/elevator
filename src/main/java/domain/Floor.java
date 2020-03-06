package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Floor {

    private final static int MIN_INITIAL_NUMBER_OF_PASSENGERS = 0;
    private final static int MAX_INITIAL_NUMBER_OF_PASSENGERS = 10;
    private int number;
    private boolean isUpButtonPressed;
    private boolean isDownButtonPressed;
    private List<Passenger> movingUpPassengers;
    private List<Passenger> movingDownPassengers;

    public Floor() {
        movingUpPassengers = new ArrayList<>();
        movingDownPassengers = new ArrayList<>();
    }

    public Floor(int floorNumber) {
        this.number = floorNumber;
        movingUpPassengers = new ArrayList<>();
        movingDownPassengers = new ArrayList<>();
    }

    public void initialize() {
        int numberOfPassengers = MIN_INITIAL_NUMBER_OF_PASSENGERS +
                (int) (Math.random() * (MAX_INITIAL_NUMBER_OF_PASSENGERS - MIN_INITIAL_NUMBER_OF_PASSENGERS) + 1);
        for (int i = 0; i < numberOfPassengers; i++) {
            Passenger passenger = new Passenger(this);
            if (passenger.isMovingUp())
                movingUpPassengers.add(passenger);
            else
                movingDownPassengers.add(passenger);
        }
    }

    public boolean isAnyButtonPressed() {
        return isUpButtonPressed || isDownButtonPressed;
    }

    public int getNumber() {
        return number;
    }

    public boolean isUpButtonPressed() {
        return isUpButtonPressed;
    }

    public boolean isDownButtonPressed() {
        return isDownButtonPressed;
    }

    public List<Passenger> getMovingUpPassengers() {
        return movingUpPassengers;
    }

    public List<Passenger> getMovingDownPassengers() {
        return movingDownPassengers;
    }

    public void setUpButtonPressed(boolean upButtonPressed) {
        isUpButtonPressed = upButtonPressed;
    }

    public void setDownButtonPressed(boolean downButtonPressed) {
        isDownButtonPressed = downButtonPressed;
    }

    public void setMovingUpPassengers(List<Passenger> movingUpPassengers) {
        this.movingUpPassengers = movingUpPassengers;
    }

    public void setMovingDownPassengers(List<Passenger> movingDownPassengers) {
        this.movingDownPassengers = movingDownPassengers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Floor floor = (Floor) o;
        return number == floor.number &&
                isUpButtonPressed == floor.isUpButtonPressed &&
                isDownButtonPressed == floor.isDownButtonPressed &&
                Objects.equals(movingUpPassengers, floor.movingUpPassengers) &&
                Objects.equals(movingDownPassengers, floor.movingDownPassengers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, isUpButtonPressed, isDownButtonPressed, movingUpPassengers, movingDownPassengers);
    }

    @Override
    public String toString() {
        return (number + 1) + ". " +
                ", isUpButtonPressed=" + isUpButtonPressed +
                ", isDownButtonPressed=" + isDownButtonPressed +
                ", movingUpPassengers=" + movingUpPassengers +
                ", \nmovingDownPassengers=" + movingDownPassengers +
                '}';
    }
}
