package aom.credito.challenge.controller;

import aom.credito.challenge.model.CreditoResponse;
import aom.credito.challenge.service.CreditoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/creditos")
@RequiredArgsConstructor
public class CreditoController {

    private final CreditoService creditoService;

    @GetMapping("/credito/{numeroCredito}")
    public ResponseEntity<CreditoResponse> getCreditoByNumeroCredito(@PathVariable(name = "numeroCredito") String numeroCredito) {
        return ResponseEntity.ok(creditoService.getCreditoByNumeroCredito(numeroCredito));
    }

    @GetMapping("/{numeroNfse}")
    public ResponseEntity<List<CreditoResponse>> getAllByNumeroNfseOrderByIdAsc(@PathVariable(name = "numeroNfse") String numeroNfse) {
        return ResponseEntity.ok(creditoService.getAllByNumeroNfseOrderByIdAsc(numeroNfse));
    }

}
