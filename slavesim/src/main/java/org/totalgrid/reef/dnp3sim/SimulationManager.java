package org.totalgrid.reef.dnp3sim;


public class SimulationManager {
    private StackSet stackSet;
    private PointUpdater updater;

    public SimulationManager(StackSet stackSet, PointUpdater updater) {
        this.stackSet = stackSet;
        this.updater = updater;
    }

    public void start() {
        stackSet.start();
        updater.start();
    }

    public void stop() {
        updater.stop();
        stackSet.removeAllStacks();
        stackSet.removeAllPorts();
        stackSet.stop();
    }
}
