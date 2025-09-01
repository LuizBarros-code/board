package br.com.dio;

import br.com.dio.persistence.migration.MigrationStrategy;
import br.com.dio.ui.MainMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import static br.com.dio.persistence.config.ConnectionConfig.getConnection;
import static br.com.dio.persistence.config.ConnectionConfig.closeConnection;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Iniciando Sistema de Gerenciamento de Boards");
        
        try {
            // Executar migrações do banco
            executeDatabaseMigrations();
            
            // Iniciar interface do usuário
            startUserInterface();
            
        } catch (Exception e) {
            logger.error("Erro fatal na aplicação: {}", e.getMessage(), e);
            System.err.println("Erro fatal na aplicação: " + e.getMessage());
            System.exit(1);
        }
    }
    
    private static void executeDatabaseMigrations() throws SQLException {
        logger.info("Executando migrações do banco de dados...");
        
        try (var connection = getConnection()) {
            new MigrationStrategy(connection).executeMigration();
            logger.info("Migrações executadas com sucesso");
        } catch (SQLException e) {
            logger.error("Erro ao executar migrações: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    private static void startUserInterface() {
        logger.info("Iniciando interface do usuário...");
        
        try {
            new MainMenu().execute();
        } catch (Exception e) {
            logger.error("Erro na interface do usuário: {}", e.getMessage(), e);
            throw new RuntimeException("Erro na interface do usuário", e);
        }
    }
}
