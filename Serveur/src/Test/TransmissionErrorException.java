package Test;

/**
 * Exception qui est retourner pour arreter le code lorsqu'on a plus de 3 packets perdus
 */
public class TransmissionErrorException extends Exception{
    /**
     * creer une exception de type transmission et lui donne un message d'erreur
     * @param message string representant le message d'erreur
     */
    public TransmissionErrorException(String message){
        super("TransmissionErrorException: " + message);
    }
}
