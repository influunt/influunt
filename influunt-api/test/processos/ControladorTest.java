package processos;


import checks.InfluuntValidator;
import models.*;
import play.Application;
import play.Logger;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;

import java.util.*;

import static play.test.Helpers.inMemoryDatabase;

/**
 * Created by rodrigosol on 6/16/16.
 */
public class ControladorTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        Map<String, String> options = new HashMap<String, String>();
        options.put("DATABASE_TO_UPPER", "FALSE");
        return getApplication(inMemoryDatabase("default", options));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Application getApplication(Map configuration) {
        return new GuiceApplicationBuilder().configure(configuration).build();
    }


    protected Controlador getControladorComDadosBasicos(){
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

    protected Controlador getControladorComAneis() {
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

    protected Controlador getControladorComAssociacao() {
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

    protected void imprimeErros(List<InfluuntValidator.Erro> erros){
        int i = 0;
        for(InfluuntValidator.Erro erro :erros){
            Logger.debug(i + ":" + erro.path);
            Logger.debug(i + ":" + erro.message);
            i++;
        }
    }
}
