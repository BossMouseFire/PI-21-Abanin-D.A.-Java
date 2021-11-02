package LaboratoriesJava;

import LaboratoriesJava.interfaces.IBombs;
import LaboratoriesJava.interfaces.ITransport;
import LaboratoriesJava.transport.Bomber;
import LaboratoriesJava.transport.Plane;

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

    public int equalsPlane(int index){
        int result = 0;
        if ((Plane) _places[index] != null){
            Plane plane = (Plane) _places[index];
            result = equalsTypePlane(plane);
        } else if ((Bomber) _places[index] != null){
            Bomber bomber = (Bomber) _places[index];
            result = equalsTypeBomber(bomber);
        }
        return result - 1;
    }

    public  int notEqualsPlane(int index){
        int amountNullObjects = 0;
        for (int i = 0; i < _places.length; i++) {
            if (_places[i] == null){
                amountNullObjects++;
            }
        }
        return _places.length - equalsPlane(index) - amountNullObjects - 1;
    }
    public int equalsTypePlane(Plane plane){
        int count = 0;
        for (int i = 0; i < _places.length; i++) {
            if(_places[i] != null){
                Plane plane_i = (Plane) _places[i];
                if (plane.mainColor.hashCode() == plane_i.mainColor.hashCode()
                                && plane.maxSpeed == plane_i.maxSpeed
                                && plane.weight == plane_i.weight) {
                    count++;
                }
            }
        }
        return count;
    }

    public int equalsTypeBomber(Bomber bomber){
        int count = 0;
        for (int i = 0; i < _places.length; i++) {
            if(_places[i] != null){
                Bomber bomber_i = (Bomber) _places[i];
                if (bomber.mainColor.hashCode() == bomber_i.mainColor.hashCode()
                        && bomber.maxSpeed == bomber_i.maxSpeed
                        && bomber.weight == bomber_i.weight
                        && bomber.additionalColor.hashCode() == bomber_i.additionalColor.hashCode()) {
                    count++;
                }
            }
        }
        return count;
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
