package engine;

/**
 * Created by rodrigosol on 11/1/16.
 */
public class TipoEventoParamsDescriptor {
    private final String nome;

    private final TipoEventoParamsTipoDeDado tipo;

    public TipoEventoParamsDescriptor(String nome, TipoEventoParamsTipoDeDado tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public TipoEventoParamsTipoDeDado getTipo() {
        return tipo;
    }
}
