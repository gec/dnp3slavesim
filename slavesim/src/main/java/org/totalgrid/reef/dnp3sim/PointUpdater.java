package org.totalgrid.reef.dnp3sim;

import org.totalgrid.reef.protocol.dnp3.Analog;
import org.totalgrid.reef.protocol.dnp3.Binary;
import org.totalgrid.reef.protocol.dnp3.Counter;
import org.totalgrid.reef.protocol.dnp3.IDataObserver;

import java.util.*;


public class PointUpdater {
    private final double pointRate;  // update / sec

    private Vector<ObserverUpdater> updaters;
    private final int pointCount;

    private Random random = new Random(System.currentTimeMillis());

    private Map<Integer, Boolean> lastStatuses = new HashMap<Integer, Boolean>();

    private Timer timer = new Timer();

    public PointUpdater(Collection<Stack> stacks, double pointRate) {
        this.pointRate = pointRate;

        int count = 0;
        updaters = new Vector<ObserverUpdater>(stacks.size());
        for (Stack stack : stacks) {
            ObserverUpdater updater = new ObserverUpdater(stack.getObserver(), stack.getDimension(), random);
            updaters.add(updater);
            count += updater.getPointCount();
        }
        pointCount = count;
    }

    public void start() {
        loadInitial();
        timer.scheduleAtFixedRate(new RandomUpdateTask(), 1000, 1000);
    }

    public void stop() {
        timer.cancel();
    }

    protected int randPos(int mod) {
        return Math.abs(random.nextInt()) % mod;
    }

    protected void loadInitial() {
        for (ObserverUpdater up : updaters) {
            up.loadAll();
        }
    }

    class RandomUpdateTask extends TimerTask {
        @Override
        public void run() {
            /*for (ObserverUpdater up : updaters) {
                up.update(4);
            }*/
            double updatesToDo = 20;
            double updatesPerDevice = updatesToDo / (double)updaters.size();

            if (updatesPerDevice > 1) {
                System.out.println("Updating all devices with " + (int)updatesPerDevice + " update.");
                for (ObserverUpdater up : updaters) {
                    up.update((int)updatesPerDevice);
                }
            } else {
                int devicesToUpdate = (int)(updatesPerDevice * (double)updaters.size());
                System.out.println("Updating " + devicesToUpdate + "with one update.");
                Set<Integer> selected = new HashSet<Integer>();
                for (int i = 0; i < devicesToUpdate; i++) {
                    int index = SetSelect.selectIndex(randPos(updaters.size()), selected, updaters.size());
                    updaters.get(index).update(1);
                    selected.add(index);
                }
            }
        }
    }
}
