package br.com.dio.persistence.config;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class ConnectionConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(ConnectionConfig.class);
    private static final Properties properties = new Properties();
    
    static {
        try (InputStream input = ConnectionConfig.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            logger.warn("Não foi possível carregar application.properties, usando valores padrão", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        var url = properties.getProperty("db.url", "jdbc:mysql://localhost/board");
        var user = properties.getProperty("db.username", "board");
        var password = properties.getProperty("db.password", "board");
        
        logger.debug("Conectando ao banco de dados: {}", url);
        
        try {
            var connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
            logger.debug("Conexão estabelecida com sucesso");
            return connection;
        } catch (SQLException e) {
            logger.error("Erro ao conectar ao banco de dados: {}", e.getMessage());
            throw e;
        }
    }
    
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                logger.debug("Conexão fechada com sucesso");
            } catch (SQLException e) {
                logger.warn("Erro ao fechar conexão: {}", e.getMessage());
            }
        }
    }
}
