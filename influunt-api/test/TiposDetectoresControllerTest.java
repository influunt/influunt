import play.test.WithApplication;

public class TiposDetectoresControllerTest extends WithApplication {
//
//    @Override
//    protected Application provideApplication() {
//        Map<String, String> options = new HashMap<String, String>();
//        options.put("DATABASE_TO_UPPER", "FALSE");
//        return getApplication(inMemoryDatabase("default", options));
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    private Application getApplication(Map configuration) {
//        return new GuiceApplicationBuilder().configure(configuration)
//                .overrides(bind(Authenticator.class).to(TestAuthenticator.class)).in(Mode.TEST).build();
//    }
//
//    @Test
//    public void testCriarNovoTipoDetector() {
//        TipoDetector tipoDetector = new TipoDetector();
//        tipoDetector.setDescricao("Descrição");
//
//        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
//                .uri(routes.TiposDetectoresController.create().url()).bodyJson(Json.toJson(tipoDetector));
//        Result result = route(postRequest);
//        JsonNode json = Json.parse(Helpers.contentAsString(result));
//        tipoDetector = Json.fromJson(json, TipoDetector.class);
//
//        assertEquals(200, result.status());
//        assertEquals("Descrição", tipoDetector.getDescricao());
//        assertNotNull(tipoDetector.getId());
//    }
//
//    @Test
//    public void testAtualizarTipoDetectorExistente() {
//        TipoDetectorCrudService tipoDetectorService = app.injector().instanceOf(TipoDetectorCrudService.class);
//        JPAApi jpaApi = app.injector().instanceOf(JPAApi.class);
//
//        String tipoDetectorId = jpaApi.withTransaction(() -> {
//            TipoDetector tipoDetector = new TipoDetector();
//            tipoDetector.setDescricao("Descrição");
//            tipoDetectorService.save(tipoDetector);
//            return tipoDetector.getId();
//        });
//
//
//        TipoDetector novoTipo = new TipoDetector();
//        novoTipo.setDescricao("Teste");
//
//        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
//                .uri(routes.TiposDetectoresController.update(tipoDetectorId).url())
//                .bodyJson(Json.toJson(novoTipo));
//        Result result = route(request);
//        JsonNode json = Json.parse(Helpers.contentAsString(result));
//        TipoDetector tipoDetector = Json.fromJson(json, TipoDetector.class);
//
//        assertEquals(200, result.status());
//        assertEquals("Teste", tipoDetector.getDescricao());
//        assertNotNull(tipoDetector.getId());
//    }
//
//    @Test
//    public void testAtualizarTipoDetectorNaoExistente() {
//        TipoDetector tipoDetector = new TipoDetector();
//        tipoDetector.setDescricao("Teste");
//
//        Http.RequestBuilder request = new Http.RequestBuilder().method("PUT")
//                .uri(routes.TiposDetectoresController.update("xxxx").url())
//                .bodyJson(Json.toJson(tipoDetector));
//        Result result = route(request);
//        assertEquals(404, result.status());
//    }
//
//    @Test
//    public void testApagarTipoDetectorExistente() {
//        TipoDetectorCrudService tipoDetectorService = app.injector().instanceOf(TipoDetectorCrudService.class);
//        JPAApi jpaApi = app.injector().instanceOf(JPAApi.class);
//
//        String tipoDetectorId = jpaApi.withTransaction(() -> {
//            TipoDetector tipoDetector = new TipoDetector();
//            tipoDetector.setDescricao("Teste");
//            tipoDetectorService.save(tipoDetector);
//            return tipoDetector.getId();
//        });
//
//        Http.RequestBuilder request = new Http.RequestBuilder().method("DELETE")
//                .uri(routes.TiposDetectoresController.delete(tipoDetectorId).url());
//        Result result = route(request);
//
//        assertEquals(200, result.status());
//        TipoDetector tipoDetector = jpaApi.withTransaction(() -> { return tipoDetectorService.findOne(tipoDetectorId); });
//        assertNull(tipoDetector);
//    }
//
//    @Test
//    public void testApagarTipoDetectorNaoExistente() {
//        TipoDetector tipoDetector = new TipoDetector();
//        tipoDetector.setDescricao("Teste");
//
//        Http.RequestBuilder request = new Http.RequestBuilder().method("DELETE")
//                .uri(routes.TiposDetectoresController.delete("xxxx").url());
//        Result result = route(request);
//        assertEquals(404, result.status());
//    }
//
//    @Test
//    public void testListarTiposDetectores() {
//        JPAApi jpaApi = app.injector().instanceOf(JPAApi.class);
//        TipoDetectorCrudService tipoDetectorService = app.injector().instanceOf(TipoDetectorCrudService.class);
//
//        jpaApi.withTransaction(() -> {
//            TipoDetector tipoDetector = new TipoDetector();
//            tipoDetector.setDescricao("Teste 1");
//            tipoDetectorService.save(tipoDetector);
//
//            tipoDetector = new TipoDetector();
//            tipoDetector.setDescricao("Teste 2");
//            tipoDetectorService.save(tipoDetector);
//        });
//
//
//        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
//                .uri(routes.TiposDetectoresController.findAll().url());
//        Result result = route(request);
//        JsonNode json = Json.parse(Helpers.contentAsString(result));
//        List<TipoDetector> tiposDetectores = Json.fromJson(json, List.class);
//
//        assertEquals(200, result.status());
//        assertEquals(2, tiposDetectores.size());
//    }

}
