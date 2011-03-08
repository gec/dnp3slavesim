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

import java.util.List;

public class CommEquipmentGenerator {
    final private int statusCount;
    final private int analogCount;
    final private int counterCount;

    public CommEquipmentGenerator(int statusCount, int analogCount, int counterCount) {
        this.statusCount = statusCount;
        this.analogCount = analogCount;
        this.counterCount = counterCount;
    }

    public Equipment equipment(String name) {
        Equipment equip = new Equipment();
        equip.setName(name);
        initStatuses(equip.getStatus());
        initAnalogs(equip.getAnalog());
        initCounters(equip.getCounter());

        return equip;
    }

    public void initStatuses(List<Status> list) {
        for (int i = 0; i < statusCount; i++) {
            list.add(initPoint(new Status(), "status" + i, i));
        }
    }

    public void initAnalogs(List<Analog> list) {
        for (int i = 0; i < analogCount; i++) {
            list.add(initPoint(new Analog(), "analog" + i, i));
        }
    }


    public void initCounters(List<Counter> list) {
        for (int i = 0; i < counterCount; i++) {
            list.add(initPoint(new Counter(), "counter" + i, i));
        }
    }

    public static <T extends PointType> T initPoint(T point, String name, int index) {
        point.setName(name);
        point.setIndex(index);
        point.setUnit("sim");
        return point;
    }
}
