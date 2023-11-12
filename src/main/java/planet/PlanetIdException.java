package planet;

public class PlanetIdException extends Exception{
    @Override
    public String getMessage() {
        return "You entered an incorrect planet id";
    }
}
