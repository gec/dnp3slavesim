/**
 * Copyright 2011 Green Energy Corp.
 *
 * Licensed to Green Energy Corp (www.greenenergycorp.com) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  Green Energy Corp licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.totalgrid.reef.dnp3sim.reefconfig;


import org.totalgrid.reef.loader.communications.*;

import java.util.LinkedList;
import java.util.List;

public class EndpointGenerator {
    final private String address;
    final private String mask;
    final private int startPort;
    final private int endpointCount;

    final private CommEquipmentGenerator equipmentGenerator;

    public EndpointGenerator(String address, String mask, int startPort, int endpointCount, CommEquipmentGenerator equipmentGenerator) {
        this.address = address;
        this.mask = mask;
        this.startPort = startPort;
        this.endpointCount = endpointCount;
        this.equipmentGenerator = equipmentGenerator;
    }

    public List<Endpoint> allEndpoints() {
        List<Endpoint> list = new LinkedList<Endpoint>();
        for(int i = 0; i < endpointCount; i++) {
            final int port = startPort + i;
            list.add(endpoint(port));
        }
        return list;
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

        ConfigFile file = new ConfigFile();
        file.setName("simdnpmaster.xml");
        protocol.getConfigFile().add(file);

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
