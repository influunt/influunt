package models;

import checks.*;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.PrivateOwned;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;

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

    @ManyToMany(mappedBy = "controladores")
    @JoinTable(name = "agrupamentos_controladores", joinColumns = {@JoinColumn(name = "controlador_id")}, inverseJoinColumns = {@JoinColumn(name = "agrupamento_id")})
    private List<Agrupamento> agrupamentos;

    @OneToOne(mappedBy = "controlador", cascade = CascadeType.ALL)
    @Valid
    private Endereco endereco;

    @OneToOne(mappedBy = "controlador", cascade = CascadeType.ALL)
    @Valid
    private TabelaHorario tabelaHoraria;

    // CONFIGURACOES CONTROLADORES

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

        this.criarPossiveisTransicoes();
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

    public List<Agrupamento> getAgrupamentos() {
        return agrupamentos;
    }

    public void setAgrupamentos(List<Agrupamento> agrupamentos) {
        this.agrupamentos = agrupamentos;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public TabelaHorario getTabelaHoraria() {
        return tabelaHoraria;
    }

    public void setTabelaHoraria(TabelaHorario tabelaHoraria) {
        this.tabelaHoraria = tabelaHoraria;
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

    public void addAgrupamento(Agrupamento agrupamento) {
        if (getAgrupamentos() == null) {
            setAgrupamentos(new ArrayList<Agrupamento>());
        }
        getAgrupamentos().add(agrupamento);
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
}
