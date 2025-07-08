package exception;

/*
 * @author Maria Fernanda S. :)
 */

// Criando uma excceção personalizada. //
public class AutenticarException extends RuntimeException {
    
    public AutenticarException(String mensagem){
        super(mensagem);
    }
    
}
