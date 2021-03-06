package ru.cs.vsu.models.data;

import java.util.ArrayList;

public class PeriodValueModel {
    private int maxValue;
    private int minValue;

    public void getValues(ArrayList<Integer> data, int start, int period) {

        int max = 0;

        for (int i = start; i < start + period; i++) {
            if (data.get(i) > max) max = data.get(i);
        }

        int min = max;

        for (int i = start; i < start + period; i++) {
            if (data.get(i) < min) min = data.get(i);
        }

        this.maxValue = max;
        this.minValue = min;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void clear(){
        maxValue = 0;
        minValue = 0;
    }
}
