package Lab1;

import java.awt.*;

public class Bomber {
    private int startPosX;
    private int startPosY ;
    private int pictureWidth;
    private int pictureHeight;
    private final int bomberWidth = 220;
    private final int bomberHeight = 138;

    public int maxSpeed;
    public boolean isStateBombs;
    public boolean isStateGun;
    public int weight;
    public Color mainColor;
    public Color additionalColor;

    public Bomber(int maxSpeed, boolean stateBombs, boolean stateGun, int weight, Color mainColor, Color additionalColor) {
        this.maxSpeed = maxSpeed;
        isStateBombs = stateBombs;
        isStateGun = stateGun;
        this.weight = weight;
        this.mainColor = mainColor;
        this.additionalColor = additionalColor;
    }

    public void setPosition(int x, int y, int width, int height){
        startPosX = x;
        startPosY = y;
        this.pictureWidth = width;
        this.pictureHeight = height;
    }

    public void Move(MovesBomber movesBomber){
        int step = maxSpeed / weight;
        switch (movesBomber){
            case Up -> {
                if(startPosY - step > 0)
                {
                    startPosY -= step;
                }
            }
            case Down -> {
                if (startPosY + step < pictureHeight - bomberHeight)
                {
                    startPosY += step;
                }
            }
            case Right -> {
                if (startPosX + step < pictureWidth - bomberWidth)
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

    public void DrawBomber(Graphics2D g)
    {

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

        if (isStateBombs)
        {
            // верхние бомбы
            g.setPaint(additionalColor);
            g.fillRect(startPosX + 52, startPosY + 40, 10, 10);
            g.fillRect(startPosX + 67, startPosY + 20, 10, 10);

            // нижние бомбы
            g.setPaint(additionalColor);
            g.fillRect(startPosX + 52, startPosY + 90, 10, 10);
            g.fillRect(startPosX + 67, startPosY + 110, 10, 10);
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

        g.drawRect(startPosX, startPosY, bomberWidth, bomberHeight);
    }
}
