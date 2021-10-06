package Lab1;

import java.awt.*;

public class Bombs {
    private AmountBombs amountBombs;

    public void setAmountBombs(int amountBombs) {
        switch (amountBombs){
            case 0 -> this.amountBombs = AmountBombs.Zero;
            case 6 -> this.amountBombs = AmountBombs.Six;
            case 8 -> this.amountBombs = AmountBombs.Eight;
            case 10 ->  this.amountBombs = AmountBombs.Ten;
            default -> this.amountBombs = AmountBombs.Zero;
        }

    }

    private void drawBomb(Graphics2D g, Color additionalColor, int x, int y){
        g.setPaint(additionalColor);
        g.fillRect(x, y, 8, 8);
    }

    public void drawBombs(Graphics2D g, Color additionalColor, int startPosX, int startPosY){
        int positionX = 47;
        int positionY = 48;
        int positionYDownBomb = 36;
        int amount;
        switch (amountBombs){
            case Zero -> amount = 0;
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
