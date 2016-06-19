package fixtures;

import models.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rodrigosol on 6/17/16.
 */
public class ControladorFixture {

    public static Controlador getControladorComDadosBasicos(){
        Cidade cidade = new Cidade();
        cidade.setNome("BH");
        cidade.save();

        Area area = new Area();
        area.setCidade(cidade);
        area.setDescricao(1);
        area.save();

        Fabricante fabricante = new Fabricante();
        fabricante.setNome("Raro Labs");
        fabricante.save();

        ConfiguracaoControlador conf =  new ConfiguracaoControlador();
        conf.setLimiteAnel(4);
        conf.setLimiteGrupoSemaforico(16);
        conf.save();

        ModeloControlador modeloControlador = new ModeloControlador();
        modeloControlador.setFabricante(fabricante);
        modeloControlador.setConfiguracao(conf);
        modeloControlador.save();

        Controlador controlador = new Controlador();
        controlador.setDescricao("Teste");
        controlador.setLatitude(1.0);
        controlador.setLongitude(2.0);
        controlador.setArea(area);
        controlador.setModelo(modeloControlador);
        controlador.setNumeroSMEE("1234");
        controlador.setFirmware("1235");

        return controlador;
    }

    public static Controlador getControladorComAneis() {
        Controlador controlador = getControladorComDadosBasicos();
        Anel anel = new Anel();
        Anel anel2 = new Anel();
        Anel anelAtivo = new Anel();
        Anel anelAtivo2 = new Anel();

        anelAtivo.setAtivo(true);
        anelAtivo2.setAtivo(true);
        controlador.setAneis(Arrays.asList(anel, anel2, anelAtivo, anelAtivo2));
        anelAtivo.setPosicao(1);
        anelAtivo.setLatitude(1.0);
        anelAtivo.setLongitude(2.0);
        anelAtivo2.setPosicao(1);
        anelAtivo2.setLatitude(1.0);
        anelAtivo2.setLongitude(2.0);

        anelAtivo2.setQuantidadeGrupoVeicular(2);
        anelAtivo2.setQuantidadeDetectorVeicular(2);

        anelAtivo.setQuantidadeGrupoVeicular(8);
        anelAtivo.setQuantidadeDetectorPedestre(2);

        anelAtivo.setMovimentos(Arrays.asList(new Movimento(), new Movimento()));
        anelAtivo2.setMovimentos(Arrays.asList(new Movimento(), new Movimento()));

        controlador.save();
        controlador.refresh();
        return controlador;
    }

    public static Controlador getControladorComAssociacao() {
        Controlador controlador = getControladorComAneis();
        List<EstagioGrupoSemaforico> estagioGrupoSemaforicos = new ArrayList<EstagioGrupoSemaforico>();


        controlador.getAneis().stream().filter(anel -> anel.isAtivo()).forEach(anel -> {
            anel.getMovimentos().stream().forEach(movimento -> {
                EstagioGrupoSemaforico estagioGrupoSemaforico = new EstagioGrupoSemaforico();

                Estagio estagio = movimento.getEstagio();
                GrupoSemaforico grupoSemaforico = anel.getGruposSemaforicos().get(0);

                estagioGrupoSemaforico.setEstagio(estagio);
                estagioGrupoSemaforico.setGrupoSemaforico(grupoSemaforico);
                estagioGrupoSemaforicos.add(estagioGrupoSemaforico);

                estagio.setEstagiosGruposSemaforicos(estagioGrupoSemaforicos);
                grupoSemaforico.setEstagioGrupoSemaforicos(estagioGrupoSemaforicos);
            });
            anel.getGruposSemaforicos().stream().forEach(grupoSemaforico -> {
                grupoSemaforico.setTipo(TipoGrupoSemaforico.VEICULAR);
            });
        });
        return controlador;
    }

    public static Controlador getControladorComVerdesConflitantes() {
        Controlador controlador = getControladorComAssociacao();

        GrupoSemaforico grupoSemaforicoA1 = controlador.getAneis().get(2).getGruposSemaforicos().get(0);
        GrupoSemaforico grupoSemaforicoA2 = controlador.getAneis().get(2).getGruposSemaforicos().get(1);

        GrupoSemaforico grupoSemaforicoB1 = controlador.getAneis().get(3).getGruposSemaforicos().get(0);
        GrupoSemaforico grupoSemaforicoB2 = controlador.getAneis().get(3).getGruposSemaforicos().get(1);

        grupoSemaforicoA1.setVerdesConflitantes(Arrays.asList(grupoSemaforicoA2));
        grupoSemaforicoB1.setVerdesConflitantes(Arrays.asList(grupoSemaforicoB2));

        return controlador;
    }

}
