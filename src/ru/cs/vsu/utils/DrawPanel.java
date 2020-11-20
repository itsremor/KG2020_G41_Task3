package ru.cs.vsu.utils;

import ru.cs.vsu.ScreenConvertor;
import ru.cs.vsu.linedrawers.DDALineDrawer;
import ru.cs.vsu.linedrawers.LineDrawer;
import ru.cs.vsu.models.Line;
import ru.cs.vsu.models.MyRectangle;
import ru.cs.vsu.models.data.Torch;
import ru.cs.vsu.pixeldrawers.BufferedImagePixelDrawer;
import ru.cs.vsu.pixeldrawers.PixelDrawer;
import ru.cs.vsu.points.RealPoint;
import ru.cs.vsu.points.ScreenPoint;
import ru.cs.vsu.rectangledrawers.DDARectangleDrawer;
import ru.cs.vsu.rectangledrawers.RectangleDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener {
    private ArrayList<Line> lines = new ArrayList<>();
    private ArrayList<Torch> torches =
            Torch.getTorchesByData(FileUtils.getFileData("C:\\Users\\akamo\\IdeaProjects\\LEXUS\\KG2020_G41_Task3\\src\\ru\\cs\\vsu\\data\\dayData.txt"),
                    60);
    private ArrayList<MyRectangle> rectangles = new ArrayList<>();



    private ScreenConvertor sc = new ScreenConvertor(-2, 2, 4, 4, 800, 600);

    private Line xAxis = new Line(-2, 0, 2, 0);
    private Line yAxis = new Line(0, -2, 0, 2);

    public DrawPanel() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D bi_g = bi.createGraphics();
        PixelDrawer pd = new BufferedImagePixelDrawer(bi);
        LineDrawer ld = new DDALineDrawer(pd);
        RectangleDrawer rd = new DDARectangleDrawer(pd);
        sc.setScreenH(getHeight());
        sc.setScreenW(getWidth());
        bi_g.fillRect(0, 0, getWidth(), getHeight());
        bi_g.setColor(Color.black);
        drawAxes(ld);
        drawAll(ld);

        if (currentLine != null) {
            drawLine(ld, currentLine);
        }

        if (rect != null) {
            drawRect(rd, rect);
        }
        bi_g.dispose();
        g.drawImage(bi, 0, 0, null);
    }

    private void drawLine(LineDrawer ld, Line l) {
        ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()));
    }

    private void drawRect(RectangleDrawer rd, MyRectangle rect) {
        rd.drawRectangle(sc.r2s(rect.getP1()), sc.r2s(rect.getP2()));
    }

    public void drawChart(RectangleDrawer rd, LineDrawer ld, ArrayList<Torch> torches){
        boolean stonks;
        for (int i = 0; i < torches.size(); i++) {
            stonks = torches.get(i).isStonks();
            if(stonks) rd.setColor(Color.GREEN);
            else rd.setColor(Color.RED);

            rd.drawRectangle(sc.r2s(torches.get(i).getStart()),
                    sc.r2s(torches.get(i).getEnd()));

            if(stonks) {
                ld.drawLine(sc.r2s(new RealPoint(((torches.get(i).getEnd().getX() - torches.get(i).getStart().getX()) / 2),
                                torches.get(i).getEnd().getY())),
                        sc.r2s(new RealPoint(((torches.get(i).getEnd().getX() - torches.get(i).getStart().getX()) / 2),
                                torches.get(i).getMaxValue())));

                ld.drawLine(sc.r2s(new RealPoint(((torches.get(i).getEnd().getX() - torches.get(i).getStart().getX()) / 2),
                                torches.get(i).getStart().getY())),
                        sc.r2s(new RealPoint(((torches.get(i).getEnd().getX() - torches.get(i).getStart().getX()) / 2),
                                torches.get(i).getMinValue())));
            }
            else{
                ld.drawLine(sc.r2s(new RealPoint(((torches.get(i).getEnd().getX() - torches.get(i).getStart().getX()) / 2),
                                torches.get(i).getEnd().getY())),
                        sc.r2s(new RealPoint(((torches.get(i).getEnd().getX() - torches.get(i).getStart().getX()) / 2),
                                torches.get(i).getMinValue())));

                ld.drawLine(sc.r2s(new RealPoint(((torches.get(i).getEnd().getX() - torches.get(i).getStart().getX()) / 2),
                                torches.get(i).getStart().getY())),
                        sc.r2s(new RealPoint(((torches.get(i).getEnd().getX() - torches.get(i).getStart().getX()) / 2),
                                torches.get(i).getMaxValue())));
            }
        }
    }

    private void drawAll(LineDrawer ld) {
//        int w = this.getWidth()/2;
//        int h = this.getHeight()/2;
//
//        Graphics2D g1 = (Graphics2D) g;
//        g1.setStroke(new BasicStroke(2));
//        g1.setColor(Color.black);
//        g1.drawLine(0,h,w*2,h);
//        g1.drawLine(w,0,w,h*2);
//        g1.drawString("0", w - 7, h + 13);
//
//        int scale = 10;
//        for (int x = 0; x <= 4; x++) {
//            p.addPoint(w+scale*x, h - scale*((x*x*x) + x - 3));
//        }
////...lines skipped
//        Polygon p1 = new Polygon();
//        for (int x = -10; x <= 10; x++) {
//            p1.addPoint(w + scale*x, h - scale*((x*x*x)/100) - x + 10);
//        }
//


        drawAxes(ld);
    }

    private void drawAxes(LineDrawer ld) {
//        ld.drawLine(sc.realToScreen(xAxis.getP1()), sc.realToScreen(xAxis.getP2()));
//        ld.drawLine(sc.realToScreen(yAxis.getP1()), sc.realToScreen(yAxis.getP2()));
//        for (Line l : lines) {
//            ld.drawLine(sc.realToScreen(l.getP1()),
//                    sc.realToScreen(l.getP2()));
//        }

        RealPoint xp1 = new RealPoint(sc.getX(), 0);
        RealPoint xp2 = new RealPoint(sc.getX() + sc.getW(), 0);
        RealPoint yp1 = new RealPoint(0, sc.getY());
        RealPoint yp2 = new RealPoint(0, sc.getY() - sc.getH());
        Line xAxis = new Line(xp1, xp2);
        Line yAxis = new Line(yp1, yp2);
        drawLine(ld, xAxis);
        drawLine(ld, yAxis);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    private ScreenPoint prevDrag;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        ScreenPoint current = new ScreenPoint(e.getX(), e.getY());
        moveScreen(e, current);

        if (rect != null) {
            rect.setP2(sc.s2r(current));
        }

        /*
        if (currentLine != null) {
            currentLine.setP2(sc.s2r(current));
        }
        */

        repaint();
    }

    private void moveScreen(MouseEvent e, ScreenPoint current) {
        ScreenPoint delta;

        if (prevDrag != null) {
            delta = new ScreenPoint(
                    current.getX() - prevDrag.getX(),
                    current.getY() - prevDrag.getY());


            RealPoint deltaReal = sc.s2r(delta);
            RealPoint zeroReal = sc.s2r(new ScreenPoint(0, 0));
            RealPoint vector = new RealPoint(
                    deltaReal.getX() - zeroReal.getX(),
                    deltaReal.getY() - zeroReal.getY()
            );
            sc.setX(sc.getX() - vector.getX());
            sc.setY(sc.getY() - vector.getY());
            prevDrag = current;
        }
    }

    private Line currentLine = null;
    private MyRectangle rect = null;

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            prevDrag = new ScreenPoint(e.getX(), e.getY());
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            /* currentLine = new Line(
                    sc.s2r(new ScreenPoint(e.getX(), e.getY())),
                    sc.s2r(new ScreenPoint(e.getX(), e.getY()))
            ); */

            rect = new MyRectangle(
                    sc.s2r(new ScreenPoint(e.getX(), e.getY())),
                    sc.s2r(new ScreenPoint(e.getX(), e.getY())));
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            prevDrag = null;
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            rectangles.add(rect);
            rect = null;

            lines.add(currentLine);
            currentLine = null;
        }

        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        double scale = 1;
        double coef = clicks <= 0 ? 0.9 : 1.1;
        for (int i = 0; i < Math.abs(clicks); i++) {
            scale *= coef;
        }
        sc.setW(sc.getW() * scale);
        sc.setH(sc.getH() * scale);

        sc.setX(sc.getX() * scale);
        sc.setY(sc.getY() * scale);

        repaint();
    }
}
