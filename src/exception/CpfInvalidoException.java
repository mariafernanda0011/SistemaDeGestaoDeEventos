package exception;

/*
 * @author Maria Fernanda S. :)
 */

public class CpfInvalidoException extends RuntimeException {
    
    public CpfInvalidoException(String mensagem){
        super(mensagem);
    }
    
}
