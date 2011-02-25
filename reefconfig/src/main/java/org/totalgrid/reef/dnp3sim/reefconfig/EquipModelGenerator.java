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
import org.totalgrid.reef.loader.equipment.*;
import org.totalgrid.reef.loader.equipment.Analog;
import org.totalgrid.reef.loader.equipment.Counter;
import org.totalgrid.reef.loader.equipment.Equipment;
import org.totalgrid.reef.loader.equipment.PointType;
import org.totalgrid.reef.loader.equipment.Status;
import org.totalgrid.reef.loader.equipment.Type;

public class EquipModelGenerator {

    public EquipmentModel fromCommModel(CommunicationsModel comm) {
        EquipmentModel model = new EquipmentModel();

        for(Endpoint end : comm.getEndpoint()) {
            for(org.totalgrid.reef.loader.communications.Equipment equip : end.getEquipment()) {
                model.getEquipment().add(convertEquipment(equip));
            }
        }

        return model;
    }

    public Equipment convertEquipment(org.totalgrid.reef.loader.communications.Equipment input) {
        Equipment equip = new Equipment();
        equip.setName(input.getName());
        equip.getType().add(buildType("Device"));

        for(org.totalgrid.reef.loader.communications.Status st : input.getStatus()) {
            equip.getStatus().add(convertPoint(new Status(), st));
        }
        for(org.totalgrid.reef.loader.communications.Analog an : input.getAnalog()) {
            equip.getAnalog().add(convertPoint(new Analog(), an));
        }
        for(org.totalgrid.reef.loader.communications.Counter c : input.getCounter()) {
            equip.getCounter().add(convertPoint(new Counter(), c));
        }

        return equip;
    }

    public static Type buildType(String name) {
        Type typ = new Type();
        typ.setName(name);
        return typ;
    }

    public static <E extends PointType, C extends org.totalgrid.reef.loader.communications.PointType>
    E convertPoint(E out, C in) {
        out.setName(in.getName());
        out.setUnit(in.getUnit());
        return out;
    }
}
