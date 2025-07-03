package stepDefinitions;

import au.com.telstra.simcardactivator.SimCardActivator;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;
import io.cucumber.java.en.*;
import org.springframework.http.*;
import java.util.Map;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {
    @Autowired
    private TestRestTemplate restTemplate;

    private String iccid;
    private String customerEmail;
    private ResponseEntity<Boolean> activationResponse;
    private ResponseEntity<Map> queryResponse;

    @Given("I have a SIM card with ICCID {string} and customer email {string}")
    public void i_have_a_sim_card_with_iccid_and_customer_email(String iccid, String email) {
        this.iccid = iccid;
        this.customerEmail = email;
    }

    @When("I activate the SIM card")
    public void i_activate_the_sim_card() {
        String url = "http://localhost:8080/api/sim/activate";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = String.format("{\"iccid\":\"%s\",\"customerEmail\":\"%s\"}", iccid, customerEmail);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        activationResponse = restTemplate.postForEntity(url, request, Boolean.class);
    }

    @Then("the activation should be successful")
    public void the_activation_should_be_successful() {
        assert activationResponse.getStatusCode() == HttpStatus.OK;
        assert Boolean.TRUE.equals(activationResponse.getBody());
    }

    @Then("the activation should fail")
    public void the_activation_should_fail() {
        assert activationResponse.getStatusCode() == HttpStatus.OK;
        assert Boolean.FALSE.equals(activationResponse.getBody());
    }

    @And("the SIM card record with ID {int} should be active")
    public void the_sim_card_record_with_id_should_be_active(Integer id) {
        String url = "http://localhost:8080/api/sim/query?simCardId=" + id;
        queryResponse = restTemplate.getForEntity(url, Map.class);
        assert queryResponse.getStatusCode() == HttpStatus.OK;
        Map body = queryResponse.getBody();
        assert body != null;
        assert Boolean.TRUE.equals(body.get("active"));
        assert iccid.equals(body.get("iccid"));
        assert customerEmail.equals(body.get("customerEmail"));
    }

    @And("the SIM card record with ID {int} should not be active")
    public void the_sim_card_record_with_id_should_not_be_active(Integer id) {
        String url = "http://localhost:8080/api/sim/query?simCardId=" + id;
        queryResponse = restTemplate.getForEntity(url, Map.class);
        assert queryResponse.getStatusCode() == HttpStatus.OK;
        Map body = queryResponse.getBody();
        assert body != null;
        assert Boolean.FALSE.equals(body.get("active"));
        assert iccid.equals(body.get("iccid"));
        assert customerEmail.equals(body.get("customerEmail"));
    }
}