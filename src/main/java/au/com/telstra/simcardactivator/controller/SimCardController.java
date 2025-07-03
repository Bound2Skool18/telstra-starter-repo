package au.com.telstra.simcardactivator.controller;

import au.com.telstra.simcardactivator.dto.SimCardActivationRequest;
import au.com.telstra.simcardactivator.model.SimCard;
import au.com.telstra.simcardactivator.repository.SimCardRepository;
import au.com.telstra.simcardactivator.service.ActuatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sim")
public class SimCardController {
    private final ActuatorService actuatorService;
    private final SimCardRepository simCardRepository;

    @Autowired
    public SimCardController(ActuatorService actuatorService, SimCardRepository simCardRepository) {
        this.actuatorService = actuatorService;
        this.simCardRepository = simCardRepository;
    }

    @PostMapping("/activate")
    public ResponseEntity<Boolean> activateSimCard(@RequestBody SimCardActivationRequest request) {
        boolean result = actuatorService.activateSimCard(request.getIccid(), request.getCustomerEmail());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/query")
    public ResponseEntity<SimCardResponse> querySimCard(@RequestParam Long simCardId) {
        return simCardRepository.findById(simCardId)
                .map(simCard -> ResponseEntity.ok(new SimCardResponse(simCard)))
                .orElse(ResponseEntity.notFound().build());
    }

    private static class SimCardResponse {
        private final String iccid;
        private final String customerEmail;
        private final boolean active;

        public SimCardResponse(SimCard simCard) {
            this.iccid = simCard.getIccid();
            this.customerEmail = simCard.getCustomerEmail();
            this.active = simCard.isActive();
        }

        public String getIccid() {
            return iccid;
        }

        public String getCustomerEmail() {
            return customerEmail;
        }

        public boolean isActive() {
            return active;
        }
    }
}