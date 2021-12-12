package LaboratoriesJava;

import LaboratoriesJava.transport.Bomber;
import LaboratoriesJava.transport.Plane;
import LaboratoriesJava.transport.Vehicle;

import java.util.Comparator;

public class PlaneComparator implements Comparator<Vehicle> {

    @Override
    public int compare(Vehicle x, Vehicle y)
    {
        if (x instanceof Bomber && y instanceof Bomber) {
            return compareBomber((Bomber) x, (Bomber) y);
        }
        return comparePlane((Plane) x, (Plane) y);
    }

    private int comparePlane(Plane x, Plane y)
    {
        return x.compareTo(y);
    }

    private int compareBomber(Bomber x, Bomber y)
    {
        return x.compareTo(y);
    }
}
