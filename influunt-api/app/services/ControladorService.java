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
 *
 * Created by lesiopinheiro on 8/19/16.
 */
public class ControladorService {

    @Inject
    private Provider<Application> provider;

    @NotNull
    public Controlador criarCloneControlador(Controlador controlador, Usuario usuario) {
        Ebean.beginTransaction();

        Controlador controladorEdicao = null;

        try {
            controladorEdicao = new ControladorUtil().provider(provider).deepClone(controlador);
            ControladorFisico controladorFisico = controlador.getVersaoControlador().getControladorFisico();

            VersaoControlador novaVersao = new VersaoControlador(controlador, controladorEdicao, usuario);
            novaVersao.setStatusVersao(StatusVersao.EDITANDO);
            novaVersao.setControladorFisico(controladorFisico);
            novaVersao.setDescricao("Controlador clonado pelo usuário: " + usuario.getNome());
            controladorFisico.addVersaoControlador(novaVersao);
            controladorFisico.update();
            controladorEdicao.setVersaoControlador(novaVersao);

            VersaoControlador versaoAntiga = controlador.getVersaoControlador();
            versaoAntiga.setStatusVersao(StatusVersao.ARQUIVADO);
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



    @NotNull
    public void cancelar(Controlador controlador) {
        Ebean.beginTransaction();

        try {
            VersaoControlador versaoControlador = controlador.getVersaoControlador();
            Controlador controladorOrigem = versaoControlador.getControladorOrigem();
            controladorOrigem.setStatusControlador(StatusControlador.ATIVO);
            VersaoControlador versaoControladorOrigem = controladorOrigem.getVersaoControlador();
            versaoControladorOrigem.setStatusVersao(StatusVersao.ATIVO);
            versaoControladorOrigem.update();
            controladorOrigem.update();

            controlador.delete();

            Ebean.commitTransaction();
        } catch (Exception e) {
            Ebean.rollbackTransaction();
            Logger.error(e.getMessage(), e);

        } finally {
            Ebean.endTransaction();
        }

    }

    public void criarClonePlanos(Controlador controlador, Usuario usuario) {
        DBUtils.executeWithTransaction(() -> {
            new ControladorUtil().provider(provider).deepClonePlanos(controlador, usuario);
        });
    }

    public void criarCloneTabelaHoraria(Controlador controlador, Usuario usuario) {
        DBUtils.executeWithTransaction(() -> {
            new ControladorUtil().provider(provider).deepCloneTabelaHoraria(controlador, usuario);
        });
    }
}
