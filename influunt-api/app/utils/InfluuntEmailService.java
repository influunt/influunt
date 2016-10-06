package utils;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.joda.time.DateTime;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import play.Application;
import play.Configuration;
import play.Logger;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;

/**
 * Created by lesiopinheiro on 9/14/16.
 */
@Singleton
public class InfluuntEmailService {

    @Inject
    private MailerClient mailerClient;

    @Inject
    private Provider<Application> provider;

    @Inject
    private Configuration configuration;


    public void enviarEmailRecuperarSenha(String nome, String link, String emailAddress) {
        final Context ctx = new Context();
        ctx.setVariable("logo", configuration.getString("influuntUrl").concat("/images/logo-escura.png"));
        ctx.setVariable("nome", nome);
        ctx.setVariable("link", link);
        ctx.setVariable("data", InfluuntUtils.formatDateToString(new DateTime(), "dd 'de' MMMM 'de' yyyy 'às' HH:mm:ss"));

        FileTemplateResolver fileTemplateResolver = new FileTemplateResolver();
        fileTemplateResolver.setPrefix(provider.get().path().toString().concat("/app/templates/"));
        fileTemplateResolver.setSuffix(".html");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(fileTemplateResolver);

        final String htmlContent = templateEngine.process("recuperarSenha", ctx);

        Email email = new Email()
                .setSubject("Influunt Recuperar Senha")
                .setFrom("naoresponda@influunt.com.br")
                .addTo(emailAddress)
                .setBodyHtml(htmlContent);
        mailerClient.send(email);
    }
}
