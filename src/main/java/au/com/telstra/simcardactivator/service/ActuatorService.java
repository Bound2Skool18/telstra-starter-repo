package au.com.telstra.simcardactivator.service;

import au.com.telstra.simcardactivator.dto.ActuatorRequest;
import au.com.telstra.simcardactivator.dto.ActuatorResponse;
import au.com.telstra.simcardactivator.model.SimCard;
import au.com.telstra.simcardactivator.repository.SimCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ActuatorService {
    private final RestTemplate restTemplate;
    private final SimCardRepository simCardRepository;
    private static final String ACTUATOR_URL = "http://localhost:8444/actuate";

    @Autowired
    public ActuatorService(SimCardRepository simCardRepository) {
        this.restTemplate = new RestTemplate();
        this.simCardRepository = simCardRepository;
    }

    public boolean activateSimCard(String iccid, String customerEmail) {
        ActuatorRequest request = new ActuatorRequest(iccid);
        
        ActuatorResponse response = restTemplate.postForObject(
            ACTUATOR_URL,
            request,
            ActuatorResponse.class
        );

        boolean success = response != null && response.isSuccess();

        SimCard simCard = new SimCard(iccid, customerEmail, success);
        simCardRepository.save(simCard);

        return success;
    }
}