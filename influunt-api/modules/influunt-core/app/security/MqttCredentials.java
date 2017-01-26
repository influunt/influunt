package security;

public class MqttCredentials {
    protected String username;

    protected String password;

    protected String clientId;

    protected String topic;

    protected Integer access;

    public Integer getAccess() {
        return access;
    }

    public void setAccess(Integer access) {
        this.access = access;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public boolean isSub() {
        return access != null && access == 1;
    }

    public boolean isPub() {
        return access != null && access == 2;
    }
}
