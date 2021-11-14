package LaboratoriesJava;
import LaboratoriesJava.transport.Vehicle;

import java.util.HashMap;
import java.util.LinkedList;

public class HangarCollection {
    private final HashMap<String, Hangar<Vehicle>> hangarStages;

    public final LinkedList<Vehicle> listPlanes;

    private final int pictureWidth;

    private final int pictureHeight;

    public HangarCollection(int pictureWidth, int pictureHeight)
    {
        hangarStages = new HashMap<>();
        listPlanes = new LinkedList<>();
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
    }

    public void addHangar(String name)
    {
        if (!hangarStages.containsKey(name))
        {
            Hangar<Vehicle> value = new Hangar<>(pictureWidth, pictureHeight);
            hangarStages.put(name, value);
        }
    }

    public void delHangar(String name)
    {
        hangarStages.remove(name);
    }

    public Hangar<Vehicle> getHangar (String name) {
        if (hangarStages.containsKey(name)) {
            return hangarStages.get(name);
        }
        return null;
    }

    public String[] getKeys(){
        return hangarStages.keySet().toArray(new String[0]);
    }

    public Vehicle getPlaneFromHC(String nameHangar, int index){
        if(hangarStages.containsKey(nameHangar)){
            Hangar<Vehicle> hangar = hangarStages.get(nameHangar);
            if(index > -1 && index < hangar.getSize()){
                return hangar.getPlane(index);
            }
            return null;
        }
        return null;
    }
}
