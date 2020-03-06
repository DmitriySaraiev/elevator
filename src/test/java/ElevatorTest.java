import domain.Building;
import domain.Elevator;
import domain.Floor;
import domain.Passenger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


import java.util.ArrayList;

public class ElevatorTest {

    private Building building;
    private Elevator elevator;

    @Before
    public void initialize() {
        building = Building.getInstance();
        building.initialize();
        elevator = building.getElevator();
    }

    @Test
    public void testNumberOfPassengers() {
        Floor floor = building.getFloors().get(0);
        floor.setMovingUpPassengers(new ArrayList<>());
        for (int i = 0; i < 10; i++) {
            floor.getMovingUpPassengers().add(new Passenger(floor));
        }
        elevator.setCurrentFloor(floor);
        elevator.setMovingUp(true);
        elevator.loadPassengers();
        assertTrue(elevator.getPassengers().size() == 5);
        assertTrue(floor.getMovingUpPassengers().size() == 5);
    }

    @Test
    public void testPassengersNumberInBuildingConsistency() {
        int numberOfPassengersAfterInit = 0;
        int numberOfPassengersAfterProcess = 0;
        for (Floor floor : building.getFloors()) {
            for (Passenger passenger : floor.getMovingDownPassengers())
                numberOfPassengersAfterInit++;
            for (Passenger passenger : floor.getMovingUpPassengers())
                numberOfPassengersAfterInit++;
        }
        for (int i = 0; i < 20; i++) {
            if (elevator.shouldStop()) {
                elevator.unloadPassengers();
                elevator.loadPassengers();
            }
            if (elevator.getPassengers().isEmpty())
                elevator.setFinalFloor(building.findClosestRequestedFloor(elevator.getCurrentFloor()));
            if (elevator.isMovingUp())
                elevator.moveUp();
            else
                elevator.moveDown();
        }
        for (Floor floor : building.getFloors()) {
            for (Passenger passenger : floor.getMovingDownPassengers())
                numberOfPassengersAfterProcess++;
            for (Passenger passenger : floor.getMovingUpPassengers())
                numberOfPassengersAfterProcess++;
        }
        for(Passenger passenger : elevator.getPassengers())
            numberOfPassengersAfterProcess++;
        assertEquals(numberOfPassengersAfterInit, numberOfPassengersAfterProcess);
    }
}
