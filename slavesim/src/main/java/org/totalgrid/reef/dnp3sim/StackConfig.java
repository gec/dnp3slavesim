package org.totalgrid.reef.dnp3sim;


import org.totalgrid.reef.protocol.dnp3.*;

public class StackConfig {
    public static final int slaveAddress = 1;
    public static final int masterAddress = 100;
    public static final int linkTimeout = 5000;
    public static final int linkRetry = 0;

    private DataDimension dimension;

    public StackConfig(DataDimension dimension) {
        this.dimension = dimension;
    }

    public DataDimension getDimension() {
        return dimension;
    }

    public SlaveStackConfig buildConfig() {
        SlaveConfig slaveConfig = new SlaveConfig(); // defaults okay

        DeviceTemplate template = new DeviceTemplate(dimension.getStatusCount(), dimension.getAnalogCount(), dimension.getCounterCount());

        AppConfig appConfig = new AppConfig(); // defaults okay

        LinkConfig linkConfig = new LinkConfig(false, false, linkRetry, slaveAddress, masterAddress, linkTimeout);

        SlaveStackConfig config = new SlaveStackConfig();
        config.setLink(linkConfig);
        config.setApp(appConfig);
        config.setDevice(template);
        config.setSlave(slaveConfig);

        return config;
    }
}