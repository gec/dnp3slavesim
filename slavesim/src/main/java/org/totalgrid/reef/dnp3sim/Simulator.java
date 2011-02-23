package org.totalgrid.reef.dnp3sim;


import org.totalgrid.reef.dnp3sim.xml.SimNode;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;

public class Simulator {

    public static SimNode loadXml(String file) {
        SimNode config = null;
        try {
            JAXBContext context = JAXBContext.newInstance(SimNode.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            FileReader reader = new FileReader(file);
            config = (SimNode)unmarshaller.unmarshal(reader);

        }  catch (JAXBException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return config;
    }

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Must include config file.");
            System.exit(1);
        }

        SimNode config = loadXml(args[0]);
        if (config == null) throw new RuntimeException("Could not load config.");

        SimulationManager mgr = new SimulationManager(config);

        mgr.start();

        System.out.println("Enter a line to quit.");
        try {
            System.in.read();
        } catch (IOException ex) {
            System.out.println("IOException: " + ex);
        }

        mgr.stop();
    }
}
