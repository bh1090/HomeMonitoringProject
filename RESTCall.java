package eecs1021;

// importing the required libraries
import java.io.InputStream;
import com.mashape.unirest.http.*;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

// link to the github files:
// https://github.com/Kong/unirest-java

// This class allows me to upload my arduino sensor data to thingspeak IOT.
// This class was partially modified by me and was created by Kong (Github Link above).
public class RESTCall implements Callback<JsonNode>{

// This method below was modified by me to allow me to send data Light and sound data to things-speak for this project.
    public void sendDataOverRest(double lightValue, double soundValue) {
//
        Unirest.post("https://api.thingspeak.com/update.json")
                .header("accept", "application/json")
                .field("api_key", "QCE3GL51VV5ZWULG")
                .field("field1",lightValue)
                .field("field2", soundValue)
                .asJsonAsync(this);
    }

    // methods below implemented by Kong (link above).
    @Override
    public void cancelled() {
        // TODO Auto-generated method stub
        System.out.println("The request has been cancelled");
    }

    // methods below implemented by Kong (link above).
    @Override // derived from gitbub. (the link)
    public void completed(HttpResponse<JsonNode> response) {
        // TODO Auto-generated method stub
        int code = response.getStatus();
        //  Map<String, String> headers = response.getHeaders();
        JsonNode body =response.getBody();
        InputStream rawBody = response.getRawBody();

        System.out.println(code);
        System.out.println(body);
        System.out.println(rawBody);

    }
    // methods below implemented by Kong (link above).
    @Override
    public void failed(UnirestException arg0) {
        // TODO Auto-generated method stub
        System.out.println("The request has failed");
    }

}
