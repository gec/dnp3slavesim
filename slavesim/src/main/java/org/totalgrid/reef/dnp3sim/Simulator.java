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
package org.totalgrid.reef.dnp3sim;


import org.totalgrid.reef.dnp3sim.xml.SimNode;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;

public class Simulator {
    private Simulator() {}

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

    public static void printUsage() {
        System.out.println("Usage: <config file> <update rate (Hz)>");
    }

    public static void main(String[] args) {
        double updateRate = 2;

        if (args.length < 1) {
            printUsage();
            System.exit(1);
        }

        SimNode config = loadXml(args[0]);
        if (config == null) {
            System.out.println("Could not load config.");
            System.exit(1);
        }

        if (args.length > 1) {
            updateRate = Double.parseDouble(args[1]);
        }


        StackSet stackSet = XmlStackLoader.loadConfig(config);

        PointUpdater updater = new PointUpdater(stackSet.getStacks(), updateRate);

        System.out.println(" - Devices: " + stackSet.getStacks().size());
        System.out.println(" - Points: " + updater.getPointCount());
        System.out.println(" - Update rate: " + updateRate + " updates per second per point.");

        SimulationManager mgr = new SimulationManager(stackSet, updater);

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
