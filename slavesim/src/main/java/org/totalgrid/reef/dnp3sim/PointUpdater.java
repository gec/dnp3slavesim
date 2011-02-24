package org.totalgrid.reef.dnp3sim;

import org.totalgrid.reef.protocol.dnp3.Analog;
import org.totalgrid.reef.protocol.dnp3.Binary;
import org.totalgrid.reef.protocol.dnp3.Counter;
import org.totalgrid.reef.protocol.dnp3.IDataObserver;

import java.util.*;


public class PointUpdater {
    private Vector<Stack> stacks;
    private final double pointRate;  // update / sec

    //private final int pointCount;

    private Random random = new Random(System.currentTimeMillis());

    private Map<Integer, Boolean> lastStatuses = new HashMap<Integer, Boolean>();

    private Timer timer = new Timer();

    public PointUpdater(Vector<Stack> stacks, double pointRate) {
        this.stacks = stacks;
        this.pointRate = pointRate;

        //pointCount = (dimension.getAnalogCount() + dimension.getStatusCount() + dimension.getCounterCount()) * observers.size();


    }

    public void start() {
        timer.scheduleAtFixedRate(new RandomUpdateTask(), 1000, 1000);
    }

    public void stop() {
        timer.cancel();
    }

    protected int randPos(int mod) {
        return Math.abs(random.nextInt()) % mod;
    }

    class RandomUpdateTask extends TimerTask {
        @Override
        public void run() {
            //int rand = Math.abs(random.nextInt());
            //int index = rand % stacks.size();
            //System.out.println("Index: " + index + ", size: " + stacks.size() + ", rand: " + rand);
            Stack stack = stacks.get(randPos(stacks.size()));
            IDataObserver observer = stack.getObserver();
            DataDimension dimension = stack.getDimension();

            observer.Start();
            int typ = randPos(3);//random.nextInt() % 3;
            switch (typ) {
                case 0:
                    int statusIndex = randPos(dimension.getStatusCount());//random.nextInt() % dimension.getStatusCount();
                    Binary b = randomBinary(statusIndex);
                    observer.Update(b, statusIndex);
                    break;
                case 1:
                    int analogIndex =  randPos(dimension.getAnalogCount()); //random.nextInt() % dimension.getAnalogCount();
                    Analog a = randomAnalog();
                    observer.Update(a, analogIndex);
                    break;
                case 2:
                    int counterIndex = randPos(dimension.getCounterCount()); //random.nextInt() % dimension.getCounterCount();
                    Counter c = randomCounter();
                    observer.Update(c, counterIndex);
                    break;
                default: break;
            }
            observer.End();
        }
    }


    protected Binary randomBinary(int index) {
        Binary b = new Binary();
        b.SetToNow();

        boolean value = true;
        if (lastStatuses.containsKey((Integer)index)) {
            value = !(boolean)lastStatuses.get((Integer)index);
            lastStatuses.put((Integer)index, value);
        }
        b.SetValue(value);
        b.SetQuality((short)0);
        return b;
    }

    protected Analog randomAnalog() {
        Analog a = new Analog();
        a.SetToNow();
        a.SetQuality((short)0);
        a.SetValue(random.nextDouble()*100000);
        return a;
    }

    protected Counter randomCounter() {
        Counter c = new Counter();
        c.SetToNow();
        c.SetQuality((short)0);
        c.SetValue(random.nextInt());
        return c;
    }
}
