package org.totalgrid.reef.dnp3sim.reefconfig;


import org.totalgrid.reef.loader.communications.CommunicationsModel;
import org.totalgrid.reef.loader.configuration.Configuration;
import org.totalgrid.reef.loader.equipment.EquipmentModel;

public class ConfigurationGenerator {
    final CommModelGenerator commModelGenerator;
    final EquipModelGenerator equipModelGenerator;

    public ConfigurationGenerator(CommModelGenerator commModelGenerator, EquipModelGenerator equipModelGenerator) {
        this.commModelGenerator = commModelGenerator;
        this.equipModelGenerator = equipModelGenerator;
    }

    public Configuration build() {
        Configuration config = new Configuration();

        CommunicationsModel comms = commModelGenerator.model();
        config.setCommunicationsModel(comms);

        EquipmentModel equip = equipModelGenerator.fromCommModel(comms);
        config.setEquipmentModel(equip);

        return config;
    }
}
