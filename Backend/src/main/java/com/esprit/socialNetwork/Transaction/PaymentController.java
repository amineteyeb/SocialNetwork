package com.esprit.socialNetwork.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymeeService paymeeService;

    @Autowired
    public PaymentController(PaymeeService paymeeService) {
        this.paymeeService = paymeeService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestBody PaymeePaymentRequest paymentRequest) { {
        // Logic to create payment and return payment token
        String paymentToken = String.valueOf(paymeeService.createPayment(paymentRequest));
        return ResponseEntity.ok(paymentToken);
    }


    /*@GetMapping("/{token}/check")
    public ResponseEntity<PaymeePaymentResponse> checkPaymentStatus(@PathVariable String token) {
        PaymeePaymentResponse response = paymeeService.checkPaymentStatus(token);
        return ResponseEntity.ok(response);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleJsonParsingError(HttpMessageNotReadableException ex) {
        // Log the error details
        System.out.println("Error parsing JSON request: {}"+ex.getMessage());

        // Create an error response object with a descriptive message
        ErrorResponse errorResponse = new ErrorResponse() {
            @Override
            public HttpStatusCode getStatusCode() {
                return null;
            }

            @Override
            public ProblemDetail getBody() {
                return null;
            }
        };

        // Return the error response to the client
        return ResponseEntity.badRequest().body(errorResponse);
    }*/
}}
// JacksonConfig.java

