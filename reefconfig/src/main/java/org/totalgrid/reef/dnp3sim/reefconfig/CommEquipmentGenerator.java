package org.totalgrid.reef.dnp3sim.reefconfig;


import org.totalgrid.reef.loader.communications.*;

import java.util.List;

public class CommEquipmentGenerator {
    final private int statusCount;
    final private int analogCount;
    final private int counterCount;

    public CommEquipmentGenerator(int stCount, int anCount, int cntrCount) {
        statusCount = stCount;
        analogCount = anCount;
        counterCount = cntrCount;
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
