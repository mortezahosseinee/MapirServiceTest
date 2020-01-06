package ir.mapservice.models.requests;

public class SearchRequest {

    private String text;

    public SearchRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
