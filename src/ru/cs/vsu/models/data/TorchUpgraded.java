package ru.cs.vsu.models.data;

import ru.cs.vsu.points.RealPoint;

import java.util.ArrayList;

public class TorchUpgraded {
    private int start;
    private int end;

    private int minValue;
    private int maxValue;

    private boolean stonks;

    public TorchUpgraded(int start, int end, int minValue, int maxValue, boolean stonks) {
        this.start = start;
        this.end = end;

        this.minValue = minValue;
        this.maxValue = maxValue;

        this.stonks = stonks;
    }

    public static ArrayList<TorchUpgraded> getTorchesByData(ArrayList<Integer> data, int period) {
        if (data.size() < 2) return null;
        ArrayList<TorchUpgraded> torches = new ArrayList<>();
        int torchCounter = 0;

        PeriodValueModel model = new PeriodValueModel();
        model.getValues(data, 0, period);

        torches.add(new TorchUpgraded(0, data.get(period - 1), model.getMinValue(), model.getMaxValue(), true));
        torchCounter++;

        boolean stonksType;
        int trueMaxValue;
        int trueMinValue;

        for (int i = period - 1; i < data.size() - period; i += period) {

            model.getValues(data, i, period);

            if(torches.get(torchCounter - 1).end > data.get(i + period)) stonksType = false;
            else stonksType = true;

            trueMaxValue = Math.max(Math.max(model.getMaxValue(), data.get(i)), data.get(i + 1));
            trueMinValue = Math.min(Math.min(model.getMinValue(), data.get(i)), data.get(i + 1));

            torches.add(new TorchUpgraded(data.get(i), data.get(i + period), trueMinValue,
                    trueMaxValue, stonksType));

            torchCounter++;
        }

        model.getValues(data, period * torchCounter, data.size() - period * torchCounter);

        return torches;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public boolean isStonks() {
        return stonks;
    }
}
