# 🚀 Guia de Desenvolvimento

Este documento contém informações essenciais para desenvolvedores que desejam contribuir com o projeto.

## 🛠️ Ambiente de Desenvolvimento

### Requisitos
- **Java 17** ou superior
- **Gradle 8.0** ou superior
- **MySQL 8.0** ou superior
- **IDE**: IntelliJ IDEA, Eclipse ou VS Code

### Configuração Inicial

1. **Clone o repositório**
   ```bash
   git clone <url-do-repositorio>
   cd board
   ```

2. **Configure o banco de dados**
   ```sql
   CREATE DATABASE board;
   CREATE USER 'board'@'localhost' IDENTIFIED BY 'board';
   GRANT ALL PRIVILEGES ON board.* TO 'board'@'localhost';
   FLUSH PRIVILEGES;
   ```

3. **Execute as migrações**
   ```bash
   ./gradlew liquibaseUpdate
   ```

4. **Execute os testes**
   ```bash
   ./gradlew test
   ```

## 🏗️ Arquitetura do Projeto

### Estrutura de Pacotes
```
br.com.dio/
├── ui/                    # Interface do usuário
├── service/               # Lógica de negócio
├── persistence/           # Camada de persistência
│   ├── entity/           # Entidades de domínio
│   ├── dao/              # Data Access Objects
│   ├── config/           # Configurações
│   └── migration/        # Migrações Liquibase
├── dto/                  # Data Transfer Objects
├── exception/            # Exceções customizadas
└── util/                 # Utilitários
```

### Padrões Utilizados
- **DAO Pattern**: Para acesso a dados
- **Service Layer**: Para lógica de negócio
- **DTO Pattern**: Para transferência de dados
- **Builder Pattern**: Para construção de objetos complexos
- **Strategy Pattern**: Para migrações de banco

## 📝 Convenções de Código

### Nomenclatura
- **Classes**: PascalCase (ex: `BoardEntity`)
- **Métodos**: camelCase (ex: `createBoard()`)
- **Constantes**: UPPER_SNAKE_CASE (ex: `MAX_COLUMNS`)
- **Variáveis**: camelCase (ex: `boardName`)

### Estrutura de Classes
```java
public class ExampleClass {
    
    // 1. Constantes estáticas
    private static final String CONSTANT = "value";
    
    // 2. Variáveis de instância
    private String instanceVariable;
    
    // 3. Construtor
    public ExampleClass() {
        // inicialização
    }
    
    // 4. Métodos públicos
    public void publicMethod() {
        // implementação
    }
    
    // 5. Métodos privados
    private void privateMethod() {
        // implementação
    }
}
```

### Documentação
- Use **JavaDoc** para métodos públicos
- Documente exceções lançadas
- Inclua exemplos de uso quando apropriado

```java
/**
 * Cria um novo board com as colunas especificadas.
 * 
 * @param name Nome do board (3-100 caracteres)
 * @param columns Lista de colunas do board
 * @return Board criado com ID gerado
 * @throws IllegalArgumentException se o nome for inválido
 * @throws SQLException se houver erro no banco
 */
public BoardEntity createBoard(String name, List<BoardColumnEntity> columns) 
        throws SQLException {
    // implementação
}
```

## 🧪 Testes

### Estrutura de Testes
- **Testes unitários**: `src/test/java/`
- **Testes de integração**: `src/test/java/integration/`
- **Dados de teste**: `src/test/resources/`

### Convenções de Testes
- Use **JUnit 5** com **AssertJ**
- Nomeie os testes de forma descritiva
- Use anotações `@DisplayName` para legibilidade
- Organize os testes com `@BeforeEach` e `@AfterEach`

```java
@Test
@DisplayName("Deve criar board válido com colunas padrão")
void shouldCreateValidBoardWithDefaultColumns() {
    // Given
    BoardEntity board = new BoardEntity();
    board.setName("Board de Teste");
    
    // When
    board.addColumn(initialColumn);
    
    // Then
    assertThat(board.isValid()).isTrue();
    assertThat(board.getBoardColumns()).hasSize(1);
}
```

### Cobertura de Testes
- Execute `./gradlew jacocoTestReport` para gerar relatório
- Mantenha cobertura acima de 80%
- Foque em testes de casos de borda e cenários de erro

## 🔧 Build e Deploy

### Comandos Gradle
```bash
# Compilar
./gradlew compileJava

# Executar testes
./gradlew test

# Gerar JAR
./gradlew jar

# Executar aplicação
./gradlew run

# Limpar build
./gradlew clean

# Verificar qualidade
./gradlew check
```

### Análise de Código
- **Checkstyle**: `./gradlew checkstyleMain`
- **Jacoco**: `./gradlew jacocoTestReport`
- **Dependências**: `./gradlew dependencies`

## 🗄️ Banco de Dados

### Migrações
- Use **Liquibase** para todas as mudanças no banco
- Crie arquivos SQL na pasta `src/main/resources/db/changelog/migrations/`
- Use nomenclatura: `db.changelog-YYYYMMDDHHMM.sql`
- Inclua rollback para todas as migrações

```sql
--liquibase formatted sql
--changeset autor:data
--comment: descrição da mudança

CREATE TABLE EXAMPLE (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

--rollback DROP TABLE EXAMPLE
```

### Entidades
- Use anotações **Bean Validation** para validações
- Implemente métodos `equals()`, `hashCode()` e `toString()`
- Use **Lombok** para reduzir boilerplate
- Documente relacionamentos entre entidades

## 🚨 Tratamento de Erros

### Exceções Customizadas
- Crie exceções específicas para cada tipo de erro
- Herde de `RuntimeException` para erros de negócio
- Herde de `Exception` para erros recuperáveis
- Inclua mensagens descritivas e contexto

```java
public class BoardNotFoundException extends RuntimeException {
    
    public BoardNotFoundException(Long boardId) {
        super(String.format("Board com ID %d não encontrado", boardId));
    }
}
```

### Logging
- Use **SLF4J** para logging
- Configure níveis apropriados para cada ambiente
- Inclua contexto relevante nas mensagens
- Use placeholders para performance

```java
private static final Logger logger = LoggerFactory.getLogger(ExampleClass.class);

logger.info("Board {} criado com {} colunas", boardId, columnCount);
logger.error("Erro ao salvar board: {}", e.getMessage(), e);
```

## 📊 Monitoramento

### Métricas
- **Tempo de resposta** das operações
- **Taxa de erro** por operação
- **Uso de recursos** (CPU, memória, banco)
- **Contadores** de operações

### Logs
- Configure rotação de logs
- Use níveis apropriados (DEBUG, INFO, WARN, ERROR)
- Inclua IDs de transação para rastreamento
- Estruture logs para análise automatizada

## 🔄 Processo de Desenvolvimento

### Workflow Git
1. **Crie uma branch** para sua feature
2. **Desenvolva** com commits frequentes
3. **Execute testes** antes de cada commit
4. **Atualize documentação** quando necessário
5. **Crie Pull Request** com descrição detalhada

### Code Review
- Revise código de outros desenvolvedores
- Verifique cobertura de testes
- Valide padrões de código
- Teste funcionalidades implementadas

### Deploy
- Use **Git tags** para versões
- Automatize build e testes
- Valide em ambiente de staging
- Documente mudanças entre versões

## 🆘 Solução de Problemas

### Problemas Comuns
1. **Erro de conexão com banco**
   - Verifique se MySQL está rodando
   - Valide credenciais em `application.properties`
   - Teste conexão manualmente

2. **Erro de migração**
   - Verifique logs do Liquibase
   - Valide sintaxe SQL
   - Execute migrações manualmente se necessário

3. **Erro de build**
   - Limpe cache: `./gradlew clean`
   - Verifique dependências
   - Valide versão do Java

### Recursos Úteis
- **Logs da aplicação**: `logs/board-app.log`
- **Relatórios de teste**: `build/reports/tests/`
- **Relatórios de cobertura**: `build/reports/jacoco/`
- **Documentação da API**: JavaDoc gerado

## 📚 Recursos Adicionais

### Documentação
- [Java 17 Documentation](https://docs.oracle.com/en/java/javase/17/)
- [Gradle User Guide](https://docs.gradle.org/current/userguide/userguide.html)
- [Liquibase Documentation](https://docs.liquibase.com/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)

### Ferramentas
- **IDE**: IntelliJ IDEA, Eclipse, VS Code
- **Git**: SourceTree, GitKraken, GitHub Desktop
- **Banco**: MySQL Workbench, DBeaver
- **API**: Postman, Insomnia

---

💡 **Dica**: Mantenha este documento atualizado conforme o projeto evolui!
