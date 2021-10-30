package LaboratoriesJava.transport;

import LaboratoriesJava.bombForms.*;
import LaboratoriesJava.enums.BombForms;
import LaboratoriesJava.interfaces.IBombs;

import java.awt.*;

public class Bomber extends Plane {

    public IBombs bombs;
    public boolean isStateBombs;
    public boolean isStateGun;
    public Color additionalColor;

    public Bomber(int maxSpeed, boolean stateBombs,
                  boolean stateGun, int weight, Color mainColor, Color additionalColor,
                  BombForms bombForms) {
        super(maxSpeed, weight, mainColor);
        isStateBombs = stateBombs;
        isStateGun = stateGun;
        this.additionalColor = additionalColor;

        switch (bombForms){
            case Oval -> {
                bombs = new BombsOval();
            }
            case Rocket -> {
                bombs = new BombsRocket();
            }
            case Rectangle -> {
                bombs = new BombsRect();
            }
        }
        int rangeRandom = 3 + (int) (Math.random() * 3);
        bombs.setAmountBombs(rangeRandom * 2);
    }

    @Override
    public void drawTransport(Graphics2D g)
    {
        super.drawTransport(g);
        if (isStateBombs)
        {
            bombs.drawBombs(g, additionalColor, startPosX, startPosY);
        }

        if (isStateGun)
        {
            g.setPaint(additionalColor);
            g.fillPolygon(
                    new int[]{startPosX + 140, startPosX + 140, startPosX + 110},
                    new int[]{startPosY + 70, startPosY + 40, startPosY + 70},
                    3
            );
            g.setPaint(additionalColor);
            g.fillOval(startPosX + 130, startPosY + 30, 20, 20);
        }
    }
}
