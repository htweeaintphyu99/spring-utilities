package com.example.multitenantusers.config;

import com.example.multitenantusers.tenant.MultitenantDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class MultitenantConfiguration {


    @Value("${defaultTenant}")
    private String defaultTenant;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;


    @Bean
    @ConfigurationProperties
    public DataSource dataSource() throws SQLException {
        Map<Object, Object> resolvedDataSources = new HashMap<>();

        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .url(url)
                .username(username)
                .password(password)
                .build();

        // Fetch all properties
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM database_config");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String driverClassName = resultSet.getString("driver_class_name");
                String url = resultSet.getString("url");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String tenantId = resultSet.getString("tenant_id");

                // Create a new DataSource for each row and store it in a map
                DataSourceBuilder dataSourceBuilder = DataSourceBuilder
                        .create()
                        .username(username)
                        .password(password)
                        .url(url)
                        .driverClassName(driverClassName);

                System.err.println("Datasource url " + url);

                // Store the DataSource in your property source or wherever you need it
                // For example, you can store it in a map
                DataSource tenantDataSource = dataSourceBuilder.build();
                resolvedDataSources.put(tenantId, tenantDataSource);

                // Check if tables exist for the current tenant, if not, create them
                if (!tablesExistForTenant(tenantDataSource)) {
                    createTablesForTenant(tenantDataSource);
                }
            }
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        AbstractRoutingDataSource abstractRoutingDataSource = new MultitenantDataSource();
        abstractRoutingDataSource.setDefaultTargetDataSource(resolvedDataSources.get(defaultTenant));
        abstractRoutingDataSource.setTargetDataSources(resolvedDataSources);

        abstractRoutingDataSource.afterPropertiesSet();
        return abstractRoutingDataSource;
    }

    private boolean tablesExistForTenant(DataSource dataSource) throws SQLException {
        DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
        try (ResultSet rs = metaData.getTables(null, null, "item", null)) {
            return rs.next();
        }
    }

    private void createTablesForTenant(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            // Create the sequence
            try (PreparedStatement sequenceStatement = connection.prepareStatement(
                    "CREATE SEQUENCE IF NOT EXISTS item_seq START 1")) {
                sequenceStatement.execute();
            }
            // Create the table
            try (PreparedStatement tableStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS item (" +
                            "id SERIAL PRIMARY KEY," +
                            "name VARCHAR(255)" +
                            ")")) {
                tableStatement.execute();
            }
        } catch (SQLException e) {
                e.printStackTrace();
            }
        }

}

