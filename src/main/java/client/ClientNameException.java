package client;

public class ClientNameException extends Exception{
    @Override
    public String getMessage() {
        return "You entered an incorrect client name";
    }
}
