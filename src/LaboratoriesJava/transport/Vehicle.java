package LaboratoriesJava.transport;

import LaboratoriesJava.enums.MovesBomber;
import LaboratoriesJava.interfaces.ITransport;

import java.awt.*;

public abstract class Vehicle implements ITransport {
    protected int startPosX;
    protected int startPosY ;
    protected int pictureWidth;
    protected int pictureHeight;

    public int maxSpeed;
    public int weight;
    public Color mainColor;

    public void setPosition(int x, int y, int width, int height)
    {
        startPosX = x;
        startPosY = y;
        this.pictureWidth = width;
        this.pictureHeight = height;
    }
    public abstract void drawTransport(Graphics2D g);
    public abstract void moveTransport(MovesBomber movesBomber);

    @Override
    public void setMainColor(Color mainColor) {
        this.mainColor = mainColor;
    }
}
