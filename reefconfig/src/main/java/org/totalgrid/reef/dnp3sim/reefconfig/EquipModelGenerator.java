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
