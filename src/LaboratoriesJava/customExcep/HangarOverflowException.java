package LaboratoriesJava.customExcep;

public class HangarOverflowException extends Exception{

    public  HangarOverflowException() {
        super("В ангаре нет свободных мест");
    }
}
