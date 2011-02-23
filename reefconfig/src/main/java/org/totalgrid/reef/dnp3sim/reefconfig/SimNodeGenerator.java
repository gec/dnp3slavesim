package org.totalgrid.reef.dnp3sim.reefconfig;


import org.totalgrid.reef.dnp3sim.xml.SimNode;
import org.totalgrid.reef.dnp3sim.xml.SlaveArray;

import java.util.LinkedList;
import java.util.List;

public class SimNodeGenerator {

    public static void printUsage() {
        System.out.println("Usage: <input file> <output file>");
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            printUsage();
            System.exit(1);
        }

        final String inputFile = args[0];
        final String outputFile = args[1];

        SimNode node = SimNodeReader.loadConfig(inputFile);

        CommModelGenerator commModelGenerator = nodeToCommModel(node);
        ConfigurationGenerator generator = new ConfigurationGenerator(commModelGenerator, new EquipModelGenerator());

        ReefConfigWriter.write(outputFile, generator.build());
    }

    public static CommModelGenerator nodeToCommModel(SimNode node) {
        List<EndpointGenerator> list = new LinkedList<EndpointGenerator>();
        for (SlaveArray array : node.getSlaveArray()) {
            list.add(arrayEndpoints(array));
        }
        return new CommModelGenerator(list);
    }

    public static EndpointGenerator arrayEndpoints(SlaveArray array) {
        int startPort = (int)array.getStartPort();
        int portCount = (int)array.getCount();
        int statusCount = (int)array.getPointWidth().getStatus();
        int analogCount = (int)array.getPointWidth().getAnalog();
        int counterCount = (int)array.getPointWidth().getCounter();

        CommEquipmentGenerator commEquipmentGenerator = new CommEquipmentGenerator(statusCount, analogCount, counterCount);
        return new EndpointGenerator("127.0.0.1", "255.255.255.0", startPort, portCount, commEquipmentGenerator);
    }

}
