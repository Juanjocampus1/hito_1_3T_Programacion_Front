package HTTP.Response;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class GetRequest {
    private static final Logger LOGGER = Logger.getLogger(GetRequest.class.getName());

    // Método para realizar una solicitud GET
    public void sendGetRequest() {
        try {
            // Crea el cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Crea la solicitud GET
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8090/api/crud/findAll")) // Endpoint para la solicitud GET
                    .GET() // Define que es una solicitud GET
                    .build();

            // Envía la solicitud y recibe la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Muestra el código de estado y el cuerpo de la respuesta
            LOGGER.info("Código de estado: " + response.statusCode());
            LOGGER.info("Respuesta de la API: " + response.body());

        } catch (Exception e) {
            LOGGER.severe("Error al enviar solicitud GET: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
