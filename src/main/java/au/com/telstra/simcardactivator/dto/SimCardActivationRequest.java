package au.com.telstra.simcardactivator.dto;

public class SimCardActivationRequest {
    private String iccid;
    private String customerEmail;

    public SimCardActivationRequest() {
    }

    public SimCardActivationRequest(String iccid, String customerEmail) {
        this.iccid = iccid;
        this.customerEmail = customerEmail;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}