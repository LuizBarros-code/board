@echo off
REM Script de inicialização do Sistema de Gerenciamento de Boards
REM Autor: DIO
REM Versão: 1.0.0

setlocal enabledelayedexpansion

REM Cores para output (Windows 10+)
set "RED=[91m"
set "GREEN=[92m"
set "YELLOW=[93m"
set "BLUE=[94m"
set "NC=[0m"

REM Função para imprimir mensagens coloridas
:print_message
echo %GREEN%[INFO]%NC% %~1
goto :eof

:print_warning
echo %YELLOW%[WARN]%NC% %~1
goto :eof

:print_error
echo %RED%[ERROR]%NC% %~1
goto :eof

:print_header
echo %BLUE%================================%NC%
echo %BLUE%  Sistema de Gerenciamento de   %NC%
echo %BLUE%        Boards Kanban           %NC%
echo %BLUE%================================%NC%
echo.
goto :eof

REM Função para verificar se o Java está instalado
:check_java
java -version >nul 2>&1
if errorlevel 1 (
    call :print_error "Java não está instalado ou não está no PATH"
    call :print_message "Por favor, instale o Java 17 ou superior"
    exit /b 1
)

for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set JAVA_VERSION=%%g
    set JAVA_VERSION=!JAVA_VERSION:"=!
    for /f "tokens=1 delims=." %%v in ("!JAVA_VERSION!") do set JAVA_MAJOR=%%v
)

if !JAVA_MAJOR! LSS 17 (
    call :print_error "Java 17 ou superior é necessário. Versão atual: !JAVA_MAJOR!"
    exit /b 1
)

call :print_message "Java !JAVA_MAJOR! detectado"
goto :eof

REM Função para verificar se o Gradle está disponível
:check_gradle
if not exist "gradlew.bat" (
    call :print_error "Gradle wrapper não encontrado"
    exit /b 1
)

call :print_message "Gradle wrapper encontrado"
goto :eof

REM Função para executar a aplicação
:run_application
call :print_message "Iniciando aplicação..."

if "%~1"=="--build" (
    call :print_message "Compilando projeto..."
    call gradlew.bat clean build
    if errorlevel 1 (
        call :print_error "Erro na compilação"
        exit /b 1
    )
    call :print_message "Executando aplicação..."
    call gradlew.bat run
) else (
    call :print_message "Executando aplicação..."
    call gradlew.bat run
)
goto :eof

REM Função para executar testes
:run_tests
call :print_message "Executando testes..."
call gradlew.bat test
goto :eof

REM Função para gerar relatório de cobertura
:generate_coverage
call :print_message "Gerando relatório de cobertura..."
call gradlew.bat jacocoTestReport
call :print_message "Relatório gerado em: build\reports\jacoco\test\html\index.html"
goto :eof

REM Função para mostrar ajuda
:show_help
echo Uso: %~nx0 [OPÇÃO]
echo.
echo Opções:
echo   --help, -h     Mostra esta mensagem de ajuda
echo   --build        Compila o projeto antes de executar
echo   --test         Executa os testes
echo   --coverage     Gera relatório de cobertura
echo.
echo Exemplos:
echo   %~nx0              # Executa a aplicação
echo   %~nx0 --build      # Compila e executa
echo   %~nx0 --test       # Executa testes
echo   %~nx0 --coverage   # Gera cobertura
goto :eof

REM Função principal
:main
call :print_header

if "%~1"=="" goto :run_default
if "%~1"=="--help" goto :show_help
if "%~1"=="-h" goto :show_help
if "%~1"=="--test" goto :run_test
if "%~1"=="--coverage" goto :run_coverage
if "%~1"=="--build" goto :run_build

call :print_error "Opção inválida: %~1"
call :show_help
exit /b 1

:run_default
call :check_java
call :check_gradle
call :run_application
goto :end

:run_test
call :check_java
call :check_gradle
call :run_tests
goto :end

:run_coverage
call :check_java
call :check_gradle
call :generate_coverage
goto :end

:run_build
call :check_java
call :check_gradle
call :run_application --build
goto :end

:end
echo.
call :print_message "Aplicação finalizada"
pause
