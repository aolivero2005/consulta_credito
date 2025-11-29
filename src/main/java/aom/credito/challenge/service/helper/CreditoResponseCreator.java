package aom.credito.challenge.service.helper;

import aom.credito.challenge.datasource.Credito;
import aom.credito.challenge.model.CreditoResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditoResponseCreator {

    private final ModelMapper modelMapper;

    public CreditoResponse create(Credito credito) {
        return modelMapper.map(credito, CreditoResponse.class);
    }

}
