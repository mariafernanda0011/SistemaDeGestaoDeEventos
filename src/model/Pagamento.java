package model;

/*
 * @author  Maria Fernanda S. :)
 */

public class Pagamento {

    private int id;
    private Participante participante;
    private Evento evento;
    private boolean status; // false = pendente, true = confirmado

    public Pagamento(int id, Participante participante, Evento evento, boolean status) {
        this.id = id;
        this.participante = participante;
        this.evento = evento;
        this.status = status;
    }
    
    public Pagamento(){
    
    }

    public int getId() {
        return this.id;
    }

    public Participante getParticipante() {
        return this.participante;
    }

    public Evento getEvento() {
        return this.evento;
    }

    public boolean getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return "Pagamento {"
                + "id=" + id
                + ", Participante: " + participante
                + ", Evento: " + evento
                + ", Confirmado: " + status
                + '}';
    }

    public void setStatus(boolean b) {
        this.status = b;
    }

    public void setId(int id) {
        this.id = id;
    }


}
