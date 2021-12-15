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

        if (x instanceof Bomber && y instanceof Plane) {
            return 1;
        }
        if (x instanceof Plane && y instanceof Bomber) {
            return -1;
        }

        if (x instanceof Plane && y instanceof Plane) {
            return comparePlane((Plane) x, (Plane) y);
        }
        return  0;
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
