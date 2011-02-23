package org.totalgrid.reef.dnp3sim;


import org.totalgrid.reef.protocol.dnp3.FilterLevel;
import org.totalgrid.reef.protocol.dnp3.ILogBase;

public class ConsoleLogAdapter extends ILogBase {

    @Override
    public void Log(FilterLevel level, String loggerName, String location, String message, int code) {
        System.out.println("LOG: " + level + " " + loggerName + " " + location + " " + message + "[" + code + "]");
    }
}
