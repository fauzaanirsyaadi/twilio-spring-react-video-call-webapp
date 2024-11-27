package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Application is running...");
        System.out.println("Swagger UI: http://localhost:8080/swagger-ui.html");
        System.out.println("Front end WEB: http://localhost:8080/");
        System.out.println("Health Check: http://localhost:8080/api/health");
    }

    @Bean
    public CommandLineRunner initDatabase(DataSource dataSource) {
        return args -> {
            try (Connection connection = dataSource.getConnection()) {
                logger.info("Checking database...");

                // SQL to check if tables exist and have data
                String checkDataSql = "SELECT " +
                        "(SELECT COUNT(*) FROM users) AS user_count, " +
                        "(SELECT COUNT(*) FROM queues) AS queue_count, " +
                        "(SELECT COUNT(*) FROM admins) AS admin_count, " +
                        "(SELECT COUNT(*) FROM calls) AS call_count, " +
                        "(SELECT COUNT(*) FROM logs) AS log_count";

                try (Statement stmt = connection.createStatement();
                     ResultSet rs = stmt.executeQuery(checkDataSql)) {

                    if (rs.next()) {
                        int totalCount = rs.getInt("user_count") + rs.getInt("queue_count") +
                                rs.getInt("admin_count") + rs.getInt("call_count") +
                                rs.getInt("log_count");

                        if (totalCount == 0) {
                            System.out.println("Database is empty. Inserting dummy data...");
                            ScriptUtils.executeSqlScript(connection, new ClassPathResource("data.sql"));
                            System.out.println("Dummy data inserted successfully.");
                        } else {
                            System.out.println("Database already contains data.");
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error connecting to the database. Creating tables...");
                try (Connection connection = dataSource.getConnection()) {
                    ScriptUtils.executeSqlScript(connection, new ClassPathResource("create_tables.sql"));
                    System.out.println("Tables created successfully.");
                    ScriptUtils.executeSqlScript(connection, new ClassPathResource("data.sql"));
                    System.out.println("Dummy data inserted successfully.");
                } catch (SQLException e1) {
                    System.err.println("Error creating tables and inserting data:");
                    logger.error("Error creating tables and inserting data:", e1);
                }
            }
        };
    }
}