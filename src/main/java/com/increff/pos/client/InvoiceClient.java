package com.increff.pos.client;


import com.increff.pos.model.form.InvoiceDetails;
import com.increff.pos.service.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InvoiceClient {

    @Value("${invoice.url}")
    private String fopUrl;

    public String generateInvoice(InvoiceDetails invoiceDetails) throws ApiException{
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.postForObject(fopUrl ,invoiceDetails, String.class);
            return response;
    }
}
