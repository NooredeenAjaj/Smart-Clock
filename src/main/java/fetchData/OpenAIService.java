package fetchData;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Consumer;  // Viktigt import
import org.json.JSONObject;
import io.github.cdimascio.dotenv.Dotenv;

public class OpenAIService {
    private HttpClient httpClient;
    private String apiKey = Dotenv.load().get("apiKey");
    public OpenAIService(){
        httpClient = HttpClient.newHttpClient();
    }

    /**
     * Metod som hämtar nya/populära Java-frameworks från OpenAI
     * och sedan anropar en callback-metod (Consumer) när svaret är redo.
     */
    public void fetchNewJavaFrameworks(Consumer<String> onResult){
        // Din prompt (instruktion) till ChatGPT
        String prompt =
                "söka på nätet: Lista ENBART namnen på de tre eller mest populära Java-frameworksen i en enkel punktlista. " +
                        "Inga förklaringar eller beskrivningar.";

        JSONObject json = new JSONObject();
        json.put("model", "gpt-3.5-turbo");
        json.put("messages", new JSONObject[] {
                new JSONObject().put("role", "system").put("content", "You are a helpful assistant."),
                new JSONObject().put("role", "user").put("content", prompt)
        });
        json.put("max_tokens", 300);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(responseBody -> {
                    try {
                        JSONObject responseObject = new JSONObject(responseBody);
                        String responseText = responseObject.getJSONArray("choices")
                                .getJSONObject(0)
                                .getJSONObject("message")
                                .getString("content")
                                .trim();

                        // Skicka vidare svaret till callback (panelens metod)
                        onResult.accept(responseText);

                    } catch (Exception e) {
                        e.printStackTrace();
                        onResult.accept("Fel vid tolkning av OpenAI-svar: " + e.getMessage());
                    }
                })
                .exceptionally(e -> {
                    e.printStackTrace();
                    onResult.accept("Misslyckades med att hämta svar från OpenAI: " + e.getMessage());
                    return null;
                });
    }

    // Om du vill behålla TTS-metoden kan du ha den kvar här,
    // men den är inte nödvändig för att endast visa text på skärmen.
}
