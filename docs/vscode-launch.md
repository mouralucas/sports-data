# Configuração de launch para o VS Code

Adicione o conteúdo abaixo ao arquivo `.vscode/launch.json` para iniciar o projeto Spring Boot diretamente no VS Code.

```json
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "Spring Boot: sports_data",
      "request": "launch",
      "mainClass": "com.rolf.sports_data.SportsDataApplication",
      "projectName": "sports_data",
      "cwd": "${workspaceFolder}",
      "console": "integratedTerminal",
      "env": {
        "DATABASE_URL": "jdbc:postgresql://localhost:5432/sport_dev_db",
        "DATABASE_USERNAME": "dev-user",
        "DATABASE_PASSWORD": "password"
      }
    }
  ]
}
```

Depois disso, abra a aba “Run and Debug” do VS Code e selecione a configuração “Spring Boot: sports_data”.
