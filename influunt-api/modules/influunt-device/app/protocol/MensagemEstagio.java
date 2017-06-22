package protocol;

import com.google.common.collect.RangeMap;
import engine.IntervaloGrupoSemaforico;
import models.EstadoGrupoSemaforico;

import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;

/**
 * Created by rodrigosol on 11/3/16.
 */
public class MensagemEstagio extends Mensagem {

    private MensagemGrupo[] mensagemGrupo;

    public MensagemEstagio(TipoDeMensagemBaixoNivel tipoMensagem, Integer sequencia, Integer quantidadeGrupos) {
        super(tipoMensagem, sequencia);
        this.mensagemGrupo = new MensagemGrupo[quantidadeGrupos];
    }

    public MensagemEstagio(byte[] contents) {
        super(contents);
        mensagemGrupo = new MensagemGrupo[contents[4]];
        for (int i = 0; i < mensagemGrupo.length; i++) {
            mensagemGrupo[i] = new MensagemGrupo(i, contents);
        }
    }

    @Override
    protected byte[] getBytes() {
        byte[] resp = new byte[innerSize()];
        resp[0] = getFlags();

        for (int i = 0; i < mensagemGrupo.length; i++) {
            mensagemGrupo[i].fill(i, resp);
        }
        return resp;
    }

    @Override
    public int innerSize() {
        return 1 + mensagemGrupo.length * 14;
    }

    public byte getFlags() {
        return (byte) mensagemGrupo.length;
    }

    public void addGrupo(int index, int grupo, int tempoAtrasoDeGrupo, int tempoAmareloOuVermelhoIntermitente,
                         int tempoVermelhoLimpeza, int tempoVerdeOuVermelho) {

        mensagemGrupo[index] = new MensagemGrupo(grupo, tempoAtrasoDeGrupo,
            tempoAmareloOuVermelhoIntermitente, tempoVermelhoLimpeza,
            tempoVerdeOuVermelho);
    }


    public void addIntervalos(IntervaloGrupoSemaforico intervalos) {
        final HashMap<Integer, RangeMap<Long, EstadoGrupoSemaforico>> estados = intervalos.getEstados();
        int i = 0;
        for (Integer grupo : estados.keySet()) {
            MensagemGrupo msg = new MensagemGrupo(grupo, estados.get(grupo));
            addGrupo(i++, msg);
        }
    }

    private void addGrupo(int index, MensagemGrupo msg) {
        mensagemGrupo[index] = msg;
    }

    public String print() {
        StringBuffer stringBuffer = new StringBuffer(new Formatter().format("|GR|P/V|FL|AT/VE|AM/VI|VL|VE/V/AI/D|").toString());
        Arrays.stream(mensagemGrupo).forEach(m -> {
            stringBuffer.append(m).append("\n");
        });
        return stringBuffer.toString();
    }

    public MensagemGrupo[] getMensagemGrupo() {
        return mensagemGrupo;
    }
}
