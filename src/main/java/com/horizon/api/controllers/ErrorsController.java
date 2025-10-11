// package com.horizon.api.controllers;

// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.web.error.ErrorAttributeOptions;
// import org.springframework.boot.web.servlet.error.ErrorAttributes;
// import org.springframework.boot.web.servlet.error.ErrorController;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.context.request.WebRequest;

// @RestController
// public class ErrorsController implements ErrorController {

//     @Autowired
//     private ErrorAttributes errorAttributes;

//     @GetMapping("/error")
//     public ResponseEntity<Map<String, Object>> handleError(WebRequest webRequest) {
//         Map<String, Object> attributes = errorAttributes.getErrorAttributes(
//                 webRequest, ErrorAttributeOptions.defaults());

//         int status = (int) attributes.getOrDefault("status", 500);

//         if(status == 404) {
//             attributes.put("message", "Route not found");
//         }

//         return ResponseEntity.status(status).body(attributes);
//     }
// }

