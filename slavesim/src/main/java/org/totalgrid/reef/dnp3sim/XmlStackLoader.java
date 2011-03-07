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
import org.totalgrid.reef.dnp3sim.xml.SlaveArray;


public class XmlStackLoader {

    private XmlStackLoader() {}

    public static StackSet loadConfig(SimNode config) {
        StackSet stackSet = new StackSet();
        for (SlaveArray array : config.getSlaveArray()) {
            loadArray(array, stackSet);
        }
        return stackSet;
    }

    protected static void loadArray(SlaveArray array, StackSet stacks) {
        int startPort = (int) array.getStartPort();
        int portCount = (int) array.getCount();
        int statusCount = (int) array.getPointWidth().getStatus();
        int analogCount = (int) array.getPointWidth().getAnalog();
        int counterCount = (int) array.getPointWidth().getCounter();

        StackConfig config = new StackConfig(new DataDimension(statusCount, analogCount, counterCount));

        for (int i = 0; i < portCount; i++) {
            int port = startPort + i;
            String portName = "port" + port;
            stacks.addPort(portName, port);
            stacks.addStack("slave" + port, portName, config);
        }
    }
}
