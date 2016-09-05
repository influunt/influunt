package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;
import play.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.UUID;


@Entity
@Table(name = "imagens")
public class Imagem extends Model implements Serializable {

    private static final long serialVersionUID = 238472872642410060L;

    public static Finder<UUID, Imagem> find = new Finder<UUID, Imagem>(Imagem.class);

    @Id
    private UUID id;

    @Column
    private String idJson;

    @Column
    private String filename;

    @Column
    private String contentType;

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

    public Imagem() {
        super();
        this.idJson = UUID.randomUUID().toString();
    }

    public String getIdJson() {
        return idJson;
    }

    public void setIdJson(String idJson) {
        this.idJson = idJson;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
    public String toString() {
        return "Imagem [id=" + id + ", dataCriacao=" + dataCriacao + ", dataAtualizacao=" + dataAtualizacao + "]";
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public File getPath(File rootPath) {
        File folder = new File(rootPath, "imagens");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return new File(rootPath, "imagens/" + this.getId());
    }

    public File getPath(File rootPath, String version) {
        File folder = new File(rootPath, "imagens");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return new File(rootPath, "imagens/" + this.getId().toString().concat(".").concat(version));
    }

    public boolean apagar(File rootPath) {
        File imagemPath = getPath(rootPath);
        if (this.delete()) {
            try {
                Files.delete(imagemPath.toPath());
            } catch (IOException e) {
                Logger.error(e.getMessage(), e);
            }
            return true;
        }
        return false;
    }
}
