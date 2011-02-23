package org.totalgrid.reef.dnp3sim.reefconfig;

import org.totalgrid.reef.dnp3sim.xml.SimNode;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;


public class SimNodeReader {

    public static SimNode loadConfig(String file) {
        SimNode config = null;
        try {
            JAXBContext context = JAXBContext.newInstance(SimNode.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            FileReader reader = new FileReader(file);
            config = (SimNode) unmarshaller.unmarshal(reader);

        } catch (JAXBException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return config;
    }
}
