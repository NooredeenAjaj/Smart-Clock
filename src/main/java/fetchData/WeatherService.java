package fetchData;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.JSONObject;
import io.github.cdimascio.dotenv.Dotenv;

public class WeatherService {
    private HttpClient httpClient;
    Dotenv dotenv = Dotenv.load();

    private String apiKey = dotenv.get("API_KEY") ;// Consider moving this to a config file
    private String cityName = "london";

    public WeatherService() {

        httpClient = HttpClient.newHttpClient();
    }

    public void fetchWeather(WeatherCallback callback) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + cityName + "&aqi=no"))
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(responseBody -> {
                    String formattedDate = "";
                    String temperature = "";
                    String cond = "";
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody);
                        System.out.println(jsonObject);

                        JSONObject current = jsonObject.getJSONObject("current");
                        JSONObject condition = current.getJSONObject("condition");


                        cond = condition.getString("text");
                        JSONObject location = jsonObject.getJSONObject("location");
                        



                        double tempC = current.getDouble("temp_c");

                        String localTime = location.getString("localtime");
                        LocalDateTime dateTime = LocalDateTime.parse(localTime, DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm"));
                        formattedDate = dateTime.format(DateTimeFormatter.ofPattern("EEEE, MMMM dd"));

                        temperature = tempC + "°C";
                    } catch (Exception e) {
                        System.out.println("Error parsing weather data: " + e.getMessage());
                    }
                    callback.onWeatherFetched(formattedDate, temperature,cond);

                })
                .exceptionally(e -> {
                    System.out.println("Failed to fetch weather data: " + e.getMessage());
                    return null;
                });
    }

    public interface WeatherCallback {
        void onWeatherFetched(String formattedDate, String temperature , String condition);
    }
}
