package services;

import com.avaje.ebean.Ebean;
import com.google.inject.Inject;
import com.google.inject.Provider;
import helpers.ControladorUtil;
import models.*;
import org.jetbrains.annotations.NotNull;
import play.Application;
import play.Logger;
import utils.DBUtils;

/**
 * Serviço do {@link Controlador}
 * <p>
 * Created by lesiopinheiro on 8/19/16.
 */
public class ControladorService {

    @Inject
    private Provider<Application> provider;

    @NotNull
    public Controlador criarCloneControlador(Controlador controlador, Usuario usuario) {
        Ebean.beginTransaction();

        Controlador controladorClonado = null;

        try {
            controladorClonado = new ControladorUtil().provider(provider).deepClone(controlador, usuario);
            ControladorFisico controladorFisico = controlador.getVersaoControlador().getControladorFisico();

            VersaoControlador novaVersao = new VersaoControlador(controlador, controladorClonado, usuario);
            novaVersao.setStatusVersao(StatusVersao.EDITANDO);
            novaVersao.setControladorFisico(controladorFisico);
            novaVersao.setDescricao("Controlador clonado pelo usuário: " + usuario.getNome());
            controladorFisico.addVersaoControlador(novaVersao);
            controladorFisico.update();

            controladorClonado.setVersaoControlador(novaVersao);
            VersaoControlador versaoAntiga = controlador.getVersaoControlador();
            versaoAntiga.setStatusVersao(StatusVersao.ARQUIVADO);
            versaoAntiga.update();
            controlador.update();
            controladorClonado.update();

            Ebean.commitTransaction();
        } catch (Exception e) {
            Ebean.rollbackTransaction();
            Logger.error(e.getMessage(), e);

        } finally {
            Ebean.endTransaction();
        }

        return controladorClonado;
    }

    public boolean cancelar(Controlador controlador) {
        boolean result = DBUtils.executeWithTransaction(() -> {
            VersaoControlador versaoControlador = controlador.getVersaoControlador();
            Controlador controladorOrigem = versaoControlador.getControladorOrigem();
            controladorOrigem.setStatusVersao(StatusVersao.CONFIGURADO);
            controladorOrigem.update();

            controlador.delete();
        });
        return result;
    }

    public void criarClonePlanos(Controlador controlador, Usuario usuario) {
        DBUtils.executeWithTransaction(() -> new ControladorUtil().provider(provider).deepClonePlanos(controlador, usuario));
    }

    public void criarCloneTabelaHoraria(Controlador controlador, Usuario usuario) {
        DBUtils.executeWithTransaction(() -> new ControladorUtil().provider(provider).deepCloneTabelaHoraria(controlador, usuario));
    }
}
