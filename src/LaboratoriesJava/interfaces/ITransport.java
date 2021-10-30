package LaboratoriesJava.interfaces;

import LaboratoriesJava.enums.MovesBomber;

import java.awt.*;

public interface ITransport {

    void setPosition(int x, int y, int width, int height);

    void moveTransport(MovesBomber movesBomber);

    void drawTransport(Graphics2D g);

}
