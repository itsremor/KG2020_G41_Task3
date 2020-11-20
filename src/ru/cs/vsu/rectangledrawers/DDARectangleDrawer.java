package ru.cs.vsu.rectangledrawers;

import ru.cs.vsu.pixeldrawers.PixelDrawer;
import ru.cs.vsu.points.ScreenPoint;

import java.awt.*;

public class DDARectangleDrawer implements RectangleDrawer{
    private PixelDrawer pd;
    private Color color = Color.BLACK;

    @Override
    public void setColor(Color color){
        this.color = color;
    }

    public DDARectangleDrawer(PixelDrawer pd) {
        this.pd = pd;
    }

    @Override
    public void drawRectangle(ScreenPoint p1, ScreenPoint p2) {
        int x0 = Math.min(p1.getX(), p2.getX());
        int x1 = Math.max(p1.getX(), p2.getX());
        int y0 = Math.min(p1.getY(), p2.getY());
        int y1 = Math.max(p1.getY(), p2.getY());


        for (int i = y0; i <= y1; i++) {
            for (int j = x0; j < x1; j++) {
                pd.setPixel(j, i, color);
            }
        }

    }
}
