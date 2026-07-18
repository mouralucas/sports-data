# API

# Parte 1 — API Overview and Conventions

## 1. Objetivo

Este documento define o contrato público da Sports Data API.

Seu objetivo é documentar todos os recursos disponibilizados pela aplicação, bem como os formatos de requisição e resposta, convenções adotadas, códigos de erro e regras de utilização.

Esta documentação é independente da implementação interna da aplicação.

Alterações em controllers, services, DTOs ou entidades não devem impactar este documento, desde que o contrato da API permaneça inalterado.

---

# 2. Objetivos da API

A API deve permitir:

* cadastrar esportes;
* cadastrar organizações esportivas;
* cadastrar participantes;
* configurar competições;
* configurar temporadas;
* configurar formatos de competição;
* registrar eventos esportivos;
* registrar ações ocorridas durante um evento;
* calcular classificações;
* consultar resultados;
* executar simulações;
* importar dados históricos;
* acompanhar competições em tempo real.

---

# 3. Arquitetura

A API segue os princípios REST, adotando convenções próprias para manter consistência entre todos os recursos.

Cada endpoint representa um recurso de domínio da aplicação.

Exemplos:

```text
Sport

Competition

CompetitionEdition

CompetitionEvent

Classification
```

---

# 4. Versionamento

Todas as rotas serão versionadas.

Formato:

```text
/api/v1
```

Exemplos:

```http
GET /api/v1/sports

GET /api/v1/sport/1

POST /api/v1/sport
```

Alterações incompatíveis deverão ser introduzidas em uma nova versão da API.

---

# 5. Content Type

Todas as requisições e respostas utilizam JSON.

Request

```text
Content-Type: application/json
```

Response

```text
Content-Type: application/json
```

---

# 6. Character Encoding

Toda a API utiliza UTF-8.

---

# 7. Date and Time

Datas seguem o padrão ISO-8601.

Exemplos:

Data:

```json
"2026-06-15"
```

Data e hora:

```json
"2026-06-15T19:30:00Z"
```

Sempre que possível, horários deverão ser armazenados em UTC.

---

# 8. Identificadores

Todos os recursos possuem um identificador único do tipo `Long`.

Exemplo:

```json
{
    "id": 15
}
```

Identificadores externos poderão ser armazenados separadamente quando necessário.

---

# 9. Convenção de Nomenclatura

O projeto adota convenções distintas para banco de dados e API.

## Banco de Dados

As tabelas utilizam nomes no singular, pois representam um domínio da aplicação.

Exemplos:

```text
sport

competition

participant

organization
```

## API

A API diferencia operações sobre coleções e operações sobre um recurso específico.

### Coleções

Endpoints de coleção utilizam nomes no plural.

Sempre retornam uma lista de objetos.

Todos os filtros devem ser enviados através de query parameters.

Exemplos:

```http
GET /sports

GET /sports?name=Football

GET /sports?active=true

GET /competitions?organizationId=5

GET /participants?sportId=2
```

Não devem existir endpoints específicos para cada tipo de filtro.

Toda filtragem ocorre através dos parâmetros da consulta.

---

### Recurso Individual

Endpoints de recurso individual utilizam o nome no singular.

Sempre retornam exatamente um objeto.

O identificador faz parte da URL.

Exemplos:

```http
GET /sport/15

GET /competition/8

GET /participant/120
```

Caso o recurso não exista, a API deverá retornar **404 Not Found**.

---

# 10. Estratégia de Consulta

A URL identifica o recurso consultado.

Os query parameters modificam apenas a consulta sobre coleções.

Exemplos:

Coleção:

```http
GET /competitions
```

Coleção filtrada:

```http
GET /competitions?organizationId=5
```

Coleção com múltiplos filtros:

```http
GET /competitions?organizationId=5&sportId=1&status=ACTIVE
```

Recurso individual:

```http
GET /competition/15
```

Este padrão deve ser seguido por todos os recursos da API.

---

# 11. HTTP Methods

| Método | Responsabilidade         |
| ------ | ------------------------ |
| GET    | Consulta                 |
| POST   | Criação                  |
| PUT    | Substituição completa    |
| PATCH  | Atualização parcial      |
| DELETE | Remoção lógica ou física |

---

# 12. HTTP Status Codes

| Código | Significado           |
| ------ | --------------------- |
| 200    | OK                    |
| 201    | Created               |
| 204    | No Content            |
| 400    | Bad Request           |
| 401    | Unauthorized          |
| 403    | Forbidden             |
| 404    | Not Found             |
| 409    | Conflict              |
| 422    | Unprocessable Entity  |
| 500    | Internal Server Error |

---

# 13. Paginação

Todos os endpoints de coleção devem suportar paginação.

Parâmetros padrão:

| Parâmetro | Descrição                          |
| --------- | ---------------------------------- |
| page      | Número da página                   |
| size      | Quantidade de registros por página |
| sort      | Campo utilizado para ordenação     |

Exemplo:

```http
GET /competitions?page=0&size=20
```

---

# 14. Filtros

Filtros são opcionais e enviados exclusivamente através de query parameters.

Exemplo:

```http
GET /competition-editions?competitionId=10&season=2026
```

Os filtros podem ser combinados livremente.

---

# 15. Ordenação

Resultados podem ser ordenados.

Exemplo:

```http
GET /participants?sort=name
```

Ordem decrescente:

```http
GET /participants?sort=-name
```

---

# 16. Idempotência

* GET é sempre idempotente.
* PUT deve ser idempotente.
* PATCH altera apenas os campos enviados.
* POST cria novos recursos.
* DELETE remove recursos respeitando as regras de negócio.

---

# 17. Error Response

Todas as respostas de erro seguem um formato padronizado.

Exemplo:

```json
{
    "timestamp": "2026-07-18T14:10:25Z",
    "status": 404,
    "error": "Not Found",
    "message": "Competition not found.",
    "path": "/api/v1/competition/15"
}
```

O modelo completo de erros será detalhado em uma seção específica deste documento.

---

# 18. Evolução da API

A API deve evoluir preservando compatibilidade sempre que possível.

Novos campos podem ser adicionados às respostas desde que não alterem o significado dos campos existentes.

Mudanças incompatíveis deverão resultar em uma nova versão da API.

---

# 19. Escopo deste Documento

As próximas partes deste documento descrevem cada recurso da API, incluindo:

* endpoints;
* parâmetros;
* payloads;
* respostas;
* regras de negócio;
* códigos de erro específicos.

Cada recurso será documentado de forma independente, permitindo evolução incremental da API.

---

# API

# Parte 2 — Core Resources

Os recursos descritos nesta seção representam as entidades fundamentais do domínio esportivo.

Eles são utilizados por todas as demais partes do sistema.

---

# 20. Sport

Representa uma modalidade esportiva.

Exemplos:

* Football
* Formula 1
* Basketball
* Tennis

## Collection

Consultar esportes.

```http
GET /api/v1/sports
```

### Query Parameters

| Parâmetro | Tipo    | Obrigatório | Descrição             |
| --------- | ------- | ----------- | --------------------- |
| name      | String  | Não         | Filtra pelo nome      |
| active    | Boolean | Não         | Filtra por status     |
| page      | Integer | Não         | Página                |
| size      | Integer | Não         | Quantidade por página |
| sort      | String  | Não         | Ordenação             |

### Response

```json
[
  {
    "id": 1,
    "name": "Futebol",
    "slug": "futebol",
    "description": "O futebol é...",
    "active": true
  }
]
```

---

## Resource

Consultar um esporte.

```http
GET /api/v1/sport/{sportId}
```

### Response

```json
{
  "id": 1,
  "name": "Futebol",
  "slug": "futebol",
  "description": "O futebol é...",
  "active": true
}
```

---

## Create

```http
POST /api/v1/sport
```

### Request

```json
{
  "name": "Football",
  "description": "O futebol é..."
}
```

### Response

```json
{
  "id": 1,
  "name": "Futebol",
  "slug": "futebol",
  "description": "O futebol é...",
  "active": true
}
```

---

## Update

```http
PATCH /api/v1/sport/{sportId}
```

Atualiza parcialmente um esporte.

---

## Delete

```http
DELETE /api/v1/sport/{sportId}
```

Remove (ou desativa) um esporte conforme as regras de negócio.

---

# 21. Organization

Representa uma organização esportiva.

Exemplos:

* FIFA
* UEFA
* CONMEBOL
* FIA
* Formula One Management

Uma organização pode possuir uma organização pai.

---

## Collection

```http
GET /api/v1/organizations
```

### Query Parameters

| Parâmetro            | Descrição           |
| -------------------- | ------------------- |
| name                 | Nome da organização |
| parentOrganizationId | Organização pai     |
| active               | Status              |
| page                 | Paginação           |
| size                 | Paginação           |
| sort                 | Ordenação           |

---

## Resource

```http
GET /api/v1/organization/{organizationId}
```

---

## Create

```http
POST /api/v1/organization
```

---

## Update

```http
PATCH /api/v1/organization/{organizationId}
```

---

## Delete

```http
DELETE /api/v1/organization/{organizationId}
```

---

# 22. Location

Representa uma localização geográfica.

Pode ser utilizada por arenas, organizações ou outros recursos.

---

## Collection

```http
GET /api/v1/locations
```

Filtros:

* country
* state
* city

---

## Resource

```http
GET /api/v1/location/{locationId}
```

---

## Create

```http
POST /api/v1/location
```

---

## Update

```http
PATCH /api/v1/location/{locationId}
```

---

## Delete

```http
DELETE /api/v1/location/{locationId}
```

---

# 23. Venue

Representa um local onde eventos esportivos podem ocorrer.

Exemplos:

* Wembley Stadium
* Interlagos
* Suzuka
* Maracanã

---

## Collection

```http
GET /api/v1/venues
```

Filtros:

* locationId
* name
* active

---

## Resource

```http
GET /api/v1/venue/{venueId}
```

---

## Create

```http
POST /api/v1/venue
```

---

## Update

```http
PATCH /api/v1/venue/{venueId}
```

---

## Delete

```http
DELETE /api/v1/venue/{venueId}
```

---

# 24. Participant

Representa qualquer entidade capaz de participar de uma competição.

Exemplos:

* Clube
* Seleção
* Piloto
* Carro
* Jogador
* Dupla
* Equipe

---

## Collection

```http
GET /api/v1/participants
```

### Query Parameters

| Parâmetro      | Descrição            |
| -------------- | -------------------- |
| sportId        | Modalidade esportiva |
| type           | Tipo de participante |
| organizationId | Organização          |
| active         | Status               |
| page           | Paginação            |
| size           | Paginação            |
| sort           | Ordenação            |

---

## Resource

```http
GET /api/v1/participant/{participantId}
```

---

## Create

```http
POST /api/v1/participant
```

---

## Update

```http
PATCH /api/v1/participant/{participantId}
```

---

## Delete

```http
DELETE /api/v1/participant/{participantId}
```

---

# 25. Participant Relation

Representa relacionamentos entre participantes.

Exemplos:

* piloto → equipe
* carro → equipe
* jogador → clube
* clube → federação

Essas relações podem variar ao longo do tempo.

---

## Collection

```http
GET /api/v1/participant-relations
```

Filtros:

* participantId
* relatedParticipantId
* relationType
* active

---

## Resource

```http
GET /api/v1/participant-relation/{participantRelationId}
```

---

## Create

```http
POST /api/v1/participant-relation
```

---

## Update

```http
PATCH /api/v1/participant-relation/{participantRelationId}
```

---

## Delete

```http
DELETE /api/v1/participant-relation/{participantRelationId}
```

---

# API

# Parte 3 — Competition Configuration

Esta seção descreve os recursos responsáveis pela configuração de competições.

Esses recursos definem **como uma competição funciona**, independentemente de uma edição específica.

As configurações criadas aqui poderão ser reutilizadas por diversas temporadas.

---

# 26. Competition

Representa uma competição.

Exemplos:

* FIFA World Cup
* Formula One World Championship
* Premier League
* UEFA Champions League

Uma competição pertence a um esporte e normalmente é organizada por uma organização.

---

## Collection

```http
GET /api/v1/competitions
```

### Query Parameters

| Parâmetro      | Descrição              |
| -------------- | ---------------------- |
| sportId        | Filtra por esporte     |
| organizationId | Filtra por organização |
| name           | Busca pelo nome        |
| active         | Filtra por status      |
| page           | Paginação              |
| size           | Quantidade por página  |
| sort           | Ordenação              |

---

## Resource

```http
GET /api/v1/competition/{competitionId}
```

---

## Create

```http
POST /api/v1/competition
```

### Request

```json
{
  "sportId": 1,
  "organizationId": 2,
  "name": "FIFA World Cup",
  "shortName": "World Cup",
  "code": "WORLD_CUP",
  "active": true
}
```

### Response

```json
{
  "id": 10,
  "sportId": 1,
  "organizationId": 2,
  "name": "FIFA World Cup",
  "shortName": "World Cup",
  "code": "WORLD_CUP",
  "active": true
}
```

---

## Update

```http
PATCH /api/v1/competition/{competitionId}
```

Atualiza parcialmente uma competição.

---

## Delete

```http
DELETE /api/v1/competition/{competitionId}
```

---

# 27. Competition Template

Define o formato de uma competição.

Um template pode ser reutilizado por diversas edições.

Exemplos:

* Copa do Mundo 2026
* Brasileirão (Pontos Corridos)
* Fórmula 1

---

## Collection

```http
GET /api/v1/competition-templates
```

Filtros:

* competitionId
* engineType
* active

---

## Resource

```http
GET /api/v1/competition-template/{competitionTemplateId}
```

---

## Create

```http
POST /api/v1/competition-template
```

### Request

```json
{
  "competitionId": 10,
  "name": "World Cup Modern Format",
  "engineType": "HYBRID",
  "configuration": {
    "groupCount": 12,
    "qualifiedPerGroup": 2,
    "bestThirdPlaces": 8
  },
  "active": true
}
```

### Response

```json
{
  "id": 20,
  "competitionId": 10,
  "name": "World Cup Modern Format",
  "engineType": "HYBRID",
  "configuration": {
    "groupCount": 12,
    "qualifiedPerGroup": 2,
    "bestThirdPlaces": 8
  },
  "active": true
}
```

---

## Regras

* pertence a uma Competition;
* define apenas a estrutura da competição;
* não possui participantes;
* pode ser reutilizado por diversas edições.

---

# 28. Competition Rule

Representa uma regra utilizada pela Competition Engine.

Exemplos:

* vitória vale 3 pontos;
* dois classificados por grupo;
* saldo de gols como desempate;
* pontuação da Fórmula 1.

---

## Collection

```http
GET /api/v1/competition-rules
```

Filtros:

* competitionTemplateId
* ruleType
* active

---

## Resource

```http
GET /api/v1/competition-rule/{competitionRuleId}
```

---

## Create

```http
POST /api/v1/competition-rule
```

### Request

```json
{
  "competitionTemplateId": 20,
  "ruleType": "SCORING",
  "configuration": {
    "win": 3,
    "draw": 1,
    "loss": 0
  }
}
```

---

## Regras

* pertence a um CompetitionTemplate;
* a configuração é armazenada em JSON;
* múltiplas regras podem coexistir.

---

# 29. Competition Stage Template

Representa uma fase da competição.

Exemplos:

* Group Stage
* Round of 32
* Round of 16
* Quarter Finals
* Final

---

## Collection

```http
GET /api/v1/competition-stage-templates
```

Filtros:

* competitionTemplateId
* stageType
* parentStageTemplateId

---

## Resource

```http
GET /api/v1/competition-stage-template/{stageTemplateId}
```

---

## Create

```http
POST /api/v1/competition-stage-template
```

### Request

```json
{
  "competitionTemplateId": 20,
  "name": "Group Stage",
  "stageType": "GROUP",
  "sequence": 1
}
```

---

## Regras

* pertence a um CompetitionTemplate;
* pode possuir uma fase pai;
* pode possuir fases filhas;
* define apenas a estrutura da fase.

---

# 30. Stage Slot Template

Representa uma posição dentro de uma fase.

Exemplos:

* Grupo A
* Grupo B
* Vencedor do Grupo A
* Segundo colocado do Grupo C

---

## Collection

```http
GET /api/v1/stage-slot-templates
```

Filtros:

* stageTemplateId
* slotType

---

## Resource

```http
GET /api/v1/stage-slot-template/{stageSlotTemplateId}
```

---

## Create

```http
POST /api/v1/stage-slot-template
```

### Request

```json
{
  "stageTemplateId": 15,
  "code": "GROUP_A_1",
  "slotType": "GROUP_POSITION",
  "position": 1
}
```

---

## Regras

* pertence a uma CompetitionStageTemplate;
* representa uma posição lógica;
* não possui participante associado.

---

# 31. Competition Event Template

Define um evento pertencente a uma fase.

Exemplo:

* Brasil × Argentina
* Slot A × Slot B
* Corrida de Monza

---

## Collection

```http
GET /api/v1/competition-event-templates
```

Filtros:

* stageTemplateId

---

## Resource

```http
GET /api/v1/competition-event-template/{competitionEventTemplateId}
```

---

## Create

```http
POST /api/v1/competition-event-template
```

### Request

```json
{
  "stageTemplateId": 15,
  "firstSlotTemplateId": 30,
  "secondSlotTemplateId": 31,
  "sequence": 1
}
```

### Response

```json
{
  "id": 55,
  "stageTemplateId": 15,
  "firstSlotTemplateId": 30,
  "secondSlotTemplateId": 31,
  "sequence": 1
}
```

---

## Regras

* pertence a uma CompetitionStageTemplate;
* referencia dois StageSlotTemplate;
* define apenas a estrutura do evento;
* os participantes reais serão definidos durante a criação da CompetitionEdition.

---

# API

# Parte 4 — Competition Execution

Esta seção descreve os recursos responsáveis pela execução de uma competição.

Enquanto a Parte 3 define **como uma competição deve funcionar**, esta seção representa uma competição em execução.

A Engine monitora automaticamente alterações nesses recursos e executa todas as regras de negócio necessárias.

Não existem endpoints específicos para "processar" uma competição ou "avançar" uma fase. Essas operações são responsabilidade exclusiva da Engine.

---

# 32. Competition Edition

Representa uma edição específica de uma competição.

Exemplos:

* FIFA World Cup 2026
* Formula One World Championship 2026
* Premier League 2025/2026

Uma Competition Edition utiliza exatamente um Competition Template.

---

## Collection

```http
GET /api/v1/competition-editions
```

### Query Parameters

| Parâmetro     | Descrição             |
| ------------- | --------------------- |
| competitionId | Competição            |
| season        | Temporada             |
| status        | Status                |
| page          | Página                |
| size          | Quantidade por página |
| sort          | Ordenação             |

---

## Resource

```http
GET /api/v1/competition-edition/{competitionEditionId}
```

---

## Create

```http
POST /api/v1/competition-edition
```

### Request

```json
{
    "competitionId": 1,
    "competitionTemplateId": 3,
    "season": "2026",
    "startDate": "2026-06-11",
    "endDate": "2026-07-19"
}
```

### Response

```json
{
    "id": 15,
    "status": "NOT_STARTED"
}
```

---

## Update

```http
PATCH /api/v1/competition-edition/{competitionEditionId}
```

Atualiza informações administrativas da edição.

Exemplos:

* datas;
* nome;
* informações descritivas.

---

## Delete

```http
DELETE /api/v1/competition-edition/{competitionEditionId}
```

---

# 33. Competition Entry

Representa um participante inscrito em uma Competition Edition.

Exemplos:

* Brasil
* Argentina
* Ferrari
* Max Verstappen

---

## Collection

```http
GET /api/v1/competition-entries
```

Filtros:

* competitionEditionId
* participantId
* status

---

## Resource

```http
GET /api/v1/competition-entry/{competitionEntryId}
```

---

## Create

```http
POST /api/v1/competition-entry
```

### Request

```json
{
    "competitionEditionId": 15,
    "participantId": 45,
    "seed": 1
}
```

---

## Delete

```http
DELETE /api/v1/competition-entry/{competitionEntryId}
```

---

# 34. Competition Stage

Representa uma fase existente durante a execução da competição.

Exemplos:

* Grupo A
* Grupo B
* Oitavas de Final
* Final

Normalmente as fases são criadas automaticamente pela Engine durante a geração da Competition Edition.

---

## Collection

```http
GET /api/v1/competition-stages
```

Filtros:

* competitionEditionId
* status
* stageType

---

## Resource

```http
GET /api/v1/competition-stage/{competitionStageId}
```

---

# 35. Stage Slot

Representa uma posição ocupada dentro de uma Competition Stage.

Exemplos:

* Grupo A — 1º colocado
* Grupo A — 2º colocado
* Winner Match 12

Os participantes associados aos slots podem ser alterados automaticamente pela Engine conforme os resultados da competição.

---

## Collection

```http
GET /api/v1/stage-slots
```

Filtros:

* competitionStageId
* participantId

---

## Resource

```http
GET /api/v1/stage-slot/{stageSlotId}
```

---

# 36. Competition Event

Representa um evento esportivo pertencente a uma Competition Edition.

Exemplos:

* Partida
* Corrida
* Prova
* Bateria

Este é o principal recurso utilizado durante a execução da competição.

Alterações em um Competition Event podem provocar automaticamente atualizações em outros recursos da competição.

---

## Collection

```http
GET /api/v1/competition-events
```

### Query Parameters

| Parâmetro            | Descrição             |
| -------------------- | --------------------- |
| competitionEditionId | Competição            |
| competitionStageId   | Fase                  |
| status               | Status                |
| venueId              | Local                 |
| scheduledDate        | Data                  |
| page                 | Página                |
| size                 | Quantidade por página |
| sort                 | Ordenação             |

---

## Resource

```http
GET /api/v1/competition-event/{competitionEventId}
```

---

## Create

```http
POST /api/v1/competition-event
```

Utilizado apenas em casos excepcionais, como importações históricas ou competições configuradas manualmente.

Na maioria dos casos, os eventos são criados automaticamente pela Engine.

---

## Update

```http
PATCH /api/v1/competition-event/{competitionEventId}
```

Atualiza informações do evento.

Exemplos:

* data;
* horário;
* local;
* placar;
* vencedor;
* status.

### Comportamento da Engine

Sempre que um Competition Event sofrer alterações relevantes (por exemplo, resultado ou status), a Competition Engine poderá executar automaticamente operações como:

* atualização de estatísticas;
* atualização da classificação;
* aplicação dos critérios de desempate;
* definição de participantes classificados;
* preenchimento de Stage Slots;
* criação automática da próxima fase, quando aplicável;
* encerramento automático da Competition Stage;
* encerramento automático da Competition Edition.

Essas operações fazem parte do comportamento interno da Engine e não exigem chamadas adicionais da API.

---

# 37. Event Action

Representa acontecimentos registrados durante um Competition Event.

Exemplos:

* gol;
* cartão amarelo;
* cartão vermelho;
* pit stop;
* safety car;
* ultrapassagem;
* penalidade.

As Event Actions fornecem informações detalhadas sobre um evento esportivo e podem ser utilizadas pela Engine para cálculos de estatísticas ou métricas específicas.

---

## Collection

```http
GET /api/v1/event-actions
```

Filtros:

* competitionEventId
* actionType
* participantId
* playerId

---

## Resource

```http
GET /api/v1/event-action/{eventActionId}
```

---

## Create

```http
POST /api/v1/event-action
```

### Request

```json
{
    "competitionEventId": 50,
    "actionType": "GOAL",
    "participantId": 10,
    "playerId": 250,
    "minute": 67
}
```

---

## Update

```http
PATCH /api/v1/event-action/{eventActionId}
```

Permite corrigir informações registradas anteriormente.

Alterações podem provocar um novo processamento automático da Engine.

---

## Delete

```http
DELETE /api/v1/event-action/{eventActionId}
```

Permitido apenas quando compatível com as regras da competição.

A remoção também pode provocar um recálculo automático da Competition Engine.

---

# 38. Engine Integration

Os recursos desta seção representam o estado persistido da competição.

A Competition Engine observa automaticamente alterações nesses recursos e executa todas as regras de negócio necessárias para manter a consistência do campeonato.

A API é responsável apenas por alterar o estado do domínio.

Toda a lógica de cálculo, classificação, geração de fases, promoção de participantes e encerramento de competições pertence exclusivamente à Competition Engine.

Essa separação garante que qualquer alteração realizada por APIs, importadores de dados ou processos internos produza exatamente o mesmo comportamento, preservando a consistência e a previsibilidade do sistema.

---

# API

# Parte 5 — Engine Operations

Esta seção descreve as operações administrativas disponibilizadas pela Competition Engine.

Ao contrário dos recursos documentados nas partes anteriores, estas operações representam funcionalidades excepcionais da Engine e não recursos persistidos.

A maior parte das regras de negócio é executada automaticamente sempre que o estado da competição é alterado.

Os endpoints desta seção existem apenas para cenários específicos, como geração inicial da competição, importação de dados históricos, simulações e reconstrução completa do estado da competição.

---

# 39. Generate Competition Structure

Gera toda a estrutura inicial de uma Competition Edition utilizando o Competition Template associado.

A operação cria automaticamente:

* Competition Stages;
* Stage Slots;
* Competition Events;
* estruturas de classificação;
* demais estruturas necessárias para execução da competição.

Normalmente esta operação é executada apenas uma vez, logo após a criação da Competition Edition.

---

## Endpoint

```http
POST /api/v1/competition-edition/{competitionEditionId}/generate
```

---

### Response

```json
{
    "success": true,
    "message": "Competition structure generated successfully."
}
```

---

## Regras

* permitido apenas antes do início da competição;
* não pode ser executado novamente após a geração da estrutura;
* utiliza exclusivamente a configuração definida no Competition Template.

---

# 40. Recalculate Competition

Reconstrói completamente o estado de uma Competition Edition.

A Competition Engine ignora todos os dados derivados existentes e executa novamente todas as regras de negócio utilizando apenas os dados persistidos.

Durante o recálculo poderão ser reconstruídos automaticamente:

* classificações;
* métricas;
* estatísticas;
* rankings;
* participantes classificados;
* fases futuras;
* confrontos;
* quaisquer outras informações derivadas.

Nenhum evento esportivo, participante ou ação registrada é alterado.

---

## Endpoint

```http
POST /api/v1/competition-edition/{competitionEditionId}/recalculate
```

---

### Response

```json
{
    "success": true,
    "message": "Competition recalculated successfully."
}
```

---

## Exemplos de utilização

* correção de dados históricos;
* alteração nas regras da Competition Engine;
* reconstrução após manutenção;
* recuperação de inconsistências.

---

# 41. Simulation

Executa uma simulação completa da Competition Engine.

A simulação utiliza alterações temporárias informadas na requisição para prever os impactos na competição.

Nenhuma informação oficial é persistida.

---

## Endpoint

```http
POST /api/v1/competition-edition/{competitionEditionId}/simulate
```

---

### Request

```json
{
    "changes": [
        {
            "competitionEventId": 52,
            "homeScore": 2,
            "awayScore": 1,
            "status": "FINISHED"
        }
    ]
}
```

---

### Response

```json
{
    "simulation": true,
    "classification": [
        ...
    ],
    "generatedStages": [],
    "generatedEvents": []
}
```

---

## Regras

* nenhuma alteração é persistida;
* toda a execução ocorre em memória;
* utiliza exatamente as mesmas regras da Competition Engine oficial.

---

# 42. Import Historical Data

Importa informações históricas para uma Competition Edition.

Este endpoint destina-se ao carregamento de competições já concluídas ou provenientes de fontes externas.

A importação poderá incluir:

* participantes;
* fases;
* eventos;
* ações;
* resultados;
* estatísticas.

Após a importação, a Competition Engine poderá executar automaticamente um recálculo completo da competição.

---

## Endpoint

```http
POST /api/v1/competition-edition/{competitionEditionId}/import
```

---

### Response

```json
{
    "success": true,
    "importedParticipants": 32,
    "importedEvents": 64,
    "importedActions": 312
}
```

---

## Regras

* valida a consistência dos dados importados;
* preserva a integridade referencial da competição;
* pode executar automaticamente um recálculo ao término da importação.

---

# 43. Princípios das Operações da Engine

As operações desta seção seguem os seguintes princípios:

* determinísticas;
* auditáveis;
* independentes da interface utilizada;
* reutilizáveis por APIs, importadores, integrações e tarefas agendadas.

Todas as demais regras da Competition Engine são executadas automaticamente em resposta às alterações dos recursos persistidos, conforme descrito na Parte 4.

---

# API

# Parte 6 — API Standards

Esta seção descreve os padrões utilizados por toda a Sports Data API.

As convenções aqui apresentadas aplicam-se a todos os recursos documentados nas seções anteriores.

---

# 44. Error Response

Todas as respostas de erro seguem um formato padronizado.

Exemplo:

```json id="a2o0k9"
{
    "timestamp": "2026-07-18T20:15:32Z",
    "status": 404,
    "error": "Not Found",
    "message": "Competition not found.",
    "path": "/api/v1/competition/15"
}
```

---

## Campos

| Campo     | Descrição           |
| --------- | ------------------- |
| timestamp | Data e hora do erro |
| status    | Código HTTP         |
| error     | Descrição resumida  |
| message   | Mensagem detalhada  |
| path      | Endpoint solicitado |

---

# 45. Validation Errors

Erros de validação utilizam HTTP **422 - Unprocessable Entity**.

Exemplo:

```json id="e8pnvq"
{
    "timestamp": "2026-07-18T20:18:14Z",
    "status": 422,
    "error": "Validation Error",
    "message": "Validation failed.",
    "fields": [
        {
            "field": "name",
            "message": "Name is required."
        },
        {
            "field": "sportId",
            "message": "Sport not found."
        }
    ]
}
```

---

# 46. Common HTTP Status Codes

| Código | Utilização                                 |
| ------ | ------------------------------------------ |
| 200    | Operação realizada com sucesso             |
| 201    | Recurso criado                             |
| 204    | Operação realizada sem conteúdo de retorno |
| 400    | Requisição inválida                        |
| 401    | Usuário não autenticado                    |
| 403    | Usuário autenticado sem permissão          |
| 404    | Recurso não encontrado                     |
| 409    | Conflito de estado                         |
| 422    | Erro de validação                          |
| 500    | Erro interno inesperado                    |

---

# 47. Pagination Response

Todos os endpoints de coleção retornam uma estrutura padronizada.

Exemplo:

```json id="2n4x4r"
{
    "content": [
        {
            "id": 1,
            "name": "Football"
        }
    ],
    "page": 0,
    "size": 20,
    "totalElements": 154,
    "totalPages": 8
}
```

---

# 48. Sorting

A ordenação é realizada através do parâmetro `sort`.

Exemplos:

```http id="ijyd9b"
GET /api/v1/competitions?sort=name
```

Ordem decrescente:

```http id="zxqwdk"
GET /api/v1/competitions?sort=-name
```

Múltiplos critérios:

```http id="quw40t"
GET /api/v1/competitions?sort=season,-name
```

---

# 49. Filtering

Todos os filtros são enviados através de query parameters.

Exemplos:

```http id="ukjlwm"
GET /api/v1/participants?sportId=1
```

```http id="2gkq0o"
GET /api/v1/competitions?organizationId=5&active=true
```

```http id="hsx5lq"
GET /api/v1/competition-events?competitionEditionId=15&status=SCHEDULED
```

Filtros podem ser combinados livremente, desde que sejam compatíveis com o recurso consultado.

---

# 50. Authentication

A autenticação será adicionada em versões futuras da API.

A arquitetura foi preparada para integração com um microserviço dedicado de autenticação e usuários.

Quando habilitada, todas as operações protegidas deverão utilizar um token de autenticação.

---

# 51. Authorization

O controle de acesso também será implementado futuramente.

As permissões serão avaliadas de acordo com o usuário autenticado e o contexto da operação solicitada.

---

# 52. API Compatibility

A evolução da API deve preservar compatibilidade sempre que possível.

São consideradas alterações compatíveis:

* inclusão de novos endpoints;
* inclusão de novos campos opcionais;
* novos filtros opcionais;
* novas operações administrativas.

Alterações incompatíveis deverão resultar em uma nova versão da API.

---

# 53. Design Principles

A Sports Data API segue os seguintes princípios:

* arquitetura REST;
* recursos de domínio claramente definidos;
* coleções representadas por endpoints no plural;
* recursos individuais representados por endpoints no singular;
* filtros realizados exclusivamente por query parameters;
* respostas previsíveis e consistentes;
* regras de negócio centralizadas na Competition Engine;
* baixo acoplamento entre API e Engine;
* compatibilidade retroativa sempre que possível.

---

# 54. Integração com a Competition Engine

A API atua exclusivamente como interface de acesso ao domínio da aplicação.

Toda a lógica de negócio relacionada à execução das competições é responsabilidade da Competition Engine.

Sempre que recursos persistidos forem alterados, a Engine poderá executar automaticamente operações como:

* atualização de classificações;
* geração de fases;
* preenchimento de slots;
* promoção de participantes;
* encerramento de fases;
* encerramento de competições.

Essa integração é transparente para os consumidores da API e garante que diferentes clientes (frontend, importadores, integrações externas ou processos internos) produzam exatamente o mesmo comportamento ao modificar o estado do domínio.

---

# 55. Encerramento

Este documento define o contrato público da Sports Data API.

A implementação interna da aplicação poderá evoluir livremente, desde que preserve os contratos aqui definidos.

Os documentos `architecture.md`, `database.md` e `engine.md` complementam esta especificação, descrevendo respectivamente a arquitetura da solução, o modelo de persistência e o funcionamento interno da Competition Engine.

---
