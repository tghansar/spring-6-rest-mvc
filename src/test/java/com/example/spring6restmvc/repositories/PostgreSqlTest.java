package com.example.spring6restmvc.repositories;

import com.example.spring6restmvc.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2025/01/09, Thu, 14:37
 **/

@Testcontainers
@SpringBootTest
@ActiveProfiles("postgres")
public class PostgreSqlTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17.2")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test"); // alternative is to create a bean for the container

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testListBeers() {
        List<Beer> beers = beerRepository.findAll();

        assertThat(beers.size()).isGreaterThan(0);
    }
}

//@Testcontainers
//@SpringBootTest
//@ActiveProfiles("postgres")
//public class PostgreSqlTest {
//
//    @Autowired
//    DataSource dataSource; // bean for db connections
//
//    @Container
//    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17.2")
//            .withDatabaseName("test")
//            .withUsername("test")
//            .withPassword("test"); // alternative is to create a bean for the container
//
//    @DynamicPropertySource // dynamically set the properties for the PostgreSQL container
//    static void postgresSqlProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
//        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
//        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
//    }
//
//    @Autowired
//    BeerRepository beerRepository;
//
//    @Test
//    void testListBeers() {
//        List<Beer> beers = beerRepository.findAll();
//
//        assertThat(beers.size()).isGreaterThan(0);
//    }
//}
