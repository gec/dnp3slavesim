package org.totalgrid.reef.dnp3sim;


import org.totalgrid.reef.protocol.dnp3.BinaryOutput;
import org.totalgrid.reef.protocol.dnp3.ICommandAcceptor;
import org.totalgrid.reef.protocol.dnp3.IResponseAcceptor;
import org.totalgrid.reef.protocol.dnp3.Setpoint;

public class CommandAcceptor extends ICommandAcceptor {

    @Override
    public void AcceptCommand(BinaryOutput control, long index, int aSequence, IResponseAcceptor apRspAcceptor) {
        System.out.println("Got control for index: " + index);
    }

    @Override
    public void AcceptCommand(Setpoint setpoint, long index, int aSequence, IResponseAcceptor apRspAcceptor) {
        System.out.println("Got setpoint for index: " + index);
    }
}
