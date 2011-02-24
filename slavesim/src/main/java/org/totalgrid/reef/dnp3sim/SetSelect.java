package org.totalgrid.reef.dnp3sim;


import java.util.Set;

public class SetSelect {
    public static int selectIndex(int i, Set<Integer> set, int limit) {
        if (!set.contains(i)) {
           return i;
        } else {
            return selectIndex((i + 1) % limit, set, limit);
        }
    }
}
