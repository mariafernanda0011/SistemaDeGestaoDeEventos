package dao;

import model.*;
import java.util.List;

/*
 * @author Maria Fernanda S. :)
 */

public interface EventoDao {

    void criarEvento(Evento evento);

    void excluirEvento(Evento evento);

    void editarEvento(Evento evento, String nomeEvento, String novaDuracao, String data, int hora, int novoLimite);

    Evento buscarEvento(String nomeEvento);
    
    List<Evento> visualizarEventos();

}
