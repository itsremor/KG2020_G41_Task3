package ru.cs.vsu.models.data;

import ru.cs.vsu.points.RealPoint;

import java.util.ArrayList;

public class Torch {
    private RealPoint start;
    private RealPoint end;

    private int minValue;
    private int maxValue;

    private boolean stonks;

    public Torch(RealPoint start, RealPoint end, int minValue, int maxValue, boolean stonks) {
        this.start = start;
        this.end = end;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.stonks = stonks;
    }

    public static ArrayList<Torch> getTorches(ArrayList<Integer> data, int period){
        if(data.size() < 2) return null;
        ArrayList<Torch> torches = new ArrayList<>();

        torches.add(new Torch(new RealPoint(0, 0), new RealPoint(1, data.get(0)), 0, data.get(0), true));

        for (int i = 1; i < data.size() - 1; i++) {
         //torches.add(new Torch());
        }

        return torches;
    }
}
