package aom.credito.challenge.service.helper;

import aom.credito.challenge.datasource.Credito;
import aom.credito.challenge.model.CreditoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CreditoResponseCreatorTest {

    private CreditoResponseCreator creator;

    @BeforeEach
    void setUp() {
        // Usamos una instancia real de ModelMapper para validar el mapeo simple campo a campo
        creator = new CreditoResponseCreator(new ModelMapper());
    }

    @Test
    void create_debeMapearTodosLosCampos() {
        Credito credito = new Credito();
        credito.setId(99L);
        credito.setNumeroCredito("CR-999");
        credito.setNumeroNfse("NF-999");
        credito.setDataConstituicao(LocalDate.of(2024, 2, 20));
        credito.setValorIssqn(new BigDecimal("123.45"));
        credito.setTipoCredito("NORMAL");
        credito.setSimplesNacional(true);
        credito.setAliquota(new BigDecimal("7.00"));
        credito.setValorFaturado(new BigDecimal("3000.00"));
        credito.setValorDeducao(new BigDecimal("150.00"));
        credito.setBaseCalculo(new BigDecimal("2850.00"));

        CreditoResponse response = creator.create(credito);

        assertNotNull(response);
        assertEquals("CR-999", response.getNumeroCredito());
        assertEquals("NF-999", response.getNumeroNfse());
        assertEquals(LocalDate.of(2024, 2, 20), response.getDataConstituicao());
        assertEquals(new BigDecimal("123.45"), response.getValorIssqn());
        assertEquals("NORMAL", response.getTipoCredito());
        assertTrue(response.isSimplesNacional());
        assertEquals(new BigDecimal("7.00"), response.getAliquota());
        assertEquals(new BigDecimal("3000.00"), response.getValorFaturado());
        assertEquals(new BigDecimal("150.00"), response.getValorDeducao());
        assertEquals(new BigDecimal("2850.00"), response.getBaseCalculo());
    }

    @Test
    void create_debeManejarNulosYValoresPorDefecto() {
        // Dejamos varios campos como null y no seteamos el boolean (queda false por defecto)
        Credito credito = new Credito();
        credito.setNumeroCredito(null);
        credito.setNumeroNfse(null);
        credito.setDataConstituicao(null);
        credito.setValorIssqn(null);
        credito.setTipoCredito(null);
        credito.setAliquota(null);
        credito.setValorFaturado(null);
        credito.setValorDeducao(null);
        credito.setBaseCalculo(null);

        CreditoResponse response = creator.create(credito);

        assertNotNull(response);
        assertNull(response.getNumeroCredito());
        assertNull(response.getNumeroNfse());
        assertNull(response.getDataConstituicao());
        assertNull(response.getValorIssqn());
        assertNull(response.getTipoCredito());
        assertFalse(response.isSimplesNacional());
        assertNull(response.getAliquota());
        assertNull(response.getValorFaturado());
        assertNull(response.getValorDeducao());
        assertNull(response.getBaseCalculo());
    }
}
