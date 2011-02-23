package org.totalgrid.reef.dnp3sim;


import org.totalgrid.reef.dnp3sim.xml.SimNode;
import org.totalgrid.reef.dnp3sim.xml.SlaveArray;

public class SimulationManager {
    private SimNode config;
    private StackSet stacks = new StackSet();


    public void start() {
        loadConfig();
    }

    public void stop() {
        stacks.removeAllStacks();
        stacks.removeAllPorts();
        stacks.stop();
    }

    public void loadConfig() {
        for(SlaveArray array : config.getSlaveArray()) {
            loadArray(array);
        }
    }

    public void loadArray(SlaveArray array) {
        int startPort = (int)array.getStartPort();
        int portCount = (int)array.getCount();
        int statusCount = (int)array.getPointWidth().getStatus();
        int analogCount = (int)array.getPointWidth().getAnalog();
        int counterCount = (int)array.getPointWidth().getCounter();

        StackConfig config = new StackConfig(statusCount, analogCount, counterCount);

        for(int i = 0; i < portCount; i++) {
            int port = startPort + i;
            String portName = "port" + port;
            stacks.addPort(portName, port);
            stacks.addStack("slave" + port, portName, config);
        }
    }

}
