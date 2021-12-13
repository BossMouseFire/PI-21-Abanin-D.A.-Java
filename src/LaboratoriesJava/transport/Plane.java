package LaboratoriesJava.transport;

import LaboratoriesJava.enums.MovesBomber;
import LaboratoriesJava.interfaces.ITransport;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.Iterator;

public class Plane extends Vehicle implements Comparable<Plane>, Iterable<Field>, Iterator<Field> {

    protected int planeWidth = 220;
    protected int planeHeight = 138;

    protected final char separator = ';';
    private int index = -1;

    public Plane(int maxSpeed, int weight, Color mainColor)
    {
        this.maxSpeed = maxSpeed;
        this.weight = weight;
        this.mainColor = mainColor;
    }

    protected Plane(int maxSpeed, int weight, Color mainColor, int planeWidth, int planeHeight)
    {
        this.maxSpeed = maxSpeed;
        this.weight = weight;
        this.mainColor = mainColor;
        this.planeWidth = planeWidth;
        this.planeHeight = planeHeight;
    }

    public Plane(String info)
    {
        String[] strs = info.split(String.valueOf(separator));
        if (strs.length == 3)
        {
            maxSpeed = Integer.parseInt(strs[0]);
            weight = Integer.parseInt(strs[1]);
            mainColor = new Color(Integer.parseInt(strs[2]));
        }
    }

    @Override
    public void drawTransport(Graphics2D g) {
        g.setPaint(mainColor);
        g.fillPolygon(
                new int[]{startPosX, startPosX + 10, startPosX + 175, startPosX + 220, startPosX + 175, startPosX + 10},
                new int[]{startPosY + 70, startPosY + 60, startPosY + 60, startPosY + 70, startPosY + 80, startPosY + 80},
                6
        );

        //Хвост
        g.setPaint(mainColor);
        g.fillPolygon(
                new int[]{startPosX + 155, startPosX + 185, startPosX + 185, startPosX + 185},
                new int[]{startPosY + 70, startPosY + 30, startPosY + 80, startPosY + 110},
                4
        );
        //Верхнее крыло
        g.setPaint(mainColor);
        g.fillPolygon(
                new int[]{startPosX + 50, startPosX + 90, startPosX + 100, startPosX + 70},
                new int[]{startPosY + 60, startPosY + 5, startPosY + 5, startPosY + 60},
                4
        );

        // нижнее крыло
        g.setPaint(mainColor);
        g.fillPolygon(
                new int[]{startPosX + 50, startPosX + 90, startPosX + 100, startPosX + 70},
                new int[]{startPosY + 80, startPosY + 135, startPosY + 135, startPosY + 80},
                4
        );
    }

    @Override
    public void moveTransport(MovesBomber movesBomber){
        int step = maxSpeed / weight;
        switch (movesBomber){
            case Up -> {
                if(startPosY - step > 0)
                {
                    startPosY -= step;
                }
            }
            case Down -> {
                if (startPosY + step < pictureHeight - planeHeight)
                {
                    startPosY += step;
                }
            }
            case Right -> {
                if (startPosX + step < pictureWidth - planeWidth)
                {
                    startPosX += step;
                }
            }
            case Left -> {
                if (startPosX - step > 0)
                {
                    startPosX -= step;
                }
            }
        }
    }

    @Override
    public String toString() {
        String color = Integer.toString(mainColor.getRGB());
        return String.format("%s%s%s%s%s", maxSpeed, separator, weight, separator, color);
    }

    @Override
    public int compareTo(Plane o) {
        if (maxSpeed != o.maxSpeed)
        {
            return maxSpeed > o.maxSpeed ? 1 : -1;
        }
        if (weight != o.weight)
        {
            return weight > o.weight ? 1 : -1;
        }
        if (mainColor.hashCode() != o.mainColor.hashCode())
        {
            return mainColor.hashCode() > o.mainColor.hashCode() ? 1 : -1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plane plane = (Plane) o;
        return maxSpeed == plane.maxSpeed
                && weight == plane.weight
                && mainColor.hashCode() == plane.mainColor.hashCode();
    }

    @Override
    public Iterator<Field> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        if (++index < this.getClass().getDeclaredFields().length) {
            return true;
        }
        index = -1;
        return false;
    }
    @Override
    public Field next() {
        return this.getClass().getDeclaredFields()[index];
    }
}
