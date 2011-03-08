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


import org.totalgrid.reef.dnp3sim.xml.SimNode;
import org.totalgrid.reef.dnp3sim.xml.SlaveArray;

import java.util.LinkedList;
import java.util.List;

public final class SimNodeGenerator {

    private SimNodeGenerator() {}

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
