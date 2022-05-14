package it.polimi.ingsw.exceptions;

public class TimedOutException extends Exception{
    public TimedOutException(){
        super("Timeout exceeded");
    }
}
