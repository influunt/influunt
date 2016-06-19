package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fixtures.ControladorFixture;
import org.junit.Test;
import play.libs.Json;

import java.io.IOException;
import java.util.Arrays;

import static java.awt.SystemColor.control;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by pedropires on 6/19/16.
 */
public class ControladorTest {


    @Test
    public void testCascade() {
        Controlador controlador = ControladorFixture.getControladorComDadosBasicos();
        controlador.save();

        Anel anel = new Anel();
        Anel anel2 = new Anel();
        Anel anelAtivo = new Anel();
        Anel anelAtivo2 = new Anel();

        anelAtivo.setAtivo(true);
        anelAtivo2.setAtivo(true);
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

        controlador.setAneis(Arrays.asList(anel, anel2, anelAtivo, anelAtivo2));
        controlador.update();

        controlador.refresh();
        anelAtivo.refresh();


        controlador = Controlador.find.byId(controlador.getId());

        assertEquals(controlador.getGruposSemaforicos().size(), 10);
        assertEquals(anelAtivo.getGruposSemaforicos().size(), 8);
        assertEquals(anelAtivo2.getGruposSemaforicos().size(), 2);
        assertEquals(anelAtivo.getDetectores().size(), 2);
        assertEquals(anelAtivo2.getDetectores().size(), 2);
    }

    @Test
    public void testJsonUpdate() {
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

        // Dados basicos
        String jsonStr = "{\"area\":{\"id\":\""+ area.getId().toString() +"\",\"descricao\":51,\"limitesGeograficos\":[],\"dataCriacao\":\"18/06/2016 16:13:00\",\"dataAtualizacao\":\"18/06/2016 16:13:00\"},\"descricao\":\"desc\",\"numeroSMEE\":\"1111\",\"numeroSMEEConjugado1\":\"1\",\"numeroSMEEConjugado2\":\"1\",\"numeroSMEEConjugado3\":\"1\",\"firmware\":\"111\",\"latitude\":1,\"longitude\":-90,\"modelo\":{\"id\":\""+ modeloControlador.getId().toString() +"\",\"configuracao\":{\"id\":\""+ conf.getId().toString() +"\",\"descricao\":\"asas\",\"limiteEstagio\":5,\"limiteGrupoSemaforico\":5,\"limiteAnel\":5,\"limiteDetectorPedestre\":5,\"limiteDetectorVeicular\":5,\"dataCriacao\":\"18/06/2016 16:14:07\",\"dataAtualizacao\":\"18/06/2016 16:14:07\"},\"descricao\":\"desc\",\"dataCriacao\":\"18/06/2016 16:14:46\",\"dataAtualizacao\":\"18/06/2016 16:14:46\",\"nomeFabricante\":\"Opa\"}}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controlador controlador = Json.fromJson(jsonNode, Controlador.class);
        controlador.save();
        assertNotNull(controlador.getId());

        Imagem imagem1 = new Imagem();
        imagem1.setFilename("opa.png");
        imagem1.setContentType("image/png");
        imagem1.save();

        Imagem imagem2 = new Imagem();
        imagem2.setFilename("opa.png");
        imagem2.setContentType("image/png");
        imagem2.save();

        Imagem imagem3 = new Imagem();
        imagem3.setFilename("opa.png");
        imagem3.setContentType("image/png");
        imagem3.save();

        Imagem imagem4 = new Imagem();
        imagem4.setFilename("opa.png");
        imagem4.setContentType("image/png");
        imagem4.save();

        // Aneis
        jsonStr = "{\"id\":"+controlador.getId().toString()+",\"dataCriacao\":\"19/06/2016 10:06:08\",\"dataAtualizacao\":\"19/06/2016 10:06:08\",\"descricao\":\"desc\",\"numeroSMEE\":\"1111\",\"numeroSMEEConjugado1\":\"1\",\"numeroSMEEConjugado2\":\"1\",\"numeroSMEEConjugado3\":\"1\",\"firmware\":\"111\",\"latitude\":1,\"longitude\":-90,\"modelo\":{\"id\":\""+modeloControlador.getId().toString()+"\",\"configuracao\":{\"id\":\""+conf.getId().toString()+"\",\"descricao\":\"asas\",\"limiteEstagio\":5,\"limiteGrupoSemaforico\":5,\"limiteAnel\":5,\"limiteDetectorPedestre\":5,\"limiteDetectorVeicular\":5,\"dataCriacao\":\"18/06/2016 16:14:07\",\"dataAtualizacao\":\"18/06/2016 16:14:07\"},\"descricao\":\"desc\",\"dataCriacao\":\"18/06/2016 16:14:46\",\"dataAtualizacao\":\"18/06/2016 16:14:46\",\"nomeFabricante\":\"Opa\"},\"area\":{\"id\":\""+area.getId().toString()+"\",\"descricao\":51,\"limitesGeograficos\":[],\"dataCriacao\":\"18/06/2016 16:13:00\",\"dataAtualizacao\":\"18/06/2016 16:13:00\"},\"aneis\":[{\"ativo\":true,\"id_anel\":\"51.000.0008-1\",\"posicao\":1,\"quantidadeGrupoPedestre\":1,\"quantidadeGrupoVeicular\":1,\"quantidadeDetectorPedestre\":1,\"descricao\":\"asas\",\"numero_smee\":\"555\",\"latitude\":-23.550257863381706,\"longitude\":-46.663527488708496,\"valid\":{\"form\":true,\"required\":{\"descricao\":true,\"latitude\":true,\"longitude\":true},\"totalGruposSemaforicos\":true,\"totalDetectorVeicular\":true,\"totalDetectorPedestres\":true},\"quantidadeDetectorVeicular\":1,\"movimentos\":[{\"imagem\":{\"id\":\""+imagem1.getId().toString()+"\"}},{\"imagem\":{\"id\":\""+imagem2.getId().toString()+"\"}}]},{\"ativo\":true,\"id_anel\":\"51.000.0008-2\",\"posicao\":2,\"quantidadeGrupoPedestre\":1,\"quantidadeGrupoVeicular\":1,\"quantidadeDetectorPedestre\":1,\"descricao\":\"dfgdfdf\",\"numero_smee\":\"999\",\"latitude\":-23.548880923858743,\"longitude\":-46.66541576385498,\"valid\":{\"form\":true,\"required\":{\"descricao\":true,\"latitude\":true,\"longitude\":true}},\"quantidadeDetectorVeicular\":1,\"movimentos\":[{\"imagem\":{\"id\":\""+imagem3.getId().toString()+"\"}},{\"imagem\":{\"id\":\""+imagem4.getId().toString()+"\"}}]},{\"ativo\":false,\"id_anel\":\"51.000.0008-3\",\"posicao\":3,\"quantidadeGrupoPedestre\":null,\"quantidadeGrupoVeicular\":null,\"quantidadeDetectorPedestre\":null,\"descricao\":null,\"numero_smee\":null,\"latitude\":null,\"longitude\":null,\"valid\":{\"form\":true,\"required\":{}}},{\"ativo\":false,\"id_anel\":\"51.000.0008-4\",\"posicao\":4,\"quantidadeGrupoPedestre\":null,\"quantidadeGrupoVeicular\":null,\"quantidadeDetectorPedestre\":null,\"descricao\":null,\"numero_smee\":null,\"latitude\":null,\"longitude\":null,\"valid\":{\"form\":true,\"required\":{}}},{\"ativo\":false,\"id_anel\":\"51.000.0008-5\",\"posicao\":5,\"quantidadeGrupoPedestre\":null,\"quantidadeGrupoVeicular\":null,\"quantidadeDetectorPedestre\":null,\"descricao\":null,\"numero_smee\":null,\"latitude\":null,\"longitude\":null,\"valid\":{\"form\":true,\"required\":{}}}],\"gruposSemaforicos\":[],\"detectores\":[],\"movimentos\":[],\"idControlador\":\"51.000.0008\"}";
        mapper = new ObjectMapper();
        jsonNode = null;
        try {
            jsonNode = mapper.readTree(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        controlador = Json.fromJson(jsonNode, Controlador.class);

        for(Anel anel : controlador.getAneis()) {
            anel.save();
        }

        controlador.update();
        assertEquals(5, controlador.getAneis().size());
        assertEquals(4, controlador.getGruposSemaforicos().size());
    }

    @Test
    public TestCriarControlador() {
        Controlador controlador = new Controlador();

    }

}
