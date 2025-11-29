package aom.credito.challenge.controller;

import aom.credito.challenge.exception.CreditoNotFoundException;
import aom.credito.challenge.exception.GlobalExceptionHandler;
import aom.credito.challenge.model.CreditoResponse;
import aom.credito.challenge.service.CreditoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CreditoController.class)
@Import(GlobalExceptionHandler.class)
class CreditoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditoService creditoService;

    @Test
    @DisplayName("GET /api/creditos/credito/{numeroCredito} debe devolver 200 con el crédito mapeado")
    void getCreditoByNumeroCredito_ok() throws Exception {
        String numeroCredito = "CR-ABC";
        CreditoResponse response = buildResponse(numeroCredito, "NF-123");

        when(creditoService.getCreditoByNumeroCredito(numeroCredito)).thenReturn(response);

        mockMvc.perform(get("/api/creditos/credito/{numero}", numeroCredito)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.numeroCredito").value("CR-ABC"))
                .andExpect(jsonPath("$.numeroNfse").value("NF-123"))
                .andExpect(jsonPath("$.simplesNacional").value(true))
                .andExpect(jsonPath("$.valorIssqn").value(100.5))
                .andExpect(jsonPath("$.aliquota").value(5.0));
    }

    @Test
    @DisplayName("GET /api/creditos/{numeroNfse} debe devolver 200 con lista de créditos")
    void getAllByNumeroNfse_ok() throws Exception {
        String nfse = "NF-XYZ";
        List<CreditoResponse> lista = List.of(
                buildResponse("CR-1", nfse),
                buildResponse("CR-2", nfse)
        );

        when(creditoService.getAllByNumeroNfseOrderByIdAsc(nfse)).thenReturn(lista);

        mockMvc.perform(get("/api/creditos/{nfse}", nfse)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].numeroCredito").value("CR-1"))
                .andExpect(jsonPath("$[0].numeroNfse").value(nfse))
                .andExpect(jsonPath("$[1].numeroCredito").value("CR-2"))
                .andExpect(jsonPath("$[1].numeroNfse").value(nfse));
    }

    @Test
    @DisplayName("GET /api/creditos/credito/{numeroCredito} cuando no existe debe devolver 404 y ErrorResponse")
    void getCreditoByNumeroCredito_notFound() throws Exception {
        String numeroCredito = "CR-NOPE";
        when(creditoService.getCreditoByNumeroCredito(numeroCredito))
                .thenThrow(new CreditoNotFoundException("Credito com número CR-NOPE não encontrada."));

        mockMvc.perform(get("/api/creditos/credito/{numero}", numeroCredito)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", containsString("CR-NOPE")))
                .andExpect(jsonPath("$.statusCode").value(404));
    }

    private CreditoResponse buildResponse(String numeroCredito, String numeroNfse) {
        CreditoResponse r = new CreditoResponse();
        r.setNumeroCredito(numeroCredito);
        r.setNumeroNfse(numeroNfse);
        r.setDataConstituicao(LocalDate.of(2024, 1, 10));
        r.setValorIssqn(new BigDecimal("100.50"));
        r.setTipoCredito("TIPO");
        r.setSimplesNacional(true);
        r.setAliquota(new BigDecimal("5.0"));
        r.setValorFaturado(new BigDecimal("2000.00"));
        r.setValorDeducao(new BigDecimal("100.00"));
        r.setBaseCalculo(new BigDecimal("1900.00"));
        return r;
    }
}
