package aom.credito.challenge.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up the ModelMapper bean.
 * <p>
 * This class defines a Spring configuration that registers a ModelMapper bean
 * to be used for object mapping between different data transfer and entity models.
 * </p>
 * <p>
 * The ModelMapper tool is useful for simplifying the process of transferring data
 * between various layers of an application, such as converting DTOs to entity objects
 * or vice versa.
 * </p>
 */
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
