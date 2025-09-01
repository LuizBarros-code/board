# 🎯 Sistema de Gerenciamento de Boards Kanban

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/projects/jdk/17/)
[![Gradle](https://img.shields.io/badge/Gradle-8.0+-green.svg)](https://gradle.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)](https://www.mysql.com/)
[![Liquibase](https://img.shields.io/badge/Liquibase-4.29.1-yellow.svg)](https://www.liquibase.org/)

## 📋 Descrição

O **Sistema de Gerenciamento de Boards Kanban** é uma aplicação Java de console que permite gerenciar boards estilo Kanban com funcionalidades completas para controle de tarefas, colunas e fluxos de trabalho.

## ✨ Funcionalidades Principais

### 🎴 Gerenciamento de Boards
- ✅ **Criar boards** com nome personalizado
- ✅ **Configurar colunas** padrão e customizadas
- ✅ **Excluir boards** existentes
- ✅ **Selecionar boards** para trabalho

### 🗂️ Sistema de Colunas
- 🚀 **Coluna Inicial**: Para tarefas novas
- ⏳ **Colunas Pendentes**: Para tarefas em andamento (configuráveis)
- ✅ **Coluna Final**: Para tarefas concluídas
- ❌ **Coluna de Cancelamento**: Para tarefas canceladas

### 🃏 Gerenciamento de Cards
- 📝 **Criar cards** com título e descrição
- 🔄 **Mover cards** entre colunas
- 🚫 **Bloquear/Desbloquear** cards com motivos
- ❌ **Cancelar cards** movendo para coluna de cancelamento
- 📊 **Visualizar status** e histórico de cards

### 🔒 Sistema de Bloqueios
- 📋 **Rastreamento completo** de bloqueios
- ⏰ **Timestamps** de bloqueio/desbloqueio
- 📝 **Motivos** para cada ação
- 📈 **Contadores** de bloqueios por card

## 🏗️ Arquitetura

```
src/
├── main/
│   ├── java/br/com/dio/
│   │   ├── ui/                    # Interface do usuário
│   │   ├── service/               # Lógica de negócio
│   │   ├── persistence/           # Camada de persistência
│   │   │   ├── entity/           # Entidades JPA
│   │   │   ├── dao/              # Data Access Objects
│   │   │   ├── config/           # Configurações
│   │   │   └── migration/        # Migrações Liquibase
│   │   ├── dto/                  # Data Transfer Objects
│   │   ├── exception/            # Exceções customizadas
│   │   └── util/                 # Utilitários
│   └── resources/
│       ├── db/                   # Scripts de banco
│       ├── application.properties # Configurações
│       └── logback.xml          # Configuração de logging
└── test/                         # Testes unitários
```

## 🚀 Tecnologias Utilizadas

- **Java 17** - Linguagem de programação
- **Gradle** - Gerenciador de dependências e build
- **MySQL 8.0+** - Banco de dados
- **Liquibase** - Controle de versão do banco
- **Lombok** - Redução de boilerplate
- **SLF4J + Logback** - Sistema de logging
- **Hibernate Validator** - Validações
- **JUnit 5** - Framework de testes
- **Mockito** - Framework de mocking
- **Checkstyle** - Análise estática de código

## 📋 Pré-requisitos

- **Java 17** ou superior
- **MySQL 8.0** ou superior
- **Gradle 8.0** ou superior

## ⚙️ Configuração

### 1. Clone o repositório
```bash
git clone <url-do-repositorio>
cd board
```

### 2. Configure o banco de dados
```sql
CREATE DATABASE board;
CREATE USER 'board'@'localhost' IDENTIFIED BY 'board';
GRANT ALL PRIVILEGES ON board.* TO 'board'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configure as propriedades
Edite `src/main/resources/application.properties`:
```properties
# Database Configuration
db.url=jdbc:mysql://localhost/board
db.username=board
db.password=board
db.driver=com.mysql.cj.jdbc.Driver
```

### 4. Execute a aplicação
```bash
# Executar com Gradle
./gradlew run

# Ou compilar e executar
./gradlew build
java -jar build/libs/board-1.0.0.jar
```

## 🧪 Executando Testes

```bash
# Executar todos os testes
./gradlew test

# Executar testes com relatório de cobertura
./gradlew jacocoTestReport

# Verificar qualidade do código
./gradlew checkstyleMain
```

## 📊 Funcionalidades Detalhadas

### 🎴 Criação de Board
1. **Nome do board** (3-100 caracteres)
2. **Colunas padrão**:
   - Coluna inicial (ex: "A Fazer")
   - Colunas pendentes customizadas (ex: "Em Análise", "Em Desenvolvimento")
   - Coluna final (ex: "Concluído")
   - Coluna de cancelamento (ex: "Cancelado")

### 🃏 Gerenciamento de Cards
- **Criação**: Título (3-100 chars) + Descrição (max 500 chars)
- **Movimento**: Sequencial entre colunas
- **Bloqueio**: Com motivo e timestamp
- **Histórico**: Rastreamento completo de ações

### 🔒 Sistema de Bloqueios
- **Bloqueio**: Impede movimento do card
- **Desbloqueio**: Permite movimento novamente
- **Auditoria**: Motivo + timestamp para cada ação

## 🎯 Casos de Uso

### 👨‍💼 Gerente de Projeto
- Criar boards para diferentes projetos
- Configurar fluxos de trabalho personalizados
- Monitorar progresso das equipes

### 👨‍💻 Desenvolvedor
- Criar cards para tarefas
- Mover cards conforme progresso
- Bloquear cards quando necessário

### 📊 Analista
- Visualizar status dos projetos
- Analisar gargalos no fluxo
- Rastrear histórico de mudanças

## 🔧 Desenvolvimento

### Estrutura de Pacotes
- **`ui`**: Interface de usuário em console
- **`service`**: Lógica de negócio e regras
- **`persistence`**: Acesso a dados e migrações
- **`entity`**: Modelos de domínio
- **`dto`**: Objetos de transferência de dados
- **`exception`**: Exceções customizadas
- **`util`**: Classes utilitárias

### Padrões Utilizados
- **DAO Pattern**: Para acesso a dados
- **Service Layer**: Para lógica de negócio
- **DTO Pattern**: Para transferência de dados
- **Builder Pattern**: Para construção de objetos complexos
- **Strategy Pattern**: Para migrações de banco

### Validações
- **Bean Validation**: Validações automáticas
- **Validações customizadas**: Regras de negócio específicas
- **Validação de integridade**: Verificações entre entidades

## 📈 Melhorias Futuras

- [ ] **Interface Web**: Dashboard web responsivo
- [ ] **API REST**: Endpoints para integração
- [ ] **Notificações**: Sistema de alertas
- [ ] **Relatórios**: Dashboards e métricas
- [ ] **Integração**: Webhooks e APIs externas
- [ ] **Mobile**: Aplicativo mobile
- [ ] **Multi-tenant**: Suporte a múltiplas organizações
- [ ] **Auditoria**: Logs detalhados de todas as ações

## 🐛 Solução de Problemas

### Erro de Conexão com Banco
```bash
# Verificar se MySQL está rodando
sudo systemctl status mysql

# Verificar credenciais
mysql -u board -p board
```

### Erro de Migração
```bash
# Verificar logs do Liquibase
tail -f liquibase.log

# Executar migrações manualmente
./gradlew liquibaseUpdate
```

### Problemas de Build
```bash
# Limpar cache do Gradle
./gradlew clean

# Verificar dependências
./gradlew dependencies
```

## 🤝 Contribuição

1. **Fork** o projeto
2. **Crie** uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. **Commit** suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. **Push** para a branch (`git push origin feature/AmazingFeature`)
5. **Abra** um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👨‍💻 Autores

- **DIO** - *Desenvolvimento inicial* - [DIO](https://www.dio.me/)

## 🙏 Agradecimentos

- **DIO** pela oportunidade de aprendizado
- **Comunidade Java** pelos recursos e documentação
- **Contribuidores** que ajudaram no desenvolvimento


⭐ **Se este projeto te ajudou, considere dar uma estrela!**
