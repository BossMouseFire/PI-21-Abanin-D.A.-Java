package LaboratoriesJava;
import LaboratoriesJava.customExcep.HangarAlreadyHaveException;
import LaboratoriesJava.customExcep.HangarOverflowException;
import LaboratoriesJava.interfaces.ITransport;
import LaboratoriesJava.transport.Bomber;
import LaboratoriesJava.transport.Plane;
import LaboratoriesJava.transport.Vehicle;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.FileAlreadyExistsException;
import java.security.KeyStore;
import java.util.FormatterClosedException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class HangarCollection {
    private final HashMap<String, Hangar<Vehicle>> hangarStages;

    public final LinkedList<Vehicle> listPlanes;

    private final int pictureWidth;

    private final int pictureHeight;

    protected final char separator = ':';


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
            return hangar.getPlane(index);
        }
        return null;
    }

    public void saveData(File file) throws IOException {
        FileWriter writer = new FileWriter(file, false);
        writer.write("HangarCollection\n");

        for (HashMap.Entry<String, Hangar<Vehicle>> level : hangarStages.entrySet()) {
            writer.write(String.format("Hangar%s%s\n", separator, level.getKey()));
            ITransport plane;
            for (int i = 0; (plane = level.getValue().getPlane(i)) != null; i++)
            {
                //если место не пустое
                //Записываем тип машины
                if (plane.getClass() == Plane.class)
                {
                    writer.write(String.format("Plane%s", separator));
                }
                if (plane.getClass() == Bomber.class)
                {
                    writer.write(String.format("Bomber%s", separator));
                }
                writer.write(plane.toString() + '\n');
            }
        }
        writer.flush();
        writer.close();
    }

    public void saveDataHangar(File file, String name) throws IOException {
        FileWriter writer = new FileWriter(file, false);
        writer.write(String.format("Hangar%s%s\n", separator, name));
        ITransport plane;
        Hangar<Vehicle> hangar = getHangar(name);

        if (hangar == null) {
            return;
        }

        for (int i = 0; (plane = hangar.getPlane(i)) != null; i++)
        {
            if (plane.getClass() == Plane.class)
            {
                writer.write(String.format("Plane%s", separator));
            }
            if (plane.getClass() == Bomber.class)
            {
                writer.write(String.format("Bomber%s", separator));
            }
            writer.write(plane.toString() + '\n');
        }
        writer.flush();
        writer.close();
    }

    public void loadDataHangar(File file) throws IOException, HangarOverflowException, HangarAlreadyHaveException {
        FileReader fileReader;
        if (file.isFile()) {
            fileReader = new FileReader(file);
        } else {
            throw new IOException("Файл не найден");
        }

        Scanner scannerFile = new Scanner(fileReader);
        String line = scannerFile.nextLine();
        String key;
        if (line.contains("Hangar") ) {
            key = line.split(String.valueOf(separator))[1];
            Hangar<Vehicle> hangar = hangarStages.get(key);
            if (hangar != null) {
                hangar.clearHangar();
            } else {
                hangarStages.put(key, new Hangar<>(pictureWidth,
                        pictureHeight));
            }
        } else{
            throw new IOException("Неверный формат файла");
        }
        while (scannerFile.hasNextLine()) {
            line = scannerFile.nextLine();
            Vehicle plane = null;
            String newSep = String.valueOf(separator);

            if (line.length() == 0)
            {
                continue;
            }
            if (line.split(newSep)[0].equals("Plane"))
            {
                plane = new Plane(line.split(newSep)[1]);
            }
            else if (line.split(newSep)[0].equals("Bomber"))
            {
                plane = new Bomber(line.split(newSep)[1]);
            }
            if (plane != null)
            {
                var result = hangarStages.get(key).addPlane(plane);
                if (!result)
                {
                    throw new IOException("Не удалось загрузить самолёт в ангар");
                }
            }
        }
    }
    public void loadData(File file) throws IOException, HangarOverflowException, HangarAlreadyHaveException {
        FileReader fileReader;
        if (file.isFile()) {
            fileReader = new FileReader(file);
        } else {
            throw new IOException("Файл не найден");
        }

        Scanner scannerFile = new Scanner(fileReader);
        String key = "";
        String line = scannerFile.nextLine();

        if (line.contains("HangarCollection"))
        {
            hangarStages.clear();
        }
        else
        {
            throw new IOException("Неверный формат файла");
        }
        while (scannerFile.hasNextLine()){
            line = scannerFile.nextLine();

            Vehicle plane = null;
            String newSep = String.valueOf(separator);
            if (line.contains("Hangar") && !hangarStages.containsKey(line.split(newSep)[1]))
            {
                //начинаем новую парковку
                key = line.split(newSep)[1];
                hangarStages.put(key, new Hangar<>(pictureWidth,
                        pictureHeight));
                continue;
            }
            if (line.length() == 0)
            {
                continue;
            }
            if (line.split(newSep)[0].equals("Plane"))
            {
                plane = new Plane(line.split(newSep)[1]);
            }
            else if (line.split(newSep)[0].equals("Bomber"))
            {
                plane = new Bomber(line.split(newSep)[1]);
            }
            if (plane != null)
            {
                var result = hangarStages.get(key).addPlane(plane);
                if (!result)
                {
                    throw new IOException("Не удалось загрузить самолёт в ангар");
                }
            }
        }
    }

}
