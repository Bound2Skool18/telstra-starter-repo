package au.com.telstra.simcardactivator.controller;

import au.com.telstra.simcardactivator.dto.SimCardActivationRequest;
import au.com.telstra.simcardactivator.service.ActuatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sim")
public class SimCardController {
    
    private final ActuatorService actuatorService;

    public SimCardController(ActuatorService actuatorService) {
        this.actuatorService = actuatorService;
    }

    @PostMapping("/activate")
    public ResponseEntity<Boolean> activateSimCard(@RequestBody SimCardActivationRequest request) {
        boolean success = actuatorService.activateSimCard(request.getIccid());
        return ResponseEntity.ok(success);
    }
}