package org.totalgrid.reef.dnp3sim;


import org.totalgrid.reef.dnp3sim.xml.SimNode;
import org.totalgrid.reef.dnp3sim.xml.SlaveArray;


public class XmlStackLoader {

    public static StackSet loadConfig(SimNode config) {
        StackSet stackSet = new StackSet();
        for (SlaveArray array : config.getSlaveArray()) {
            loadArray(array, stackSet);
        }
        return stackSet;
    }

    protected static void loadArray(SlaveArray array, StackSet stacks) {
        int startPort = (int) array.getStartPort();
        int portCount = (int) array.getCount();
        int statusCount = (int) array.getPointWidth().getStatus();
        int analogCount = (int) array.getPointWidth().getAnalog();
        int counterCount = (int) array.getPointWidth().getCounter();

        StackConfig config = new StackConfig(new DataDimension(statusCount, analogCount, counterCount));

        for (int i = 0; i < portCount; i++) {
            int port = startPort + i;
            String portName = "port" + port;
            stacks.addPort(portName, port);
            stacks.addStack("slave" + port, portName, config);
        }
    }
}
