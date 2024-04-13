package main;

import java.util.ArrayList;

public class BarrierPool {
    //attributes
    private static ArrayList<Barrier> barrierPool = new ArrayList<>();
    public static final int initCount = 16;
    public static final int maxCount = 20;

    //initialize the object pool
    static {
        for (int i = 0; i < initCount; i++) {
            barrierPool.add(new Barrier());
        }
    }

    //get the object from the pool
    public static Barrier getPool() {
        int size = barrierPool.size();
        if (size > 0) {
            return barrierPool.remove(size-1);
            
        }else {
            return new Barrier();
        }
    }

    //return the object to the pool
    public static void setPool(Barrier barrier) {
        if (barrierPool.size() < maxCount) {
            barrierPool.add(barrier);
        }
    }

    //getters and setters
    public static ArrayList<Barrier> getBarrierPool() {
        return barrierPool;
    }

    public static void setBarrierPool(ArrayList<Barrier> barrierPool) {
        BarrierPool.barrierPool = barrierPool;
    }


}
