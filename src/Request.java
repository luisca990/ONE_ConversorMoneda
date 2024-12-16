import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Request {
    private String miAPIkey;
    private String miURL;
    private String complementoURL;

    public Request(String complementoURL) {
        this.miURL = "https://v6.exchangerate-api.com/v6/";
        this.complementoURL=complementoURL;
        this.miAPIkey="7313b5ef78c5e470b691c8d8";
    }

    public String getMiURL() {
        return miURL;
    }

    public void setMiURL(String miURL) {
        this.miURL = miURL;
    }

    public HttpResponse<String> ejecutarRequest() throws IOException, InterruptedException {
        String fullURL=miURL+miAPIkey+complementoURL;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullURL))
                .build();
        return client
                .send(request, HttpResponse.BodyHandlers.ofString());
    }

}
