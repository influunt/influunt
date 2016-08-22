package services;

import com.avaje.ebean.Ebean;
import com.google.inject.Inject;
import com.google.inject.Provider;
import helpers.ControladorUtil;
import models.*;
import org.jetbrains.annotations.NotNull;
import play.Application;
import play.Logger;
import play.mvc.Controller;

/**
 * Serviço do {@link Controlador}
 * <p>
 * Created by lesiopinheiro on 8/19/16.
 */
public class ControladorService {

    @Inject
    Provider<Application> provider;

    @NotNull
    public Controlador criarCloneControlador(Controlador controlador, Usuario usuario) {
        Ebean.beginTransaction();

        Controlador controladorEdicao = null;

        try {
            controladorEdicao = new ControladorUtil().provider(provider).deepClone(controlador);
            ControladorFisico controladorFisico = controlador.getVersaoControlador().getControladorFisico();
            VersaoControlador novaVersao = new VersaoControlador(controlador, controladorEdicao, usuario);
            novaVersao.setStatusVersaoControlador(StatusVersaoControlador.EDITANDO);
            novaVersao.setControladorFisico(controladorFisico);
            novaVersao.setDescricao("Controlador clonado pelo usuário: " + usuario.getNome());
            controladorFisico.addVersaoControlador(novaVersao);
            controladorFisico.update();
            controladorEdicao.setVersaoControlador(novaVersao);
            VersaoControlador versaoAntiga = controlador.getVersaoControlador();
            versaoAntiga.setStatusVersaoControlador(StatusVersaoControlador.ARQUIVADO);
            versaoAntiga.update();
            controlador.update();
            controladorEdicao.update();

            Ebean.commitTransaction();
        } catch (Exception e) {
            Ebean.rollbackTransaction();
            Logger.error(e.getMessage(), e);

        } finally {
            Ebean.endTransaction();
        }

        return controladorEdicao;
    }

}
