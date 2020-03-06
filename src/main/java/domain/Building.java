package domain;

import java.util.ArrayList;
import java.util.List;

public class Building {

    private final static int MIN_NUMBER_OF_FLOORS = 5;
    private final static int MAX_NUMBER_OF_FLOORS = 20;
    private int numberOfFloors;
    private List<Floor> floors;
    private Elevator elevator;
    private static Building instance;

    private Building() {
        this.numberOfFloors = MIN_NUMBER_OF_FLOORS + (int) (Math.random() * (MAX_NUMBER_OF_FLOORS - MIN_NUMBER_OF_FLOORS) + 1);
        floors = new ArrayList<>();
    }

    public void initialize() {
        for (int i = 0; i < numberOfFloors; i++) {
            Floor floor = new Floor(i);
            floors.add(floor);
        }
        for (Floor floor : floors) {
            floor.initialize();
        }
        elevator = new Elevator();
    }


    /**
     * Elevator simulation loop method
     *
     * @throws InterruptedException
     */
    public void startElevator() throws InterruptedException {
        elevator.loadPassengers();
        int step = 1;
        while (true) {
            Thread.sleep(1000);
            if (elevator.shouldStop()) {
                elevator.unloadPassengers();
                elevator.loadPassengers();
            }
            if (elevator.getPassengers().isEmpty())
                elevator.setFinalFloor(findClosestRequestedFloor(elevator.getCurrentFloor()));
            printResult(step++);
            if (elevator.isMovingUp())
                elevator.moveUp();
            else
                elevator.moveDown();
        }
    }

    public Floor findClosestRequestedFloor(Floor currentFloor) {
        Floor destinationFloor = floors.get(0);
        for (int i = 1; i < floors.size(); i++) {
            if(currentFloor.getNumber() + i < floors.size())
                if(floors.get(currentFloor.getNumber() + i).isAnyButtonPressed()) {
                    elevator.setMovingUp(true);
                    destinationFloor = floors.get(currentFloor.getNumber() + i);
                }
            if(currentFloor.getNumber() - i >= 0)
                if(floors.get(currentFloor.getNumber() - i).isAnyButtonPressed()) {
                    elevator.setMovingUp(false);
                    destinationFloor = floors.get(currentFloor.getNumber() - i);
                }
        }
        return destinationFloor;
    }

    private void printResult(int step) {
        System.out.println("*** Step " + step++ + " ***");
        for (int i = floors.size() - 1; i >= 0; i--) {
            System.out.print(floors.get(i).getNumber() + ". ");
            for (Passenger passenger : floors.get(i).getMovingUpPassengers())
                System.out.print("^" + passenger.getDestinationFloor().getNumber() + " ");
            for (Passenger passenger : floors.get(i).getMovingDownPassengers())
                System.out.print("v" + passenger.getDestinationFloor().getNumber() + " ");
            if (floors.get(i).equals(elevator.getCurrentFloor())) {
                System.out.print("||");
                if (elevator.isMovingUp())
                    System.out.print("^ ");
                else
                    System.out.print("v ");
                for (Passenger passenger : elevator.getPassengers())
                    System.out.print(passenger.getDestinationFloor().getNumber() + " ");
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public Elevator getElevator() {
        return elevator;
    }

    public static Building getInstance() {
        if (instance == null)
            instance = new Building();
        return instance;
    }

    @Override
    public String toString() {
        return "domain.Building{" +
                "floors=" + floors +
                '}';
    }
}
