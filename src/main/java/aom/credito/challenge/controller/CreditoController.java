package aom.credito.challenge.controller;

import aom.credito.challenge.model.CreditoResponse;
import aom.credito.challenge.service.CreditoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/creditos")
@RequiredArgsConstructor
public class CreditoController {

    private final CreditoService creditoService;

    @GetMapping("/{numeroNfse}")
    public ResponseEntity<CreditoResponse> create(@PathVariable(name = "numeroNfse") String numeroNfse) {
        CreditoResponse response = creditoService.get(numeroNfse);
        return ResponseEntity.ok(response);
    }

}
