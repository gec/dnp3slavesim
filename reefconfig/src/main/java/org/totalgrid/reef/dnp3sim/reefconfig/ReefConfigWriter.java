package org.totalgrid.reef.dnp3sim.reefconfig;


import org.totalgrid.reef.loader.configuration.Configuration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileWriter;
import java.io.IOException;

public class ReefConfigWriter {

    public static void write(String file, Configuration config) {
        try {
            JAXBContext context = JAXBContext.newInstance(Configuration.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(config, new FileWriter(file));
        } catch (JAXBException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
