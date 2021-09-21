package Lab1;

import java.awt.*;

public class Bombs {
    private int amountBombs;

    public void setAmountBombs(int amountBombs) {
        this.amountBombs = amountBombs;
    }

    private void drawBomb(Graphics2D g, Color additionalColor, int x, int y){
        g.setPaint(additionalColor);
        g.fillRect(x, y, 8, 8);
    }

    public void drawBombs(Graphics2D g, Color additionalColor, int startPosX, int startPosY){
        int positionX = 47;
        int positionY = 48;
        int positionYDownBomb = 36;
        for(int i = 0; i < amountBombs / 2; i++){
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
