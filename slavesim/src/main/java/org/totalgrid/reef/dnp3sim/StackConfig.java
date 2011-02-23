package org.totalgrid.reef.dnp3sim;


import org.totalgrid.reef.protocol.dnp3.*;

public class StackConfig {
    public static final int slaveAddress = 1;
    public static final int masterAddress = 100;
    public static final int linkTimeout = 5000;
    public static final int linkRetry = 0;

    private int statusCount;
    private int analogCount;
    private int counterCount;

    public StackConfig(int statusCount, int analogCount, int counterCount) {
        this.statusCount = statusCount;
        this.analogCount = analogCount;
        this.counterCount = counterCount;
    }

    public SlaveStackConfig buildConfig() {
        SlaveConfig slaveConfig = new SlaveConfig(); // defaults okay

        DeviceTemplate template = new DeviceTemplate(statusCount, analogCount, counterCount);

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