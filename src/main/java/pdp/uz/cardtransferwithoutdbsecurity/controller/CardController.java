package pdp.uz.cardtransferwithoutdbsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pdp.uz.cardtransferwithoutdbsecurity.payload.ApiResponse;
import pdp.uz.cardtransferwithoutdbsecurity.payload.CardDto;
import pdp.uz.cardtransferwithoutdbsecurity.security.JwtProvider;
import pdp.uz.cardtransferwithoutdbsecurity.service.CardServise;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/card")
public class CardController {

    @Autowired
    CardServise cardServise;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody CardDto cardDto, HttpServletRequest request) {
        ApiResponse apiResponse = cardServise.add(cardDto, request);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 209).body(apiResponse.getMessage());
    }

    @GetMapping
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        return ResponseEntity.ok(cardServise.getCards(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id, HttpServletRequest request) {
        return ResponseEntity.ok(cardServise.getOne(id, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @RequestBody CardDto cardDto, HttpServletRequest request) {
        ApiResponse apiResponse = cardServise.edit(id, cardDto, request);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse.getMessage());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id, HttpServletRequest request) {
        ApiResponse apiResponse = cardServise.delete(id, request);
        return ResponseEntity.status(apiResponse.isSuccess() ? 204 : 409).body(apiResponse.getMessage());
    }
}
