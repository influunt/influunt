package checks;

import com.avaje.ebean.Model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Erro {

    public String root;
    public String message;
    public String path;
    public String objectId = "";
    public String position = "";

    public Erro(String root, String message, String path, Object object) {
        this(root,message,path);
        try {
            Method getId = object.getClass().getMethod("getId");
            if(getId!=null){
                Object value = getId.invoke(object);
                if(value != null){
                    objectId = value.toString();
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            Method getPosicao = object.getClass().getMethod("getPosicao");
            if(getPosicao!=null){
                Object value = getPosicao.invoke(object);
                if(value != null){
                    objectId = value.toString();
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public Erro(String root, String message, String path) {
        this.root = root;
        this.message = message;
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Erro erro = (Erro) o;

        if (!root.equals(erro.root)) return false;
        if (!message.equals(erro.message)) return false;
        return path.equals(erro.path);

    }

    @Override
    public int hashCode() {
        int result = root.hashCode();
        result = 31 * result + message.hashCode();
        result = 31 * result + path.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Erro{" +
                "root='" + root + '\'' +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                ", objectId='" + objectId + '\'' +
                ", position='" + position + '\'' +
                '}';
    }

}