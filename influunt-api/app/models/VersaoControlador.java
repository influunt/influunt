package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.UUID;

/**
* Created by lesiopinheiro on 8/4/16.
*/
public class VersaoControlador extends Model implements Serializable {

   private static final long serialVersionUID = -7484351940720950030L;

   public static Finder<UUID, VersaoControlador> find = new Finder<UUID, VersaoControlador>(VersaoControlador.class);

   @Id
   private UUID id;

   @ManyToOne
   private Controlador controladorOrigem;

   @ManyToOne
   private Controlador controladorEdicao;

   @ManyToOne
   private Usuario usuario;

   @Column
   private StatusControlador statusControlador;

   @Column
   private String descricao;

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

   public UUID getId() {
       return id;
   }

   public void setId(UUID id) {
       this.id = id;
   }

   public Controlador getControladorOrigem() {
       return controladorOrigem;
   }

   public void setOrigem(Controlador controladorOrigem) {
       this.controladorOrigem = controladorOrigem;
   }

   public Controlador getControladorEdicao() {
       return controladorEdicao;
   }

   public void setControladorEdicao(Controlador controladorEdicao) {
       this.controladorEdicao = controladorEdicao;
   }

   public Usuario getUsuario() {
       return usuario;
   }

   public void setUsuario(Usuario usuario) {
       this.usuario = usuario;
   }

   public StatusControlador getStatusControlador() {
       return statusControlador;
   }

   public void setStatusControlador(StatusControlador statusControlador) {
       this.statusControlador = statusControlador;
   }

   public String getDescricao() {
       return descricao;
   }

   public void setDescricao(String descricao) {
       this.descricao = descricao;
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

   @Override
   public boolean equals(Object o) {
       if (this == o) return true;
       if (o == null || getClass() != o.getClass()) return false;

       VersaoControlador that = (VersaoControlador) o;

       return id != null ? id.equals(that.id) : that.id == null;

   }

   @Override
   public int hashCode() {
       return id != null ? id.hashCode() : 0;
   }
}

