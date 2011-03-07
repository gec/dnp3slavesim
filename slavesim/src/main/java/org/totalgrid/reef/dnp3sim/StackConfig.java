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

public class StackConfig {
    public static final int SLAVE_ADDRESS = 1;
    public static final int MASTER_ADDRESS = 100;
    public static final int LINK_TIMEOUT = 5000;
    public static final int LINK_RETRY = 0;
    public static final int UNSOL_PACK_DELAY = 50;

    private DataDimension dimension;

    public StackConfig(DataDimension dimension) {
        this.dimension = dimension;
    }

    public DataDimension getDimension() {
        return dimension;
    }

    public SlaveStackConfig buildConfig() {
        SlaveConfig slaveConfig = new SlaveConfig(); // defaults okay
        slaveConfig.setMUnsolPackDelay(UNSOL_PACK_DELAY);

        DeviceTemplate template = new DeviceTemplate(dimension.getStatusCount(), dimension.getAnalogCount(), dimension.getCounterCount());

        AppConfig appConfig = new AppConfig(); // defaults okay

        LinkConfig linkConfig = new LinkConfig(false, false, LINK_RETRY, SLAVE_ADDRESS, MASTER_ADDRESS, LINK_TIMEOUT);

        SlaveStackConfig config = new SlaveStackConfig();
        config.setLink(linkConfig);
        config.setApp(appConfig);
        config.setDevice(template);
        config.setSlave(slaveConfig);

        return config;
    }
}