package ru.cs.vsu.models.data;

import ru.cs.vsu.points.RealPoint;

import java.util.ArrayList;

public class Torch {
    private RealPoint start;
    private RealPoint end;

    private int minValue;
    private int maxValue;

    private boolean stonks;

    //перевод всех этих дискретных данных в реальные (т.е.  отсавить только
    // min/max/start/end ТОЛЬКО ПО ИГРИКУ
    //по иксу значение бара хранится по порядку бара
    //т.к. бар привязывается к порядковому номеру дня (а не от его начала до конца)
    //!ф-ция принимает свечу, (рил данные) и возвращает рил поинты
    //для бара (2 точки) и для двух пиков (по 2 точки)

    public Torch(RealPoint start, RealPoint end, int minValue, int maxValue, boolean stonks) {
        this.start = start;
        this.end = end;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.stonks = stonks;
    }

    public static ArrayList<Torch> getTorchesByData(ArrayList<Integer> data, int period) {
        if (data.size() < 2) return null;
        ArrayList<Torch> torches = new ArrayList<>();
        int torchCounter = 0;

        torches.add(new Torch(new RealPoint(0, 0), new RealPoint(1, data.get(period - 1)), 0, data.get(period - 1), true));
        torchCounter++;

        PeriodValueModel model = new PeriodValueModel();
        boolean stonksType;

        for (int i = period; i < data.size() - period; i += period) {

            //в свечках убрать координату, вместо этого использовать два double значения - start end
            //для min и max аналогично, использовать даблы
            model.getValues(data, i, period);
            if(torches.get(torchCounter - 1).end.getY() > data.get(i + period)) stonksType = false;
            else stonksType = true;

            torches.add(new Torch(torches.get(torchCounter-1).end, new RealPoint(torchCounter+1,
                    data.get(i + period)), Math.min(model.getMinValue(), data.get(i + period)),
                    Math.max(model.getMaxValue(), data.get(i + period)), stonksType));
            torchCounter++;
        }

        model.getValues(data, period * torchCounter, data.size() - period * torchCounter);

        return torches;
    }

    public boolean isStonks() {
        return stonks;
    }

    public RealPoint getStart() {
        return start;
    }

    public RealPoint getEnd() {
        return end;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }
}
