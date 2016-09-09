package checks;

public class Erro {

    public String root;

    public String message;

    public String path;

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
                '}';
    }

}