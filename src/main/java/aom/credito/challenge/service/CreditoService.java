package aom.credito.challenge.service;


import aom.credito.challenge.datasource.Credito;
import aom.credito.challenge.datasource.repository.CreditoRepository;
import aom.credito.challenge.exception.CreditoNotFoundException;
import aom.credito.challenge.model.CreditoResponse;
import aom.credito.challenge.service.helper.CreditoResponseCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditoService {

    private final CreditoResponseCreator creditoResponseCreator;
    private final CreditoRepository creditoRepository;

    public CreditoResponse get(String numeroNfse){
        Credito credito = getCreditoByNumeroNfse(numeroNfse);
        return creditoResponseCreator.create(credito) ;
    }

    public Credito getCreditoByNumeroNfse(String numeroNfse) {
        return creditoRepository.findByNumeroNfse(numeroNfse)
                .orElseThrow(() -> new CreditoNotFoundException("Nfse com número " + numeroNfse + " não encontrada."));
    }

}
