package services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientManager {

    private static final Logger log = LoggerFactory.getLogger(HttpClientManager.class);

    private static final String BASE_URL = "http://localhost:8080";

    private static HttpClient createClient() {
        return HttpClient.newHttpClient();
    }

    public static void post(String uri, String body) {
        HttpClient client = createClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + uri))
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (ConnectException e) {
            log.error("Echec de la communication avec le serveur : Serveur introuvable");
        } catch (IOException | InterruptedException e) {
            log.error("Echec de la communication avec le serveur : {}", e.getLocalizedMessage());
        }
    }
}
