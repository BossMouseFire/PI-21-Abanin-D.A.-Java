package LaboratoriesJava.customExcep;

public class HangarAlreadyHaveException extends Exception{
    public HangarAlreadyHaveException() {
        super("В ангаре есть уже такой самолёт");
    }
}
