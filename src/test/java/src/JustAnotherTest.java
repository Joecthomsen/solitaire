package src;

public class JustAnotherTest {
    public static void main(String[] args) {
        Simulation simulation = new Simulation(1000, 500, true);
        TestResult testResult = simulation.runSimulation();
        System.out.println(testResult.gamesWon + "    "  + testResult.gamesLost);
    }
}
