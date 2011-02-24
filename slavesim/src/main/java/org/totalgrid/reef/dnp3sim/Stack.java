package org.totalgrid.reef.dnp3sim;

import org.totalgrid.reef.protocol.dnp3.IDataObserver;


public class Stack {
    private IDataObserver observer;
    private CommandAcceptor acceptor;
    private DataDimension dimension;

    public IDataObserver getObserver() {
        return observer;
    }

    public CommandAcceptor getAcceptor() {
        return acceptor;
    }

    public DataDimension getDimension() {
        return dimension;
    }

    public Stack(IDataObserver observer, CommandAcceptor acceptor, DataDimension dimension) {
        this.observer = observer;
        this.acceptor = acceptor;
        this.dimension = dimension;
    }
}
