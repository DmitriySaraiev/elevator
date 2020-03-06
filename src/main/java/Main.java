import domain.Building;

public class Main {

    public static void main(String[] args) {
        Building building = Building.getInstance();
        building.initialize();
        try {
            building.startElevator();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
