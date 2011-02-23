package org.totalgrid.reef.dnp3sim;


import org.totalgrid.reef.protocol.dnp3.*;

import java.util.*;

public class StackSet {

    public class StackItems {
        private IDataObserver observer;
        private CommandAcceptor acceptor;

        public IDataObserver getObserver() {
            return observer;
        }

        public CommandAcceptor getAcceptor() {
            return acceptor;
        }

        public StackItems(IDataObserver observer, CommandAcceptor acceptor) {
            this.observer = observer;
            this.acceptor = acceptor;
        }

    }

    private StackManager dnp = new StackManager(true);
    private Map<String, StackItems> stackMap = new HashMap<String, StackItems>();
    private ConsoleLogAdapter logAdapter = new ConsoleLogAdapter();

    public StackSet() {
        dnp.AddLogHook(logAdapter);
    }

    public void addStack(String name, String portName, StackConfig config) {
        CommandAcceptor cmdAcceptor = new CommandAcceptor();
        SlaveStackConfig cfg = config.buildConfig();
        IDataObserver obs = dnp.AddSlave(portName, name, FilterLevel.LEV_DEBUG, cmdAcceptor, cfg);

        stackMap.put(name, new StackItems(obs, cmdAcceptor));
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

    public void stop() {
        dnp.Stop();
    }
}
