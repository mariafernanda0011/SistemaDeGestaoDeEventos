package model;

import java.util.List;

/* 
 * @author Maria Fernanda S. :)
 */
public class Atividade {
    
    private int id;
    private String descricao;
    private String ministrante;
    private int dataDeRealizacao;
    private int limiteIncritos;
    private TipoDeAtividade tipo;
    private List<Inscricao> inscritos;

    public Atividade(String descricao, String ministrante, int dataDeRealizacao, int limiteIncritos, TipoDeAtividade tipo) {
        this.descricao = descricao;
        this.ministrante = ministrante;
        this.dataDeRealizacao = dataDeRealizacao;
        this.limiteIncritos = limiteIncritos;
        this.tipo = tipo;
    }

    public Atividade(int id, String descricao, String ministrante, int dataDeRealizacao, int limiteIncritos, TipoDeAtividade tipo) {
        this.id = id;
        this.descricao = descricao;
        this.ministrante = ministrante;
        this.dataDeRealizacao = dataDeRealizacao;
        this.limiteIncritos = limiteIncritos;
        this.tipo = tipo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public String getMinistrante() {
        return this.ministrante;
    }

    public int getDataDeRealizacao() {
        return this.dataDeRealizacao;
    }

    public int getLimiteDeVagas() {
        return this.limiteIncritos;
    }

    public TipoDeAtividade getTipo() {
        return this.tipo;
    }

    public int getId() {
        return this.id;
    }
}
