package LaboratoriesJava.bombForms;

import LaboratoriesJava.enums.AmountBombs;
import LaboratoriesJava.interfaces.IBombs;

import java.awt.*;

public class BombsOval implements IBombs {
    private AmountBombs amountBombs;

    @Override
    public void setAmountBombs(int amountBombs) {
        switch (amountBombs){
            case 6 -> this.amountBombs = AmountBombs.Six;
            case 8 -> this.amountBombs = AmountBombs.Eight;
            case 10 ->  this.amountBombs = AmountBombs.Ten;
            default -> this.amountBombs = AmountBombs.Zero;
        }
    }

    private void drawBomb(Graphics2D g, Color additionalColor, int x, int y) {
        g.setPaint(additionalColor);
        g.fillOval(x, y, 8, 8);
    }

    @Override
    public void drawBombs(Graphics2D g, Color additionalColor, int startPosX, int startPosY) {
        int positionX = 47;
        int positionY = 48;
        int positionYDownBomb = 36;
        int amount;
        switch (amountBombs){
            case Six -> amount = 6;
            case Eight -> amount = 8;
            case Ten -> amount = 10;
            default -> amount = 0;
        }
        for(int i = 0; i < amount / 2; i++){
            //отрисовка верхней бомбы
            drawBomb(g, additionalColor, startPosX + positionX, startPosY + positionY);
            //отрисовка нижней бомбы
            drawBomb(g, additionalColor, startPosX + positionX, startPosY + positionY + positionYDownBomb);
            positionX += 8;
            positionY -= 11;
            positionYDownBomb += 22;
        }
    }
}
