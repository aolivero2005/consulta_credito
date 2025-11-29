package aom.credito.challenge.service;

import aom.credito.challenge.datasource.Credito;
import aom.credito.challenge.datasource.repository.CreditoRepository;
import aom.credito.challenge.exception.CreditoNotFoundException;
import aom.credito.challenge.model.CreditoResponse;
import aom.credito.challenge.service.helper.CreditoResponseCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditoServiceTest {

    @Mock
    private CreditoRepository creditoRepository;

    @Mock
    private CreditoResponseCreator creditoResponseCreator;

    @InjectMocks
    private CreditoService creditoService;

    @Test
    void getCreditoByNumeroCredito_debeRetornarRespuestaCuandoExiste() {
        // Arrange
        String numeroCredito = "CR-123";
        Credito credito = buildCredito(1L, numeroCredito, "NF-1");
        CreditoResponse esperado = buildResponse(numeroCredito, "NF-1");

        when(creditoRepository.findByNumeroCredito(numeroCredito)).thenReturn(Optional.of(credito));
        when(creditoResponseCreator.create(credito)).thenReturn(esperado);

        // Act
        CreditoResponse actual = creditoService.getCreditoByNumeroCredito(numeroCredito);

        // Assert
        assertNotNull(actual);
        assertEquals(esperado.getNumeroCredito(), actual.getNumeroCredito());
        assertEquals(esperado.getNumeroNfse(), actual.getNumeroNfse());
        verify(creditoRepository).findByNumeroCredito(numeroCredito);
        verify(creditoResponseCreator).create(credito);
        verifyNoMoreInteractions(creditoRepository, creditoResponseCreator);
    }

    @Test
    void getCreditoByNumeroCredito_debeLanzarExcepcionCuandoNoExiste() {
        // Arrange
        String numeroCredito = "CR-404";
        when(creditoRepository.findByNumeroCredito(numeroCredito)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CreditoNotFoundException.class, () ->
                creditoService.getCreditoByNumeroCredito(numeroCredito)
        );

        verify(creditoRepository).findByNumeroCredito(numeroCredito);
        verifyNoInteractions(creditoResponseCreator);
    }

    @Test
    void getAllByNumeroNfseOrderByIdAsc_debeRetornarListaMapeada() {
        // Arrange
        String nfse = "NF-777";
        Credito c1 = buildCredito(1L, "CR-1", nfse);
        Credito c2 = buildCredito(2L, "CR-2", nfse);
        List<Credito> encontrados = List.of(c1, c2);
        CreditoResponse r1 = buildResponse("CR-1", nfse);
        CreditoResponse r2 = buildResponse("CR-2", nfse);

        when(creditoRepository.findAllByNumeroNfseOrderByIdAsc(nfse)).thenReturn(encontrados);
        when(creditoResponseCreator.create(c1)).thenReturn(r1);
        when(creditoResponseCreator.create(c2)).thenReturn(r2);

        // Act
        List<CreditoResponse> result = creditoService.getAllByNumeroNfseOrderByIdAsc(nfse);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("CR-1", result.get(0).getNumeroCredito());
        assertEquals("CR-2", result.get(1).getNumeroCredito());
        verify(creditoRepository).findAllByNumeroNfseOrderByIdAsc(nfse);
        verify(creditoResponseCreator).create(c1);
        verify(creditoResponseCreator).create(c2);
        verifyNoMoreInteractions(creditoRepository, creditoResponseCreator);
    }

    private Credito buildCredito(Long id, String numeroCredito, String numeroNfse) {
        Credito c = new Credito();
        c.setId(id);
        c.setNumeroCredito(numeroCredito);
        c.setNumeroNfse(numeroNfse);
        c.setDataConstituicao(LocalDate.of(2024, 1, 10));
        c.setValorIssqn(new BigDecimal("100.50"));
        c.setTipoCredito("TIPO");
        c.setSimplesNacional(true);
        c.setAliquota(new BigDecimal("5.00"));
        c.setValorFaturado(new BigDecimal("2000.00"));
        c.setValorDeducao(new BigDecimal("100.00"));
        c.setBaseCalculo(new BigDecimal("1900.00"));
        return c;
    }

    private CreditoResponse buildResponse(String numeroCredito, String numeroNfse) {
        CreditoResponse r = new CreditoResponse();
        r.setNumeroCredito(numeroCredito);
        r.setNumeroNfse(numeroNfse);
        r.setDataConstituicao(LocalDate.of(2024, 1, 10));
        r.setValorIssqn(new BigDecimal("100.50"));
        r.setTipoCredito("TIPO");
        r.setSimplesNacional(true);
        r.setAliquota(new BigDecimal("5.00"));
        r.setValorFaturado(new BigDecimal("2000.00"));
        r.setValorDeducao(new BigDecimal("100.00"));
        r.setBaseCalculo(new BigDecimal("1900.00"));
        return r;
    }
}
