package LaboratoriesJava;

import LaboratoriesJava.customExcep.HangarAlreadyHaveException;
import LaboratoriesJava.customExcep.HangarNotFoundException;
import LaboratoriesJava.customExcep.HangarOverflowException;
import LaboratoriesJava.interfaces.ITransport;


import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Hangar<T extends Object & ITransport> {
    private final List<T> _places;

    private final int _maxCount;

    private final int pictureWidth;

    private final int pictureHeight;

    private final int _placeSizeWidth = 223;

    private final int _placeSizeHeight = 140;

    public Hangar(int picWidth, int picHeight){
        int width = picWidth / _placeSizeWidth;
        int height = picHeight / _placeSizeHeight;
        _places = new ArrayList<>();
        pictureWidth = picWidth;
        pictureHeight = picHeight;
        _maxCount = width * height;
    }

    public boolean addPlane (T plane) throws HangarOverflowException, HangarAlreadyHaveException {
        if (_places.size() >= _maxCount) {
            throw new HangarOverflowException();
        }
        if (_places.contains(plane))
        {
            throw new HangarAlreadyHaveException();
        }
        _places.add(plane);
        return true;
    }

    public T removePlane (int index) throws HangarNotFoundException {
        if (index < 0  || index >= _places.size()) {
           throw new HangarNotFoundException(index);
        }
        T plane = _places.get(index);
        _places.remove(index);
        return plane;
    }

    public void clearHangar() {
        _places.clear();
    }

    public void Draw(Graphics2D g)
    {
        DrawMarking(g);
        for (int i = 0; i < _places.size(); i++) {
            if(_places.get(i) != null){
                int indexWidth = pictureWidth / _placeSizeWidth;
                int indexHeight = pictureHeight / _placeSizeHeight;
                _places.get(i).setPosition(
                        _placeSizeWidth * (i % indexWidth),
                        _placeSizeHeight * (i / indexHeight),
                        _placeSizeWidth, _placeSizeHeight);
                _places.get(i).drawTransport(g);
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

    public T getPlane(int index){
        if (index >= 0 && index < _places.size()){
            return _places.get(index);
        }
        return null;
    }

    public void sort() {
        _places.sort((Comparator<? super T>) new PlaneComparator());
    }
}
