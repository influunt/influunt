package os72c.client.models;

/**
 * Created by rodrigosol on 7/13/16.
 */
public class Interrupcao implements Comparable<Interrupcao>{
    public final TipoInterrupcao tipoInterrupcao;
    public final Integer indice;

    public Interrupcao(TipoInterrupcao tipoInterrupcao, Integer indice){
        this.tipoInterrupcao = tipoInterrupcao;
        this.indice = indice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Interrupcao that = (Interrupcao) o;

        if (tipoInterrupcao != that.tipoInterrupcao) return false;
        return indice != null ? indice.equals(that.indice) : that.indice == null;

    }

    @Override
    public int hashCode() {
        int result = tipoInterrupcao != null ? tipoInterrupcao.hashCode() : 0;
        result = 31 * result + (indice != null ? indice.hashCode() : 0);
        return result;
    }


    @Override
    public int compareTo(Interrupcao o) {
        if(this.tipoInterrupcao.equals(o.tipoInterrupcao)){
            return 0;
        }else if(this.tipoInterrupcao.equals(TipoInterrupcao.ERRO)){
            return -1;
        }else if(o.tipoInterrupcao.equals(TipoInterrupcao.ERRO)){
            return 1;
        }else {
            return this.tipoInterrupcao.compareTo(o.tipoInterrupcao);
        }
    }
}
