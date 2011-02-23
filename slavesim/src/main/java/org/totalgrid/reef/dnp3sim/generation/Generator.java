package org.totalgrid.reef.dnp3sim.generation;


import org.totalgrid.reef.loader.configuration.Configuration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileWriter;
import java.io.IOException;

public class Generator {

    public static void main(String[] args) {

        CommEquipmentGenerator commEquipmentGenerator = new CommEquipmentGenerator(5, 5, 5);
        CommModelGenerator commModelGenerator = new CommModelGenerator("127.0.0.1", "255.255.255.0", 50000, 4, commEquipmentGenerator);

        ConfigurationGenerator generator = new ConfigurationGenerator(commModelGenerator, new EquipModelGenerator());

        Configuration config = generator.build();

        try {
            JAXBContext context = JAXBContext.newInstance(Configuration.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(config, new FileWriter("example01.xml"));
        } catch (JAXBException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }
}
