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
import play.libs.Json;
import status.StatusConexaoControlador;
import utils.DBUtils;
import utils.RangeUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


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
    @JsonIgnore
    @Transient
    boolean atualizando = false;
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
    @OneToOne(mappedBy = "controlador", cascade = CascadeType.ALL)
    @Valid
    private Endereco endereco;
    @OneToOne(mappedBy = "controlador", cascade = CascadeType.REMOVE)
    private VersaoControlador versaoControlador;
    @OneToMany(mappedBy = "controlador", cascade = CascadeType.ALL)
    private List<VersaoTabelaHoraria> versoesTabelasHorarias;
    @Column
    private Boolean bloqueado = false;
    @Column
    private Boolean planosBloqueado = false;
    @Column
    private Boolean sincronizado = false;
    @Column
    @NotNull
    private boolean exclusivoParaTeste = false;
    @JsonIgnore
    @Transient
    private VersaoTabelaHoraria versaoTabelaHorariaAtiva;
    @JsonIgnore
    @Transient
    private VersaoTabelaHoraria versaoTabelaHorariaEmEdicao;
    @JsonIgnore
    @Transient
    private VersaoTabelaHoraria versaoTabelaHorariaConfigurada;
    @JsonIgnore
    @Transient
    @Valid
    private VersaoTabelaHoraria versaoTabelaHoraria;
    @JsonIgnore
    @Transient
    private RangeUtils rangeUtils;

    public static Controlador isValido(Object conteudo) {
        JsonNode controladorJson = Json.parse(conteudo.toString());
        Controlador controlador = new ControladorCustomDeserializer().getControladorFromJson(controladorJson);

        List<Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
            ControladorTransicoesProibidasCheck.class, ControladorAtrasoDeGrupoCheck.class, ControladorTabelaEntreVerdesCheck.class,
            ControladorAssociacaoDetectoresCheck.class);
        return erros.isEmpty() ? controlador : null;
    }

    public static Controlador isPacotePlanosValido(Object controladorObject, Object planosObject) {
        JsonNode controladorJson = Json.parse(controladorObject.toString());
        JsonNode planoJson = Json.parse(planosObject.toString());

        Controlador controlador = new ControladorCustomDeserializer().getPacotePlanosFromJson(controladorJson, planoJson);
        List<Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
            ControladorTransicoesProibidasCheck.class, ControladorAtrasoDeGrupoCheck.class, ControladorTabelaEntreVerdesCheck.class,
            ControladorAssociacaoDetectoresCheck.class, PlanosCheck.class, TabelaHorariosCheck.class);
        return erros.isEmpty() ? controlador : null;
    }

    public static Controlador isPacoteTabelaHorariaValido(Object controladorObject, Object pacoteTabelaHoraria) {
        return isPacoteTabelaHorariaValido(controladorObject, pacoteTabelaHoraria, null);
    }

    private static Controlador isPacoteTabelaHorariaValido(Object controladorObject, Object pacoteTabelaHoraria, Usuario usuario) {
        JsonNode controladorJson = Json.parse(controladorObject.toString());
        JsonNode pacoteTabelaHorariaJson = Json.parse(pacoteTabelaHoraria.toString());

        Controlador controlador = new ControladorCustomDeserializer().getPacoteTabelaHorariaFromJson(controladorJson, pacoteTabelaHorariaJson, usuario);
        List<Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
            ControladorTransicoesProibidasCheck.class, ControladorAtrasoDeGrupoCheck.class, ControladorTabelaEntreVerdesCheck.class,
            ControladorAssociacaoDetectoresCheck.class, PlanosCheck.class, TabelaHorariosCheck.class);
        return erros.isEmpty() ? controlador : null;
    }

    public static Object checkConfiguracaoTabelaHoraria(Object controladorObject, Object pacoteTabelaHoraria, Usuario usuario) {
        Controlador controlador = isPacoteTabelaHorariaValido(controladorObject, pacoteTabelaHoraria, usuario);
        if (controlador == null) {
            JsonNode controladorJson = Json.parse(controladorObject.toString());
            JsonNode pacoteTabelaHorariaJson = Json.parse(pacoteTabelaHoraria.toString());

            controlador = new ControladorCustomDeserializer().getPacoteTabelaHorariaFromJson(controladorJson, pacoteTabelaHorariaJson);
            return new InfluuntValidator<Controlador>().validate(controlador, javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
                ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
                ControladorTransicoesProibidasCheck.class, ControladorAtrasoDeGrupoCheck.class, ControladorTabelaEntreVerdesCheck.class,
                ControladorAssociacaoDetectoresCheck.class, PlanosCheck.class, TabelaHorariosCheck.class);
        }
        return controlador;
    }


    public static Controlador isPacoteConfiguracaoCompletaValido(Object controladorObject, Object pacotePlanos, Object pacoteTabelaHoraria) {
        JsonNode controladorJson = Json.parse(controladorObject.toString());
        JsonNode pacoteTabelaHorariaJson = Json.parse(pacoteTabelaHoraria.toString());
        JsonNode pacotePlanosJson = Json.parse(pacotePlanos.toString());

        Controlador controlador = new ControladorCustomDeserializer().getPacoteConfiguracaoCompletaFromJson(controladorJson, pacotePlanosJson, pacoteTabelaHorariaJson);

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

    public boolean isCompletoDevice() {
        List<Erro> erros = new InfluuntValidator<Controlador>().validate(this, javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
            ControladorTransicoesProibidasCheck.class, ControladorAtrasoDeGrupoCheck.class, ControladorTabelaEntreVerdesCheck.class,
            ControladorAssociacaoDetectoresCheck.class, PlanosCheck.class, TabelaHorariosCheck.class);
        return erros.isEmpty();
    }

    public boolean isCompleto() {
        List<Erro> erros = new InfluuntValidator<Controlador>().validate(this, javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
            ControladorTransicoesProibidasCheck.class, ControladorAtrasoDeGrupoCheck.class, ControladorTabelaEntreVerdesCheck.class,
            ControladorAssociacaoDetectoresCheck.class, PlanosCheck.class, PlanosCentralCheck.class,
            TabelaHorariosCheck.class);
        return erros.isEmpty();
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
            this.setStatusVersao(StatusVersao.EM_CONFIGURACAO);

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

        deleteAnelSeNecessario();
        deleteGruposSemaforicos(this);
        deleteVerdesConflitantes(this);
        deleteEstagiosGruposSemaforicos(this);
        deleteTransicoesProibidas(this);
        deleteTabelasEntreVerdes(this);
        deleteEstagiosGruposSemaforicosPlanos(this);
        deleteEventos(this);
        deleteEstagiosPlanos(this);
        if (isAtualizando()) {
            // agrupamentos só podem ser apagados numa atualização
            // de controlador. Cadastro de planos também passa por aqui
            deleteAgrupamentos(this);
        }
        criarPossiveisTransicoes();
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

    private void deleteEventos(Controlador controlador) {
        if (controlador.getId() != null && controlador.getTabelaHoraria() != null) {
            controlador.getTabelaHoraria().getEventos().forEach(estagio -> {
                if (estagio.isDestroy()) {
                    estagio.delete();
                }
            });
        }
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

    private void deleteEstagiosGruposSemaforicosPlanos(Controlador controlador) {
        if (controlador.getId() != null) {
            controlador.getAneis().forEach(anel -> {
                anel.getPlanos().forEach(plano -> {
                    if (plano != null) {
                        plano.getGruposSemaforicosPlanos().forEach(grupoSemaforicoPlano -> {
                            if (grupoSemaforicoPlano.isDestroy()) {
                                grupoSemaforicoPlano.delete();
                            }
                        });
                    }
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

    private void deleteEstagiosPlanos(Controlador controlador) {
        if (controlador.getId() != null) {
            controlador.getAneis().stream().filter(Anel::isAtivo).forEach(anel -> {
                anel.getVersoesPlanos().forEach(versaoPlano -> {
                    versaoPlano.getPlanos().forEach(plano -> {
                        if (plano != null) {
                            plano.getEstagiosPlanos().stream()
                                .filter(EstagioPlano::isDestroy)
                                .forEach(EstagioPlano::delete);
                        }
                    });
                });
            });
        }
    }

    private void deleteAgrupamentos(Controlador controlador) {
        VersaoControlador versao = controlador.getVersaoControlador();
        if (versao != null) {
            Controlador controladorOrigem = versao.getControladorOrigem();
            if (controladorOrigem != null) {
                if (controladorOrigem.getAneisAtivos().size() != getAneisAtivos().size()) {
                    // mudança no controlador foi muito grande, agrupamentos
                    // não fazem mais sentido do jeito que são. Todos os agrupamentos
                    // associados com a versão antiga do controlador serão apagados.
                    getAneis().forEach(anel -> {
                        List<Agrupamento> agrupamentos = Agrupamento.find.where().eq("aneis.id", anel.getId()).findList();
                        if (agrupamentos != null && !agrupamentos.isEmpty()) {
                            for (Agrupamento agrupamento : agrupamentos) {
                                agrupamento.delete();
                            }
                        }
                    });
                }
            }
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
            versaoTabelaHoraria = getVersaoTabelaHorariaEmEdicao();
        } else if (getVersaoTabelaHorariaConfigurada() != null) {
            versaoTabelaHoraria = getVersaoTabelaHorariaConfigurada();
        } else if (getVersaoTabelaHorariaAtiva() != null) {
            versaoTabelaHoraria = getVersaoTabelaHorariaAtiva();
        }
        return versaoTabelaHoraria;
    }

    public VersaoControlador getVersaoControlador() {
        return versaoControlador;
    }

    public void setVersaoControlador(VersaoControlador versaoControlador) {
        this.versaoControlador = versaoControlador;
    }

    public StatusVersao getStatusVersao() {
        if (getVersaoControlador() != null) {
            return getVersaoControlador().getStatusVersao();
        }

        return null;
    }

    public void setStatusVersao(StatusVersao statusVersao) {
        VersaoControlador versao = getVersaoControlador();
        if (versao != null) {
            versao.setStatusVersao(statusVersao);
            versao.update();
        }
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

    @AssertTrue(groups = ControladorFinalizaConfiguracaoCheck.class, message = "O controlador não pode ser finalizado sem o número do SMEE preenchido.")
    public boolean isNumeroSmeePreenchido() {
        return (this.getNumeroSMEE() != null && !this.getNumeroSMEE().isEmpty()) || this.isExclusivoParaTeste();
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

    public StatusVersao getStatusControladorReal() {
        StatusVersao statusVersaoControlador = getStatusVersao();
        if (StatusVersao.CONFIGURADO.equals(statusVersaoControlador) || StatusVersao.SINCRONIZADO.equals(statusVersaoControlador)) {
            TabelaHorario tabela = getTabelaHoraria();
            if (tabela != null) {
                VersaoTabelaHoraria versaoTabelaHoraria = tabela.getVersaoTabelaHoraria();
                if (versaoTabelaHoraria != null) {
                    StatusVersao statusTabelaHoraria = versaoTabelaHoraria.getStatusVersao();
                    if (StatusVersao.EDITANDO.equals(statusTabelaHoraria)) {
                        return StatusVersao.EDITANDO;
                    }
                }
            }

            for (Anel anel : getAneis()) {
                if (anel.isAtivo()) {
                    VersaoPlano versaoPlano = anel.getVersaoPlano();
                    if (versaoPlano != null) {
                        StatusVersao statusPlano = versaoPlano.getStatusVersao();
                        if (StatusVersao.EDITANDO.equals(statusPlano)) {
                            return StatusVersao.EDITANDO;
                        }
                    }
                }
            }
        }

        return getStatusVersao();
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

            setBloqueado(false);
            setPlanosBloqueado(false);
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

            this.update();
        });
    }

    public boolean isConfigurado() {
        if (getEndereco() != null) {
            getEndereco().getAlturaNumerica();
        }

        if (getAneis() != null) {
            getAneisAtivos().stream().forEach(anel -> {
                if (anel.getEndereco() != null) {
                    anel.getEndereco().getAlturaNumerica();
                }
            });
        }

        if (getVersoesTabelasHorarias() != null) {
            getVersoesTabelasHorarias().forEach(versaoTabelaHoraria -> versaoTabelaHoraria.getTabelaHoraria().getVersaoTabelaHoraria());
        }

        return new InfluuntValidator<Controlador>().validate(this, javax.validation.groups.Default.class, ControladorAneisCheck.class, ControladorGruposSemaforicosCheck.class,
            ControladorVerdesConflitantesCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class,
            ControladorTransicoesProibidasCheck.class, ControladorAtrasoDeGrupoCheck.class, ControladorTabelaEntreVerdesCheck.class,
            ControladorAssociacaoDetectoresCheck.class).size() == 0;
    }

    public boolean isPlanoCentralConfigurado() {
        return new InfluuntValidator<Controlador>().validate(this, javax.validation.groups.Default.class,
            PlanosCheck.class, PlanosCentralCheck.class).size() == 0;
    }

    public boolean isPlanoConfigurado() {
        return new InfluuntValidator<Controlador>().validate(this, javax.validation.groups.Default.class,
            PlanosCheck.class).size() == 0;
    }

    public boolean isTabelaHorariaConfigurado() {
        return new InfluuntValidator<Controlador>().validate(this, javax.validation.groups.Default.class,
            TabelaHorariosCheck.class).size() == 0;
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

    public boolean podeClonar() {
        StatusVersao statusVersaoControlador = getVersaoControlador().getStatusVersao();
        return StatusVersao.SINCRONIZADO.equals(statusVersaoControlador) || StatusVersao.CONFIGURADO.equals(statusVersaoControlador);
    }

    public boolean podeEditar(Usuario usuario) {
        VersaoControlador versaoControlador = VersaoControlador.findByControlador(this);
        StatusVersao statusControlador = versaoControlador.getStatusVersao();
        boolean statusOk = !StatusVersao.EM_CONFIGURACAO.equals(statusControlador) && !StatusVersao.EDITANDO.equals(statusControlador);
        return statusOk || usuario.equals(versaoControlador.getUsuario());
    }

    public boolean podeSerEditadoPorUsuario(Usuario usuario) {
        VersaoControlador versao = VersaoControlador.findByControlador(this);
        return versao != null && usuario.equals(versao.getUsuario());
    }

    public VersaoTabelaHoraria getVersaoTabelaHorariaAtivaOuConfigurada() {
        if (getVersaoTabelaHorariaAtiva() != null) {
            return getVersaoTabelaHorariaAtiva();
        }
        return getVersaoTabelaHorariaConfigurada();
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public boolean isPlanosBloqueado() {
        return planosBloqueado;
    }

    public void setPlanosBloqueado(boolean planosBloqueado) {
        this.planosBloqueado = planosBloqueado;
    }

    public Anel findAnelByPosicao(Integer posicao) {
        if (Objects.nonNull(posicao)) {
            return getAneis().stream().filter(anel -> posicao.equals(anel.getPosicao())).findFirst().orElse(null);
        }
        return null;
    }

    public Detector findDetectorByPosicaoETipo(TipoDetector tipoDetector, Integer posicao) {
        return getAneis().stream()
            .map(Anel::getDetectores)
            .flatMap(Collection::stream)
            .filter(detector -> posicao.equals(detector.getPosicao()) && tipoDetector.equals(detector.getTipo()))
            .findFirst()
            .orElse(null);
    }

    public RangeUtils getRangeUtils() {
        return rangeUtils;
    }

    public void setRangeUtils(RangeUtils rangeUtils) {
        this.rangeUtils = rangeUtils;
    }

    public Anel findAnelByDetector(TipoDetector tipoDetector, Integer posicao) {
        Detector detector = findDetectorByPosicaoETipo(tipoDetector, posicao);
        if (detector != null) {
            return detector.getAnel();
        }
        return null;
    }

    public GrupoSemaforico findGrupoSemaforicoByPosicao(Integer posicao) {
        return getAneis().stream()
            .map(Anel::getGruposSemaforicos)
            .flatMap(Collection::stream)
            .filter(grupoSemaforico -> posicao.equals(grupoSemaforico.getPosicao()))
            .findFirst()
            .orElse(null);
    }

    public Anel findAnelByGrupoSemaforico(Integer posicao) {
        GrupoSemaforico grupoSemaforico = findGrupoSemaforicoByPosicao(posicao);
        if (grupoSemaforico != null) {
            return grupoSemaforico.getAnel();
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Controlador controlador = (Controlador) o;

        return id != null ? id.equals(controlador.id) : idJson != null ? idJson.equals(controlador.idJson) : controlador.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public List<Anel> getAneisAtivos() {
        return getAneis().stream().filter(Anel::isAtivo).collect(Collectors.toList());
    }

    public Integer getTotalEstagios() {
        return getAneisAtivos().stream().mapToInt(anel -> anel.getEstagios().size()).sum();
    }

    public Long getTotalDetectoresVeicular() {
        return getAneisAtivos().stream().mapToLong(anel -> anel.getDetectores().stream().filter(detector -> detector.isVeicular()).count()).sum();
    }

    public Long getTotalDetectoresPedestre() {
        return getAneisAtivos().stream().mapToLong(anel -> anel.getDetectores().stream().filter(detector -> detector.isPedestre()).count()).sum();
    }

    @JsonIgnore
    public String getControladorFisicoId() {
        return getVersaoControlador().getControladorFisico().getId().toString();
    }

    @JsonIgnore
    public String getFabricanteOs() {
        return getVersaoControlador().getControladorFisico().getMarca();
    }

    @JsonIgnore
    public String getModeloOs() {
        return getVersaoControlador().getControladorFisico().getModelo();
    }

    @JsonIgnore
    public String getVersaoOs() {
        return getFirmware();
    }

    @JsonIgnore
    public String getFabricanteHardware() {
        return getVersaoControlador().getControladorFisico().getFabricanteHardware();
    }

    @JsonIgnore
    public String getModeloHardware() {
        return getVersaoControlador().getControladorFisico().getModeloHardware();
    }

    @JsonIgnore
    public String getVersaoHardware() {
        return getVersaoControlador().getControladorFisico().getVersaoFirmwareHardware();
    }

    @JsonIgnore
    public DateTime getAtualizacaoVersao() {
        return getVersaoControlador().getControladorFisico().getAtualizacaoVersaoHardware();
    }

    @JsonIgnore
    public String getCentralPublicKey() {
        return this.getVersaoControlador().getControladorFisico().getCentralPublicKey();
    }

    @JsonIgnore
    public String getControladorPrivateKey() {
        return this.getVersaoControlador().getControladorFisico().getControladorPrivateKey();
    }

    @JsonIgnore
    public String getCentralPrivateKey() {
        return this.getVersaoControlador().getControladorFisico().getCentralPrivateKey();
    }

    public Boolean getSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(Boolean sincronizado) {
        this.sincronizado = sincronizado;
    }

    public boolean podeInativar() {
        return (getControladorFisico().getStatusDevice().equals(StatusDevice.ATIVO)
            || getControladorFisico().getStatusDevice().equals(StatusDevice.EM_MANUTENCAO)
            || getControladorFisico().getStatusDevice().equals(StatusDevice.CONFIGURADO));
    }

    public boolean podeColocarEmManutencao() {
        return (getControladorFisico().getStatusDevice().equals(StatusDevice.ATIVO) || getControladorFisico().getStatusDevice().equals(StatusDevice.COM_FALHAS));
    }

    public boolean podeAtivar() {
        return (!getControladorFisico().getStatusDevice().equals(StatusDevice.ATIVO) && !getControladorFisico().getStatusDevice().equals(StatusDevice.NOVO));
    }

    private ControladorFisico getControladorFisico() {
        return getVersaoControlador().getControladorFisico();
    }

    public boolean isExclusivoParaTeste() {
        return exclusivoParaTeste;
    }

    public void setExclusivoParaTeste(boolean exclusivoParaTeste) {
        this.exclusivoParaTeste = exclusivoParaTeste;
    }

    public boolean isOnline() {
        StatusConexaoControlador status = StatusConexaoControlador.ultimoStatus(this.getControladorFisicoId());
        if (status != null) {
            return status.isConectado();
        }
        return false;
    }

    public boolean isAtualizando() {
        return atualizando;
    }

    public void setAtualizando(boolean atualizando) {
        this.atualizando = atualizando;
    }
}
