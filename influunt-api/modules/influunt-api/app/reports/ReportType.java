package reports;

/**
 * Created by lesiopinheiro on 05/10/16.
 */
public enum ReportType {

    PDF("application/pdf"),
    CSV("text/csv");

    private final String contentType;

    ReportType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return this.contentType;
    }
}
