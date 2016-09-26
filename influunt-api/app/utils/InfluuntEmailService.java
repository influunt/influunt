package utils;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import play.Application;
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


    public void enviarEmailRecuperarSenha(String nome, String link, String emailAddress) {
        final Context ctx = new Context();
        ctx.setVariable("nome", nome);
        ctx.setVariable("link", link);

        Logger.warn("URL: " + provider.get().path().toString().concat("/app/templates/"));

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
                .setBodyText(htmlContent);
        mailerClient.send(email);
    }
}
