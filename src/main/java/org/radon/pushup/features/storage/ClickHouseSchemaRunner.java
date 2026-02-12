package org.radon.pushup.features.storage;

import org.radon.pushup.shared.aop.errorHandling.RedisCacheErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClickHouseSchemaRunner implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;
    private final List<ClickHouseEntity> chEntities;

    public ClickHouseSchemaRunner(@Qualifier("clickHouseJdbcTemplate")JdbcTemplate jdbcTemplate, List<ClickHouseEntity> chEntities) {
        this.jdbcTemplate = jdbcTemplate;
        this.chEntities = chEntities;
    }

    @Override
    public void run(String... args) throws Exception {


        chEntities.forEach(it -> jdbcTemplate.execute(it.initialize()));

        System.out.println("ClickHouse tables initialized");
        LoggerFactory.getLogger(this.getClass()).info("ClickHouse tables initialized...");

    }


}
