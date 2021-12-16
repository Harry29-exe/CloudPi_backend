package com.cloudpi.cloudpi_backend.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Table;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TestDbService {

    // Autowired via constructor based injected (using Lombok for generated constructor)
    private final EntityManager entityManager;
    private List<String> tableNames;

    @Transactional
    public void resetDatabase() {
//        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        for (String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName + " CASCADE").executeUpdate();
        }

//        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

    @PostConstruct
    void afterPropertiesSet() {
        tableNames = entityManager.getMetamodel().getEntities().stream()
                .filter(entityType -> entityType.getJavaType().getAnnotation(Table.class) != null)
                .map(entityType -> entityType.getJavaType().getAnnotation(Table.class))
                .map(this::convertToTableName).toList();
    }

    private String convertToTableName(Table table) {
        String schema = table.schema();
        String tableName = table.name();

        String convertedSchema = StringUtils.hasText(schema) ? schema.toLowerCase() + "." : "";
        String convertedTableName = tableName.replaceAll("([a-z])([A-Z])", "$1_$2");

        return convertedSchema + convertedTableName;
    }
}