package LaboratoriesJava.transport;

import LaboratoriesJava.bombForms.*;
import LaboratoriesJava.enums.BombForms;
import LaboratoriesJava.interfaces.IBombs;

import java.awt.*;

public class Bomber extends Plane implements Comparable<Plane> {

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

        changeBombsType(bombForms);
    }

    public Bomber(String info)
    {
        super(info);
        String[] strs = info.split(String.valueOf(separator));
        if (strs.length == 7)
        {
            maxSpeed = Integer.parseInt(strs[0]);
            weight = Integer.parseInt(strs[1]);
            mainColor = new Color(Integer.parseInt(strs[2]));
            additionalColor = new Color(Integer.parseInt(strs[3]));
            isStateBombs = Boolean.parseBoolean(strs[4]);
            isStateGun = Boolean.parseBoolean(strs[5]);
            changeBombsType(BombForms.valueOf(strs[6]));
        }
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

    public void setAddColor(Color additionalColor) {
        this.additionalColor = additionalColor;
    }

    public void changeBombsType(BombForms bombForms){
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

    private BombForms changeIBombsToType(IBombs bombs) {
        if (BombsOval.class.equals(bombs.getClass())) {
            return BombForms.Oval;
        } else if(BombsRect.class.equals(bombs.getClass())) {
            return BombForms.Rectangle;
        } else{
            return BombForms.Rocket;
        }
    }

    @Override
    public String toString() {
        String addColor = Integer.toString(additionalColor.getRGB());
        BombForms bombForms = changeIBombsToType(bombs);
        return super.toString() + separator + addColor + separator + isStateBombs + separator + isStateGun + separator + bombForms.toString();
    }

    public int compareTo(Bomber o) {

        if (additionalColor != o.additionalColor)
        {
            return additionalColor.hashCode() > o.additionalColor.hashCode() ? 1 : -1;
        }
        if (isStateBombs != o.isStateBombs)
        {
            return isStateBombs ? 1 : -1;
        }
        if (isStateGun != o.isStateGun)
        {
            return isStateGun  ? 1 : -1;
        }
        if (bombs.getClass() != bombs.getClass()) {
            return 1;
        }

        return super.compareTo(o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Bomber bomber = (Bomber) o;
        return isStateBombs == bomber.isStateBombs
                && isStateGun == bomber.isStateGun
                && bombs.getClass() == bomber.bombs.getClass()
                && additionalColor.hashCode() == bomber.additionalColor.hashCode();
    }
}
