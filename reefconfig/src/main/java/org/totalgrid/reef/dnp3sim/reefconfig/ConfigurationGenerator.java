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
