package aom.credito.challenge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuration class for enabling JPA auditing.
 * <p>
 * This class is responsible for activating JPA auditing functionality,
 * which allows automatic population and tracking of auditing fields such as
 * createdDate, lastModifiedDate, createdBy, and lastModifiedBy in entity classes.
 * </p>
 * <p>
 * By annotating the class with @EnableJpaAuditing, it ensures that Spring Data JPA
 * auditing support is enabled throughout the application.
 * </p>
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {

}
