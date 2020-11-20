package ru.cs.vsu.rectangledrawers;

import ru.cs.vsu.pixeldrawers.PixelDrawer;
import ru.cs.vsu.points.ScreenPoint;

import java.awt.*;

public class DDARectangleDrawer implements RectangleDrawer{
    private PixelDrawer pd;
    private Color color = Color.BLACK;

    public void setColor(Color color){
        this.color = color;
    }

    public DDARectangleDrawer(PixelDrawer pd) {
        this.pd = pd;
    }

    @Override
    public void drawRectangle(ScreenPoint p1, ScreenPoint p2) {

    }
}
