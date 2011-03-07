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


import org.totalgrid.reef.protocol.dnp3.*;

import java.util.*;

public class StackSet {
    public static final int RETRY_MS = 5000;

    private StackManager dnp = new StackManager(false);
    private Map<String, Stack> stackMap = new HashMap<String, Stack>();
    private ConsoleLogAdapter logAdapter = new ConsoleLogAdapter();

    public StackSet() {
        dnp.AddLogHook(logAdapter);
    }

    public List<Stack> getStacks() {
        List<Stack> stacks = new LinkedList<Stack>();
        for (Stack stack : stackMap.values()) {
            stacks.add(stack);
        }
        return stacks;
    }

    public void addStack(String name, String portName, StackConfig config) {
        CommandAcceptor cmdAcceptor = new CommandAcceptor();
        SlaveStackConfig cfg = config.buildConfig();
        IDataObserver obs = dnp.AddSlave(portName, name, FilterLevel.LEV_WARNING, cmdAcceptor, cfg);

        stackMap.put(name, new Stack(obs, cmdAcceptor, config.getDimension()));
    }

    public void removeStack(String name) {
        stackMap.remove(name);
        dnp.RemoveStack(name);
    }

    public void removeAllStacks() {
        for(String name : stackMap.keySet()) {
            dnp.RemoveStack(name);
        }
        stackMap.clear();
    }

    public void addPort(String name, int port) {
        PhysLayerSettings phys = new PhysLayerSettings(FilterLevel.LEV_WARNING, RETRY_MS);
        dnp.AddTCPServer(name, phys, "127.0.0.1", port);
    }

    public void removePort(String name) {
        dnp.RemovePort(name);
    }

    public void removeAllPorts() {
        VectorOfString vec = dnp.GetPortNames();
        for (int i = 0; i < vec.size(); i++) {
            dnp.RemovePort(vec.get(i));
        }
    }

    public void start() {
        dnp.Start();
    }
    public void stop() {
        dnp.Stop();
    }
}
