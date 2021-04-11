package pdp.uz.cardtransferwithoutdbsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pdp.uz.cardtransferwithoutdbsecurity.payload.ApiResponse;
import pdp.uz.cardtransferwithoutdbsecurity.service.IncomeService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/income")
public class IncomeController {

    @Autowired
    IncomeService incomeService;

    @GetMapping
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        return ResponseEntity.ok(incomeService.getAll(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id, HttpServletRequest request) {
        ApiResponse apiResponse = incomeService.getOne(id, request);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse.getObject());
    }
}
