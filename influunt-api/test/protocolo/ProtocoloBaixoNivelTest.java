package protocolo;

import checks.Erro;
import engine.GerenciadorDeTabelaHoraria;
import models.DiaDaSemana;
import models.Evento;
import models.TipoEvento;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import protocol.*;
import utils.CustomCalendar;

import java.util.Calendar;

import static org.apache.commons.codec.binary.Hex.encodeHexString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by rodrigosol on 9/8/16.
 */
public class ProtocoloBaixoNivelTest {

    @Test
    public void retornoOkTest(){
        Mensagem mensagem = new MensagemRetorno(TipoDeMensagemBaixoNivel.RETORNO,1, Retorno.OK);
        assertEquals(4,mensagem.size());
        byte[] expected = new byte[]{0,1,0,0};
        assertThat(mensagem.toByteArray(), org.hamcrest.Matchers.equalTo(expected));
    }


    @Test
    public void retornoErroTest(){
        Mensagem mensagem = new MensagemRetorno(TipoDeMensagemBaixoNivel.RETORNO,1, Retorno.ERRO);
        assertEquals(4,mensagem.size());
        byte[] expected = new byte[]{0,1,1,0};
        assertThat(mensagem.toByteArray(), org.hamcrest.Matchers.equalTo(expected));
    }

    @Test
    public void estagioTest(){
        MensagemEstagio mensagem = new MensagemEstagio(TipoDeMensagemBaixoNivel.ESTAGIO,2, 5);
        mensagem.addGrupo(0,1,false,false,false,255,10,127,128);
        mensagem.addGrupo(1,2,false,false,false,255,10,127,128);
        mensagem.addGrupo(2,3,false,false,false,255,10,127,128);
        mensagem.addGrupo(3,4,false,false,false,255,10,127,128);
        mensagem.addGrupo(4,5,false,false,false,255,10,127,128);

        assertEquals(29,mensagem.size());
        byte[] expected = new byte[]{(byte)TipoDeMensagemBaixoNivel.ESTAGIO.ordinal(),2,
            5,
            1,(byte)255,10,127,(byte)128,
            2,(byte)255,10,127,(byte)128,
            3,(byte)255,10,127,(byte)128,
            4,(byte)255,10,127,(byte)128,
            5,(byte)255,10,127,(byte)128
            ,0};
        assertThat(mensagem.toByteArray(), org.hamcrest.Matchers.equalTo(expected));
    }

    @Test
    public void estagioComFlagsTest(){
        MensagemEstagio mensagem = new MensagemEstagio(TipoDeMensagemBaixoNivel.ESTAGIO,2, 5);
        mensagem.addGrupo(0,1,true,false,false,255,10,127,128);
        mensagem.addGrupo(1,2,false,true,false,255,10,127,128);
        mensagem.addGrupo(2,3,false,false,true,255,10,127,128);
        mensagem.addGrupo(3,4,true,true,true,255,10,127,128);
        mensagem.addGrupo(4,5,true,true,false,255,10,127,128);

        assertEquals(29,mensagem.size());
        byte[] expected = new byte[]{(byte)TipoDeMensagemBaixoNivel.ESTAGIO.ordinal(),2,
            5,
            (byte)129,(byte)255,10,127,(byte)128,
            66,(byte)255,10,127,(byte)128,
            35,(byte)255,10,127,(byte)128,
            (byte)228,(byte)255,10,127,(byte)128,
            (byte)197,(byte)255,10,127,(byte)128
            ,0};
        assertThat(mensagem.toByteArray(), org.hamcrest.Matchers.equalTo(expected));
    }



}
