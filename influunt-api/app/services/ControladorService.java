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

import java.util.List;

/**
 * Serviço do {@link Controlador}
 * <p>
 * Created by lesiopinheiro on 8/19/16.
 */
public class ControladorService {

    @Inject
    private Provider<Application> provider;

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
            System.out.println("\nErro ao clonar:");
            e.printStackTrace();

        } finally {
            Ebean.endTransaction();
        }

        return controladorClonado;
    }

    public boolean cancelar(Controlador controlador) {
        return DBUtils.executeWithTransaction(() -> {
            VersaoControlador versaoControlador = controlador.getVersaoControlador();
            Controlador controladorOrigem = versaoControlador.getControladorOrigem();

            if (controladorOrigem != null) {
                controladorOrigem.setStatusVersao(StatusVersao.CONFIGURADO);
                controladorOrigem.update();

                controlador.getAneis().forEach(anel -> {
                    VersaoPlano versaoAtual = anel.getVersaoPlanoEmEdicao();
                    if (versaoAtual != null && StatusVersao.EDITANDO.equals(versaoAtual.getStatusVersao())) {
                        VersaoPlano versaoAnterior = versaoAtual.getVersaoAnterior();
                        if (versaoAnterior != null) {
                            versaoAnterior.setStatusVersao(StatusVersao.CONFIGURADO);
                            versaoAnterior.update();
                            versaoAtual.delete();
                        }
                    }

                    List<Agrupamento> agrupamentos = Agrupamento.find.where().eq("aneis.id", anel.getId()).findList();
                    Anel anelOrigem = controladorOrigem.getAneis().stream()
                        .filter(a -> a.getIdJson().equals(anel.getIdJson()))
                        .findFirst().orElse(null);
                    agrupamentos.forEach(agrupamento -> {
                        agrupamento.getAneis().remove(anel);
                        agrupamento.addAnel(anelOrigem);
                        agrupamento.update();
                    });
                });

                if (controlador.getVersaoTabelaHoraria() != null) {
                    TabelaHorario tabelaAtual = controlador.getVersaoTabelaHoraria().getTabelaHoraria();
                    tabelaAtual.voltarVersaoAnterior();
                }

                controlador.delete();
            }
        });
    }

    public boolean criarClonePlanos(Controlador controlador, Usuario usuario) {
        return DBUtils.executeWithTransaction(() -> new ControladorUtil().provider(provider).deepClonePlanos(controlador, usuario));
    }

    public boolean criarCloneTabelaHoraria(Controlador controlador, Usuario usuario) {
        return DBUtils.executeWithTransaction(() -> new ControladorUtil().provider(provider).deepCloneTabelaHoraria(controlador, usuario));
    }
}
