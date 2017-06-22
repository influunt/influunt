package security;

import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import models.Controlador;
import models.Usuario;
import org.jetbrains.annotations.Contract;
import play.mvc.Http;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppDynamicResourceHandler implements DynamicResourceHandler {

    @Inject
    private Authenticator authenticator;

    @Override
    public CompletionStage<Boolean> checkPermission(String name,
                                                    Optional<String> meta,
                                                    DeadboltHandler deadboltHandler,
                                                    Http.Context ctx) {

        return CompletableFuture.completedFuture(Boolean.FALSE);
    }

    @Override
    public CompletionStage<Boolean> isAllowed(String permissionValue,
                                              Optional<String> meta,
                                              DeadboltHandler deadboltHandler,
                                              Http.Context ctx) {

        InfluuntContextManager ctxManager = new InfluuntContextManager(authenticator);
        Usuario usuario = ctxManager.getUsuario(ctx);

        if (usuario.isRoot()) {
            return CompletableFuture.completedFuture(Boolean.TRUE);
        }

        String chave = ctxManager.getChave(ctx);
        boolean acessandoStatus = "GET /api/v1/monitoramento/status_controladores".equals(chave) ||
            "GET /api/v1/monitoramento/controladores_onlines".equals(chave) ||
            "GET /api/v1/monitoramento/controladores_offlines".equals(chave) ||
            "GET /api/v1/monitoramento/detalhe_controlador/$id<[^/]+>".equals(chave) ||
            "GET /api/v1/controladores/$id<[^/]+>/status_dinamico".equals(chave);

        if (acessandoStatus) {
            return CompletableFuture.completedFuture(Boolean.TRUE);
        }

        if (isUsuarioAuthorized(usuario, chave)) {

            if ("ControladorAreaAuth(bodyArea)".equals(permissionValue)) {
                if (usuario.podeAcessarTodasAreas()) return CompletableFuture.completedFuture(Boolean.TRUE);
                String areaId = getAreaIdFromRequestBody(ctx.request().body());
                boolean authorized = usuario.getArea() != null && usuario.getArea().getId().toString().equals(areaId);
                return CompletableFuture.completedFuture(authorized);

            } else if ("ControladorAreaAuth(path)".equals(permissionValue) || "ControladorAreaAuth(body)".equals(permissionValue)) {
                if (usuario.podeAcessarTodasAreas()) return CompletableFuture.completedFuture(Boolean.TRUE);
                String controladorId = null;
                if ("ControladorAreaAuth(path)".equals(permissionValue)) {
                    controladorId = getControladorIdFromPath(ctx.request().path());
                } else if ("ControladorAreaAuth(body)".equals(permissionValue)) {
                    controladorId = getControladorIdFromRequestBody(ctx.request().body());
                }

                if (controladorId != null && usuario.getArea() != null) {

                    // TODO fazer uma query com melhor performance do que buscar um controlador inteiro no banco
                    Controlador controlador = Controlador.findUniqueByArea(controladorId, usuario.getArea().getId().toString());
                    if (controlador != null) {
                        return CompletableFuture.completedFuture(Boolean.TRUE);
                    }

                }
            } else if ("Influunt".equals(permissionValue)) {
                return CompletableFuture.completedFuture(Boolean.TRUE);
            }
        } else {
            boolean editandoProprioUsuario = "GET /api/v1/usuarios/$id<[^/]+>".equals(chave) ||
                "GET /api/api/v1/usuarios/$id<[^/]+>".equals(chave) ||
                "PUT /api/v1/usuarios/$id<[^/]+>".equals(chave) ||
                "PUT /api/api/v1/usuarios/$id<[^/]+>".equals(chave);
            if (editandoProprioUsuario) {
                String usuarioId = getUsuarioIdFromPath(ctx.request().path());
                if (usuario.getId().toString().equals(usuarioId)) {
                    return CompletableFuture.completedFuture(Boolean.TRUE);
                }
            }
        }

        return CompletableFuture.completedFuture(Boolean.FALSE);
    }

    @Contract("null, _ -> false")
    private boolean isUsuarioAuthorized(Usuario u, String chave) {
        return u != null && (u.isRoot() || u.isAllowed(chave));
    }

    private String getControladorIdFromPath(String path) {
        Pattern pathPattern = Pattern.compile("controladores/([0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12})");
        Matcher matcher = pathPattern.matcher(path);
        if (matcher.find() && matcher.groupCount() > 0) {
            return matcher.group(1);
        }
        return "";
    }

    private String getUsuarioIdFromPath(String path) {
        Pattern pathPattern = Pattern.compile("usuarios/([0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12})");
        Matcher matcher = pathPattern.matcher(path);
        if (matcher.find() && matcher.groupCount() > 0) {
            return matcher.group(1);
        }
        return "";
    }

    private String getControladorIdFromRequestBody(Http.RequestBody body) {
        JsonNode controladorJson = body.asJson();
        if (controladorJson.get("id") != null) {
            return controladorJson.get("id").asText();
        }
        return "";
    }

    private String getAreaIdFromRequestBody(Http.RequestBody body) {
        JsonNode controladorJson = body.asJson();
        if (controladorJson.get("area") != null && controladorJson.get("area").get("idJson") != null) {
            String areaIdJson = controladorJson.get("area").get("idJson").asText();
            if (controladorJson.get("areas") != null && controladorJson.get("areas").isArray()) {
                for (JsonNode areaJson : controladorJson.get("areas")) {
                    if (areaJson.get("idJson") != null && areaJson.get("idJson").asText().equals(areaIdJson)) {
                        return areaJson.get("id").asText();
                    }
                }
            }
        }
        return "";
    }
}
