package controllers.api.mqtt;

import com.avaje.ebean.annotation.Index;
import com.google.inject.Inject;
import play.data.Form;
import play.data.FormFactory;
import play.db.ebean.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import security.MqttCredentials;
import services.MQTTAuthService;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;



public class MqttController extends Controller {

    @Inject
    MQTTAuthService authService;

    @Inject
    FormFactory formFactory;

    @Transactional
    public CompletionStage<Result> auth() {
        Form<MqttCredentials> form = formFactory.form(MqttCredentials.class);
        MqttCredentials credentials = form.bindFromRequest().get();
        return CompletableFuture.completedFuture(authService.auth(credentials));
    }

    @Transactional
    public CompletionStage<Result> acl() {
        Form<MqttCredentials> form = formFactory.form(MqttCredentials.class);
        MqttCredentials credentials = form.bindFromRequest().get();
        return CompletableFuture.completedFuture(authService.acl(credentials));
    }


}


