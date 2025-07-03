package au.com.telstra.simcardactivator.controller;

import au.com.telstra.simcardactivator.dto.ActuatorRequest;
import au.com.telstra.simcardactivator.dto.ActuatorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActuateController {

    @PostMapping("/actuate")
    public ActuatorResponse actuate(@RequestBody ActuatorRequest request) {
        // Simulate activation logic (always successful for demo)
        return new ActuatorResponse(true);
    }
}
