package aom.credito.challenge.service;


import aom.credito.challenge.datasource.Credito;
import aom.credito.challenge.datasource.repository.CreditoRepository;
import aom.credito.challenge.exception.CreditoNotFoundException;
import aom.credito.challenge.model.CreditoResponse;
import aom.credito.challenge.service.helper.CreditoResponseCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditoService {

    private final CreditoResponseCreator creditoResponseCreator;
    private final CreditoRepository creditoRepository;
    private final KafkaProducerService kafkaProducerService;

    public CreditoResponse getCreditoByNumeroCredito(String numeroCredito){
        CreditoResponse response = creditoRepository.findByNumeroCredito(numeroCredito)
                .map(creditoResponseCreator::create)
                .orElseThrow(() -> new CreditoNotFoundException("Credito com número " + numeroCredito + " não encontrada."));

        // publish event (numeroNfse unknown in this lookup -> null)
        kafkaProducerService.publishConsultaEvent(null, numeroCredito);

        return response;
    }

    public List<CreditoResponse> getAllByNumeroNfseOrderByIdAsc(String numeroNfse){
        List<CreditoResponse> list = creditoRepository.findAllByNumeroNfseOrderByIdAsc(numeroNfse)
                .stream()
                .map(creditoResponseCreator::create)
                .toList();

        // publish event summarizing lookup
        kafkaProducerService.publishConsultaEvent(numeroNfse, null);

        return list;
    }

}
