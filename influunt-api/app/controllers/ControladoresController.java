package controllers;

import be.objectify.deadbolt.java.actions.Dynamic;
import checks.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import models.*;
import org.apache.commons.lang.StringUtils;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import services.ControladorService;
import services.FalhasEAlertasService;
import utils.InfluuntQueryBuilder;
import utils.InfluuntResultBuilder;
import utils.RangeUtils;
import utils.TransacaoHelper;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static models.VersaoControlador.usuarioPodeEditarControlador;

@Security.Authenticated(Secured.class)
public class ControladoresController extends Controller {

    @Inject
    private ControladorService controladorService;

    @Inject
    private TransacaoHelper transacaoHelper;

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(bodyArea)")
    public CompletionStage<Result> dadosBasicos() {
        return doStep(javax.validation.groups.Default.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(body)")
    public CompletionStage<Result> aneis() {
        return doStep(javax.validation.groups.Default.class, ControladorAneisCheck.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(body)")
    public CompletionStage<Result> gruposSemaforicos() {
        return doStep(javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(body)")
    public CompletionStage<Result> verdesConflitantes() {
        return doStep(javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorVerdesConflitantesCheck.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(body)")
    public CompletionStage<Result> associacaoGruposSemaforicos() {
        return doStep(javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(body)")
    public CompletionStage<Result> transicoesProibidas() {
        return doStep(javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
            ControladorTransicoesProibidasCheck.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(body)")
    public CompletionStage<Result> entreVerdes() {
        return doStep(javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
            ControladorTransicoesProibidasCheck.class, ControladorTabelaEntreVerdesCheck.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(body)")
    public CompletionStage<Result> atrasoDeGrupo() {
        return doStep(javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
            ControladorTransicoesProibidasCheck.class, ControladorTabelaEntreVerdesCheck.class,
            ControladorAtrasoDeGrupoCheck.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(body)")
    public CompletionStage<Result> associacaoDetectores() {
        return doStep(javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
            ControladorTransicoesProibidasCheck.class, ControladorTabelaEntreVerdesCheck.class,
            ControladorAtrasoDeGrupoCheck.class, ControladorAssociacaoDetectoresCheck.class);
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(path)")
    public CompletionStage<Result> findOne(String id) {
//        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        StringBuffer controladorJson = new StringBuffer();
        controladorJson.append("{\"id\":\"cbcd342b-39e0-4bfc-9dcd-97ba25b69375\",\"versoesTabelasHorarias\":[{\"id\":\"6b8e7a8c-a6d7-4599-90a7-aa8dfba31138\",\"idJson\":\"a89cda54-5150-42d5-a8c0-aa6ac41a6e51\",\"statusVersao\":\"EDITANDO\",\"tabelaHoraria\":{\"idJson\":\"a64ad9fe-e10c-4149-94cd-a2752dd4173c\"},\"dataCriacao\":\"19/12/2016 11:03:22\",\"dataAtualizacao\":\"19/12/2016 11:08:29\",\"usuario\":{\"nome\":\"Administrador Geral\"}}],\"controladorFisicoId\":\"1a754281-cd96-49f4-a069-d39e7a04e064\",\"croqui\":{\"id\":\"bb01172e-2b3f-4305-b7e9-729b785e1127\",\"idJson\":\"0d8ce251-5722-48eb-b8eb-e53d2d9835e8\"},\"sequencia\":1,\"limiteEstagio\":16,\"limiteGrupoSemaforico\":16,\"limiteAnel\":4,\"limiteDetectorPedestre\":4,\"limiteDetectorVeicular\":8,\"limiteTabelasEntreVerdes\":2,\"limitePlanos\":16,\"nomeEndereco\":\"R. Clélia com R. Jeroaquara\",\"dataCriacao\":\"19/12/2016 09:26:44\",\"dataAtualizacao\":\"19/12/2016 11:08:29\",\"CLC\":\"2.234.0001\",\"bloqueado\":false,\"planosBloqueado\":false,\"exclusivoParaTeste\":false,\"verdeMin\":\"1\",\"verdeMax\":\"255\",\"verdeMinimoMin\":\"10\",\"verdeMinimoMax\":\"255\",\"verdeMaximoMin\":\"10\",\"verdeMaximoMax\":\"255\",\"extensaoVerdeMin\":\"1.0\",\"extensaoVerdeMax\":\"10.0\",\"verdeIntermediarioMin\":\"10\",\"verdeIntermediarioMax\":\"255\",\"defasagemMin\":\"0\",\"defasagemMax\":\"255\",\"amareloMin\":\"3\",\"amareloMax\":\"5\",\"vermelhoIntermitenteMin\":\"3\",\"vermelhoIntermitenteMax\":\"32\",\"vermelhoLimpezaVeicularMin\":\"0\",\"vermelhoLimpezaVeicularMax\":\"20\",\"vermelhoLimpezaPedestreMin\":\"0\",\"vermelhoLimpezaPedestreMax\":\"5\",\"atrasoGrupoMin\":\"0\",\"atrasoGrupoMax\":\"20\",\"verdeSegurancaVeicularMin\":\"10\",\"verdeSegurancaVeicularMax\":\"30\",\"verdeSegurancaPedestreMin\":\"4\",\"verdeSegurancaPedestreMax\":\"10\",\"maximoPermanenciaEstagioMin\":\"20\",\"maximoPermanenciaEstagioMax\":\"255\",\"defaultMaximoPermanenciaEstagioVeicular\":127,\"cicloMin\":\"30\",\"cicloMax\":\"255\",\"ausenciaDeteccaoMin\":\"0\",\"ausenciaDeteccaoMax\":\"5800\",\"deteccaoPermanenteMin\":\"0\",\"deteccaoPermanenteMax\":\"10\",\"statusControlador\":\"EM_CONFIGURACAO\",\"statusControladorReal\":\"EM_CONFIGURACAO\",\"area\":{\"idJson\":\"91fa698e-b2cd-40de-a3f6-427c57d90ceb\"},\"subarea\":{\"id\":\"9311e5ed-87f4-4a84-bed8-109537232062\",\"idJson\":\"fea13347-e3a7-4808-9642-c639cdf3c002\",\"nome\":\"CLÉLIA\",\"numero\":234,\"area\":{\"idJson\":\"91fa698e-b2cd-40de-a3f6-427c57d90ceb\"}},\"endereco\":{\"idJson\":\"f1aea396-5396-49e6-9e89-ff3ae2fb0757\"},\"modelo\":{\"id\":\"ae19bd85-c4a6-11e6-970d-0401fa9c1b01\",\"idJson\":\"ae19bd9d-c4a6-11e6-970d-0401fa9c1b01\",\"descricao\":\"Modelo Básico\",\"fabricante\":{\"id\":\"ae19ac62-c4a6-11e6-970d-0401fa9c1b01\",\"nome\":\"Raro Labs\"}},\"aneis\":[{\"id\":\"197d27cc-fc95-4ba9-962f-7a3e06b18714\",\"idJson\":\"bea52273-962f-475b-b6d6-aa8d77956c66\",\"numeroSMEE\":\"-\",\"ativo\":true,\"aceitaModoManual\":true,\"posicao\":1,\"CLA\":\"2.234.0001.1\",\"croqui\":{\"id\":\"fd257189-49ce-4406-ad0b-e1340ce396ef\",\"idJson\":\"5642eebc-b17b-4779-89da-e3f091bf6d5f\"},\"versaoPlano\":{\"idJson\":\"b35938a8-4718-402d-ae6a-d16a2bd0810c\"},\"estagios\":[{\"idJson\":\"0c6e6f5d-d2c6-4b3a-b66f-f351440b8557\"},{\"idJson\":\"1567c16a-4a9c-48e4-871c-b01b8827e8d6\"}],\"gruposSemaforicos\":[{\"idJson\":\"d3590521-8d5a-40e2-a474-2996996477c9\"},{\"idJson\":\"b50789a7-f4a2-4d5d-9aff-db85f99ecdc8\"}],\"detectores\":[],\"planos\":[{\"idJson\":\"bfaf928e-ddc3-4763-b472-0c2fae64aa90\"},{\"idJson\":\"2a5732a4-73e4-409a-9584-e3655ec49004\"},{\"idJson\":\"47af6b99-11bf-44af-aa08-e7012516ff1d\"}],\"endereco\":{\"idJson\":\"659ee719-7d29-48ad-b0f0-afd5fe530ee1\"}},{\"id\":\"21411ba9-a855-409d-8291-608c156f8a52\",\"idJson\":\"67d5e519-9c8d-41f1-8b07-1f3580f4eb0a\",\"ativo\":false,\"aceitaModoManual\":true,\"posicao\":4,\"CLA\":\"2.234.0001.4\",\"estagios\":[],\"gruposSemaforicos\":[],\"detectores\":[],\"planos\":[]},{\"id\":\"8fdab1fa-d8b5-492b-be00-371fea8f80db\",\"idJson\":\"790305f6-990a-4e4b-8a5c-7f86b2eae7c1\",\"ativo\":true,\"aceitaModoManual\":true,\"posicao\":2,\"CLA\":\"2.234.0001.2\",\"croqui\":{\"id\":\"07a0f631-91cb-453a-b349-1ed5a2959ffb\",\"idJson\":\"426f9c03-9693-4e81-92c3-dd80fbade070\"},\"versaoPlano\":{\"idJson\":\"5bbe1489-e038-471c-912f-9c73f24e0a87\"},\"estagios\":[{\"idJson\":\"8fdb99c1-ba36-4241-b6c9-ef0855c35592\"},{\"idJson\":\"1f864610-8025-46ef-b97d-a6ec71a2282b\"},{\"idJson\":\"03023953-6508-433b-b39d-a37bd6bd3d1d\"}],\"gruposSemaforicos\":[{\"idJson\":\"aaf1ee79-67d4-4351-863c-c765629559b0\"},{\"idJson\":\"fd4c8975-904f-4a97-b2c2-1296ad26af44\"},{\"idJson\":\"6db3142b-20fa-48d4-bd4a-03a0a4da05e8\"},{\"idJson\":\"833a8267-01fb-4a84-8d8f-2df2fcfe8ab1\"},{\"idJson\":\"e5328ee4-f87f-4158-9506-8448544cef11\"}],\"detectores\":[{\"idJson\":\"5e17da12-4a5d-4659-b51c-8c2b4638e9d4\"}],\"planos\":[{\"idJson\":\"84778c7f-0f2a-47c4-82d3-5150f4a0fa61\"},{\"idJson\":\"817e7874-960d-4c05-838e-82fc9780d967\"},{\"idJson\":\"d1c60e27-a081-4796-9635-d95fe819071d\"}],\"endereco\":{\"idJson\":\"381d6c1d-7f55-4d71-9640-71c354c84fe7\"}},{\"id\":\"99d42029-5bff-477e-a025-a6c47743fb82\",\"idJson\":\"399db683-ae37-469e-b2bd-c14ac13d3427\",\"ativo\":false,\"aceitaModoManual\":true,\"posicao\":3,\"CLA\":\"2.234.0001.3\",\"estagios\":[],\"gruposSemaforicos\":[],\"detectores\":[],\"planos\":[]}],\"estagios\":[{\"id\":\"48a9b202-abcc-4cc1-9e83-f69e19864c74\",\"idJson\":\"8fdb99c1-ba36-4241-b6c9-ef0855c35592\",\"tempoMaximoPermanencia\":100,\"tempoMaximoPermanenciaAtivado\":true,\"demandaPrioritaria\":false,\"tempoVerdeDemandaPrioritaria\":1,\"posicao\":1,\"anel\":{\"idJson\":\"790305f6-990a-4e4b-8a5c-7f86b2eae7c1\"},\"imagem\":{\"idJson\":\"282fe5c8-2a98-4dfc-b7e7-22f1c8225e6f\"},\"origemDeTransicoesProibidas\":[],\"destinoDeTransicoesProibidas\":[],\"alternativaDeTransicoesProibidas\":[],\"estagiosGruposSemaforicos\":[{\"idJson\":\"edbefe32-68e3-437d-9593-4364c293e575\"},{\"idJson\":\"7823ea9a-2d29-4cbb-abd0-87565ed0d286\"}],\"estagiosPlanos\":[{\"idJson\":\"0b96e017-7a60-4a25-9a9e-23f0f76159c4\"},{\"idJson\":\"ed4ceb4b-bc17-4624-97fa-43ae85e72392\"},{\"idJson\":\"17a6ac18-ee01-4be9-bbab-d2627b313c9f\"}]},{\"id\":\"05fda0bb-4328-40fd-a8b9-0411072b7ec1\",\"idJson\":\"1567c16a-4a9c-48e4-871c-b01b8827e8d6\",\"tempoMaximoPermanencia\":100,\"tempoMaximoPermanenciaAtivado\":true,\"demandaPrioritaria\":false,\"tempoVerdeDemandaPrioritaria\":1,\"posicao\":2,\"anel\":{\"idJson\":\"bea52273-962f-475b-b6d6-aa8d77956c66\"},\"imagem\":{\"idJson\":\"277dcb7c-9c53-440c-ada0-68b1b01252c7\"},\"origemDeTransicoesProibidas\":[],\"destinoDeTransicoesProibidas\":[],\"alternativaDeTransicoesProibidas\":[],\"estagiosGruposSemaforicos\":[{\"idJson\":\"adbea512-7ac6-4b5d-9560-90d238740c61\"}],\"estagiosPlanos\":[{\"idJson\":\"1da47954-b202-44a7-bb6d-c6f859b9571d\"},{\"idJson\":\"2100ec65-72b0-4aa0-a089-7480a19c74a6\"},{\"idJson\":\"f57e3837-c134-4e19-9c83-0f4183dd66c4\"}]},{\"id\":\"02033b38-a02f-4882-9e48-5d9f1a7b5f25\",\"idJson\":\"0c6e6f5d-d2c6-4b3a-b66f-f351440b8557\",\"tempoMaximoPermanencia\":100,\"tempoMaximoPermanenciaAtivado\":true,\"demandaPrioritaria\":false,\"tempoVerdeDemandaPrioritaria\":1,\"posicao\":1,\"anel\":{\"idJson\":\"bea52273-962f-475b-b6d6-aa8d77956c66\"},\"imagem\":{\"idJson\":\"108685de-8892-46c3-90fb-88ae5d985007\"},\"origemDeTransicoesProibidas\":[],\"destinoDeTransicoesProibidas\":[],\"alternativaDeTransicoesProibidas\":[],\"estagiosGruposSemaforicos\":[{\"idJson\":\"816c0437-8e4f-4c16-b5c8-ba0e4924dd86\"}],\"estagiosPlanos\":[{\"idJson\":\"72cb8e66-f692-4d16-8c29-4100f179af9f\"},{\"idJson\":\"92668628-1d4a-424f-af8a-f54aa6748a7f\"},{\"idJson\":\"f9b13dcd-3976-4a4e-b8b1-f29cebd7d2c5\"},{\"idJson\":\"a8e0f5e3-a25f-4dd0-b2e2-823bcb0da65a\"}]},{\"id\":\"ebcd8743-f53c-4a9a-9a3e-b524e861dc0b\",\"idJson\":\"03023953-6508-433b-b39d-a37bd6bd3d1d\",\"tempoMaximoPermanencia\":100,\"tempoMaximoPermanenciaAtivado\":true,\"demandaPrioritaria\":false,\"tempoVerdeDemandaPrioritaria\":1,\"posicao\":3,\"anel\":{\"idJson\":\"790305f6-990a-4e4b-8a5c-7f86b2eae7c1\"},\"imagem\":{\"idJson\":\"91ea45a9-2286-4455-b01c-01da2e62c849\"},\"origemDeTransicoesProibidas\":[],\"destinoDeTransicoesProibidas\":[],\"alternativaDeTransicoesProibidas\":[],\"estagiosGruposSemaforicos\":[{\"idJson\":\"699ad2ed-8b66-418d-896a-814701c4967a\"},{\"idJson\":\"decd8f12-4567-4cd5-89e3-d77f734d6ca9\"}],\"estagiosPlanos\":[{\"idJson\":\"f5f4aedc-20e1-42a2-a600-b4c5547c0891\"},{\"idJson\":\"f989403b-d2f1-4410-b88e-9b755fd83571\"},{\"idJson\":\"6e2b8c3b-fe93-40fc-8548-ad39541d3862\"}]},{\"id\":\"4a781caa-f9a9-46f5-a348-6bba99bc33bc\",\"idJson\":\"1f864610-8025-46ef-b97d-a6ec71a2282b\",\"tempoMaximoPermanencia\":20,\"tempoMaximoPermanenciaAtivado\":true,\"demandaPrioritaria\":false,\"tempoVerdeDemandaPrioritaria\":1,\"posicao\":2,\"anel\":{\"idJson\":\"790305f6-990a-4e4b-8a5c-7f86b2eae7c1\"},\"imagem\":{\"idJson\":\"a06d1ef7-c7c2-4a13-8961-c4969eb7c5a7\"},\"detector\":{\"idJson\":\"5e17da12-4a5d-4659-b51c-8c2b4638e9d4\"},\"origemDeTransicoesProibidas\":[],\"destinoDeTransicoesProibidas\":[],\"alternativaDeTransicoesProibidas\":[],\"estagiosGruposSemaforicos\":[{\"idJson\":\"0fe0222e-efbd-4676-8c62-2712a5fafd68\"},{\"idJson\":\"e7829308-c935-4b86-ad54-650301b24903\"},{\"idJson\":\"f5838f19-0f26-470a-b2a7-93956d3dd8b1\"}],\"estagiosPlanos\":[{\"idJson\":\"302a80b6-be4e-4c49-a888-4a6f7ad85ef7\"},{\"idJson\":\"c5f9149a-3725-4ee6-8f00-872dbdc5fd2d\"},{\"idJson\":\"0ffbcffa-851d-4159-a50c-72acf5a9bcea\"},{\"idJson\":\"1e936308-3215-43ad-a048-79158e08a106\"}]}],\"gruposSemaforicos\":[{\"id\":\"979bf370-b4fd-4e42-abef-a8e71874abf8\",\"idJson\":\"833a8267-01fb-4a84-8d8f-2df2fcfe8ab1\",\"tipo\":\"PEDESTRE\",\"posicao\":7,\"descricao\":\"PEDESTRE CARONA JEROAQUARA\",\"faseVermelhaApagadaAmareloIntermitente\":false,\"tempoVerdeSeguranca\":5,\"anel\":{\"idJson\":\"790305f6-990a-4e4b-8a5c-7f86b2eae7c1\"},\"verdesConflitantesOrigem\":[],\"verdesConflitantesDestino\":[{\"idJson\":\"c72e90d9-9bb1-4aa2-9127-d87aea94be78\"}],\"estagiosGruposSemaforicos\":[{\"idJson\":\"e7829308-c935-4b86-ad54-650301b24903\"},{\"idJson\":\"decd8f12-4567-4cd5-89e3-d77f734d6ca9\"}],\"transicoes\":[{\"idJson\":\"30a63250-957f-448e-b0e5-2d45e675e1ba\"},{\"idJson\":\"ee95456f-b60c-4a15-9349-757730997436\"}],\"transicoesComGanhoDePassagem\":[{\"idJson\":\"a1760397-8a53-4e0f-bd61-20e191afdcbc\"},{\"idJson\":\"ab1fc84b-9eba-48c6-9dee-50672f305987\"}],\"tabelasEntreVerdes\":[{\"idJson\":\"2eab061e-a258-4d3d-a4e3-508fc081660b\"}]},{\"id\":\"27e5070d-0b3c-4234-a9dd-b7be716b7954\",\"idJson\":\"aaf1ee79-67d4-4351-863c-c765629559b0\",\"tipo\":\"VEICULAR\",\"posicao\":3,\"descricao\":\"RUA JEROAQUARA\",\"faseVermelhaApagadaAmareloIntermitente\":true,\"tempoVerdeSeguranca\":10,\"anel\":{\"idJson\":\"790305f6-990a-4e4b-8a5c-7f86b2eae7c1\"},\"verdesConflitantesOrigem\":[{\"idJson\":\"c72e90d9-9bb1-4aa2-9127-d87aea94be78\"},{\"idJson\":\"46c0d51e-b8c0-4594-9bf3-f9e32e74174d\"},{\"idJson\":\"b85b6423-1736-4d8c-90de-5462a165c47d\"}],\"verdesConflitantesDestino\":[],\"estagiosGruposSemaforicos\":[{\"idJson\":\"edbefe32-68e3-437d-9593-4364c293e575\"}],\"transicoes\":[{\"idJson\":\"42d8e1b1-2b7f-4671-92f8-e830434dd78f\"},{\"idJson\":\"cbd1227f-7521-4952-aa43-b8732d07b5b0\"}],\"transicoesComGanhoDePassagem\":[{\"idJson\":\"1e3b5644-2f20-4162-b7ff-a899693185bb\"},{\"idJson\":\"0f529d05-83e6-425a-ab3e-643e786ffeb2\"}],\"tabelasEntreVerdes\":[{\"idJson\":\"b757761f-7807-4931-b71d-59209ee9dea2\"}]},{\"id\":\"7ca3c59f-f26e-491a-8c30-0e4571e20a9b\",\"idJson\":\"b50789a7-f4a2-4d5d-9aff-db85f99ecdc8\",\"tipo\":\"VEICULAR\",\"posicao\":1,\"descricao\":\"RUA CLÉLIA\",\"faseVermelhaApagadaAmareloIntermitente\":true,\"tempoVerdeSeguranca\":12,\"anel\":{\"idJson\":\"bea52273-962f-475b-b6d6-aa8d77956c66\"},\"verdesConflitantesOrigem\":[{\"idJson\":\"c3aba617-c49c-46f8-acbb-3b7e3027403e\"}],\"verdesConflitantesDestino\":[],\"estagiosGruposSemaforicos\":[{\"idJson\":\"816c0437-8e4f-4c16-b5c8-ba0e4924dd86\"}],\"transicoes\":[{\"idJson\":\"09cd3793-7901-4300-9d0c-8748f8495af3\"}],\"transicoesComGanhoDePassagem\":[{\"idJson\":\"88badab7-681e-4038-a23e-e07365f55217\"}],\"tabelasEntreVerdes\":[{\"idJson\":\"17cf1669-53bf-43c7-bfa9-74a021034dc7\"}]},{\"id\":\"e55c18e7-5e35-4a80-91d1-863b7ba6a18f\",\"idJson\":\"e5328ee4-f87f-4158-9506-8448544cef11\",\"tipo\":\"PEDESTRE\",\"posicao\":6,\"descricao\":\"PEDESTRE CARONA FAUSTOLO\",\"faseVermelhaApagadaAmareloIntermitente\":false,\"tempoVerdeSeguranca\":5,\"anel\":{\"idJson\":\"790305f6-990a-4e4b-8a5c-7f86b2eae7c1\"},\"verdesConflitantesOrigem\":[],\"verdesConflitantesDestino\":[{\"idJson\":\"765dfbe0-1e60-4578-91f5-168f2b8a3e6b\"}],\"estagiosGruposSemaforicos\":[{\"idJson\":\"0fe0222e-efbd-4676-8c62-2712a5fafd68\"},{\"idJson\":\"7823ea9a-2d29-4cbb-abd0-87565ed0d286\"}],\"transicoes\":[{\"idJson\":\"cbe1700a-ea67-4f67-b0ca-d80403d516ed\"},{\"idJson\":\"da19572d-9145-4352-b51a-a59a71144f41\"}],\"transicoesComGanhoDePassagem\":[{\"idJson\":\"a22ebbd8-837a-4143-9d13-db59b632ec68\"},{\"idJson\":\"f50cbe5a-c4f8-4407-883d-90763e7561da\"}],\"tabelasEntreVerdes\":[{\"idJson\":\"f70092cb-9c80-43d9-969f-d160d0bc89b2\"}]},{\"id\":\"129d5b7f-1601-4c1d-ba2c-c1e4ebb9e704\",\"idJson\":\"d3590521-8d5a-40e2-a474-2996996477c9\",\"tipo\":\"VEICULAR\",\"posicao\":2,\"descricao\":\"RUA JEROAQUARA\",\"faseVermelhaApagadaAmareloIntermitente\":true,\"tempoVerdeSeguranca\":10,\"anel\":{\"idJson\":\"bea52273-962f-475b-b6d6-aa8d77956c66\"},\"verdesConflitantesOrigem\":[],\"verdesConflitantesDestino\":[{\"idJson\":\"c3aba617-c49c-46f8-acbb-3b7e3027403e\"}],\"estagiosGruposSemaforicos\":[{\"idJson\":\"adbea512-7ac6-4b5d-9560-90d238740c61\"}],\"transicoes\":[{\"idJson\":\"72adb741-26ee-4aed-8f68-6ed20b9a7e1a\"}],\"transicoesComGanhoDePassagem\":[{\"idJson\":\"f0973986-8fe3-4485-945e-6e7ee6bc0b63\"}],\"tabelasEntreVerdes\":[{\"idJson\":\"c9e62fab-41fd-47cd-8c57-730ce8060c27\"}]},{\"id\":\"44fcb9ae-369b-4b33-8f02-001f843940b4\",\"idJson\":\"fd4c8975-904f-4a97-b2c2-1296ad26af44\",\"tipo\":\"VEICULAR\",\"posicao\":4,\"descricao\":\"RUA FAUSTOLO\",\"faseVermelhaApagadaAmareloIntermitente\":true,\"tempoVerdeSeguranca\":10,\"anel\":{\"idJson\":\"790305f6-990a-4e4b-8a5c-7f86b2eae7c1\"},\"verdesConflitantesOrigem\":[{\"idJson\":\"007f001c-3ae1-44db-a1d1-7e0027315532\"},{\"idJson\":\"765dfbe0-1e60-4578-91f5-168f2b8a3e6b\"}],\"verdesConflitantesDestino\":[{\"idJson\":\"46c0d51e-b8c0-4594-9bf3-f9e32e74174d\"}],\"estagiosGruposSemaforicos\":[{\"idJson\":\"699ad2ed-8b66-418d-896a-814701c4967a\"}],\"transicoes\":[{\"idJson\":\"a619ba67-c606-4b5e-86ff-62d0c9acfd3e\"},{\"idJson\":\"56cc08a4-80d4-4747-b51d-cccc9e8140b6\"}],\"transicoesComGanhoDePassagem\":[{\"idJson\":\"2b8a034c-b9e0-4dee-b0e4-6e6e9335fef5\"},{\"idJson\":\"1f216cf7-3337-4d0b-a302-d88e2a249c1a\"}],\"tabelasEntreVerdes\":[{\"idJson\":\"946d090f-aa0c-4d9a-a311-0c676553be4d\"}]},{\"id\":\"948e4b62-d1b9-40c4-a7fa-5af7cbff7437\",\"idJson\":\"6db3142b-20fa-48d4-bd4a-03a0a4da05e8\",\"tipo\":\"PEDESTRE\",\"posicao\":5,\"descricao\":\"PEDESTRE DEMANDA\",\"faseVermelhaApagadaAmareloIntermitente\":false,\"tempoVerdeSeguranca\":5,\"anel\":{\"idJson\":\"790305f6-990a-4e4b-8a5c-7f86b2eae7c1\"},\"verdesConflitantesOrigem\":[],\"verdesConflitantesDestino\":[{\"idJson\":\"007f001c-3ae1-44db-a1d1-7e0027315532\"},{\"idJson\":\"b85b6423-1736-4d8c-90de-5462a165c47d\"}],\"estagiosGruposSemaforicos\":[{\"idJson\":\"f5838f19-0f26-470a-b2a7-93956d3dd8b1\"}],\"transicoes\":[{\"idJson\":\"a8a7ce90-aa95-4eaa-bc97-b88a0d87fb58\"},{\"idJson\":\"54704ef6-fdbe-4c20-9e2d-5601341c08b0\"}],\"transicoesComGanhoDePassagem\":[{\"idJson\":\"ab3c5a20-ffd8-4c58-b5db-c16c311cd610\"},{\"idJson\":\"00675b60-c668-4aa0-9374-d270e2876920\"}],\"tabelasEntreVerdes\":[{\"idJson\":\"fcd06571-11d9-4571-bcd7-233ec4eaf3d7\"}]}],\"detectores\":[{\"id\":\"6edffc55-a3fc-4484-b851-e93d4d05a8ba\",\"idJson\":\"5e17da12-4a5d-4659-b51c-8c2b4638e9d4\",\"tipo\":\"PEDESTRE\",\"posicao\":1,\"monitorado\":true,\"tempoAusenciaDeteccao\":5000,\"tempoDeteccaoPermanente\":5,\"anel\":{\"idJson\":\"790305f6-990a-4e4b-8a5c-7f86b2eae7c1\"},\"estagio\":{\"idJson\":\"1f864610-8025-46ef-b97d-a6ec71a2282b\"}}],\"transicoesProibidas\":[],\"estagiosGruposSemaforicos\":[{\"id\":\"2030112b-0011-4518-ae56-3575eb9d232d\",\"idJson\":\"699ad2ed-8b66-418d-896a-814701c4967a\",\"estagio\":{\"idJson\":\"03023953-6508-433b-b39d-a37bd6bd3d1d\"},\"grupoSemaforico\":{\"idJson\":\"fd4c8975-904f-4a97-b2c2-1296ad26af44\"}},{\"id\":\"410f35d9-285c-4106-aee7-e6330eee3508\",\"idJson\":\"e7829308-c935-4b86-ad54-650301b24903\",\"estagio\":{\"idJson\":\"1f864610-8025-46ef-b97d-a6ec71a2282b\"},\"grupoSemaforico\":{\"idJson\":\"833a8267-01fb-4a84-8d8f-2df2fcfe8ab1\"}},{\"id\":\"83e64ea3-b0b9-4219-9625-f695a56a340e\",\"idJson\":\"decd8f12-4567-4cd5-89e3-d77f734d6ca9\",\"estagio\":{\"idJson\":\"03023953-6508-433b-b39d-a37bd6bd3d1d\"},\"grupoSemaforico\":{\"idJson\":\"833a8267-01fb-4a84-8d8f-2df2fcfe8ab1\"}},{\"id\":\"5fb12334-0fa4-45bb-9934-3e642d7b04f2\",\"idJson\":\"adbea512-7ac6-4b5d-9560-90d238740c61\",\"estagio\":{\"idJson\":\"1567c16a-4a9c-48e4-871c-b01b8827e8d6\"},\"grupoSemaforico\":{\"idJson\":\"d3590521-8d5a-40e2-a474-2996996477c9\"}},{\"id\":\"1a0768c6-628c-4f57-a77b-8882b2686289\",\"idJson\":\"0fe0222e-efbd-4676-8c62-2712a5fafd68\",\"estagio\":{\"idJson\":\"1f864610-8025-46ef-b97d-a6ec71a2282b\"},\"grupoSemaforico\":{\"idJson\":\"e5328ee4-f87f-4158-9506-8448544cef11\"}},{\"id\":\"a42ffcd5-ef36-4896-8cd3-1b7658dad602\",\"idJson\":\"7823ea9a-2d29-4cbb-abd0-87565ed0d286\",\"estagio\":{\"idJson\":\"8fdb99c1-ba36-4241-b6c9-ef0855c35592\"},\"grupoSemaforico\":{\"idJson\":\"e5328ee4-f87f-4158-9506-8448544cef11\"}},{\"id\":\"7d452b25-ab20-4961-88ee-af19b24cca8d\",\"idJson\":\"816c0437-8e4f-4c16-b5c8-ba0e4924dd86\",\"estagio\":{\"idJson\":\"0c6e6f5d-d2c6-4b3a-b66f-f351440b8557\"},\"grupoSemaforico\":{\"idJson\":\"b50789a7-f4a2-4d5d-9aff-db85f99ecdc8\"}},{\"id\":\"f5126929-34cc-455b-a1d3-c92aa3689bbb\",\"idJson\":\"f5838f19-0f26-470a-b2a7-93956d3dd8b1\",\"estagio\":{\"idJson\":\"1f864610-8025-46ef-b97d-a6ec71a2282b\"},\"grupoSemaforico\":{\"idJson\":\"6db3142b-20fa-48d4-bd4a-03a0a4da05e8\"}},{\"id\":\"40a0fa9f-923a-43b9-bcc0-13f9e510c175\",\"idJson\":\"edbefe32-68e3-437d-9593-4364c293e575\",\"estagio\":{\"idJson\":\"8fdb99c1-ba36-4241-b6c9-ef0855c35592\"},\"grupoSemaforico\":{\"idJson\":\"aaf1ee79-67d4-4351-863c-c765629559b0\"}}],\"verdesConflitantes\":[{\"id\":\"866abbb7-cce0-43d1-8e73-b2ac804a24a5\",\"idJson\":\"007f001c-3ae1-44db-a1d1-7e0027315532\",\"origem\":{\"idJson\":\"fd4c8975-904f-4a97-b2c2-1296ad26af44\"},\"destino\":{\"idJson\":\"6db3142b-20fa-48d4-bd4a-03a0a4da05e8\"}},{\"id\":\"182c52c7-6104-4c62-b18e-198b9cd841fc\",\"idJson\":\"c3aba617-c49c-46f8-acbb-3b7e3027403e\",\"origem\":{\"idJson\":\"b50789a7-f4a2-4d5d-9aff-db85f99ecdc8\"},\"destino\":{\"idJson\":\"d3590521-8d5a-40e2-a474-2996996477c9\"}},{\"id\":\"b4868709-f747-4ecd-a158-1e2154d3cf23\",\"idJson\":\"765dfbe0-1e60-4578-91f5-168f2b8a3e6b\",\"origem\":{\"idJson\":\"fd4c8975-904f-4a97-b2c2-1296ad26af44\"},\"destino\":{\"idJson\":\"e5328ee4-f87f-4158-9506-8448544cef11\"}},{\"id\":\"c59aa54a-8249-4ac8-9a11-64ea51ed2eed\",\"idJson\":\"b85b6423-1736-4d8c-90de-5462a165c47d\",\"origem\":{\"idJson\":\"aaf1ee79-67d4-4351-863c-c765629559b0\"},\"destino\":{\"idJson\":\"6db3142b-20fa-48d4-bd4a-03a0a4da05e8\"}},{\"id\":\"97b6c18b-0eaf-4d8a-bf47-9d9e5895d47e\",\"idJson\":\"c72e90d9-9bb1-4aa2-9127-d87aea94be78\",\"origem\":{\"idJson\":\"aaf1ee79-67d4-4351-863c-c765629559b0\"},\"destino\":{\"idJson\":\"833a8267-01fb-4a84-8d8f-2df2fcfe8ab1\"}},{\"id\":\"9f91c42f-ff8d-4c83-8c27-2922f9e40ea8\",\"idJson\":\"46c0d51e-b8c0-4594-9bf3-f9e32e74174d\",\"origem\":{\"idJson\":\"aaf1ee79-67d4-4351-863c-c765629559b0\"},\"destino\":{\"idJson\":\"fd4c8975-904f-4a97-b2c2-1296ad26af44\"}}],\"transicoes\":[{\"id\":\"c69c9b1a-7b83-4b31-870c-e6ecf88e5bf2\",\"idJson\":\"54704ef6-fdbe-4c20-9e2d-5601341c08b0\",\"origem\":{\"idJson\":\"1f864610-8025-46ef-b97d-a6ec71a2282b\"},\"destino\":{\"idJson\":\"8fdb99c1-ba36-4241-b6c9-ef0855c35592\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"c3015a36-f502-47fc-b8ad-1e535367a23d\"}],\"grupoSemaforico\":{\"idJson\":\"6db3142b-20fa-48d4-bd4a-03a0a4da05e8\"},\"tipo\":\"PERDA_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"fb1b2e81-499e-4910-ad94-49a6855bcb78\"},\"modoIntermitenteOuApagado\":false},{\"id\":\"4c87a759-b4f2-487d-9d9d-fa709400499d\",\"idJson\":\"a619ba67-c606-4b5e-86ff-62d0c9acfd3e\",\"origem\":{\"idJson\":\"03023953-6508-433b-b39d-a37bd6bd3d1d\"},\"destino\":{\"idJson\":\"8fdb99c1-ba36-4241-b6c9-ef0855c35592\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"0b550e31-dab3-4f94-85a7-7fef5e938413\"}],\"grupoSemaforico\":{\"idJson\":\"fd4c8975-904f-4a97-b2c2-1296ad26af44\"},\"tipo\":\"PERDA_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"8\",\"atrasoDeGrupo\":{\"idJson\":\"efb89cf9-a9bd-40a6-833f-f25a48eff8ee\"},\"modoIntermitenteOuApagado\":true},{\"id\":\"2aa2eb2a-1b17-4570-a17b-b1f1e19ecdad\",\"idJson\":\"a8a7ce90-aa95-4eaa-bc97-b88a0d87fb58\",\"origem\":{\"idJson\":\"1f864610-8025-46ef-b97d-a6ec71a2282b\"},\"destino\":{\"idJson\":\"03023953-6508-433b-b39d-a37bd6bd3d1d\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"a99be5ae-8124-4cda-bcfa-933a3e335585\"}],\"grupoSemaforico\":{\"idJson\":\"6db3142b-20fa-48d4-bd4a-03a0a4da05e8\"},\"tipo\":\"PERDA_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"d0771959-3532-490c-af15-befdcc92743c\"},\"modoIntermitenteOuApagado\":true},");
        controladorJson.append("{\"id\":\"481bfbd8-7ac2-4b87-997a-d212c1ff43fe\",\"idJson\":\"cbe1700a-ea67-4f67-b0ca-d80403d516ed\",\"origem\":{\"idJson\":\"1f864610-8025-46ef-b97d-a6ec71a2282b\"},\"destino\":{\"idJson\":\"03023953-6508-433b-b39d-a37bd6bd3d1d\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"aa64f692-61db-447b-a3c7-66f054d8f5b2\"}],\"grupoSemaforico\":{\"idJson\":\"e5328ee4-f87f-4158-9506-8448544cef11\"},\"tipo\":\"PERDA_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"c1d28345-f695-4c64-b234-3fdfc624b826\"},\"modoIntermitenteOuApagado\":true},{\"id\":\"f3140943-53b0-478e-b134-0e0dca8b4972\",\"idJson\":\"72adb741-26ee-4aed-8f68-6ed20b9a7e1a\",\"origem\":{\"idJson\":\"1567c16a-4a9c-48e4-871c-b01b8827e8d6\"},\"destino\":{\"idJson\":\"0c6e6f5d-d2c6-4b3a-b66f-f351440b8557\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"6a9038e6-7d46-4639-b517-444e4fa58ba0\"}],\"grupoSemaforico\":{\"idJson\":\"d3590521-8d5a-40e2-a474-2996996477c9\"},\"tipo\":\"PERDA_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"f89e3360-5a64-4c7d-97e4-b047e1721307\"},\"modoIntermitenteOuApagado\":true},{\"id\":\"b68e7006-0599-4dba-99e3-1f6ec6c96500\",\"idJson\":\"cbd1227f-7521-4952-aa43-b8732d07b5b0\",\"origem\":{\"idJson\":\"8fdb99c1-ba36-4241-b6c9-ef0855c35592\"},\"destino\":{\"idJson\":\"1f864610-8025-46ef-b97d-a6ec71a2282b\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"090f4d99-68c1-48b1-8a77-bee9f4971518\"}],\"grupoSemaforico\":{\"idJson\":\"aaf1ee79-67d4-4351-863c-c765629559b0\"},\"tipo\":\"PERDA_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"d56b15b7-df8b-4e63-ac2a-6435a246f563\"},\"modoIntermitenteOuApagado\":true},{\"id\":\"569e5f25-69e4-4cca-a1ab-95a6a1a4e732\",\"idJson\":\"42d8e1b1-2b7f-4671-92f8-e830434dd78f\",\"origem\":{\"idJson\":\"8fdb99c1-ba36-4241-b6c9-ef0855c35592\"},\"destino\":{\"idJson\":\"03023953-6508-433b-b39d-a37bd6bd3d1d\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"361fc8bd-274d-4524-a093-b74809eb9f75\"}],\"grupoSemaforico\":{\"idJson\":\"aaf1ee79-67d4-4351-863c-c765629559b0\"},\"tipo\":\"PERDA_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"5b631936-3e56-4137-9eb6-32f27ec74137\"},\"modoIntermitenteOuApagado\":false},{\"id\":\"6aea25a3-a474-4a89-b12e-a3c2c9b656f9\",\"idJson\":\"56cc08a4-80d4-4747-b51d-cccc9e8140b6\",\"origem\":{\"idJson\":\"03023953-6508-433b-b39d-a37bd6bd3d1d\"},\"destino\":{\"idJson\":\"1f864610-8025-46ef-b97d-a6ec71a2282b\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"e2a4462c-6bf8-47ec-8af2-9700a6a9bbfd\"}],\"grupoSemaforico\":{\"idJson\":\"fd4c8975-904f-4a97-b2c2-1296ad26af44\"},\"tipo\":\"PERDA_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"7319bb74-a53c-4494-96e3-d3e9ff429a37\"},\"modoIntermitenteOuApagado\":false},{\"id\":\"e1840619-2dd8-403a-ab08-9b615e3fa9f2\",\"idJson\":\"ee95456f-b60c-4a15-9349-757730997436\",\"origem\":{\"idJson\":\"03023953-6508-433b-b39d-a37bd6bd3d1d\"},\"destino\":{\"idJson\":\"8fdb99c1-ba36-4241-b6c9-ef0855c35592\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"97fbe849-9823-49b6-9080-b5b3a2ab01c2\"}],\"grupoSemaforico\":{\"idJson\":\"833a8267-01fb-4a84-8d8f-2df2fcfe8ab1\"},\"tipo\":\"PERDA_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"cb8b0ebe-52e8-4ec5-a7f4-52c6f8915186\"},\"modoIntermitenteOuApagado\":true},{\"id\":\"839a7de6-d95b-459f-8c8d-0f9144a04289\",\"idJson\":\"30a63250-957f-448e-b0e5-2d45e675e1ba\",\"origem\":{\"idJson\":\"1f864610-8025-46ef-b97d-a6ec71a2282b\"},\"destino\":{\"idJson\":\"8fdb99c1-ba36-4241-b6c9-ef0855c35592\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"a48128c2-09f0-4f2e-9f73-a73780af553c\"}],\"grupoSemaforico\":{\"idJson\":\"833a8267-01fb-4a84-8d8f-2df2fcfe8ab1\"},\"tipo\":\"PERDA_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"ca9e9b1b-5a5c-4fde-a1c2-e4e297f56a8a\"},\"modoIntermitenteOuApagado\":true},{\"id\":\"7b8737c8-45a2-4fc9-8545-a1eb21e390e4\",\"idJson\":\"09cd3793-7901-4300-9d0c-8748f8495af3\",\"origem\":{\"idJson\":\"0c6e6f5d-d2c6-4b3a-b66f-f351440b8557\"},\"destino\":{\"idJson\":\"1567c16a-4a9c-48e4-871c-b01b8827e8d6\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"2c8f7533-e281-4b62-ac8d-88b98240804d\"}],\"grupoSemaforico\":{\"idJson\":\"b50789a7-f4a2-4d5d-9aff-db85f99ecdc8\"},\"tipo\":\"PERDA_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"04d38341-b9da-483e-8a89-d1eec55c1c58\"},\"modoIntermitenteOuApagado\":true},{\"id\":\"bfdd655a-c1a4-47e8-9b4f-2c0c534ca306\",\"idJson\":\"da19572d-9145-4352-b51a-a59a71144f41\",\"origem\":{\"idJson\":\"8fdb99c1-ba36-4241-b6c9-ef0855c35592\"},\"destino\":{\"idJson\":\"03023953-6508-433b-b39d-a37bd6bd3d1d\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"782cf519-c232-48a2-a826-862e74c1f940\"}],\"grupoSemaforico\":{\"idJson\":\"e5328ee4-f87f-4158-9506-8448544cef11\"},\"tipo\":\"PERDA_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"a1c5c674-d48b-4243-afd5-1c1764d8d786\"},\"modoIntermitenteOuApagado\":true}],\"transicoesComGanhoDePassagem\":[{\"id\":\"7d3997c1-a44e-471c-b06f-977a7963d37f\",\"idJson\":\"88badab7-681e-4038-a23e-e07365f55217\",\"origem\":{\"idJson\":\"1567c16a-4a9c-48e4-871c-b01b8827e8d6\"},\"destino\":{\"idJson\":\"0c6e6f5d-d2c6-4b3a-b66f-f351440b8557\"},\"tabelaEntreVerdesTransicoes\":[],\"grupoSemaforico\":{\"idJson\":\"b50789a7-f4a2-4d5d-9aff-db85f99ecdc8\"},\"tipo\":\"GANHO_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"dbf44a6e-8347-4079-943e-a2429274727e\"},\"modoIntermitenteOuApagado\":false},{\"id\":\"374630ae-56e1-4d92-baa4-7c0a52738ccb\",\"idJson\":\"1e3b5644-2f20-4162-b7ff-a899693185bb\",\"origem\":{\"idJson\":\"1f864610-8025-46ef-b97d-a6ec71a2282b\"},\"destino\":{\"idJson\":\"8fdb99c1-ba36-4241-b6c9-ef0855c35592\"},\"tabelaEntreVerdesTransicoes\":[],\"grupoSemaforico\":{\"idJson\":\"aaf1ee79-67d4-4351-863c-c765629559b0\"},\"tipo\":\"GANHO_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"85c49c36-8d3d-4268-8599-8402ddeeef43\"},\"modoIntermitenteOuApagado\":false},{\"id\":\"b53ad2c6-5b71-4892-a0c7-17e5000414c0\",\"idJson\":\"f0973986-8fe3-4485-945e-6e7ee6bc0b63\",\"origem\":{\"idJson\":\"0c6e6f5d-d2c6-4b3a-b66f-f351440b8557\"},\"destino\":{\"idJson\":\"1567c16a-4a9c-48e4-871c-b01b8827e8d6\"},\"tabelaEntreVerdesTransicoes\":[],\"grupoSemaforico\":{\"idJson\":\"d3590521-8d5a-40e2-a474-2996996477c9\"},\"tipo\":\"GANHO_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"f2b2ceb8-0c21-4902-a474-79b7148939e9\"},\"modoIntermitenteOuApagado\":false},{\"id\":\"82099c4c-5653-4556-9352-258b3fc5f8c7\",\"idJson\":\"a1760397-8a53-4e0f-bd61-20e191afdcbc\",\"origem\":{\"idJson\":\"8fdb99c1-ba36-4241-b6c9-ef0855c35592\"},\"destino\":{\"idJson\":\"03023953-6508-433b-b39d-a37bd6bd3d1d\"},\"tabelaEntreVerdesTransicoes\":[],\"grupoSemaforico\":{\"idJson\":\"833a8267-01fb-4a84-8d8f-2df2fcfe8ab1\"},\"tipo\":\"GANHO_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"e08ca3ca-9d5d-441e-821b-52db27eae194\"},\"modoIntermitenteOuApagado\":false},{\"id\":\"cafb5b12-d45f-4566-b971-ab87161ddc59\",\"idJson\":\"ab1fc84b-9eba-48c6-9dee-50672f305987\",\"origem\":{\"idJson\":\"8fdb99c1-ba36-4241-b6c9-ef0855c35592\"},\"destino\":{\"idJson\":\"1f864610-8025-46ef-b97d-a6ec71a2282b\"},\"tabelaEntreVerdesTransicoes\":[],\"grupoSemaforico\":{\"idJson\":\"833a8267-01fb-4a84-8d8f-2df2fcfe8ab1\"},\"tipo\":\"GANHO_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"8556f42c-b905-4714-8f6e-eed5a66d9355\"},\"modoIntermitenteOuApagado\":false},{\"id\":\"acfafa5c-ff9a-4bd6-ad35-5c991757104f\",\"idJson\":\"2b8a034c-b9e0-4dee-b0e4-6e6e9335fef5\",\"origem\":{\"idJson\":\"1f864610-8025-46ef-b97d-a6ec71a2282b\"},\"destino\":{\"idJson\":\"03023953-6508-433b-b39d-a37bd6bd3d1d\"},\"tabelaEntreVerdesTransicoes\":[],\"grupoSemaforico\":{\"idJson\":\"fd4c8975-904f-4a97-b2c2-1296ad26af44\"},\"tipo\":\"GANHO_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"ae3286ac-9258-4711-905c-479d819a2a23\"},\"modoIntermitenteOuApagado\":false},{\"id\":\"bf3a73e0-d56f-4e37-80ee-ce475298c134\",\"idJson\":\"0f529d05-83e6-425a-ab3e-643e786ffeb2\",\"origem\":{\"idJson\":\"03023953-6508-433b-b39d-a37bd6bd3d1d\"},\"destino\":{\"idJson\":\"8fdb99c1-ba36-4241-b6c9-ef0855c35592\"},\"tabelaEntreVerdesTransicoes\":[],\"grupoSemaforico\":{\"idJson\":\"aaf1ee79-67d4-4351-863c-c765629559b0\"},\"tipo\":\"GANHO_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"9bd7cb27-f33e-4449-b0b8-6e196e60a867\"},\"modoIntermitenteOuApagado\":false},{\"id\":\"b8296003-dc0d-4f16-a64f-ab0fbf0716cd\",\"idJson\":\"1f216cf7-3337-4d0b-a302-d88e2a249c1a\",\"origem\":{\"idJson\":\"8fdb99c1-ba36-4241-b6c9-ef0855c35592\"},\"destino\":{\"idJson\":\"03023953-6508-433b-b39d-a37bd6bd3d1d\"},\"tabelaEntreVerdesTransicoes\":[],\"grupoSemaforico\":{\"idJson\":\"fd4c8975-904f-4a97-b2c2-1296ad26af44\"},\"tipo\":\"GANHO_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"df24c32c-1fd3-48d5-ad35-84ab1b4ea705\"},\"modoIntermitenteOuApagado\":false},{\"id\":\"a68582c4-8f36-46f9-94d2-6106259c261b\",\"idJson\":\"00675b60-c668-4aa0-9374-d270e2876920\",\"origem\":{\"idJson\":\"8fdb99c1-ba36-4241-b6c9-ef0855c35592\"},\"destino\":{\"idJson\":\"1f864610-8025-46ef-b97d-a6ec71a2282b\"},\"tabelaEntreVerdesTransicoes\":[],\"grupoSemaforico\":{\"idJson\":\"6db3142b-20fa-48d4-bd4a-03a0a4da05e8\"},\"tipo\":\"GANHO_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"131e3e62-66e3-4986-9633-b0d1e3c91e69\"},\"modoIntermitenteOuApagado\":false},{\"id\":\"5bef19b6-4827-4544-b438-d1be1e58d9c8\",\"idJson\":\"ab3c5a20-ffd8-4c58-b5db-c16c311cd610\",\"origem\":{\"idJson\":\"03023953-6508-433b-b39d-a37bd6bd3d1d\"},\"destino\":{\"idJson\":\"1f864610-8025-46ef-b97d-a6ec71a2282b\"},\"tabelaEntreVerdesTransicoes\":[],\"grupoSemaforico\":{\"idJson\":\"6db3142b-20fa-48d4-bd4a-03a0a4da05e8\"},\"tipo\":\"GANHO_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"e28171ab-6655-4812-b4f9-6a4fbfb071ab\"},\"modoIntermitenteOuApagado\":false},{\"id\":\"69dfa152-ab04-42f9-a3ad-5f58b4a94143\",\"idJson\":\"a22ebbd8-837a-4143-9d13-db59b632ec68\",\"origem\":{\"idJson\":\"03023953-6508-433b-b39d-a37bd6bd3d1d\"},\"destino\":{\"idJson\":\"8fdb99c1-ba36-4241-b6c9-ef0855c35592\"},\"tabelaEntreVerdesTransicoes\":[],\"grupoSemaforico\":{\"idJson\":\"e5328ee4-f87f-4158-9506-8448544cef11\"},\"tipo\":\"GANHO_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"5b7678fd-ef77-4db8-bc83-b62e0dbe3efd\"},\"modoIntermitenteOuApagado\":false},{\"id\":\"aa205d9c-ff1b-440d-8819-7081cc8b5c72\",\"idJson\":\"f50cbe5a-c4f8-4407-883d-90763e7561da\",\"origem\":{\"idJson\":\"03023953-6508-433b-b39d-a37bd6bd3d1d\"},\"destino\":{\"idJson\":\"1f864610-8025-46ef-b97d-a6ec71a2282b\"},\"tabelaEntreVerdesTransicoes\":[],\"grupoSemaforico\":{\"idJson\":\"e5328ee4-f87f-4158-9506-8448544cef11\"},\"tipo\":\"GANHO_DE_PASSAGEM\",\"tempoAtrasoGrupo\":\"0\",\"atrasoDeGrupo\":{\"idJson\":\"c70139eb-1cc3-4372-a386-1b77cf3b2af4\"},\"modoIntermitenteOuApagado\":false}],\"tabelasEntreVerdes\":[{\"id\":\"ae863f72-1c34-491c-bbf7-7daa98244fce\",\"idJson\":\"946d090f-aa0c-4d9a-a311-0c676553be4d\",\"descricao\":\"PADRÃO\",\"posicao\":1,\"grupoSemaforico\":{\"idJson\":\"fd4c8975-904f-4a97-b2c2-1296ad26af44\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"0b550e31-dab3-4f94-85a7-7fef5e938413\"},{\"idJson\":\"e2a4462c-6bf8-47ec-8af2-9700a6a9bbfd\"}]},{\"id\":\"2712dff5-b40a-4f43-88ce-bc108cbda786\",\"idJson\":\"fcd06571-11d9-4571-bcd7-233ec4eaf3d7\",\"descricao\":\"PADRÃO\",\"posicao\":1,\"grupoSemaforico\":{\"idJson\":\"6db3142b-20fa-48d4-bd4a-03a0a4da05e8\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"c3015a36-f502-47fc-b8ad-1e535367a23d\"},{\"idJson\":\"a99be5ae-8124-4cda-bcfa-933a3e335585\"}]},{\"id\":\"5f088915-97da-4995-8bc9-40f5e6651070\",\"idJson\":\"17cf1669-53bf-43c7-bfa9-74a021034dc7\",\"descricao\":\"PADRÃO\",\"posicao\":1,\"grupoSemaforico\":{\"idJson\":\"b50789a7-f4a2-4d5d-9aff-db85f99ecdc8\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"2c8f7533-e281-4b62-ac8d-88b98240804d\"}]},{\"id\":\"66289849-a801-41a8-a72c-059174565c4b\",\"idJson\":\"f70092cb-9c80-43d9-969f-d160d0bc89b2\",\"descricao\":\"PADRÃO\",\"posicao\":1,\"grupoSemaforico\":{\"idJson\":\"e5328ee4-f87f-4158-9506-8448544cef11\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"782cf519-c232-48a2-a826-862e74c1f940\"},{\"idJson\":\"aa64f692-61db-447b-a3c7-66f054d8f5b2\"}]},{\"id\":\"4bf1236f-d0e7-4184-93ee-840d57e9a758\",\"idJson\":\"c9e62fab-41fd-47cd-8c57-730ce8060c27\",\"descricao\":\"PADRÃO\",\"posicao\":1,\"grupoSemaforico\":{\"idJson\":\"d3590521-8d5a-40e2-a474-2996996477c9\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"6a9038e6-7d46-4639-b517-444e4fa58ba0\"}]},{\"id\":\"3a210676-cd99-422c-a08f-f12fc34e356f\",\"idJson\":\"b757761f-7807-4931-b71d-59209ee9dea2\",\"descricao\":\"PADRÃO\",\"posicao\":1,\"grupoSemaforico\":{\"idJson\":\"aaf1ee79-67d4-4351-863c-c765629559b0\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"361fc8bd-274d-4524-a093-b74809eb9f75\"},{\"idJson\":\"090f4d99-68c1-48b1-8a77-bee9f4971518\"}]},{\"id\":\"b49188b3-ab7f-4697-a19e-ced50537a0b1\",\"idJson\":\"2eab061e-a258-4d3d-a4e3-508fc081660b\",\"descricao\":\"PADRÃO\",\"posicao\":1,\"grupoSemaforico\":{\"idJson\":\"833a8267-01fb-4a84-8d8f-2df2fcfe8ab1\"},\"tabelaEntreVerdesTransicoes\":[{\"idJson\":\"a48128c2-09f0-4f2e-9f73-a73780af553c\"},{\"idJson\":\"97fbe849-9823-49b6-9080-b5b3a2ab01c2\"}]}],\"tabelasEntreVerdesTransicoes\":[{\"id\":\"a00975af-09ba-4928-ae48-f1d041331dc3\",\"idJson\":\"6a9038e6-7d46-4639-b517-444e4fa58ba0\",\"tempoAmarelo\":\"3\",\"tempoVermelhoLimpeza\":\"1\",\"tempoAtrasoGrupo\":\"0\",\"tabelaEntreVerdes\":{\"idJson\":\"c9e62fab-41fd-47cd-8c57-730ce8060c27\"},\"transicao\":{\"idJson\":\"72adb741-26ee-4aed-8f68-6ed20b9a7e1a\"}},{\"id\":\"6840d297-fe8f-4266-b59f-65b86d180218\",\"idJson\":\"090f4d99-68c1-48b1-8a77-bee9f4971518\",\"tempoAmarelo\":\"3\",\"tempoVermelhoLimpeza\":\"2\",\"tempoAtrasoGrupo\":\"0\",\"tabelaEntreVerdes\":{\"idJson\":\"b757761f-7807-4931-b71d-59209ee9dea2\"},\"transicao\":{\"idJson\":\"cbd1227f-7521-4952-aa43-b8732d07b5b0\"}},{\"id\":\"e0c5278d-1831-42ef-992d-dd15b56cde15\",\"idJson\":\"97fbe849-9823-49b6-9080-b5b3a2ab01c2\",\"tempoVermelhoIntermitente\":\"10\",\"tempoVermelhoLimpeza\":\"2\",\"tempoAtrasoGrupo\":\"0\",\"tabelaEntreVerdes\":{\"idJson\":\"2eab061e-a258-4d3d-a4e3-508fc081660b\"},\"transicao\":{\"idJson\":\"ee95456f-b60c-4a15-9349-757730997436\"}},{\"id\":\"9b91a2de-84f5-4638-b02c-5732886bcf6e\",\"idJson\":\"2c8f7533-e281-4b62-ac8d-88b98240804d\",\"tempoAmarelo\":\"3\",\"tempoVermelhoLimpeza\":\"2\",\"tempoAtrasoGrupo\":\"0\",\"tabelaEntreVerdes\":{\"idJson\":\"17cf1669-53bf-43c7-bfa9-74a021034dc7\"},\"transicao\":{\"idJson\":\"09cd3793-7901-4300-9d0c-8748f8495af3\"}},{\"id\":\"5f48e709-3aa1-4cf5-aa9c-d8cbff23df64\",\"idJson\":\"361fc8bd-274d-4524-a093-b74809eb9f75\",\"tempoAmarelo\":\"3\",\"tempoVermelhoLimpeza\":\"1\",\"tempoAtrasoGrupo\":\"0\",\"tabelaEntreVerdes\":{\"idJson\":\"b757761f-7807-4931-b71d-59209ee9dea2\"},\"transicao\":{\"idJson\":\"42d8e1b1-2b7f-4671-92f8-e830434dd78f\"}},{\"id\":\"78fb3996-54ae-49bb-b88c-08df8601f2ec\",\"idJson\":\"c3015a36-f502-47fc-b8ad-1e535367a23d\",\"tempoVermelhoIntermitente\":\"10\",\"tempoVermelhoLimpeza\":\"2\",\"tempoAtrasoGrupo\":\"0\",\"tabelaEntreVerdes\":{\"idJson\":\"fcd06571-11d9-4571-bcd7-233ec4eaf3d7\"},\"transicao\":{\"idJson\":\"54704ef6-fdbe-4c20-9e2d-5601341c08b0\"}},{\"id\":\"6db311a0-fb4b-4ab8-adf3-28c44b3745a5\",\"idJson\":\"aa64f692-61db-447b-a3c7-66f054d8f5b2\",\"tempoVermelhoIntermitente\":\"10\",\"tempoVermelhoLimpeza\":\"1\",\"tempoAtrasoGrupo\":\"0\",\"tabelaEntreVerdes\":{\"idJson\":\"f70092cb-9c80-43d9-969f-d160d0bc89b2\"},\"transicao\":{\"idJson\":\"cbe1700a-ea67-4f67-b0ca-d80403d516ed\"}},{\"id\":\"1c26599e-c56f-48af-b676-deab09a1fe29\",\"idJson\":\"782cf519-c232-48a2-a826-862e74c1f940\",\"tempoVermelhoIntermitente\":\"10\",\"tempoVermelhoLimpeza\":\"1\",\"tempoAtrasoGrupo\":\"0\",\"tabelaEntreVerdes\":{\"idJson\":\"f70092cb-9c80-43d9-969f-d160d0bc89b2\"},\"transicao\":{\"idJson\":\"da19572d-9145-4352-b51a-a59a71144f41\"}},{\"id\":\"1570857d-3314-440a-89f9-201dfa6e47ba\",\"idJson\":\"e2a4462c-6bf8-47ec-8af2-9700a6a9bbfd\",\"tempoAmarelo\":\"3\",\"tempoVermelhoLimpeza\":\"2\",\"tempoAtrasoGrupo\":\"0\",\"tabelaEntreVerdes\":{\"idJson\":\"946d090f-aa0c-4d9a-a311-0c676553be4d\"},\"transicao\":{\"idJson\":\"56cc08a4-80d4-4747-b51d-cccc9e8140b6\"}},{\"id\":\"88df3140-da4c-4400-966e-12241b42aa80\",\"idJson\":\"a48128c2-09f0-4f2e-9f73-a73780af553c\",\"tempoVermelhoIntermitente\":\"10\",\"tempoVermelhoLimpeza\":\"1\",\"tempoAtrasoGrupo\":\"0\",\"tabelaEntreVerdes\":{\"idJson\":\"2eab061e-a258-4d3d-a4e3-508fc081660b\"},\"transicao\":{\"idJson\":\"30a63250-957f-448e-b0e5-2d45e675e1ba\"}},{\"id\":\"14d132f3-8666-4af2-9897-d7ff6116db2d\",\"idJson\":\"0b550e31-dab3-4f94-85a7-7fef5e938413\",\"tempoAmarelo\":\"3\",\"tempoVermelhoLimpeza\":\"1\",\"tempoAtrasoGrupo\":\"8\",\"tabelaEntreVerdes\":{\"idJson\":\"946d090f-aa0c-4d9a-a311-0c676553be4d\"},\"transicao\":{\"idJson\":\"a619ba67-c606-4b5e-86ff-62d0c9acfd3e\"}},{\"id\":\"d1a06823-0e3f-4d25-b971-ab4147c49eb2\",\"idJson\":\"a99be5ae-8124-4cda-bcfa-933a3e335585\",\"tempoVermelhoIntermitente\":\"10\",\"tempoVermelhoLimpeza\":\"1\",\"tempoAtrasoGrupo\":\"0\",\"tabelaEntreVerdes\":{\"idJson\":\"fcd06571-11d9-4571-bcd7-233ec4eaf3d7\"},\"transicao\":{\"idJson\":\"a8a7ce90-aa95-4eaa-bc97-b88a0d87fb58\"}}],\"planos\":[{\"id\":\"2eed8a9a-acff-4082-bbf8-0b5248283731\",\"idJson\":\"817e7874-960d-4c05-838e-82fc9780d967\",\"posicao\":1,\"descricao\":\"PLANO 1\",\"tempoCiclo\":100,\"defasagem\":0,\"posicaoTabelaEntreVerde\":1,\"modoOperacao\":\"TEMPO_FIXO_COORDENADO\",\"dataCriacao\":\"19/12/2016 10:21:38\",\"dataAtualizacao\":\"19/12/2016 11:08:29\",\"anel\":{\"idJson\":\"790305f6-990a-4e4b-8a5c-7f86b2eae7c1\"},\"versaoPlano\":{\"idJson\":\"5bbe1489-e038-471c-912f-9c73f24e0a87\"},\"estagiosPlanos\":[{\"idJson\":\"f5f4aedc-20e1-42a2-a600-b4c5547c0891\"},{\"idJson\":\"17a6ac18-ee01-4be9-bbab-d2627b313c9f\"},{\"idJson\":\"0ffbcffa-851d-4159-a50c-72acf5a9bcea\"}],\"gruposSemaforicosPlanos\":[{\"idJson\":\"12a6b8e1-191d-4c75-a705-d0f429742ca8\"},{\"idJson\":\"63aae50c-9a90-47e1-8618-fb550514f6bc\"},{\"idJson\":\"8d334406-238e-432f-9f1c-dee1e003f264\"},{\"idJson\":\"4befae18-5cd0-457d-8ca1-59f9aff435d0\"},{\"idJson\":\"d08220db-f581-47ec-831b-9c9cc4c8463e\"}]},{\"id\":\"0cadd139-a093-4e29-b8d9-b53ba6090248\",\"idJson\":\"bfaf928e-ddc3-4763-b472-0c2fae64aa90\",\"posicao\":0,\"descricao\":\"Exclusivo\",\"tempoCiclo\":30,\"defasagem\":0,\"posicaoTabelaEntreVerde\":1,\"modoOperacao\":\"MANUAL\",\"dataCriacao\":\"19/12/2016 10:48:51\",\"dataAtualizacao\":\"19/12/2016 11:08:29\",\"anel\":{\"idJson\":\"bea52273-962f-475b-b6d6-aa8d77956c66\"},\"versaoPlano\":{\"idJson\":\"b35938a8-4718-402d-ae6a-d16a2bd0810c\"},\"estagiosPlanos\":[{\"idJson\":\"72cb8e66-f692-4d16-8c29-4100f179af9f\"},{\"idJson\":\"2100ec65-72b0-4aa0-a089-7480a19c74a6\"},{\"idJson\":\"a8e0f5e3-a25f-4dd0-b2e2-823bcb0da65a\"}],\"gruposSemaforicosPlanos\":[{\"idJson\":\"7052b43c-3336-4ac4-874d-caff7e8965c6\"},{\"idJson\":\"d8dbbe37-c581-42a7-8946-6f6a2c92851b\"}]},{\"id\":\"7cf2f1a9-9558-4f7e-84f8-4849bb2d81ef\",\"idJson\":\"2a5732a4-73e4-409a-9584-e3655ec49004\",\"posicao\":2,\"descricao\":\"PLANO 2\",\"tempoCiclo\":70,\"defasagem\":0,\"posicaoTabelaEntreVerde\":1,\"modoOperacao\":\"TEMPO_FIXO_COORDENADO\",\"dataCriacao\":\"19/12/2016 10:56:21\",\"dataAtualizacao\":\"19/12/2016 11:08:29\",\"anel\":{\"idJson\":\"bea52273-962f-475b-b6d6-aa8d77956c66\"},\"versaoPlano\":{\"idJson\":\"b35938a8-4718-402d-ae6a-d16a2bd0810c\"},\"estagiosPlanos\":[{\"idJson\":\"f9b13dcd-3976-4a4e-b8b1-f29cebd7d2c5\"},{\"idJson\":\"f57e3837-c134-4e19-9c83-0f4183dd66c4\"}],\"gruposSemaforicosPlanos\":[{\"idJson\":\"9e02af97-5995-48d4-8e92-3541d56506ab\"},{\"idJson\":\"9b12c347-be82-4396-aac3-d88ee1bde3dc\"}]},{\"id\":\"0ebbef95-927c-4095-aa7b-1e301392c4ef\",\"idJson\":\"84778c7f-0f2a-47c4-82d3-5150f4a0fa61\",\"posicao\":2,\"descricao\":\"PLANO 2\",\"tempoCiclo\":70,\"defasagem\":0,\"posicaoTabelaEntreVerde\":1,\"modoOperacao\":\"TEMPO_FIXO_COORDENADO\",\"dataCriacao\":\"19/12/2016 10:56:21\",\"dataAtualizacao\":\"19/12/2016 11:08:29\",\"anel\":{\"idJson\":\"790305f6-990a-4e4b-8a5c-7f86b2eae7c1\"},\"versaoPlano\":{\"idJson\":\"5bbe1489-e038-471c-912f-9c73f24e0a87\"},\"estagiosPlanos\":[{\"idJson\":\"0b96e017-7a60-4a25-9a9e-23f0f76159c4\"},{\"idJson\":\"c5f9149a-3725-4ee6-8f00-872dbdc5fd2d\"},{\"idJson\":\"6e2b8c3b-fe93-40fc-8548-ad39541d3862\"},{\"idJson\":\"1e936308-3215-43ad-a048-79158e08a106\"}],\"gruposSemaforicosPlanos\":[{\"idJson\":\"e1c69e40-e01b-4c63-921b-290c7e0c75b7\"},{\"idJson\":\"f5f97f3a-31ef-4907-83f7-d0fcb823b72f\"},{\"idJson\":\"64804f08-8d80-46eb-83d9-2b75495fd18d\"},{\"idJson\":\"ba82065c-c1bd-4205-8b56-0ff130c810da\"},{\"idJson\":\"7efee22e-fae1-44e3-afbc-7fd08856c767\"}]},{\"id\":\"94ac5882-9557-4e46-9881-6e7de29c6911\",\"idJson\":\"47af6b99-11bf-44af-aa08-e7012516ff1d\",\"posicao\":1,\"descricao\":\"PLANO 1\",\"tempoCiclo\":100,\"defasagem\":0,\"posicaoTabelaEntreVerde\":1,\"modoOperacao\":\"TEMPO_FIXO_COORDENADO\",\"dataCriacao\":\"19/12/2016 10:48:51\",\"dataAtualizacao\":\"19/12/2016 11:08:29\",\"anel\":{\"idJson\":\"bea52273-962f-475b-b6d6-aa8d77956c66\"},");
        controladorJson.append("\"versaoPlano\":{\"idJson\":\"b35938a8-4718-402d-ae6a-d16a2bd0810c\"},\"estagiosPlanos\":[{\"idJson\":\"1da47954-b202-44a7-bb6d-c6f859b9571d\"},{\"idJson\":\"92668628-1d4a-424f-af8a-f54aa6748a7f\"}],\"gruposSemaforicosPlanos\":[{\"idJson\":\"33f54940-8c89-42a0-9acd-cf39b5e988a0\"},{\"idJson\":\"6eca4832-e1f6-4e21-80d1-1a97315446d3\"}]},{\"id\":\"c88c815e-6b19-45dd-aa3f-5b7eac284106\",\"idJson\":\"d1c60e27-a081-4796-9635-d95fe819071d\",\"posicao\":0,\"descricao\":\"Exclusivo\",\"tempoCiclo\":30,\"defasagem\":0,\"posicaoTabelaEntreVerde\":1,\"modoOperacao\":\"MANUAL\",\"dataCriacao\":\"19/12/2016 10:21:38\",\"dataAtualizacao\":\"19/12/2016 11:08:29\",\"anel\":{\"idJson\":\"790305f6-990a-4e4b-8a5c-7f86b2eae7c1\"},\"versaoPlano\":{\"idJson\":\"5bbe1489-e038-471c-912f-9c73f24e0a87\"},\"estagiosPlanos\":[{\"idJson\":\"f989403b-d2f1-4410-b88e-9b755fd83571\"},{\"idJson\":\"ed4ceb4b-bc17-4624-97fa-43ae85e72392\"},{\"idJson\":\"302a80b6-be4e-4c49-a888-4a6f7ad85ef7\"}],\"gruposSemaforicosPlanos\":[{\"idJson\":\"77855013-ceaa-49c7-aae8-1fec1114cd78\"},{\"idJson\":\"6e303aa2-32ad-44b6-8932-2f99ec77e2ed\"},{\"idJson\":\"fb725f36-d1be-491e-9ba2-21d6f51d58fe\"},{\"idJson\":\"6ff951be-e824-4181-a619-6be021816784\"},{\"idJson\":\"31bc8b8c-4823-4f24-b57c-d518f77d5afa\"}]}],\"gruposSemaforicosPlanos\":[{\"id\":\"4a002997-65db-4315-a9a2-0fc9741b0435\",\"idJson\":\"9b12c347-be82-4396-aac3-d88ee1bde3dc\",\"plano\":{\"idJson\":\"2a5732a4-73e4-409a-9584-e3655ec49004\"},\"grupoSemaforico\":{\"idJson\":\"b50789a7-f4a2-4d5d-9aff-db85f99ecdc8\"},\"ativado\":true},{\"id\":\"fa54fa00-f536-4e07-a0c8-f07bf5fb42a0\",\"idJson\":\"31bc8b8c-4823-4f24-b57c-d518f77d5afa\",\"plano\":{\"idJson\":\"d1c60e27-a081-4796-9635-d95fe819071d\"},\"grupoSemaforico\":{\"idJson\":\"6db3142b-20fa-48d4-bd4a-03a0a4da05e8\"},\"ativado\":true},{\"id\":\"ed8385fa-5d1b-41a4-806b-cb55576246c4\",\"idJson\":\"d08220db-f581-47ec-831b-9c9cc4c8463e\",\"plano\":{\"idJson\":\"817e7874-960d-4c05-838e-82fc9780d967\"},\"grupoSemaforico\":{\"idJson\":\"833a8267-01fb-4a84-8d8f-2df2fcfe8ab1\"},\"ativado\":true},{\"id\":\"50fa4e1b-f556-4069-b7a0-25e116eafa5e\",\"idJson\":\"33f54940-8c89-42a0-9acd-cf39b5e988a0\",\"plano\":{\"idJson\":\"47af6b99-11bf-44af-aa08-e7012516ff1d\"},\"grupoSemaforico\":{\"idJson\":\"b50789a7-f4a2-4d5d-9aff-db85f99ecdc8\"},\"ativado\":true},{\"id\":\"41bc6c5a-983f-4eec-a18b-1787271d3c05\",\"idJson\":\"9e02af97-5995-48d4-8e92-3541d56506ab\",\"plano\":{\"idJson\":\"2a5732a4-73e4-409a-9584-e3655ec49004\"},\"grupoSemaforico\":{\"idJson\":\"d3590521-8d5a-40e2-a474-2996996477c9\"},\"ativado\":true},{\"id\":\"6099cefd-461e-44e4-b983-843128eac581\",\"idJson\":\"f5f97f3a-31ef-4907-83f7-d0fcb823b72f\",\"plano\":{\"idJson\":\"84778c7f-0f2a-47c4-82d3-5150f4a0fa61\"},\"grupoSemaforico\":{\"idJson\":\"833a8267-01fb-4a84-8d8f-2df2fcfe8ab1\"},\"ativado\":true},{\"id\":\"3c91657e-53c8-453e-b736-459a6b1a3fdd\",\"idJson\":\"e1c69e40-e01b-4c63-921b-290c7e0c75b7\",\"plano\":{\"idJson\":\"84778c7f-0f2a-47c4-82d3-5150f4a0fa61\"},\"grupoSemaforico\":{\"idJson\":\"fd4c8975-904f-4a97-b2c2-1296ad26af44\"},\"ativado\":true},{\"id\":\"826e473d-1097-41a0-ba97-2270f980bba4\",\"idJson\":\"ba82065c-c1bd-4205-8b56-0ff130c810da\",\"plano\":{\"idJson\":\"84778c7f-0f2a-47c4-82d3-5150f4a0fa61\"},\"grupoSemaforico\":{\"idJson\":\"6db3142b-20fa-48d4-bd4a-03a0a4da05e8\"},\"ativado\":true},{\"id\":\"e508fe7f-1ee3-40ec-b3d9-74e914a33dc2\",\"idJson\":\"4befae18-5cd0-457d-8ca1-59f9aff435d0\",\"plano\":{\"idJson\":\"817e7874-960d-4c05-838e-82fc9780d967\"},\"grupoSemaforico\":{\"idJson\":\"fd4c8975-904f-4a97-b2c2-1296ad26af44\"},\"ativado\":true},{\"id\":\"6133af26-759d-4cad-815d-40e92e1decbe\",\"idJson\":\"63aae50c-9a90-47e1-8618-fb550514f6bc\",\"plano\":{\"idJson\":\"817e7874-960d-4c05-838e-82fc9780d967\"},\"grupoSemaforico\":{\"idJson\":\"6db3142b-20fa-48d4-bd4a-03a0a4da05e8\"},\"ativado\":true},{\"id\":\"8555bfb7-4641-4fa5-9917-1a6decfc7308\",\"idJson\":\"6eca4832-e1f6-4e21-80d1-1a97315446d3\",\"plano\":{\"idJson\":\"47af6b99-11bf-44af-aa08-e7012516ff1d\"},\"grupoSemaforico\":{\"idJson\":\"d3590521-8d5a-40e2-a474-2996996477c9\"},\"ativado\":true},{\"id\":\"73aeb42e-ecf0-4291-ad4b-0ab95b9a0f93\",\"idJson\":\"7052b43c-3336-4ac4-874d-caff7e8965c6\",\"plano\":{\"idJson\":\"bfaf928e-ddc3-4763-b472-0c2fae64aa90\"},\"grupoSemaforico\":{\"idJson\":\"d3590521-8d5a-40e2-a474-2996996477c9\"},\"ativado\":true},{\"id\":\"9f54b49b-4bf0-43b1-93c4-d1ef2e61241b\",\"idJson\":\"7efee22e-fae1-44e3-afbc-7fd08856c767\",\"plano\":{\"idJson\":\"84778c7f-0f2a-47c4-82d3-5150f4a0fa61\"},\"grupoSemaforico\":{\"idJson\":\"e5328ee4-f87f-4158-9506-8448544cef11\"},\"ativado\":true},{\"id\":\"066365df-2c4c-4cfd-9e2d-db6a953afe57\",\"idJson\":\"77855013-ceaa-49c7-aae8-1fec1114cd78\",\"plano\":{\"idJson\":\"d1c60e27-a081-4796-9635-d95fe819071d\"},\"grupoSemaforico\":{\"idJson\":\"fd4c8975-904f-4a97-b2c2-1296ad26af44\"},\"ativado\":true},{\"id\":\"85b26220-68d8-40fd-b05d-5a6b54ac2f0d\",\"idJson\":\"fb725f36-d1be-491e-9ba2-21d6f51d58fe\",\"plano\":{\"idJson\":\"d1c60e27-a081-4796-9635-d95fe819071d\"},\"grupoSemaforico\":{\"idJson\":\"e5328ee4-f87f-4158-9506-8448544cef11\"},\"ativado\":true},{\"id\":\"8056d42d-42e4-4749-8870-45fdcee6d598\",\"idJson\":\"6e303aa2-32ad-44b6-8932-2f99ec77e2ed\",\"plano\":{\"idJson\":\"d1c60e27-a081-4796-9635-d95fe819071d\"},\"grupoSemaforico\":{\"idJson\":\"833a8267-01fb-4a84-8d8f-2df2fcfe8ab1\"},\"ativado\":true},{\"id\":\"9a081883-9783-48dd-ba41-0a962684ba39\",\"idJson\":\"6ff951be-e824-4181-a619-6be021816784\",\"plano\":{\"idJson\":\"d1c60e27-a081-4796-9635-d95fe819071d\"},\"grupoSemaforico\":{\"idJson\":\"aaf1ee79-67d4-4351-863c-c765629559b0\"},\"ativado\":true},{\"id\":\"cc1d38c2-d876-4345-a85a-b289ae9e2023\",\"idJson\":\"8d334406-238e-432f-9f1c-dee1e003f264\",\"plano\":{\"idJson\":\"817e7874-960d-4c05-838e-82fc9780d967\"},\"grupoSemaforico\":{\"idJson\":\"aaf1ee79-67d4-4351-863c-c765629559b0\"},\"ativado\":true},{\"id\":\"748b9f0e-2207-44ce-9b85-4bbe27f0ef86\",\"idJson\":\"64804f08-8d80-46eb-83d9-2b75495fd18d\",\"plano\":{\"idJson\":\"84778c7f-0f2a-47c4-82d3-5150f4a0fa61\"},\"grupoSemaforico\":{\"idJson\":\"aaf1ee79-67d4-4351-863c-c765629559b0\"},\"ativado\":true},{\"id\":\"297e99e7-e375-4d90-a599-66d0ad0e7988\",\"idJson\":\"12a6b8e1-191d-4c75-a705-d0f429742ca8\",\"plano\":{\"idJson\":\"817e7874-960d-4c05-838e-82fc9780d967\"},\"grupoSemaforico\":{\"idJson\":\"e5328ee4-f87f-4158-9506-8448544cef11\"},\"ativado\":true},{\"id\":\"bd7ecb47-8f0f-4a91-bfc1-9d28199f7d7d\",\"idJson\":\"d8dbbe37-c581-42a7-8946-6f6a2c92851b\",\"plano\":{\"idJson\":\"bfaf928e-ddc3-4763-b472-0c2fae64aa90\"},\"grupoSemaforico\":{\"idJson\":\"b50789a7-f4a2-4d5d-9aff-db85f99ecdc8\"},\"ativado\":true}],\"estagiosPlanos\":[{\"id\":\"df6245cb-c92a-4067-9442-84d082add079\",\"idJson\":\"0ffbcffa-851d-4159-a50c-72acf5a9bcea\",\"posicao\":2,\"tempoVerde\":5,\"dispensavel\":true,\"estagioQueRecebeEstagioDispensavel\":{\"idJson\":\"f5f4aedc-20e1-42a2-a600-b4c5547c0891\"},\"plano\":{\"idJson\":\"817e7874-960d-4c05-838e-82fc9780d967\"},\"estagio\":{\"idJson\":\"1f864610-8025-46ef-b97d-a6ec71a2282b\"}},{\"id\":\"da454775-db6b-48b2-873c-c5f059c9a823\",\"idJson\":\"6e2b8c3b-fe93-40fc-8548-ad39541d3862\",\"posicao\":3,\"tempoVerde\":10,\"dispensavel\":false,\"plano\":{\"idJson\":\"84778c7f-0f2a-47c4-82d3-5150f4a0fa61\"},\"estagio\":{\"idJson\":\"03023953-6508-433b-b39d-a37bd6bd3d1d\"}},{\"id\":\"12b7ef9a-777f-4f2c-b969-d248a466cbfe\",\"idJson\":\"72cb8e66-f692-4d16-8c29-4100f179af9f\",\"posicao\":3,\"tempoVerde\":12,\"dispensavel\":false,\"plano\":{\"idJson\":\"bfaf928e-ddc3-4763-b472-0c2fae64aa90\"},\"estagio\":{\"idJson\":\"0c6e6f5d-d2c6-4b3a-b66f-f351440b8557\"}},{\"id\":\"e2630b17-3e86-4dae-8663-1d7b95134e9f\",\"idJson\":\"a8e0f5e3-a25f-4dd0-b2e2-823bcb0da65a\",\"posicao\":1,\"tempoVerde\":12,\"dispensavel\":false,\"plano\":{\"idJson\":\"bfaf928e-ddc3-4763-b472-0c2fae64aa90\"},\"estagio\":{\"idJson\":\"0c6e6f5d-d2c6-4b3a-b66f-f351440b8557\"}},{\"id\":\"c50c7f17-4edf-4e7b-9b9e-ff99d7e08728\",\"idJson\":\"f57e3837-c134-4e19-9c83-0f4183dd66c4\",\"posicao\":2,\"tempoVerde\":26,\"dispensavel\":false,\"plano\":{\"idJson\":\"2a5732a4-73e4-409a-9584-e3655ec49004\"},\"estagio\":{\"idJson\":\"1567c16a-4a9c-48e4-871c-b01b8827e8d6\"}},{\"id\":\"35585052-a9bc-4642-8c42-a739aa4889f5\",\"idJson\":\"92668628-1d4a-424f-af8a-f54aa6748a7f\",\"posicao\":1,\"tempoVerde\":39,\"dispensavel\":false,\"plano\":{\"idJson\":\"47af6b99-11bf-44af-aa08-e7012516ff1d\"},\"estagio\":{\"idJson\":\"0c6e6f5d-d2c6-4b3a-b66f-f351440b8557\"}},{\"id\":\"063d9315-7079-4232-a4bf-ec780bcc95be\",\"idJson\":\"1da47954-b202-44a7-bb6d-c6f859b9571d\",\"posicao\":2,\"tempoVerde\":52,\"dispensavel\":false,\"plano\":{\"idJson\":\"47af6b99-11bf-44af-aa08-e7012516ff1d\"},\"estagio\":{\"idJson\":\"1567c16a-4a9c-48e4-871c-b01b8827e8d6\"}},{\"id\":\"f2135cd3-0e53-48bd-a406-97e5f676a4fb\",\"idJson\":\"1e936308-3215-43ad-a048-79158e08a106\",\"posicao\":2,\"tempoVerde\":5,\"dispensavel\":true,\"estagioQueRecebeEstagioDispensavel\":{\"idJson\":\"6e2b8c3b-fe93-40fc-8548-ad39541d3862\"},\"plano\":{\"idJson\":\"84778c7f-0f2a-47c4-82d3-5150f4a0fa61\"},\"estagio\":{\"idJson\":\"1f864610-8025-46ef-b97d-a6ec71a2282b\"}},{\"id\":\"799d12a5-d5db-4aa1-8763-8ac99060b934\",\"idJson\":\"ed4ceb4b-bc17-4624-97fa-43ae85e72392\",\"posicao\":2,\"tempoVerde\":10,\"dispensavel\":false,\"plano\":{\"idJson\":\"d1c60e27-a081-4796-9635-d95fe819071d\"},\"estagio\":{\"idJson\":\"8fdb99c1-ba36-4241-b6c9-ef0855c35592\"}},{\"id\":\"508b5dd0-b16b-4c68-a162-54654725409f\",\"idJson\":\"f9b13dcd-3976-4a4e-b8b1-f29cebd7d2c5\",\"posicao\":1,\"tempoVerde\":35,\"dispensavel\":false,\"plano\":{\"idJson\":\"2a5732a4-73e4-409a-9584-e3655ec49004\"},\"estagio\":{\"idJson\":\"0c6e6f5d-d2c6-4b3a-b66f-f351440b8557\"}},{\"id\":\"181007f9-0b00-4783-ae98-debe54d86647\",\"idJson\":\"0b96e017-7a60-4a25-9a9e-23f0f76159c4\",\"posicao\":1,\"tempoVerde\":17,\"dispensavel\":false,\"plano\":{\"idJson\":\"84778c7f-0f2a-47c4-82d3-5150f4a0fa61\"},\"estagio\":{\"idJson\":\"8fdb99c1-ba36-4241-b6c9-ef0855c35592\"}},{\"id\":\"c82c6945-2bce-4f95-a6bb-7526fa75bee6\",\"idJson\":\"17a6ac18-ee01-4be9-bbab-d2627b313c9f\",\"posicao\":1,\"tempoVerde\":39,\"dispensavel\":false,\"plano\":{\"idJson\":\"817e7874-960d-4c05-838e-82fc9780d967\"},\"estagio\":{\"idJson\":\"8fdb99c1-ba36-4241-b6c9-ef0855c35592\"}},{\"id\":\"9824e706-cd99-40d2-888e-06ea044349a4\",\"idJson\":\"302a80b6-be4e-4c49-a888-4a6f7ad85ef7\",\"posicao\":3,\"tempoVerde\":5,\"dispensavel\":false,\"plano\":{\"idJson\":\"d1c60e27-a081-4796-9635-d95fe819071d\"},\"estagio\":{\"idJson\":\"1f864610-8025-46ef-b97d-a6ec71a2282b\"}},{\"id\":\"a12fa867-c322-4570-9541-56c8bb315d3d\",\"idJson\":\"c5f9149a-3725-4ee6-8f00-872dbdc5fd2d\",\"posicao\":4,\"tempoVerde\":5,\"dispensavel\":true,\"estagioQueRecebeEstagioDispensavel\":{\"idJson\":\"0b96e017-7a60-4a25-9a9e-23f0f76159c4\"},\"plano\":{\"idJson\":\"84778c7f-0f2a-47c4-82d3-5150f4a0fa61\"},\"estagio\":{\"idJson\":\"1f864610-8025-46ef-b97d-a6ec71a2282b\"}},{\"id\":\"33c40c82-eddf-4e0c-995f-4c44f2fe6455\",\"idJson\":\"f989403b-d2f1-4410-b88e-9b755fd83571\",\"posicao\":1,\"tempoVerde\":10,\"dispensavel\":false,\"plano\":{\"idJson\":\"d1c60e27-a081-4796-9635-d95fe819071d\"},\"estagio\":{\"idJson\":\"03023953-6508-433b-b39d-a37bd6bd3d1d\"}},{\"id\":\"2f04b62c-0e71-4f4b-8a41-7bf7da5f7592\",\"idJson\":\"f5f4aedc-20e1-42a2-a600-b4c5547c0891\",\"posicao\":3,\"tempoVerde\":28,\"dispensavel\":false,\"plano\":{\"idJson\":\"817e7874-960d-4c05-838e-82fc9780d967\"},\"estagio\":{\"idJson\":\"03023953-6508-433b-b39d-a37bd6bd3d1d\"}},{\"id\":\"2ed754d7-6bff-4a2f-81f2-bf95c6b47513\",\"idJson\":\"2100ec65-72b0-4aa0-a089-7480a19c74a6\",\"posicao\":2,\"tempoVerde\":10,\"dispensavel\":false,\"plano\":{\"idJson\":\"bfaf928e-ddc3-4763-b472-0c2fae64aa90\"},\"estagio\":{\"idJson\":\"1567c16a-4a9c-48e4-871c-b01b8827e8d6\"}}],\"cidades\":[{\"id\":\"ae198248-c4a6-11e6-970d-0401fa9c1b01\",\"idJson\":\"ae1997b0-c4a6-11e6-970d-0401fa9c1b01\",\"nome\":\"São Paulo\",\"areas\":[{\"idJson\":\"876a373d-5c58-436d-a22a-6d47d9a7f4f2\"},{\"idJson\":\"1b0ca2a0-5a5f-4542-ab56-e1c39bc5c0c3\"},{\"idJson\":\"ae19a5e8-c4a6-11e6-970d-0401fa9c1b01\"},{\"idJson\":\"0cb2035a-fcab-42e9-b73e-81f3cfbc0621\"},{\"idJson\":\"91fa698e-b2cd-40de-a3f6-427c57d90ceb\"}]}],\"areas\":[{\"id\":\"12f8b22e-c5bf-4f20-9350-c6ecb996866b\",\"idJson\":\"1b0ca2a0-5a5f-4542-ab56-e1c39bc5c0c3\",\"descricao\":3,\"cidade\":{\"idJson\":\"ae1997b0-c4a6-11e6-970d-0401fa9c1b01\"},\"limites\":[],\"subareas\":[{\"id\":\"ca9efcb8-b32b-4cb9-ba36-9470019eb1a0\",\"idJson\":\"6f3a0b07-604d-4ae7-8500-355b7083db7e\",\"nome\":\"Tatuape\",\"numero\":\"1\"}]},{\"id\":\"bb4c67ff-11e7-42e4-8b36-e2a955ce53d6\",\"idJson\":\"0cb2035a-fcab-42e9-b73e-81f3cfbc0621\",\"descricao\":5,\"cidade\":{\"idJson\":\"ae1997b0-c4a6-11e6-970d-0401fa9c1b01\"},\"limites\":[],\"subareas\":[{\"id\":\"3c65aa3f-a624-4da1-a89e-8685a1d5a6e8\",\"idJson\":\"a6203e9f-e2f5-41b6-9518-19365414ecd7\",\"nome\":\"CONSOLAÇÃO\",\"numero\":\"14\"}]},{\"id\":\"ae19847d-c4a6-11e6-970d-0401fa9c1b01\",\"idJson\":\"ae19a5e8-c4a6-11e6-970d-0401fa9c1b01\",\"descricao\":1,\"cidade\":{\"idJson\":\"ae1997b0-c4a6-11e6-970d-0401fa9c1b01\"},\"limites\":[],\"subareas\":[]},{\"id\":\"0e3a68cf-7f2a-4359-9a3a-02c2965863d0\",\"idJson\":\"876a373d-5c58-436d-a22a-6d47d9a7f4f2\",\"descricao\":14,\"cidade\":{\"idJson\":\"ae1997b0-c4a6-11e6-970d-0401fa9c1b01\"},\"limites\":[],\"subareas\":[{\"id\":\"4a8db002-6e33-4630-a51d-fa170eb18b0f\",\"idJson\":\"46258a83-1b7f-4c49-81d9-6854443b4463\",\"nome\":\"Vergueiro\",\"numero\":\"5\"}]},{\"id\":\"c837b76a-2221-491a-95d0-4a345ff4ded5\",\"idJson\":\"91fa698e-b2cd-40de-a3f6-427c57d90ceb\",\"descricao\":2,\"cidade\":{\"idJson\":\"ae1997b0-c4a6-11e6-970d-0401fa9c1b01\"},\"limites\":[],\"subareas\":[{\"id\":\"9311e5ed-87f4-4a84-bed8-109537232062\",\"idJson\":\"fea13347-e3a7-4808-9642-c639cdf3c002\",\"nome\":\"CLÉLIA\",\"numero\":\"234\"}]}],\"limites\":[],\"todosEnderecos\":[{\"id\":\"eaa095b2-cec0-48fe-9d19-91ff17b5cb4e\",\"idJson\":\"381d6c1d-7f55-4d71-9640-71c354c84fe7\",\"localizacao\":\"R. Jeroaquara\",\"latitude\":-23.5244471,\"longitude\":-46.70204430000001,\"localizacao2\":\"R. Faustolo\"},{\"id\":\"d15177d8-ae82-41b2-bf9e-f79043f614b8\",\"idJson\":\"659ee719-7d29-48ad-b0f0-afd5fe530ee1\",\"localizacao\":\"R. Clélia\",\"latitude\":-23.5250208,\"longitude\":-46.687978799999996,\"localizacao2\":\"R. Jeroaquara\"},{\"id\":\"a8458f9a-6d14-4b66-9dfa-2d50e330bab2\",\"idJson\":\"f1aea396-5396-49e6-9e89-ff3ae2fb0757\",\"localizacao\":\"R. Clélia\",\"latitude\":-23.5250208,\"longitude\":-46.687978799999996,\"localizacao2\":\"R. Jeroaquara\"}],\"imagens\":[{\"id\":\"bb01172e-2b3f-4305-b7e9-729b785e1127\",\"idJson\":\"0d8ce251-5722-48eb-b8eb-e53d2d9835e8\",\"fileName\":\"CLELIA X JEROAQUARA croqui-Model.jpg\",\"contentType\":\"image/jpeg\"},{\"id\":\"1697fe79-9bf0-4132-8816-a03ef879d584\",\"idJson\":\"282fe5c8-2a98-4dfc-b7e7-22f1c8225e6f\",\"fileName\":\"a2 e1 CLELIA X JEROAQUARA-Model.jpg\",\"contentType\":\"image/jpeg\"},{\"id\":\"5ea0add9-c890-49ce-b544-2bf0cd972ee5\",\"idJson\":\"277dcb7c-9c53-440c-ada0-68b1b01252c7\",\"fileName\":\"a1 e2 CLELIA X JEROAQUARA-Model.jpg\",\"contentType\":\"image/jpeg\"},{\"id\":\"fd257189-49ce-4406-ad0b-e1340ce396ef\",\"idJson\":\"5642eebc-b17b-4779-89da-e3f091bf6d5f\",\"fileName\":\"CLELIA X JEROAQUARA croqui-Model.jpg\",\"contentType\":\"image/jpeg\"},{\"id\":\"f6c9fbd4-17f5-494c-b150-a79f53822181\",\"idJson\":\"108685de-8892-46c3-90fb-88ae5d985007\",\"fileName\":\"a1 e1 CLELIA X JEROAQUARA-Model.jpg\",\"contentType\":\"image/jpeg\"},{\"id\":\"07a0f631-91cb-453a-b349-1ed5a2959ffb\",\"idJson\":\"426f9c03-9693-4e81-92c3-dd80fbade070\",\"fileName\":\"CLELIA X JEROAQUARA croqui-Model.jpg\",\"contentType\":\"image/jpeg\"},{\"id\":\"10acacbb-cba0-4ff4-b052-e02780623c1c\",\"idJson\":\"91ea45a9-2286-4455-b01c-01da2e62c849\",\"fileName\":\"a2 e3 CLELIA X JEROAQUARA-Model.jpg\",\"contentType\":\"image/jpeg\"},{\"id\":\"a27055d3-a093-4d33-a4d4-7682690e98bd\",\"idJson\":\"a06d1ef7-c7c2-4a13-8961-c4969eb7c5a7\",\"fileName\":\"a2 e2 CLELIA X JEROAQUARA-Model.jpg\",\"contentType\":\"image/jpeg\"}],\"atrasosDeGrupo\":[{\"id\":\"c4f4982c-e4bc-411a-9aa3-482c393b4697\",\"idJson\":\"e08ca3ca-9d5d-441e-821b-52db27eae194\",\"atrasoDeGrupo\":0},{\"id\":\"517b1f7a-024e-45db-b0e5-53a784fce4bd\",\"idJson\":\"df24c32c-1fd3-48d5-ad35-84ab1b4ea705\",\"atrasoDeGrupo\":0},{\"id\":\"c9aec21b-9127-4176-b1f3-eaa81e12f2bc\",\"idJson\":\"e28171ab-6655-4812-b4f9-6a4fbfb071ab\",\"atrasoDeGrupo\":0},{\"id\":\"876f4e36-4711-4840-8a12-95588de877f3\",\"idJson\":\"f89e3360-5a64-4c7d-97e4-b047e1721307\",\"atrasoDeGrupo\":0},{\"id\":\"4bde1da6-fca1-4cb4-a6f0-3adb428c884b\",\"idJson\":\"dbf44a6e-8347-4079-943e-a2429274727e\",\"atrasoDeGrupo\":0},{\"id\":\"32305dd8-d319-4aef-ac93-cf99045e0a53\",\"idJson\":\"d56b15b7-df8b-4e63-ac2a-6435a246f563\",\"atrasoDeGrupo\":0},{\"id\":\"476537ab-7fc7-4504-b13a-a6844c3a24bf\",\"idJson\":\"fb1b2e81-499e-4910-ad94-49a6855bcb78\",\"atrasoDeGrupo\":0},{\"id\":\"34e1b7fa-452a-48f6-b767-c5ab71bcad01\",\"idJson\":\"d0771959-3532-490c-af15-befdcc92743c\",\"atrasoDeGrupo\":0},{\"id\":\"b36b2eb1-b194-4f5b-b295-cf2adb8b0b5b\",\"idJson\":\"131e3e62-66e3-4986-9633-b0d1e3c91e69\",\"atrasoDeGrupo\":0},{\"id\":\"bd93db1b-1467-4a73-a968-11d2d3db78ee\",\"idJson\":\"8556f42c-b905-4714-8f6e-eed5a66d9355\",\"atrasoDeGrupo\":0},{\"id\":\"883838a7-1108-494c-a17f-ea881bd52ebe\",\"idJson\":\"04d38341-b9da-483e-8a89-d1eec55c1c58\",\"atrasoDeGrupo\":0},{\"id\":\"6ffc500e-8df4-4e43-954c-8e459c14f966\",\"idJson\":\"7319bb74-a53c-4494-96e3-d3e9ff429a37\",\"atrasoDeGrupo\":0},{\"id\":\"c8d3b0d4-a554-47ff-9560-cc5d82007492\",\"idJson\":\"85c49c36-8d3d-4268-8599-8402ddeeef43\",\"atrasoDeGrupo\":0},{\"id\":\"0b9c3c77-ee26-4054-9c08-ddc90699f5f0\",\"idJson\":\"9bd7cb27-f33e-4449-b0b8-6e196e60a867\",\"atrasoDeGrupo\":0},{\"id\":\"8ae31360-50d5-4007-9013-1304c637fb10\",\"idJson\":\"ca9e9b1b-5a5c-4fde-a1c2-e4e297f56a8a\",\"atrasoDeGrupo\":0},{\"id\":\"21ff5437-80f5-4000-ae01-1cd8ab57c9a0\",\"idJson\":\"a1c5c674-d48b-4243-afd5-1c1764d8d786\",\"atrasoDeGrupo\":0},{\"id\":\"47db7be2-788a-4f9f-b92f-6e2cf81e1666\",\"idJson\":\"f2b2ceb8-0c21-4902-a474-79b7148939e9\",\"atrasoDeGrupo\":0},{\"id\":\"f5bab147-99f5-4c22-a2ef-633abba237c3\",\"idJson\":\"ae3286ac-9258-4711-905c-479d819a2a23\",\"atrasoDeGrupo\":0},{\"id\":\"8b008e05-54d3-4922-ae64-390de50632c2\",\"idJson\":\"cb8b0ebe-52e8-4ec5-a7f4-52c6f8915186\",\"atrasoDeGrupo\":0},{\"id\":\"78e06a22-9842-461a-b0ee-5508980c55f0\",\"idJson\":\"efb89cf9-a9bd-40a6-833f-f25a48eff8ee\",\"atrasoDeGrupo\":8},{\"id\":\"e08849ce-4b03-4ef5-ad1a-1a86370fb237\",\"idJson\":\"c70139eb-1cc3-4372-a386-1b77cf3b2af4\",\"atrasoDeGrupo\":0},{\"id\":\"aadb1f86-f5ff-48b1-bd10-c413033d3d28\",\"idJson\":\"5b7678fd-ef77-4db8-bc83-b62e0dbe3efd\",\"atrasoDeGrupo\":0},{\"id\":\"07643330-f240-433a-ab1b-8ea77b060581\",\"idJson\":\"c1d28345-f695-4c64-b234-3fdfc624b826\",\"atrasoDeGrupo\":0},{\"id\":\"6e726e58-ad52-47d8-be29-bf90c808d302\",\"idJson\":\"5b631936-3e56-4137-9eb6-32f27ec74137\",\"atrasoDeGrupo\":0}],\"versaoControlador\":{\"id\":\"66b69697-1efe-4507-bdd0-a91f8d125bfc\",\"idJson\":null,\"descricao\":\"Controlador criado pelo usuário: Administrador Geral\",\"statusVersao\":\"EM_CONFIGURACAO\",\"controlador\":{\"id\":\"cbcd342b-39e0-4bfc-9dcd-97ba25b69375\"},\"controladorFisico\":{\"id\":\"1a754281-cd96-49f4-a069-d39e7a04e064\"},\"usuario\":{\"id\":\"ae19cdcc-c4a6-11e6-970d-0401fa9c1b01\",\"nome\":\"Administrador Geral\",\"login\":\"root\",\"email\":\"root@influunt.com.br\"}},\"statusVersao\":\"EM_CONFIGURACAO\",\"versoesPlanos\":[{\"id\":\"9cf01793-0c05-445f-83d6-498fe1881192\",\"idJson\":\"b35938a8-4718-402d-ae6a-d16a2bd0810c\",\"statusVersao\":\"EDITANDO\",\"anel\":{\"idJson\":\"bea52273-962f-475b-b6d6-aa8d77956c66\"},\"planos\":[{\"idJson\":\"bfaf928e-ddc3-4763-b472-0c2fae64aa90\"},{\"idJson\":\"2a5732a4-73e4-409a-9584-e3655ec49004\"},{\"idJson\":\"47af6b99-11bf-44af-aa08-e7012516ff1d\"}]},{\"id\":\"821145dc-d360-4795-a45b-661ae21d6c27\",\"idJson\":\"5bbe1489-e038-471c-912f-9c73f24e0a87\",\"statusVersao\":\"EDITANDO\",\"anel\":{\"idJson\":\"790305f6-990a-4e4b-8a5c-7f86b2eae7c1\"},\"planos\":[{\"idJson\":\"84778c7f-0f2a-47c4-82d3-5150f4a0fa61\"},{\"idJson\":\"817e7874-960d-4c05-838e-82fc9780d967\"},{\"idJson\":\"d1c60e27-a081-4796-9635-d95fe819071d\"}]}],\"tabelasHorarias\":[{\"id\":\"807f8a88-58ed-486e-b7be-fd4f7abb4761\",\"idJson\":\"a64ad9fe-e10c-4149-94cd-a2752dd4173c\",\"versaoTabelaHoraria\":{\"idJson\":\"a89cda54-5150-42d5-a8c0-aa6ac41a6e51\"},\"eventos\":[{\"idJson\":\"418ef111-d974-4c50-9191-0f5654489965\"},{\"idJson\":\"e52cc8b9-c7b7-4550-b21c-246e5eb254f2\"}]}],\"eventos\":[{\"id\":\"0b218e6e-9abe-48f6-9674-7c4db0e2493f\",\"idJson\":\"418ef111-d974-4c50-9191-0f5654489965\",\"posicao\":\"1\",\"tipo\":\"NORMAL\",\"diaDaSemana\":\"Todos os dias da semana\",\"data\":\"19-12-2016\",\"horario\":\"00:00:00.000\",\"posicaoPlano\":\"1\",\"tabelaHoraria\":{\"idJson\":\"a64ad9fe-e10c-4149-94cd-a2752dd4173c\"}},{\"id\":\"6c1348f5-561c-4e44-af05-e9a4341c8173\",\"idJson\":\"e52cc8b9-c7b7-4550-b21c-246e5eb254f2\",\"posicao\":\"2\",\"tipo\":\"NORMAL\",\"diaDaSemana\":\"Todos os dias da semana\",\"data\":\"19-12-2016\",\"horario\":\"11:10:00.000\",\"posicaoPlano\":\"2\",\"tabelaHoraria\":{\"idJson\":\"a64ad9fe-e10c-4149-94cd-a2752dd4173c\"}}]}");
        Controlador controlador = new ControladorCustomDeserializer().getControladorFromJson(Json.parse(controladorJson.toString()));

        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null))));
        }
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(path)")
    public CompletionStage<Result> findOneByControladorFisico(String id) {
        Controlador controlador = ControladorFisico.find.byId(UUID.fromString(id)).getControladorConfiguradoOuSincronizado();
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null))));
        }
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(path)")
    public CompletionStage<Result> edit(String id) {
        Usuario usuario = getUsuario();
        if (usuario == null) {
            return CompletableFuture.completedFuture(unauthorized(Json.toJson(Collections.singletonList(new Erro("clonar", "usuário não econtrado", "")))));
        }

        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        if (StatusVersao.EDITANDO.equals(controlador.getVersaoControlador().getStatusVersao()) && !controlador.podeSerEditadoPorUsuario(usuario)) {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(Collections.singletonList(new Erro("editar", "usuário diferente do que está editando controlador!", "")))));
        }

        if (!controlador.podeClonar()) {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(Collections.singletonList(new Erro("clonar", "controlador não pode ser clonado", "")))));
        }

        Controlador controladorEdicao = controladorService.criarCloneControlador(controlador, usuario);
        return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(controladorEdicao, Cidade.find.all(), RangeUtils.getInstance(null))));
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(path)")
    public CompletionStage<Result> editarPlanos(String id) {
        Usuario usuario = getUsuario();
        if (usuario == null) {
            return CompletableFuture.completedFuture(unauthorized(Json.toJson(Collections.singletonList(new Erro("clonar", "usuário não econtrado", "")))));
        }

        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        if (controlador.getVersaoControlador().getStatusVersao().equals(StatusVersao.EDITANDO) && !usuarioPodeEditarControlador(controlador, usuario)) {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(Collections.singletonList(new Erro("editar", "usuário diferente do que está editando planos", "")))));
        }

        if (!controlador.podeClonar()) {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(Collections.singletonList(new Erro("clonar", "plano não pode ser clonado", "")))));
        }

        if (controladorService.criarClonePlanos(controlador, usuario)) {
            controlador.refresh();
            return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null))));
        }

        return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(Collections.singletonList(new Erro("clonar", "erro ao clonar planos", "")))));
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(path)")
    public CompletionStage<Result> editarTabelaHoraria(String id) {
        Usuario usuario = getUsuario();
        if (usuario == null) {
            return CompletableFuture.completedFuture(unauthorized(Json.toJson(Collections.singletonList(new Erro("clonar", "usuário não econtrado", "")))));
        }

        Controlador controlador = Controlador.find.fetch("versaoControlador").where().eq("id", id).findUnique();
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        if (controlador.getVersaoControlador().getStatusVersao().equals(StatusVersao.EDITANDO) && !usuarioPodeEditarControlador(controlador, usuario)) {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(Collections.singletonList(new Erro("editar", "usuário diferente do que está editando planos", "")))));
        }

        if (!controlador.podeClonar()) {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(Collections.singletonList(new Erro("editar", "tabela horária não pode ser clonada", "")))));
        }

        if (controladorService.criarCloneTabelaHoraria(controlador, getUsuario())) {
            controlador.refresh();
            return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(controlador, Cidade.find.all(), RangeUtils.getInstance(null))));
        }

        return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(Collections.singletonList(new Erro("editar", "erro ao clonar tabela horária", "")))));
    }

    @Transactional
    @Dynamic(value = "Influunt")
    public CompletionStage<Result> findAll() {
        Usuario u = getUsuario();
        if (u.isRoot() || u.podeAcessarTodasAreas()) {
            InfluuntResultBuilder result = new InfluuntResultBuilder(new InfluuntQueryBuilder(Controlador.class, request().queryString()).fetch(Arrays.asList("versaoControlador", "modelo")).query());
            return CompletableFuture.completedFuture(ok(result.toJson()));
        } else if (u.getArea() != null) {
            String[] areaId = {u.getArea().getId().toString()};
            Map<String, String[]> params = new HashMap<>();
            params.putAll(ctx().request().queryString());
            if (params.containsKey("area.descricao")) {
                params.remove("area.descricao");
            }
            params.put("area.id", areaId);
            InfluuntResultBuilder result = new InfluuntResultBuilder(new InfluuntQueryBuilder(Controlador.class, params).fetch(Arrays.asList("area", "versaoControlador", "modelo")).query());
            return CompletableFuture.completedFuture(ok(result.toJson()));
        }
        return CompletableFuture.completedFuture(forbidden());
    }

    @Transactional
    @Dynamic(value = "Influunt")
    public CompletionStage<Result> getControladoresForMapa() {
        Usuario u = getUsuario();
        List<ControladorFisico> controladoresFisicos = null;
        if (u.isRoot() || u.podeAcessarTodasAreas()) {
            controladoresFisicos = ControladorFisico.find.fetch("versoes").findList();
        } else if (u.getArea() != null) {
            controladoresFisicos = ControladorFisico.find.fetch("versoes").where().eq("area_id", u.getArea().getId()).findList();
        }

        if (controladoresFisicos != null) {
            List<Controlador> controladores = new ArrayList<Controlador>();
            controladoresFisicos.forEach(controladorFisico -> {
                Controlador controlador = controladorFisico.getControladorConfiguradoOuSincronizado();
                if (controlador != null) {
                    controladores.add(controlador);
                }
            });
            return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladoresForMapas(controladores)));
        }

        return CompletableFuture.completedFuture(forbidden());
    }

    @Transactional
    @Dynamic(value = "Influunt")
    public CompletionStage<Result> getControladoresSemSubareas() {
        Usuario u = getUsuario();
        List<ControladorFisico> controladoresFisicos = null;
        if (u.isRoot() || u.podeAcessarTodasAreas()) {
            controladoresFisicos = ControladorFisico.find.fetch("versoes").findList();
        } else if (u.getArea() != null) {
            controladoresFisicos = ControladorFisico.find.fetch("versoes").where().eq("area_id", u.getArea().getId()).findList();
        }

        if (controladoresFisicos != null) {
            List<Controlador> controladores = new ArrayList<Controlador>();
            controladoresFisicos.forEach(controladorFisico -> {
                Controlador controlador = controladorFisico.getControladorConfiguradoOuAtivoOuEditando();

                // Somente controladores sem subarea.
                if (controlador != null && controlador.getSubarea() == null) {
                    controladores.add(controlador);
                }
            });

            return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladoresAgrupamentos(controladores)));
        }

        return CompletableFuture.completedFuture(forbidden());
    }

    @Transactional
    @Dynamic(value = "Influunt")
    public CompletionStage<Result> getControladoresForAgrupamentos() {
        Usuario u = getUsuario();
        Map<String, String[]> params = new HashMap<>();
        params.putAll(ctx().request().queryString());
        String[] status = {"[CONFIGURADO,SINCRONIZADO]"};
        params.put("versaoControlador.statusVersao_in", status);

        if (u.isRoot()) {
            InfluuntResultBuilder result = new InfluuntResultBuilder(new InfluuntQueryBuilder(Controlador.class, params).fetch(Collections.singletonList("aneis")).query());
            return CompletableFuture.completedFuture(ok(result.toJson("agrupamentos")));
        } else if (u.getArea() != null) {
            String[] areaId = {u.getArea().getId().toString()};
            if (params.containsKey("area.descricao")) {
                params.remove("area.descricao");
            }
            params.put("area.id", areaId);
            InfluuntResultBuilder result = new InfluuntResultBuilder(new InfluuntQueryBuilder(Controlador.class, params).fetch(Arrays.asList("area", "aneis")).query());
            return CompletableFuture.completedFuture(ok(result.toJson("agrupamentos")));
        }
        return CompletableFuture.completedFuture(forbidden());
    }

    @Dynamic(value = "Influunt")
    public CompletionStage<Result> getControladorForSimulacao(String id) {
        Controlador controlador = Controlador.find.fetch("aneis").fetch("aneis.detectores").fetch("aneis.versoesPlanos").fetch("aneis.versoesPlanos.planos").where().eq("id", id).findUnique();
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        }
        ObjectNode response = Json.newObject();
        response.set("controlador", new ControladorCustomSerializer().getControladorSimulacao(controlador));
        response.set("falhas", Json.toJson(FalhasEAlertasService.getFalhas()));
        response.set("alarmes", Json.toJson(FalhasEAlertasService.getAlarmes()));

        return CompletableFuture.completedFuture(ok(response));
    }

    @Transactional
    @Dynamic(value = "Influunt")
    public CompletionStage<Result> getControladoresForImposicao() {
        Usuario u = getUsuario();
        Map<String, String[]> params = new HashMap<>();
        params.putAll(ctx().request().queryString());

        // somente controladores sincronizados
        params.put("controlador_sincronizado_id_ne", new String[]{null});

        // Dado que seja um usuário sob uma área.
        if (!u.isRoot() && !u.podeAcessarTodasAreas() && u.getArea() != null) {
            String[] areaId = {u.getArea().getId().toString()};
            if (params.containsKey("area.descricao")) {
                params.remove("area.descricao");
            }
            params.put("controladorSincronizado.area.id", areaId);
        }

        final String nomeEndereco = params.containsKey("nomeDoEndereco") ? params.get("nomeDoEndereco")[0] : null;
        params.remove("nomeDoEndereco");


        final String nomeEnderecoEq = params.containsKey("nomeDoEndereco_eq") ? params.get("nomeDoEndereco_eq")[0] : null;
        params.remove("nomeDoEndereco_eq");


        // Dado que seja um usuário root ou um usuário sob uma área.
        if (u.isRoot() || u.podeAcessarTodasAreas() || u.getArea() != null) {
            List<ControladorFisico> controladoresFisicos = null;
            if (params.containsKey("filtrarPor_eq")) {
                if ("Subarea".equalsIgnoreCase(params.get("filtrarPor_eq")[0])) {
                    params.remove("filtrarPor_eq");
                    if (params.containsKey("subareaAgrupamento")) {
                        params.put("controladorSincronizado.subarea.nome", params.get("subareaAgrupamento"));
                        params.remove("subareaAgrupamento");
                        controladoresFisicos = (List<ControladorFisico>) new InfluuntQueryBuilder(ControladorFisico.class, params).fetch(Arrays.asList("controladorSincronizado", "controladorSincronizado.area", "controladorSincronizado.subarea", "controladorSincronizado.aneis")).query().getResult();
                    }
                } else if ("Agrupamento".equalsIgnoreCase(params.get("filtrarPor_eq")[0])) {
                    params.remove("filtrarPor_eq");
                    if (params.containsKey("subareaAgrupamento")) {
                        params.put("controladorSincronizado.aneis.agrupamentos.nome", new String[]{params.get("subareaAgrupamento")[0]});
                        params.remove("subareaAgrupamento");
                        controladoresFisicos = (List<ControladorFisico>) new InfluuntQueryBuilder(ControladorFisico.class, params).fetch(Arrays.asList("controladorSincronizado.aneis", "controladorSincronizado.aneis.agrupamentos", "controladorSincronizado.aneis.endereco")).query().getResult();
                    }
                }
            }
            if (controladoresFisicos == null) {
                controladoresFisicos = (List<ControladorFisico>) new InfluuntQueryBuilder(ControladorFisico.class, params).fetch(Arrays.asList("controladorSincronizado.aneis")).query().getResult();
            }

            List<String> aneisIds = controladoresFisicos.stream()
                .flatMap(cf -> cf.getControladorSincronizado().getAneis().stream())
                .filter(Anel::isAtivo)
                .map(anel -> anel.getId().toString())
                .collect(Collectors.toList());

            List<Anel> aneis = new ArrayList<>();
            if (!aneisIds.isEmpty()) {
                aneis = Anel.find.select("id, descricao, posicao, endereco").fetch("controlador.subarea").where().in("id", aneisIds).findList();
            }

            ArrayNode itens = JsonNodeFactory.instance.arrayNode();
            aneis.forEach(anel -> {
                if (anel.isAtivo() && (aneisIds.isEmpty() || aneisIds.contains(anel.getId().toString()))) {
                    if ((nomeEndereco == null || anel.getEndereco().nomeEndereco().toLowerCase().contains(nomeEndereco.toLowerCase())) &&
                        (nomeEnderecoEq == null || anel.getEndereco().nomeEndereco().toLowerCase().equals(nomeEnderecoEq.toLowerCase()))) {
                        ObjectNode controlador = JsonNodeFactory.instance.objectNode();
                        controlador.put("id", anel.getControlador().getControladorFisicoId());
                        itens.addObject()
                            .put("id", anel.getId().toString())
                            .put("CLA", anel.getCLA())
                            .put("posicao", anel.getPosicao())
                            .put("endereco", anel.getEndereco().nomeEndereco())
                            .putPOJO("controlador", controlador)
                            .put("controladorFisicoId", anel.getControlador().getControladorFisicoId())
                            .put("controladorId", anel.getControlador().getId().toString())
                            .put("status", anel.getControlador().getStatusControladorReal().toString())
                            .put("online", anel.getControlador().isOnline());
                    }
                }
            });

            ObjectNode retorno = JsonNodeFactory.instance.objectNode();
            retorno.putArray("data").addAll(itens);

            return CompletableFuture.completedFuture(ok(retorno));
        }

        return CompletableFuture.completedFuture(forbidden());
    }


    @Transactional
    @Dynamic(value = "ControladorAreaAuth(path)")
    public CompletionStage<Result> delete(String id) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else if (controladorService.cancelar(controlador)) {
            return CompletableFuture.completedFuture(ok());
        } else {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(Collections.singletonList(new Erro("Controlador", "Erro ao cancelar edição de controlador", "controlador")))));
        }
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(path)")
    public CompletionStage<Result> timeline(String id) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            List<VersaoControlador> versoes = VersaoControlador.versoes(controlador);
            return CompletableFuture.completedFuture(ok(Json.toJson(versoes)));
        }
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(path)")
    public CompletionStage<Result> instalacao(String id) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            ObjectNode root = Json.newObject();
            root.put("privateKey", controlador.getVersaoControlador().getControladorFisico().getControladorPrivateKey());
            root.put("publicKey", controlador.getVersaoControlador().getControladorFisico().getCentralPublicKey());
            root.put("senha", controlador.getVersaoControlador().getControladorFisico().getPassword());
            root.put("idControlador", controlador.getControladorFisicoId());
            return CompletableFuture.completedFuture(ok(root));
        }
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(path)")
    public CompletionStage<Result> podeEditar(String id) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        if (controlador.podeEditar(getUsuario())) {
            return CompletableFuture.completedFuture(ok());
        }

        return CompletableFuture.completedFuture(forbidden(Json.toJson(
            Collections.singletonList(new Erro("controlador", "Controlador em edição com o usuário: " + controlador.getVersaoControlador().getUsuario().getNome(), "")))));
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(path)")
    public CompletionStage<Result> finalizar(String id) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {

            List<Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, ControladorFinalizaConfiguracaoCheck.class);
            if (erros.size() > 0) {
                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
            }

            if (request().body().asJson() != null) {
                String descricao = request().body().asJson().get("descricao").asText();
                if (StringUtils.isEmpty(descricao.trim())) {
                    return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY,
                        Json.toJson(Collections.singletonList(new Erro("controlador", "Informe uma descrição para finalizar a configuração", "")))));
                }
                VersaoControlador versaoControlador = controlador.getVersaoControlador();
                if (versaoControlador != null) {
                    versaoControlador.setDescricao(descricao);
                    versaoControlador.update();
                }
            }
            controlador.finalizar();
            return CompletableFuture.completedFuture(ok());
        }
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(path)")
    public CompletionStage<Result> ativar(String id) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            controlador.ativar();
            return CompletableFuture.completedFuture(ok());
        }
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(path)")
    public CompletionStage<Result> cancelarEdicao(String id) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        if (controladorService.cancelar(controlador)) {
            return CompletableFuture.completedFuture(ok());
        }
        return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(Collections.singletonList(new Erro("Controlador", "Erro ao cancelar edição de controlador", "controlador")))));
    }

    @Transactional
    @Dynamic(value = "ControladorAreaAuth(path)")
    public CompletionStage<Result> atualizarDescricao(String id) {
        JsonNode json = request().body().asJson();

        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        } else {

            Controlador controlador = Controlador.find.byId(UUID.fromString(id));
            if (controlador == null) {
                return CompletableFuture.completedFuture(notFound());
            } else {
                VersaoControlador versaoControlador = controlador.getVersaoControlador();
                if (versaoControlador != null) {
                    versaoControlador.setDescricao(json.get("descricao").asText());
                    versaoControlador.update();
                }
                return CompletableFuture.completedFuture(ok());
            }
        }
    }

    @Transactional
    @Dynamic("Influunt")
    public CompletionStage<Result> removerPlanosTabelasHorarios(String id) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            controlador.removerPlanosTabelasHorarios();
            return CompletableFuture.completedFuture(ok());
        }
    }

    @Transactional
    @Dynamic("Influunt")
    public CompletionStage<Result> lerDados() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }
        Controlador controlador = ControladorFisico.find.byId(UUID.fromString(json.get("id").asText())).getControladorSincronizado();
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            String envelopeId = transacaoHelper.lerDados(controlador);
            return CompletableFuture.completedFuture(ok(Json.toJson(envelopeId)));
        }
    }

    private CompletionStage<Result> doStep(Class<?>... validationGroups) {
        if (request().body() == null) {
            return CompletableFuture.completedFuture(badRequest());
        }

        if (getUsuario() == null) {
            return CompletableFuture.completedFuture(unauthorized(Json.toJson(Arrays.asList(new Erro("criar", "usuário não econtrado", "")))));
        }

        Controlador controlador = new ControladorCustomDeserializer().getControladorFromJson(request().body().asJson());

        boolean checkIfExists = controlador.getId() != null;
        if (checkIfExists && Controlador.find.byId(controlador.getId()) == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        List<Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, validationGroups);
        if (erros.size() > 0) {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
        } else {
            if (checkIfExists) {
                controlador.update();
            } else {
                // Criar a primeira versão e o controlador físico
                ControladorFisico controladorFisico = new ControladorFisico();
                controladorFisico.criarChaves();
                VersaoControlador versaoControlador = new VersaoControlador(controlador, controladorFisico, getUsuario());
                versaoControlador.setStatusVersao(StatusVersao.EM_CONFIGURACAO);
                controladorFisico.addVersaoControlador(versaoControlador);
                controladorFisico.setArea(controlador.getArea());
                controlador.save();
                controladorFisico.save();
            }
            Controlador controlador1 = Controlador.find.byId(controlador.getId());

            return CompletableFuture.completedFuture(ok(new ControladorCustomSerializer().getControladorJson(controlador1, Cidade.find.all(), RangeUtils.getInstance(null))));
        }
    }

    private Usuario getUsuario() {
        return (Usuario) ctx().args.get("user");
    }

}
