package protocol;

import java.util.Arrays;

import static java.util.Arrays.copyOfRange;

/**
 * Created by rodrigosol on 11/3/16.
 */
public class MensagemInfo extends Mensagem {


    private String fabricante;
    private String modelo;
    private String versao;


    public MensagemInfo(TipoDeMensagemBaixoNivel tipoMensagem, Integer sequencia, String fabricante, String modelo, String versao) {
        super(tipoMensagem, sequencia);
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.versao = versao;

    }

    public MensagemInfo(byte[] contents) {
        super(contents);
        String dados[] = new String(Arrays.copyOfRange(contents,4,contents.length -2)).split(";");
        this.fabricante = dados[0];
        this.modelo = dados[1];
        this.versao = dados[2];
    }

    @Override
    protected byte[] getBytes() {

        byte[] resp = new byte[innerSize()];
        return resp;
    }

    @Override
    public int innerSize() {
        return 1;
    }


    public String getFabricante() {
        return fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public String getVersao() {
        return versao;
    }
}
