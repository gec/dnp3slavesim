package org.totalgrid.reef.dnp3sim;


public class SimulationManager {
    private StackSet stackSet;
    private PointUpdater updater;

    public SimulationManager(StackSet stackSet, PointUpdater updater) {
        this.stackSet = stackSet;
        this.updater = updater;
    }

    public void start() {
        System.out.println("Starting sim");
        System.out.println(" - Devices: " + stackSet.getStacks().size());
        System.out.println(" - Points: " + updater.getPointCount());
        stackSet.start();
        updater.start();
    }

    public void stop() {
        System.out.println("Ending simulation.");
        updater.stop();
        stackSet.removeAllStacks();
        stackSet.removeAllPorts();
        stackSet.stop();
    }
}
