package LaboratoriesJava.customExcep;

public class HangarNotFoundException extends Exception {

    public HangarNotFoundException(int index) {
        super("Не найден автомобиль по месту " + index);
    }
}
