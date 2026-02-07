package org.radon.pushup.shared.aop.config.datasources;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.jpa.autoconfigure.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class JpaBuilderConfig {

    @Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder(
            JpaProperties jpaProperties,
            ObjectProvider<PersistenceUnitManager> persistenceUnitManager) {

        return new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(),
                dataSource -> jpaProperties.getProperties(),
                persistenceUnitManager.getIfAvailable()
        );
    }
}
