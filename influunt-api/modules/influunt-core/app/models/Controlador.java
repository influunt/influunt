package models;

import checks.*;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.PrivateOwned;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.ControladorCustomDeserializer;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;
import utils.DBUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

/**
 * Entidade que representa o {@link Controlador} no sistema
 *
 * @author lesiopinheiro
 */
@Entity
@Table(name = "controladores")
@ChangeLog
@NumeroDeAneisIgualAoModelo(groups = ControladorAneisCheck.class)
@ConformidadeDeNumeroDeGruposSemaforicos(groups = ControladorAneisCheck.class)
@ConformidadeDeNumeroDeDetectoresDePedestre(groups = ControladorAneisCheck.class)
@ConformidadeDeNumeroDeDetectoresVeicular(groups = ControladorAneisCheck.class)
@AoMenosUmAnelAtivo(groups = ControladorAneisCheck.class)
public class Controlador extends Model implements Cloneable, Serializable {

    private static final long serialVersionUID = 521560643019927963L;

    public static Finder<UUID, Controlador> find = new Finder<UUID, Controlador>(Controlador.class);

    @Id
    private UUID id;

    @Column
    private String idJson;

    @NotNull(message = "não pode ficar em branco")
    private String nomeEndereco;

    @Column
    @JsonDeserialize(using = InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    @CreatedTimestamp
    private DateTime dataCriacao;

    @Column
    @JsonDeserialize(using = InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    @UpdatedTimestamp
    private DateTime dataAtualizacao;

    @Column
    private StatusControlador statusControlador = StatusControlador.EM_CONFIGURACAO;

    @Column
    private Integer sequencia;

    @Column
    private String numeroSMEE;

    @Column
    private String numeroSMEEConjugado1;

    @Column
    private String numeroSMEEConjugado2;

    @Column
    private String numeroSMEEConjugado3;

    @Column
    private String firmware;

    @OneToOne
    private Imagem croqui;

    @ManyToOne
    @Valid
    @NotNull(message = "não pode ficar em branco")
    private ModeloControlador modelo;

    @ManyToOne
    @Valid
    @NotNull(message = "não pode ficar em branco")
    private Area area;

    @ManyToOne
    @Valid
    private Subarea subarea;

    @OneToMany(mappedBy = "controlador", cascade = CascadeType.ALL)
    @Valid
    @PrivateOwned
    private List<Anel> aneis;

    @OneToMany(mappedBy = "controlador")
    private List<GrupoSemaforico> gruposSemaforicos;

    @OneToMany(mappedBy = "controlador")
    private List<Detector> detectores;

    @OneToOne(mappedBy = "controlador", cascade = CascadeType.ALL)
    @Valid
    private Endereco endereco;

    @OneToOne(mappedBy = "controlador", cascade = CascadeType.REMOVE)
    private VersaoControlador versaoControlador;

    @OneToMany(mappedBy = "controlador", cascade = CascadeType.ALL)
    @Valid
    private List<VersaoTabelaHoraria> versoesTabelasHorarias;

    @JsonIgnore
    @Transient
    private VersaoTabelaHoraria versaoTabelaHorariaAtiva;

    @JsonIgnore
    @Transient
    private VersaoTabelaHoraria versaoTabelaHorariaEmEdicao;

    @JsonIgnore
    @Transient
    private VersaoTabelaHoraria versaoTabelaHorariaConfigurada;

    public static Controlador isValido(Object conteudo) {
        JsonNode controladorJson = play.libs.Json.parse(conteudo.toString());
        Controlador controlador = new ControladorCustomDeserializer().getControladorFromJson(controladorJson);
        List<Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
                ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
                ControladorTransicoesProibidasCheck.class, ControladorAtrasoDeGrupoCheck.class, ControladorTabelaEntreVerdesCheck.class,
                ControladorAssociacaoDetectoresCheck.class);
        return erros.isEmpty() ? controlador : null;
    }

    public static Controlador isPacotePlanosValido(Object controladorObject, Object planosObject) {
        JsonNode controladorJson = play.libs.Json.parse(controladorObject.toString());
        JsonNode planoJson = play.libs.Json.parse(planosObject.toString());
        Controlador controlador = new ControladorCustomDeserializer().getPacotesFromJson(controladorJson, planoJson);
        List<Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
                ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
                ControladorTransicoesProibidasCheck.class, ControladorAtrasoDeGrupoCheck.class, ControladorTabelaEntreVerdesCheck.class,
                ControladorAssociacaoDetectoresCheck.class, PlanosCheck.class, TabelaHorariosCheck.class);
        return erros.isEmpty() ? controlador : null;
    }

    public static Controlador findUniqueByArea(String controladorId, String areaId) {
        return Controlador.find.where().eq("id", controladorId).eq("area_id", areaId).findUnique();
    }

    public static List<Controlador> findListByArea(String areaId) {
        return Controlador.find.where().eq("area_id", areaId).findList();
    }

    @Override
    public void save() {
        antesDeSalvarOuAtualizar();
        super.save();
    }

    @Override
    public void update() {
        antesDeSalvarOuAtualizar();
        super.update();
    }

    private void antesDeSalvarOuAtualizar() {
        if (this.getId() == null) {
            this.setStatusControlador(StatusControlador.EM_CONFIGURACAO);
            int quantidade = this.getModelo().getLimiteAnel();
            for (int i = 0; i < quantidade; i++) {
                this.addAnel(new Anel(this, i + 1));
            }
            gerarCLC();
        } else {
            List<Controlador> controladores = Controlador.find.select("area").where().eq("id", this.getId().toString()).setMaxRows(1).findList();
            if (!controladores.isEmpty()) {
                if (!this.getArea().getId().equals(controladores.get(0).getArea().getId())) {
                    // Houve alteracao na área, necessario regerar o CLC
                    gerarCLC();
                }
            }
        }

        this.deleteAnelSeNecessario();
        deleteGruposSemaforicos(this);
        deleteVerdesConflitantes(this);
        deleteEstagiosGruposSemaforicos(this);
        deleteTransicoesProibidas(this);
        deleteTabelasEntreVerdes(this);

        this.criarPossiveisTransicoes();
    }

    private void deleteTabelasEntreVerdes(Controlador controlador) {
        if (controlador.getId() != null) {
            controlador.getAneis().forEach(anel -> {
                anel.getGruposSemaforicos().forEach(grupoSemaforico ->
                        grupoSemaforico.getTabelasEntreVerdes().forEach(tabelaEntreVerdes -> {
                            if (tabelaEntreVerdes.isDestroy()) {
                                tabelaEntreVerdes.delete();
                            }
                        }));
            });
        }
    }

    private void deleteVerdesConflitantes(Controlador c) {
        if (c.getId() != null) {
            c.getAneis().forEach(anel -> {
                anel.getGruposSemaforicos().forEach(grupoSemaforico ->
                        grupoSemaforico.getVerdesConflitantes().forEach(verdeConflitante -> {
                            if (verdeConflitante.isDestroy()) {
                                verdeConflitante.delete();
                            }
                        }));
            });
        }
    }

    private void deleteGruposSemaforicos(Controlador controlador) {
        if (controlador.getId() != null) {
            controlador.getAneis().forEach(anel -> {
                anel.getGruposSemaforicos().forEach(grupoSemaforico -> {
                    if (grupoSemaforico.isDestroy()) {
                        grupoSemaforico.delete();
                    }
                });
            });
        }
    }

    private void deleteEstagiosGruposSemaforicos(Controlador controlador) {
        if (controlador.getId() != null) {
            controlador.getAneis().forEach(anel -> {
                anel.getGruposSemaforicos().forEach(grupoSemaforico -> {
                    grupoSemaforico.getEstagiosGruposSemaforicos().forEach(estagioGrupoSemaforico -> {
                        if (estagioGrupoSemaforico.isDestroy()) {
                            estagioGrupoSemaforico.delete();
                        }
                    });
                });
            });
        }
    }

    private void deleteTransicoesProibidas(Controlador controlador) {
        if (controlador.getId() != null) {
            controlador.getAneis().forEach(anel -> {
                anel.getEstagios().forEach(estagio -> {
                    estagio.getOrigemDeTransicoesProibidas().forEach(transicaoProibida -> {
                        if (transicaoProibida.isDestroy()) {
                            transicaoProibida.delete();
                        }
                    });

                    estagio.getDestinoDeTransicoesProibidas().forEach(transicaoProibida -> {
                        if (transicaoProibida.isDestroy()) {
                            transicaoProibida.delete();
                        }
                    });

                    estagio.getAlternativaDeTransicoesProibidas().forEach(transicaoProibida -> {
                        if (transicaoProibida.isDestroy()) {
                            transicaoProibida.delete();
                        }
                    });
                });
            });
        }
    }

    private void gerarCLC() {
        List<Controlador> controladorList =
                Controlador.find.query()
                        .select("sequencia")
                        .where().eq("area_id", area.getId().toString())
                        .order("sequencia desc").setMaxRows(1).findList();

        if (controladorList.size() == 0) {
            this.sequencia = 1;
        } else {
            this.sequencia = controladorList.get(0).getSequencia() + 1;
        }
    }

    private void addAnel(Anel anel) {
        if (getAneis() == null) {
            setAneis(new ArrayList<Anel>());
        }

        getAneis().add(anel);
    }

    public Integer getSequencia() {
        return sequencia;
    }

    public void setSequencia(Integer sequencia) {
        this.sequencia = sequencia;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIdJson() {
        return idJson;
    }

    public void setIdJson(String idJson) {
        this.idJson = idJson;
    }

    public String getNomeEndereco() {
        return nomeEndereco;
    }

    public void setNomeEndereco(String nomeEndereco) {
        this.nomeEndereco = nomeEndereco;
    }

    public String getNumeroSMEE() {
        return numeroSMEE;
    }

    public void setNumeroSMEE(String numeroSMEE) {
        this.numeroSMEE = numeroSMEE;
    }

    public String getCLC() {
        if (this.id != null && this.area != null) {
            if (this.subarea != null) {
                return String.format("%01d.%03d.%04d", this.area.getDescricao(), this.subarea.getNumero(), this.sequencia);
            }
            return String.format("%01d.%03d.%04d", this.area.getDescricao(), 0, this.sequencia);
        }
        return "";
    }

    public String getNumeroSMEEConjugado1() {
        return numeroSMEEConjugado1;
    }

    public void setNumeroSMEEConjugado1(String numeroSMEEConjugado1) {
        this.numeroSMEEConjugado1 = numeroSMEEConjugado1;
    }

    public String getNumeroSMEEConjugado2() {
        return numeroSMEEConjugado2;
    }

    public void setNumeroSMEEConjugado2(String numeroSMEEConjugado2) {
        this.numeroSMEEConjugado2 = numeroSMEEConjugado2;
    }

    public String getNumeroSMEEConjugado3() {
        return numeroSMEEConjugado3;
    }

    public void setNumeroSMEEConjugado3(String numeroSMEEConjugado3) {
        this.numeroSMEEConjugado3 = numeroSMEEConjugado3;
    }

    public String getFirmware() {
        return firmware;
    }

    public void setFirmware(String firmware) {
        this.firmware = firmware;
    }

    public Imagem getCroqui() {
        return croqui;
    }

    public void setCroqui(Imagem croqui) {
        this.croqui = croqui;
    }

    public ModeloControlador getModelo() {
        return modelo;
    }

    public void setModelo(ModeloControlador modelo) {
        this.modelo = modelo;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Subarea getSubarea() {
        return subarea;
    }

    public void setSubarea(Subarea subarea) {
        this.subarea = subarea;
    }

    public List<Anel> getAneis() {
        return aneis;
    }

    public void setAneis(List<Anel> aneis) {
        this.aneis = aneis;
        this.aneis.stream().forEach(anel -> anel.setControlador(this));
    }

    public List<GrupoSemaforico> getGruposSemaforicos() {
        return gruposSemaforicos;
    }

    public void setGruposSemaforicos(List<GrupoSemaforico> gruposSemaforicos) {
        this.gruposSemaforicos = gruposSemaforicos;
    }

    public List<Detector> getDetectores() {
        return detectores;
    }

    public void setDetectores(List<Detector> detectores) {
        this.detectores = detectores;
    }

    public DateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(DateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public DateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(DateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public TabelaHorario getTabelaHoraria() {
        if (getVersaoTabelaHoraria() != null) {
            return getVersaoTabelaHoraria().getTabelaHoraria();
        }
        return null;
    }

    @Transient
    public VersaoTabelaHoraria getVersaoTabelaHorariaAtiva() {
        if (versaoTabelaHorariaAtiva == null) {
            if (getVersoesTabelasHorarias() != null && !getVersoesTabelasHorarias().isEmpty()) {
                this.versaoTabelaHorariaAtiva = getVersoesTabelasHorarias().stream().filter(VersaoTabelaHoraria::isAtivo).findFirst().orElse(null);
            }
        }
        return versaoTabelaHorariaAtiva;
    }

    @Transient
    public VersaoTabelaHoraria getVersaoTabelaHorariaEmEdicao() {
        if (versaoTabelaHorariaEmEdicao == null) {
            if (getVersoesTabelasHorarias() != null && !getVersoesTabelasHorarias().isEmpty()) {
                this.versaoTabelaHorariaEmEdicao = getVersoesTabelasHorarias().stream().filter(VersaoTabelaHoraria::isEditando).findFirst().orElse(null);
            }
        }
        return versaoTabelaHorariaEmEdicao;
    }

    @Transient
    public VersaoTabelaHoraria getVersaoTabelaHorariaConfigurada() {
        if (versaoTabelaHorariaConfigurada == null) {
            if (getVersoesTabelasHorarias() != null && !getVersoesTabelasHorarias().isEmpty()) {
                this.versaoTabelaHorariaConfigurada = getVersoesTabelasHorarias().stream().filter(VersaoTabelaHoraria::isConfigurada).findFirst().orElse(null);
            }
        }
        return versaoTabelaHorariaConfigurada;
    }

    @Transient
    public VersaoTabelaHoraria getVersaoTabelaHoraria() {
        if (getVersaoTabelaHorariaEmEdicao() != null) {
            return getVersaoTabelaHorariaEmEdicao();
        } else if (getVersaoTabelaHorariaConfigurada() != null) {
            return getVersaoTabelaHorariaConfigurada();
        } else if (getVersaoTabelaHorariaAtiva() != null) {
            return getVersaoTabelaHorariaAtiva();
        }
        return null;
    }

    public VersaoControlador getVersaoControlador() {
        return versaoControlador;
    }

    public void setVersaoControlador(VersaoControlador versaoControlador) {
        this.versaoControlador = versaoControlador;
    }

    public List<VersaoTabelaHoraria> getVersoesTabelasHorarias() {
        return versoesTabelasHorarias;
    }

    public void setVersoesTabelasHorarias(List<VersaoTabelaHoraria> versoesTabelasHorarias) {
        this.versoesTabelasHorarias = versoesTabelasHorarias;
    }

    public void criarPossiveisTransicoes() {
        for (Anel anel : getAneis()) {
            for (GrupoSemaforico grupoSemaforico : anel.getGruposSemaforicos()) {
                grupoSemaforico.criarPossiveisTransicoes();
            }
        }
    }

    @AssertTrue(groups = TabelaHorariosCheck.class,
            message = "O controlador deve ter tabela horária configurada.")
    public boolean isPossuiTabelaHoraria() {
        return this.getTabelaHoraria() != null;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void addGruposSemaforicos(GrupoSemaforico grupoSemaforico) {
        if (getGruposSemaforicos() == null) {
            setGruposSemaforicos(new ArrayList<GrupoSemaforico>());
        }
        getGruposSemaforicos().add(grupoSemaforico);
    }

    public StatusControlador getStatusControlador() {
        return statusControlador;
    }

    public void setStatusControlador(StatusControlador statusControlador) {
        this.statusControlador = statusControlador;
    }

    public void deleteAnelSeNecessario() {
        ListIterator<Anel> it = getAneis().listIterator();
        while (it.hasNext()) {
            Anel anel = it.next();
            if (anel.isDestroy()) {
                anel.setControlador(null);
                it.remove();
                it.add(new Anel(this, anel.getPosicao()));
            }
        }
    }

    public void addVersaoTabelaHoraria(VersaoTabelaHoraria versaoTabelaHoraria) {
        if (getVersoesTabelasHorarias() == null) {
            setVersoesTabelasHorarias(new ArrayList<VersaoTabelaHoraria>());
        }
        getVersoesTabelasHorarias().add(versaoTabelaHoraria);
    }

    public void finalizar() {
        DBUtils.executeWithTransaction(() -> {
            VersaoControlador versao = this.getVersaoControlador();
            versao.finalizar();
            versao.update();

            VersaoTabelaHoraria versaoTabelaHoraria = this.getVersaoTabelaHoraria();
            if (versaoTabelaHoraria != null) {
                versaoTabelaHoraria.finalizar();
            }

            getAneis().forEach(anel -> {
                VersaoPlano versaoPlano = anel.getVersaoPlano();
                if (versaoPlano != null) {
                    versaoPlano.finalizar();
                }
            });

            this.setStatusControlador(StatusControlador.CONFIGURADO);

            Controlador controladorOrigem = versaoControlador.getControladorOrigem();
            if (controladorOrigem != null) {
                this.reassociarAgrupamentos(controladorOrigem);
            }
            this.update();
        });
    }

    public void ativar() {
        DBUtils.executeWithTransaction(() -> {
            VersaoControlador versao = this.getVersaoControlador();
            versao.ativar();
            versao.update();

            VersaoTabelaHoraria versaoTabelaHoraria = this.getVersaoTabelaHoraria();
            if (versaoTabelaHoraria != null) {
                versaoTabelaHoraria.ativar();
            }

            getAneis().forEach(anel -> {
                VersaoPlano versaoPlano = anel.getVersaoPlano();
                if (versaoPlano != null) {
                    versaoPlano.ativar();
                }
            });

            this.setStatusControlador(StatusControlador.ATIVO);

            Controlador controladorOrigem = versaoControlador.getControladorOrigem();
            if (controladorOrigem != null) {
                this.reassociarAgrupamentos(controladorOrigem);
            }
            this.update();
        });
    }


    private void reassociarAgrupamentos(Controlador controladorOrigem) {
        long totalAneisOrigem = controladorOrigem.getAneis().stream().filter(Anel::isAtivo).count();
        long totalAneisAtual = this.getAneis().stream().filter(Anel::isAtivo).count();

        if (totalAneisAtual == totalAneisOrigem) {
            controladorOrigem.getAneis().stream().filter(Anel::isAtivo).forEach(anelOrigem -> {
                if (anelOrigem.getAgrupamentos() != null) {

                    anelOrigem.getAgrupamentos().forEach(agrupamento -> {
                        Anel anelAtual = this.getAneis()
                                .stream()
                                .filter(anel -> anel.getPosicao().equals(anelOrigem.getPosicao()))
                                .findFirst()
                                .orElse(null);
                        if (anelAtual != null) {
                            ListIterator<Anel> it = agrupamento.getAneis().listIterator();
                            while (it.hasNext()) {
                                Anel anel = it.next();
                                if (anel.getId().equals(anelOrigem.getId())) {
                                    it.remove(); // remove anel antigo do agrupamento
                                    it.add(anelAtual); // adiciona nova versão do anel no agrupamento
                                }
                            }
                            agrupamento.update();
                        }
                    });

                }
            });
        } else {
            // mudança no controlador foi muito grande, agrupamentos
            // não fazem mais sentido do jeito que são. Todos os agrupamentos
            // associados com a versão antiga do controlador serão apagados.
            controladorOrigem.getAneis().forEach(anel -> {
                if (anel.getAgrupamentos() != null) {
                    anel.getAgrupamentos().forEach(Agrupamento::delete);
                }
            });
        }
    }

    public boolean isConfigurado() {
        getEndereco().getAlturaNumerica();
        getAneis().stream().filter(Anel::isAtivo).forEach(anel -> anel.getEndereco().getAlturaNumerica());
        getVersoesTabelasHorarias().forEach(versaoTabelaHoraria -> versaoTabelaHoraria.getTabelaHoraria().getVersaoTabelaHoraria());

        return new InfluuntValidator<Controlador>().validate(this, javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
                ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
                ControladorTransicoesProibidasCheck.class, ControladorAtrasoDeGrupoCheck.class, ControladorTabelaEntreVerdesCheck.class,
                ControladorAssociacaoDetectoresCheck.class).size() == 0;
    }

    /**
     * Metodo utilizado para remoção dos {@link Plano} e {@link TabelaHorario} toda vez que houver necessidade
     */
    public void removerPlanosTabelasHorarios() {
        getAneis().forEach(anel -> {
            if (anel.getVersaoPlano() != null) {
                anel.getVersaoPlano().delete();
            }
            anel.getPlanos().forEach(Plano::delete);
        });

        if (getTabelaHoraria() != null) {
            getTabelaHoraria().delete();
        }
        if (getVersaoTabelaHoraria() != null) {
            getVersaoTabelaHoraria().delete();
        }
    }
}
