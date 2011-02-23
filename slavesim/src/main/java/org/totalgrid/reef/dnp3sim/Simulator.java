package org.totalgrid.reef.dnp3sim;


import org.totalgrid.reef.protocol.dnp3.*;

import java.io.IOException;

public class Simulator {


    public static void main(String[] args) {

        StackSet set = new StackSet();

        StackConfig config = new StackConfig(5, 5, 5);

        set.addPort("port01", 50000);
        set.addStack("slave01", "port01", config);

        System.out.println("Enter a line to quit.");
        try {
            System.in.read();
        } catch (IOException ex) {
            System.out.println("IOException: " + ex);
        }

        set.removeAllStacks();
        set.removeAllPorts();
        set.stop();

    }
}
