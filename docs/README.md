# Documentação de instalação e execução

Este documento descreve como verificar a instalação do Java, configurar o ambiente e executar o projeto Spring Boot no Linux, Windows e macOS.

## Requisitos atuais

- Java 25 (toolchain configurado no Gradle)
- Gradle 9.0.0 (via wrapper do projeto em [gradlew](../gradlew) e [gradlew.bat](../gradlew.bat))
- Spring Boot 3.5.6
- Docker e Docker Compose para o banco PostgreSQL 15

## 1. Verificar se o Java está instalado

### Linux/macOS

```bash
java -version
javac -version
```

### Windows (PowerShell)

```powershell
java -version
javac -version
```

Se o comando não for encontrado, instale o JDK 25.

## 2. Instalar o Java 25

### macOS

Com Homebrew, o mais comum é instalar o Temurin:

```bash
brew install --cask temurin
```

### Linux (Ubuntu/Debian)

```bash
sudo apt update
sudo apt install -y openjdk-25-jdk
```

### Windows

Use o instalador do Temurin 25 ou Adoptium e marque a opção de adicionar ao PATH.

## 3. Confirmar o JAVA_HOME

### Linux/macOS

```bash
export JAVA_HOME=/caminho/para/o/jdk-25
export PATH="$JAVA_HOME/bin:$PATH"
```

### Windows (PowerShell)

```powershell
$env:JAVA_HOME="C:\Program Files\Eclipse Adoptium\jdk-25"
$env:Path="$env:JAVA_HOME\bin;$env:Path"
```

## 4. Configurar o banco de dados

O projeto usa variáveis de ambiente para a conexão com o PostgreSQL.

### Iniciar o PostgreSQL com Docker

```bash
docker compose up -d pg_sports
```

### Definir as variáveis de ambiente

#### Linux/macOS

```bash
export DATABASE_URL='jdbc:postgresql://localhost:5432/sport_data_dev_db'
export DATABASE_USERNAME='dev-user'
export DATABASE_PASSWORD='password'
```

#### Windows (PowerShell)

```powershell
$env:DATABASE_URL='jdbc:postgresql://localhost:5432/sport_data_dev_db'
$env:DATABASE_USERNAME='dev-user'
$env:DATABASE_PASSWORD='password'
```

> O container definido em [docker-compose.yml](../docker-compose.yml) usa a imagem PostgreSQL 15 e o banco `sport_data_dev_db`.

## 5. Executar o projeto

### Linux/macOS

```bash
./gradlew bootRun
```

### Windows

```powershell
./gradlew.bat bootRun
```

A aplicação ficará disponível em:

```text
http://localhost:8080
```

## 6. Executar testes

```bash
./gradlew test
```

## 7. Executar no VS Code

Use o arquivo de configuração em [vscode-launch.md](vscode-launch.md) ou o arquivo [.vscode/launch.json](../.vscode/launch.json) para iniciar a aplicação diretamente pelo VS Code.
