package org.totalgrid.reef.dnp3sim;


import org.totalgrid.reef.protocol.dnp3.*;

import java.io.IOException;

public class Simulator {


    public static void main(String[] args) {
        System.out.println("main");

        //System.loadLibrary("dnp3java");

        SlaveConfig slaveConfig = new SlaveConfig(); // defaults okay

        DeviceTemplate template = new DeviceTemplate(20, 20, 20, 20);

        AppConfig appConfig = new AppConfig(); // defaults okay

        LinkConfig linkConfig = new LinkConfig(false, false, 0, 1, 100, 5000);

        SlaveStackConfig config = new SlaveStackConfig();
        config.setLink(linkConfig);
        config.setApp(appConfig);
        config.setDevice(template);
        config.setSlave(slaveConfig);

        StackManager dnp = new StackManager(true);
        dnp.AddLogHook(new ConsoleLogAdapter());

        PhysLayerSettings phys = new PhysLayerSettings(FilterLevel.LEV_INFO, 5000);
        dnp.AddTCPServer("port01", phys, "127.0.0.1", 50000);

        CommandAcceptor cmdAcceptor = new CommandAcceptor();

        IDataObserver obs = dnp.AddSlave("port01", "slave01", FilterLevel.LEV_INFO, cmdAcceptor, config);

        System.out.println("Enter a line to quit.");
        try {
            System.in.read();
        } catch (IOException ex) {
            System.out.println("IOException: " + ex);
        }

        dnp.RemoveStack("slave01");
        dnp.RemovePort("port01");
    }
}
