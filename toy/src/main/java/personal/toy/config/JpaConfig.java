package personal.toy.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
public class JpaConfig {
  @Bean
  public SpringPhysicalNamingStrategy namingStrategy() {
    return new SpringPhysicalNamingStrategy() {
      @Override
      protected Identifier getIdentifier(
          String name, boolean quoted, JdbcEnvironment jdbcEnvironment) {
        return new Identifier(name.toUpperCase(), quoted);
      }
    };
  }
}
