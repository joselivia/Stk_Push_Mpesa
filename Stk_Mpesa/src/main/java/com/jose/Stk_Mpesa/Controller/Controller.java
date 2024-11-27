package com.jose.Stk_Mpesa.Controller;

import com.jose.Stk_Mpesa.Request.MpesaRequest;
import com.jose.Stk_Mpesa.Service.MpesaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mpesa")
public class Controller {
    @Autowired
    private MpesaServices mpesaServices;

    @PostMapping("/Stk")
    public ResponseEntity<String> initiateStkPush(@RequestBody MpesaRequest mpesaRequest) {
        try {
            String response = mpesaServices.stkPush(mpesaRequest.getPhoneNumber(), mpesaRequest.getAmount(),
                    mpesaRequest.getAccountReference(), mpesaRequest.getTransactionDesc());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}