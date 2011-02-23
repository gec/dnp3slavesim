package org.totalgrid.reef.dnp3sim.generation;


import org.totalgrid.reef.loader.communications.*;
import org.totalgrid.reef.loader.configuration.Configuration;

import java.util.List;

public class CommModelGenerator {
    final private String address;
    final private String mask;
    final private int startPort;
    final private int endpointCount;

    final CommEquipmentGenerator equipmentGenerator;

    public CommModelGenerator(String address, String mask, int startPort, int endpointCount, CommEquipmentGenerator equipmentGenerator) {
        this.address = address;
        this.mask = mask;
        this.startPort = startPort;
        this.endpointCount = endpointCount;
        this.equipmentGenerator = equipmentGenerator;
    }

    public CommunicationsModel commModel() {
        CommunicationsModel model = new CommunicationsModel();

        for(int i = 0; i < endpointCount; i++) {
            final int port = startPort + i;
            model.getEndpoint().add(endpoint(port));
        }

        return model;
    }

    public Endpoint endpoint(int port) {
        final String intName = "int" + port;

        Endpoint end = new Endpoint();
        end.setName("end" + port);
        end.getType().add(buildType("Endpoint"));
        end.setProtocol(buildProtocol("dnp3"));
        end.setInterface(buildInterface(intName, address, mask, port, "any"));
        end.getEquipment().add(equipmentGenerator.equipment("equip" + port));
        return end;
    }

    public static Protocol buildProtocol(String name) {
        Protocol protocol = new Protocol();
        protocol.setName(name);
        return protocol;
    }

    public static Type buildType(String name) {
        Type typ = new Type();
        typ.setName(name);
        return typ;
    }

    public static Interface buildInterface(String name, String address, String mask, int port, String network) {
        Interface result = new Interface();
        result.setName(name);
        result.setIp(address);
        result.setNetmask(mask);
        result.setPort(port);
        result.setNetwork(network);
        return result;
    }
}
