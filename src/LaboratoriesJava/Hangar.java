package LaboratoriesJava;

import LaboratoriesJava.interfaces.IBombs;
import LaboratoriesJava.interfaces.ITransport;

import java.awt.*;

public class Hangar<T extends Object & ITransport, V extends IBombs> {
    private final T[] _places;

    private final int pictureWidth;

    private final int pictureHeight;

    private final int _placeSizeWidth = 223;

    private final int _placeSizeHeight = 140;

    public Hangar(int picWidth, int picHeight){
        int width = picWidth / _placeSizeWidth;
        int height = picHeight / _placeSizeHeight;
        _places = (T[]) new Object[width * height];
        pictureWidth = picWidth;
        pictureHeight = picHeight;
    }

    public int addPlane (T plane)
    {
        for (int i = 0; i < _places.length; i++) {
            if (_places[i] == null) {
                _places[i] = plane;
                return i;
            }
        }
        return -1;
    }

    public T removePlane (int index)
    {
        if (index < _places.length || index > _places.length) {
            T plane = _places[index];
            _places[index] = null;
            return plane;
        }
        return null;
    }

    public boolean equalsSize(int number){
        int count = 0;
        for (int i = 0; i < _places.length; i++){
            if (_places[i] != null){
                count ++;
            }
        }
        return count == number;
    }

    public boolean notEqualsSize(int number){
        return !equalsSize(number);
    }

    public void Draw(Graphics2D g)
    {
        DrawMarking(g);
        for (int i = 0; i < _places.length; i++) {
            if(_places[i] != null){
                int indexWidth = pictureWidth / _placeSizeWidth;
                int indexHeight = pictureHeight / _placeSizeHeight;
                _places[i].setPosition(
                        _placeSizeWidth * (i % indexWidth),
                        _placeSizeHeight * (i / indexHeight),
                        _placeSizeWidth, _placeSizeHeight);
                _places[i].drawTransport(g);
            }
        }
    }

    private void DrawMarking(Graphics2D g)
    {
        g.setColor(Color.black);
        Stroke stroke = new BasicStroke(2f);
        g.setStroke(stroke);
        for (int i = 0; i < pictureWidth / _placeSizeWidth; i++) {
            for (int j = 0; j < pictureHeight / _placeSizeHeight + 1; ++j) {
                //линия рамзетки места
                g.drawLine(i * _placeSizeWidth, j * _placeSizeHeight, i *
                        _placeSizeWidth + _placeSizeWidth / 2, j * _placeSizeHeight);
            }
            g.drawLine(i * _placeSizeWidth, 30, i * _placeSizeWidth,
                    (pictureHeight / _placeSizeHeight) * _placeSizeHeight);
        }
    }
}
