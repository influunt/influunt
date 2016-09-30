package utils;

import models.FaixasDeValores;

/**
 * Created by lesiopinheiro on 7/14/16.
 */
public class RangeUtils {

    public InfluuntRange TEMPO_DEFASAGEM;
    public InfluuntRange TEMPO_AMARELO;
    public InfluuntRange TEMPO_VERMELHO_INTERMITENTE;
    public InfluuntRange TEMPO_VERMELHO_LIMPEZA_VEICULAR;
    public InfluuntRange TEMPO_VERMELHO_LIMPEZA_PEDESTRE;
    public InfluuntRange TEMPO_ATRASO_GRUPO;
    public InfluuntRange TEMPO_VERDE_SEGURANCA_VEICULAR;
    public InfluuntRange TEMPO_VERDE_SEGURANCA_PEDESTRE;
    public InfluuntRange TEMPO_MAXIMO_PERMANENCIA_ESTAGIO;
    public InfluuntRange TEMPO_CICLO;
    public InfluuntRange TEMPO_VERDE_MINIMO;
    public InfluuntRange TEMPO_VERDE_MAXIMO;
    public InfluuntRange TEMPO_VERDE_INTERMEDIARIO;
    public InfluuntRange TEMPO_EXTENSAO_VERDE;
    public InfluuntRange TEMPO_VERDE;
    public InfluuntRange TEMPO_AUSENCIA_DETECCAO;
    public InfluuntRange TEMPO_DETECCAO_PERMANENTE;
    private Integer defaultMaximoPermanenciaEstagioVeicular;

    private RangeUtils() {
        FaixasDeValores valores = FaixasDeValores.getInstance();
        TEMPO_DEFASAGEM = new InfluuntRange<>(valores.getTempoDefasagemMin(), valores.getTempoDefasagemMax());
        TEMPO_AMARELO = new InfluuntRange<>(valores.getTempoAmareloMin(), valores.getTempoAmareloMax());
        TEMPO_VERMELHO_INTERMITENTE = new InfluuntRange<>(valores.getTempoVermelhoIntermitenteMin(), valores.getTempoVermelhoIntermitenteMax());
        TEMPO_VERMELHO_LIMPEZA_VEICULAR = new InfluuntRange<>(valores.getTempoVermelhoLimpezaVeicularMin(), valores.getTempoVermelhoLimpezaVeicularMax());
        TEMPO_VERMELHO_LIMPEZA_PEDESTRE = new InfluuntRange<>(valores.getTempoVermelhoLimpezaPedestreMin(), valores.getTempoVermelhoLimpezaPedestreMax());
        TEMPO_ATRASO_GRUPO = new InfluuntRange<Integer>(valores.getTempoAtrasoGrupoMin(), valores.getTempoAtrasoGrupoMax());
        TEMPO_VERDE_SEGURANCA_VEICULAR = new InfluuntRange<>(valores.getTempoVerdeSegurancaVeicularMin(), valores.getTempoVerdeSegurancaVeicularMax());
        TEMPO_VERDE_SEGURANCA_PEDESTRE = new InfluuntRange<>(valores.getTempoVerdeSegurancaPedestreMin(), valores.getTempoVerdeSegurancaPedestreMax());
        TEMPO_MAXIMO_PERMANENCIA_ESTAGIO = new InfluuntRange<>(valores.getTempoMaximoPermanenciaEstagioMin(), valores.getTempoMaximoPermanenciaEstagioMax());
        defaultMaximoPermanenciaEstagioVeicular = valores.getDefaultTempoMaximoPermanenciaEstagioVeicular();
        TEMPO_CICLO = new InfluuntRange<>(valores.getTempoCicloMin(), valores.getTempoCicloMax());
        TEMPO_VERDE_MINIMO = new InfluuntRange<>(valores.getTempoVerdeMinimoMin(), valores.getTempoVerdeMinimoMax());
        TEMPO_VERDE_MAXIMO = new InfluuntRange<>(valores.getTempoVerdeMaximoMin(), valores.getTempoVerdeMaximoMax());
        TEMPO_VERDE_INTERMEDIARIO = new InfluuntRange<>(valores.getTempoVerdeIntermediarioMin(), valores.getTempoVerdeIntermediarioMax());
        TEMPO_EXTENSAO_VERDE = new InfluuntRange<>(valores.getTempoExtensaoVerdeMin(), valores.getTempoExtensaoVerdeMax());
        TEMPO_VERDE = new InfluuntRange<>(valores.getTempoVerdeMin(), valores.getTempoVerdeMax());
        TEMPO_AUSENCIA_DETECCAO = new InfluuntRange<>(valores.getTempoAusenciaDeteccaoMin(), valores.getTempoAusenciaDeteccaoMax());
        TEMPO_DETECCAO_PERMANENTE = new InfluuntRange<>(valores.getTempoDeteccaoPermanenteMin(), valores.getTempoDeteccaoPermanenteMax());
    }

    public static RangeUtils getInstance() {
        return new RangeUtils();
    }

    public Integer getDefaultMaximoPermanenciaEstagioVeicular() {
        return defaultMaximoPermanenciaEstagioVeicular;
    }
}
