package ru.cs.vsu.rectangledrawers;

import ru.cs.vsu.points.ScreenPoint;

import java.awt.*;

public interface RectangleDrawer {
    void drawRectangle(ScreenPoint p1, ScreenPoint p2);

    void setColor(Color color);
}
