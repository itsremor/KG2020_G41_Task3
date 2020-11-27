package ru.cs.vsu.utils;

import ru.cs.vsu.ScreenConvertor;
import ru.cs.vsu.linedrawers.DDALineDrawer;
import ru.cs.vsu.linedrawers.LineDrawer;
import ru.cs.vsu.models.Line;
import ru.cs.vsu.models.MyRectangle;
import ru.cs.vsu.models.data.Torch;
import ru.cs.vsu.models.data.TorchUpgraded;
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
    private int currentX;
    private int currentY;

    private ArrayList<Line> lines = new ArrayList<>();
    private ArrayList<Torch> torches =
            Torch.getTorchesByData(FileUtils.getFileDataByString("C:\\Users\\akamo\\IdeaProjects\\LEXUS\\KG2020_G41_Task3\\src\\ru\\cs\\vsu\\data\\dayData.txt"),
                    6);

    private ArrayList<TorchUpgraded> torchesUpgraded = TorchUpgraded.getTorchesByData(
            FileUtils.getFileDataByString("C:\\Users\\akamo\\IdeaProjects\\LEXUS\\KG2020_G41_Task3\\src\\ru\\cs\\vsu\\data\\dayData.txt"),
            6);

    public void setTorches(ArrayList<Torch> torches){
        this.torches = torches;
    }
    public void setTorchesUpgraded(ArrayList<TorchUpgraded> torchesUpgraded) {
        this.torchesUpgraded = torchesUpgraded;
    }

    private ScreenConvertor sc = new ScreenConvertor(-10, 2000, 20, 4000, 800, 600);

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

        drawAll(ld, rd);
        drawAxes(ld, bi_g);
        drawCursoreData(ld, currentX, currentY, bi_g);

        bi_g.dispose();
        g.drawImage(bi, 0, 0, null);
    }

    private void drawLine(LineDrawer ld, Line l, Color color) {
        ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()), color);
    }

    private void drawCursoreData(LineDrawer ld, int x, int y, Graphics2D bi_g){
        Color grey = new Color(190,190,190);
        ld.drawLine(new ScreenPoint(70, y), new ScreenPoint(getWidth(), y), grey);
        ld.drawLine(new ScreenPoint(x, 0), new ScreenPoint(x, getHeight() - 16), grey);

        ld.drawLine(new ScreenPoint(x - 35, getHeight() - 16), new ScreenPoint(x + 35, getHeight() - 16), Color.BLACK);
        ld.drawLine(new ScreenPoint(x - 35, getHeight() - 1), new ScreenPoint(x + 35, getHeight() - 1), Color.BLACK);
        ld.drawLine(new ScreenPoint(x - 35, getHeight() - 16), new ScreenPoint(x - 35, getHeight()), Color.BLACK);
        ld.drawLine(new ScreenPoint(x + 35, getHeight() - 16), new ScreenPoint(x + 35, getHeight()), Color.BLACK);

        ld.drawLine(new ScreenPoint(70, y - 8), new ScreenPoint(70, y + 8), Color.BLACK);
        ld.drawLine(new ScreenPoint(0, y - 8), new ScreenPoint(0, y + 8), Color.BLACK);
        ld.drawLine(new ScreenPoint(0, y - 8), new ScreenPoint(70, y - 8), Color.BLACK);
        ld.drawLine(new ScreenPoint(0, y + 8), new ScreenPoint(70, y + 8), Color.BLACK);

        String stringX = String.format("%.3f", sc.s2r(new ScreenPoint(x, y)).getX());
        String stringY = String.format("%.3f", sc.s2r(new ScreenPoint(x, y)).getY());

        bi_g.drawString(stringX, x - 30, getHeight() - 4);
        bi_g.drawString(stringY, 5, y + 4);

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
                ld.drawLine(sc.r2s(new RealPoint(
                        (torches.get(i).getStart().getX() + (torches.get(i).getEnd().getX()
                                - torches.get(i).getStart().getX()) / 2),
                                torches.get(i).getEnd().getY())),
                        sc.r2s(new RealPoint(
                                (torches.get(i).getStart().getX() + (torches.get(i).getEnd().getX()
                                        - torches.get(i).getStart().getX()) / 2),
                                torches.get(i).getMaxValue())), Color.BLACK);

                ld.drawLine(sc.r2s(new RealPoint(
                                (torches.get(i).getStart().getX() + (torches.get(i).getEnd().getX()
                                        - torches.get(i).getStart().getX()) / 2),
                                torches.get(i).getStart().getY())),
                        sc.r2s(new RealPoint(
                                (torches.get(i).getStart().getX() + (torches.get(i).getEnd().getX()
                                        - torches.get(i).getStart().getX()) / 2),
                                torches.get(i).getMinValue())), Color.BLACK);
            }
            else{
                ld.drawLine(sc.r2s(new RealPoint(
                                (torches.get(i).getStart().getX() + (torches.get(i).getEnd().getX()
                                        - torches.get(i).getStart().getX()) / 2),
                                torches.get(i).getEnd().getY())),
                        sc.r2s(new RealPoint(
                                (torches.get(i).getStart().getX() + (torches.get(i).getEnd().getX()
                                        - torches.get(i).getStart().getX()) / 2),
                                torches.get(i).getMinValue())), Color.BLACK);

                ld.drawLine(sc.r2s(new RealPoint(
                                (torches.get(i).getStart().getX() + (torches.get(i).getEnd().getX()
                                        - torches.get(i).getStart().getX()) / 2),
                                torches.get(i).getStart().getY())),
                        sc.r2s(new RealPoint(
                                (torches.get(i).getStart().getX() + (torches.get(i).getEnd().getX()
                                        - torches.get(i).getStart().getX()) / 2),
                                torches.get(i).getMaxValue())), Color.BLACK);
            }

        }
    }

    public void drawChartUpgraded(RectangleDrawer rd, LineDrawer ld, ArrayList<TorchUpgraded> torches){
        boolean stonks;

        double lineX;
        double lineY1;
        double lineY2;

        ScreenPoint first;
        ScreenPoint second;

        for (int i = 0; i < torches.size(); i++) {
            stonks = torches.get(i).isStonks();
            if(stonks) rd.setColor(Color.GREEN);
            else rd.setColor(Color.RED);

            rd.drawRectangle(sc.r2s(new RealPoint(i, torches.get(i).getStart())),
                    sc.r2s(new RealPoint(i + 1, torches.get(i).getEnd())));


            if(stonks) {
                lineX = i + 0.5;

                //отрисовка верхней "тютельки"
                if(torches.get(i).getEnd() < torches.get(i).getMaxValue()) {
                    lineY1 = torches.get(i).getEnd();
                    lineY2 = torches.get(i).getMaxValue();

                    first = sc.r2s(new RealPoint(lineX, lineY1));
                    second = sc.r2s(new RealPoint(lineX, lineY2));

                    ld.drawLine(first, second, Color.BLACK);
                }

                //отрисовка нижней "тютельки"
                if(torches.get(i).getStart() > torches.get(i).getMinValue()) {
                    lineY1 = torches.get(i).getStart();
                    lineY2 = torches.get(i).getMinValue();

                    first = sc.r2s(new RealPoint(lineX, lineY1));
                    second = sc.r2s(new RealPoint(lineX, lineY2));

                    ld.drawLine(first, second, Color.BLACK);
                }

            }
            else {
                lineX = i + 0.5;

                if(torches.get(i).getStart() < torches.get(i).getMaxValue()){
                lineY1 = torches.get(i).getStart();
                lineY2 = torches.get(i).getMaxValue();

                first = sc.r2s(new RealPoint(lineX, lineY1));
                second = sc.r2s(new RealPoint(lineX, lineY2));

                ld.drawLine(first, second, Color.BLACK);
                }

                if(torches.get(i).getEnd() > torches.get(i).getMinValue()) {
                    lineY1 = torches.get(i).getEnd();
                    lineY2 = torches.get(i).getMinValue();

                    first = sc.r2s(new RealPoint(lineX, lineY2));
                    second = sc.r2s(new RealPoint(lineX, lineY1));

                    ld.drawLine(first, second, Color.BLACK);
                }
            }
        }
    }

    private void drawAll(LineDrawer ld, RectangleDrawer rd) {
        drawChartUpgraded(rd, ld, torchesUpgraded);
    }

    private void drawAxes(LineDrawer ld, Graphics2D bi_g) {
        RealPoint xp1 = new RealPoint(sc.getX(), 0);
        RealPoint xp2 = new RealPoint(sc.getX() + sc.getW(), 0);
        RealPoint yp1 = new RealPoint(0, sc.getY());
        RealPoint yp2 = new RealPoint(0, sc.getY() - sc.getH());
        Line xAxis = new Line(xp1, xp2);
        Line yAxis = new Line(yp1, yp2);
        drawLine(ld, xAxis, Color.BLACK);
        drawLine(ld, yAxis, Color.BLACK);


    }

    @Override
    public void mouseMoved(MouseEvent e) {
        currentX = e.getXOnScreen() - 8;
        currentY = e.getYOnScreen() - 30;

        repaint();
    }

    private ScreenPoint prevDrag;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        ScreenPoint current = new ScreenPoint(e.getX(), e.getY());
        moveScreen(e, current);

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

//            rect = new MyRectangle(
//                    sc.s2r(new ScreenPoint(e.getX(), e.getY())),
//                    sc.s2r(new ScreenPoint(e.getX(), e.getY())));
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            prevDrag = null;
        } else if (e.getButton() == MouseEvent.BUTTON1) {
//            rectangles.add(rect);
//            rect = null;
//
//            lines.add(currentLine);
//            currentLine = null;
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
