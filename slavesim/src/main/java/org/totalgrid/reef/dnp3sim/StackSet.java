package org.totalgrid.reef.dnp3sim;


import org.totalgrid.reef.protocol.dnp3.*;

import java.util.*;

public class StackSet {

    private StackManager dnp = new StackManager(false);
    private Map<String, Stack> stackMap = new HashMap<String, Stack>();
    private ConsoleLogAdapter logAdapter = new ConsoleLogAdapter();

    public StackSet() {
        dnp.AddLogHook(logAdapter);
    }

    public List<Stack> getStacks() {
        List<Stack> stacks = new LinkedList<Stack>();
        for (Stack stack : stackMap.values()) {
            stacks.add(stack);
        }
        return stacks;
    }

    public void addStack(String name, String portName, StackConfig config) {
        CommandAcceptor cmdAcceptor = new CommandAcceptor();
        SlaveStackConfig cfg = config.buildConfig();
        IDataObserver obs = dnp.AddSlave(portName, name, FilterLevel.LEV_DEBUG, cmdAcceptor, cfg);

        stackMap.put(name, new Stack(obs, cmdAcceptor, config.getDimension()));
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
        PhysLayerSettings phys = new PhysLayerSettings(FilterLevel.LEV_DEBUG, 5000);
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

    public void start() {
        dnp.Start();
    }
    public void stop() {
        dnp.Stop();
    }
}
