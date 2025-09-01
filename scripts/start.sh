#!/bin/bash

# Script de inicialização do Sistema de Gerenciamento de Boards
# Autor: DIO
# Versão: 1.0.0

set -e

# Cores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Função para imprimir mensagens coloridas
print_message() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

print_header() {
    echo -e "${BLUE}================================${NC}"
    echo -e "${BLUE}  Sistema de Gerenciamento de   ${NC}"
    echo -e "${BLUE}        Boards Kanban           ${NC}"
    echo -e "${BLUE}================================${NC}"
    echo
}

# Função para verificar se o Java está instalado
check_java() {
    if ! command -v java &> /dev/null; then
        print_error "Java não está instalado ou não está no PATH"
        print_message "Por favor, instale o Java 17 ou superior"
        exit 1
    fi
    
    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
    if [ "$JAVA_VERSION" -lt 17 ]; then
        print_error "Java 17 ou superior é necessário. Versão atual: $JAVA_VERSION"
        exit 1
    fi
    
    print_message "Java $JAVA_VERSION detectado"
}

# Função para verificar se o Gradle está disponível
check_gradle() {
    if ! command -v ./gradlew &> /dev/null; then
        print_error "Gradle wrapper não encontrado"
        print_message "Execute: chmod +x gradlew"
        exit 1
    fi
    
    print_message "Gradle wrapper encontrado"
}

# Função para verificar se o MySQL está rodando
check_mysql() {
    if ! command -v mysql &> /dev/null; then
        print_warning "MySQL client não encontrado. Verificando conexão via JDBC..."
        return 0
    fi
    
    if mysql -u board -pboard -h localhost -e "SELECT 1" &> /dev/null; then
        print_message "Conexão com MySQL estabelecida"
    else
        print_warning "Não foi possível conectar ao MySQL. Verifique se está rodando."
        print_message "Execute: sudo systemctl start mysql"
    fi
}

# Função para executar a aplicação
run_application() {
    print_message "Iniciando aplicação..."
    
    if [ "$1" = "--build" ]; then
        print_message "Compilando projeto..."
        ./gradlew clean build
        print_message "Executando aplicação..."
        ./gradlew run
    else
        print_message "Executando aplicação..."
        ./gradlew run
    fi
}

# Função para executar testes
run_tests() {
    print_message "Executando testes..."
    ./gradlew test
}

# Função para gerar relatório de cobertura
generate_coverage() {
    print_message "Gerando relatório de cobertura..."
    ./gradlew jacocoTestReport
    print_message "Relatório gerado em: build/reports/jacoco/test/html/index.html"
}

# Função para mostrar ajuda
show_help() {
    echo "Uso: $0 [OPÇÃO]"
    echo
    echo "Opções:"
    echo "  --help, -h     Mostra esta mensagem de ajuda"
    echo "  --build        Compila o projeto antes de executar"
    echo "  --test         Executa os testes"
    echo "  --coverage     Gera relatório de cobertura"
    echo "  --docker       Inicia ambiente Docker (MySQL + phpMyAdmin)"
    echo
    echo "Exemplos:"
    echo "  $0              # Executa a aplicação"
    echo "  $0 --build      # Compila e executa"
    echo "  $0 --test       # Executa testes"
    echo "  $0 --coverage   # Gera cobertura"
}

# Função para iniciar ambiente Docker
start_docker() {
    if ! command -v docker &> /dev/null; then
        print_error "Docker não está instalado"
        exit 1
    fi
    
    if ! command -v docker-compose &> /dev/null; then
        print_error "Docker Compose não está instalado"
        exit 1
    fi
    
    print_message "Iniciando ambiente Docker..."
    docker-compose up -d mysql
    
    print_message "Aguardando MySQL inicializar..."
    sleep 10
    
    print_message "MySQL iniciado em localhost:3306"
    print_message "Para acessar phpMyAdmin: docker-compose --profile tools up phpmyadmin"
}

# Função principal
main() {
    print_header
    
    case "${1:-}" in
        --help|-h)
            show_help
            exit 0
            ;;
        --test)
            check_java
            check_gradle
            run_tests
            exit 0
            ;;
        --coverage)
            check_java
            check_gradle
            generate_coverage
            exit 0
            ;;
        --docker)
            start_docker
            exit 0
            ;;
        --build|"")
            check_java
            check_gradle
            check_mysql
            run_application "$1"
            ;;
        *)
            print_error "Opção inválida: $1"
            show_help
            exit 1
            ;;
    esac
}

# Executar função principal
main "$@"
