package br.jus.stj.skeletoApi.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("br.jus.stj.skeletoApi.domain")
@EnableJpaRepositories("br.jus.stj.skeletoApi.repos")
@EnableTransactionManagement
public class DomainConfig {
}
