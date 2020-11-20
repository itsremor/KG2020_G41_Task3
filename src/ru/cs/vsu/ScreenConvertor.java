package ru.cs.vsu;

import ru.cs.vsu.points.RealPoint;
import ru.cs.vsu.points.ScreenPoint;

public class ScreenConvertor {
    private double x, y, w, h;
    private int screenW, screenH;

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setW(double w) {
        this.w = w;
    }

    public void setH(double h) {
        this.h = h;
    }

    public void setScreenW(int screenW) {
        this.screenW = screenW;
    }

    public void setScreenH(int screenH) {
        this.screenH = screenH;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }

    public int getScreenW() {
        return screenW;
    }

    public int getScreenH() {
        return screenH;
    }

    public ScreenPoint r2s(RealPoint rp) {
        int px = (int) ((rp.getX() - x) * screenW / w);
        int py = (int) ((y - rp.getY()) * screenH / h);
        return new ScreenPoint(px, py);
    }

    public ScreenPoint realToScreen(RealPoint rp) {
        int px = (int) ((rp.getX() - x) * screenW / w);
        int py = (int) ((y - rp.getY()) * screenH / h);
        return new ScreenPoint(px, py);
    }

    public RealPoint s2r(ScreenPoint p) {
        double px = p.getX() * w / screenW + x;
        double py = y - p.getY() * h / screenH;
        return new RealPoint(px, py);
    }

    public ScreenConvertor(double x, double y, double w, double h, int screenW, int screenH) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.screenW = screenW;
        this.screenH = screenH;
    }


}
