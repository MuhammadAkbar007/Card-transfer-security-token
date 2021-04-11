package pdp.uz.cardtransferwithoutdbsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pdp.uz.cardtransferwithoutdbsecurity.payload.ApiResponse;
import pdp.uz.cardtransferwithoutdbsecurity.payload.OutcomeDto;
import pdp.uz.cardtransferwithoutdbsecurity.service.OutcomeService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/outcome")
public class OutcomeController {

    @Autowired
    OutcomeService outcomeService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody OutcomeDto outcomeDto, HttpServletRequest request) {
        ApiResponse apiResponse = outcomeService.add(outcomeDto, request);
        return ResponseEntity.status(apiResponse.isSuccess() ? 204 : 409).body(apiResponse.getMessage());
    }

    @GetMapping
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        return ResponseEntity.ok(outcomeService.getAll(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id, HttpServletRequest request) {
        ApiResponse apiResponse = outcomeService.getOne(id, request);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse.getObject());
    }
}
