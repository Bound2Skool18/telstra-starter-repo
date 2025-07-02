package au.com.telstra.simcardactivator.service;

import au.com.telstra.simcardactivator.dto.ActuatorRequest;
import au.com.telstra.simcardactivator.dto.ActuatorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ActuatorService {
    private final RestTemplate restTemplate;
    private static final String ACTUATOR_URL = "http://localhost:8444/actuate";

    public ActuatorService() {
        this.restTemplate = new RestTemplate();
    }

    public boolean activateSimCard(String iccid) {
        ActuatorRequest request = new ActuatorRequest(iccid);
        ActuatorResponse response = restTemplate.postForObject(ACTUATOR_URL, request, ActuatorResponse.class);
        
        boolean success = response != null && response.isSuccess();
        System.out.println("Activation for ICCID " + iccid + " was " + (success ? "successful" : "unsuccessful"));
        return success;
    }
}