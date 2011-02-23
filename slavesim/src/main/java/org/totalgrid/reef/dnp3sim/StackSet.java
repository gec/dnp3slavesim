package org.totalgrid.reef.dnp3sim;


import org.totalgrid.reef.protocol.dnp3.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class StackSet {

    private StackManager dnp = new StackManager(true);
    private Map<String, IDataObserver> stackMap = new HashMap<String, IDataObserver>();

    public StackSet() {
        dnp.AddLogHook(new ConsoleLogAdapter());
    }

    public void addStack(String name, String portName, StackConfig config) {
        CommandAcceptor cmdAcceptor = new CommandAcceptor();
        IDataObserver obs = dnp.AddSlave(portName, name, FilterLevel.LEV_INFO, cmdAcceptor, config.buildConfig());

        stackMap.put(name, obs);
    }

    public void removeStack(String name) {
        stackMap.remove(name);
        dnp.RemoveStack(name);
    }

    public void removeAllStacks() {
        for(String name : stackMap.keySet()) {
            dnp.RemoveStack(name);
        }
        stackMap.clear();
    }

    public void addPort(String name, int port) {
        PhysLayerSettings phys = new PhysLayerSettings(FilterLevel.LEV_INFO, 5000);
        dnp.AddTCPServer(name, phys, "127.0.0.1", port);
    }

    public void removePort(String name) {
        dnp.RemovePort(name);
    }

    public void removeAllPorts() {
        VectorOfString vec = dnp.GetPortNames();
        for (int i = 0; i < vec.size(); i++) {
            dnp.RemovePort(vec.get(i));
        }
    }

    public void stop() {
        dnp.Stop();
    }
}
