# Sports Engine - Database Model

> Universal Sports Competition Engine
>
> Version: **1.0**
>
> Status: Draft

---

# 1. Objetivo

Este documento descreve o modelo relacional do Sports Engine.

Seu objetivo é traduzir o Domain Model para uma estrutura de banco de dados consistente, altamente normalizada e preparada para evolução.

Este documento define:

- tabelas;
- colunas;
- tipos de dados;
- relacionamentos;
- chaves primárias;
- chaves estrangeiras;
- índices;
- constraints;
- estratégia de versionamento;
- estratégia de auditoria.

As decisões descritas aqui devem servir como referência para:

- implementação das entidades JPA;
- migrations do banco de dados;
- documentação da API;
- implementação das CompetitionEngines.

---

## 3.1 Estrutura atual do modelo

O modelo implementado no projeto está dividido em dois tipos de artefatos distintos:

- Entidades / tabelas reais: classes JPA localizadas em [src/main/java/com/rolf/sports_data/entities](../src/main/java/com/rolf/sports_data/entities). Elas representam estruturas persistidas no banco e, na prática, correspondem às tabelas do modelo relacional.
- Enums de aplicação: classes localizadas em [src/main/java/com/rolf/sports_data/enums](../src/main/java/com/rolf/sports_data/enums). Elas representam valores de domínio usados pela aplicação e são persistidos como VARCHAR no banco.

### 3.1.1 Exemplos de mapeamento atual

- Entidades / tabelas reais:
  - OrganizationEntity
  - CompetitionEntity
  - CompetitionEditionEntity
  - CompetitionEventEntity
  - VenueEntity
- Enums de aplicação:
  - [src/main/java/com/rolf/sports_data/enums/VenueTypeEnum.java](../src/main/java/com/rolf/sports_data/enums/VenueTypeEnum.java)
  - [src/main/java/com/rolf/sports_data/enums/ClassificationMetricTypeEnum.java](../src/main/java/com/rolf/sports_data/enums/ClassificationMetricTypeEnum.java)
  - [src/main/java/com/rolf/sports_data/enums/ParticipantTypeEnum.java](../src/main/java/com/rolf/sports_data/enums/ParticipantTypeEnum.java)

> Observação: alguns conceitos podem aparecer tanto como entidade de suporte quanto como enum de aplicação. Exemplo: a implementação atual possui uma entidade de suporte para venue type, mas o valor de domínio é também representado por um enum Java.

---

# 2. Princípios

O modelo de banco segue os mesmos princípios definidos no Domain Model.

## Fonte da verdade

O banco armazena fatos.

Nunca armazena resultados derivados como fonte primária.

Exemplos:

- EventActions
- CompetitionEvents
- CompetitionEntries

Classificações e estatísticas sempre podem ser reconstruídas.

---

## Histórico imutável

Uma CompetitionEdition finalizada representa um registro histórico.

As tabelas devem preservar o histórico completo da competição.

Alterações posteriores devem ocorrer através de reprocessamento da CompetitionEngine.

---

## Modelo altamente normalizado

Sempre que possível, o modelo deve permanecer normalizado.

Duplicação de informações deve ser evitada.

Campos JSON devem ser utilizados apenas quando representarem configurações naturalmente flexíveis.

---

## Separação entre configuração e execução

Toda definição reutilizável pertence às tabelas de Template.

Toda informação operacional pertence às tabelas da CompetitionEdition.

---

# 3. Convenções Gerais

## Convenção de nomenclatura

Todas as tabelas utilizam:

- singular;
- snake_case;
- nomes descritivos.

Exemplos:

```
competition

competition_template

competition_stage

competition_event

participant

venue
```

---

## Colunas

Todas as colunas seguem snake_case.

Exemplo:

```
competition_id

created_at

updated_by

display_name
```

---

## Chaves primárias

Todas as tabelas utilizam:

```
BIGINT GENERATED ALWAYS AS IDENTITY
```

A coluna padrão será:

```
id
```

---

## Chaves estrangeiras

Toda chave estrangeira utiliza:

```
<entity_name>_id
```

Exemplos:

```
competition_id

sport_id

participant_id

competition_stage_id
```

---

## Timestamps

Todos os timestamps utilizam:

```
TIMESTAMP WITH TIME ZONE
```

Nunca devem ser utilizados timestamps sem timezone.

Toda data armazenada representa um instante absoluto.

---

## Datas sem horário

Quando o domínio exigir apenas uma data, utilizar:

```
DATE
```

Exemplo:

- nascimento;
- início oficial de temporada;
- encerramento oficial.

---

## Horários sem data

Quando necessário representar apenas horários, utilizar:

```
TIME
```

---

## UUID

UUID será utilizado apenas para referências externas.

Principalmente:

```
created_by

updated_by

deleted_by
```

Esses campos representam usuários pertencentes ao serviço de autenticação.

Não devem possuir Foreign Keys.

---

# 4. BaseEntity

Praticamente todas as tabelas do sistema herdam de BaseEntity.

Ela padroniza auditoria e controle de concorrência.

## Colunas

| Coluna | Tipo | Null |
|----------|------|------|
| id | BIGINT | Não |
| created_at | TIMESTAMP WITH TIME ZONE | Não |
| created_by | UUID | Sim |
| updated_at | TIMESTAMP WITH TIME ZONE | Não |
| updated_by | UUID | Sim |
| version | BIGINT | Não |

---

## Version

A coluna:

```
version
```

será utilizada pelo Hibernate para Optimistic Locking.

Ela deve ser mapeada utilizando:

```
@Version
```

Isso evita sobrescrita de alterações concorrentes.

---

# 5. SoftDeleteEntity

Nem todas as entidades suportam exclusão lógica.

Quando necessário, uma entidade herda de SoftDeleteEntity.

## Colunas adicionais

| Coluna | Tipo | Null |
|----------|------|------|
| deleted_at | TIMESTAMP WITH TIME ZONE | Sim |
| deleted_by | UUID | Sim |

---

## Objetivo

Permitir desativação lógica de entidades administrativas sem perda de histórico.

---

## Exemplos

Podem utilizar Soft Delete:

- venue;
- location;
- competition_template;
- competition_engine.

Normalmente NÃO utilizam Soft Delete:

- competition_event;
- event_action;
- competition_entry;
- competition_edition.

Essas entidades representam fatos históricos.

---

# 6. Estratégia de Enums

O sistema utiliza Enums apenas na aplicação.

No banco de dados NÃO serão utilizados tipos ENUM nativos do PostgreSQL.

As colunas serão persistidas como:

```
VARCHAR
```

As validações pertencem à aplicação.

Essa decisão simplifica:

- migrations;
- versionamento;
- compatibilidade entre versões.

---

## Exemplos

```
participant_type

competition_status

event_status

stage_type
```

### 6.1 Menu de enums relacionados

Os enums abaixo fazem parte do modelo de aplicação e devem ser consultados junto com as entidades correspondentes:

- Classificação:
  - [src/main/java/com/rolf/sports_data/enums/ClassificationTypeEnum.java](../src/main/java/com/rolf/sports_data/enums/ClassificationTypeEnum.java)
  - [src/main/java/com/rolf/sports_data/enums/ClassificationStatusEnum.java](../src/main/java/com/rolf/sports_data/enums/ClassificationStatusEnum.java)
  - [src/main/java/com/rolf/sports_data/enums/ClassificationMetricTypeEnum.java](../src/main/java/com/rolf/sports_data/enums/ClassificationMetricTypeEnum.java)
- Participantes:
  - [src/main/java/com/rolf/sports_data/enums/ParticipantTypeEnum.java](../src/main/java/com/rolf/sports_data/enums/ParticipantTypeEnum.java)
- Instalações e locais:
  - [src/main/java/com/rolf/sports_data/enums/VenueTypeEnum.java](../src/main/java/com/rolf/sports_data/enums/VenueTypeEnum.java)
- Competições e fases:
  - [src/main/java/com/rolf/sports_data/enums/CompetitionStatusEnum.java](../src/main/java/com/rolf/sports_data/enums/CompetitionStatusEnum.java)
  - [src/main/java/com/rolf/sports_data/enums/StageTypeEnum.java](../src/main/java/com/rolf/sports_data/enums/StageTypeEnum.java)

---

# 7. Estratégia para JSON

Campos JSON devem ser utilizados apenas quando a estrutura possuir natureza dinâmica.

O tipo utilizado será:

```
JSONB
```

---

## Casos previstos

CompetitionRule

```
configuration
```

EventAction

```
metadata
```

Outras utilizações devem ser justificadas.

O restante do modelo permanece totalmente relacional.

---

# 8. Estratégia de Relacionamentos

Todos os relacionamentos serão representados através de Foreign Keys.

Não serão utilizados relacionamentos implícitos.

Exemplo:

```
competition

↓

competition_template

↓

competition_stage_template

↓

competition_event_template
```

---

Relacionamentos muitos-para-muitos serão representados através de tabelas intermediárias.

---

# 9. Estratégia de Índices

Toda chave estrangeira deve possuir índice.

Além disso, serão criados índices para:

- campos utilizados em filtros;
- ordenações frequentes;
- consultas históricas;
- consultas de classificação.

Índices compostos serão adicionados quando necessário.

---

# 10. Constraints

Sempre que possível, o banco deve garantir integridade.

Utilizar:

- PRIMARY KEY;
- FOREIGN KEY;
- UNIQUE;
- CHECK.

A aplicação nunca deve ser a única responsável pela consistência.

---

# 11. Materialização

O Sports Engine diferencia claramente:

## Dados primários

Representam fatos.

Exemplos:

- CompetitionEvent;
- EventAction;
- CompetitionEntry.

---

## Dados derivados

Representam cálculos.

Exemplos:

- Classification;
- ClassificationEntry;
- ParticipantStatistic.

Inicialmente esses dados poderão ser calculados sob demanda.

No futuro poderão ser materializados através de:

- jobs;
- eventos;
- processamento assíncrono.

Essa alteração não deve modificar o modelo relacional.

---

# 12. Estrutura Geral

O banco será organizado em grandes grupos.

```
Core Tables

↓

Rule Tables

↓

Participant Tables

↓

Competition Structure Tables

↓

Runtime Tables

↓

Statistics Tables

↓

Infrastructure Tables
```

Cada grupo será detalhado nas próximas seções.

---

# 13. Core Tables

O Core representa as entidades fundamentais do domínio.

Essas tabelas definem:

- organizações;
- esportes;
- competições;
- templates;
- edições.

Todo o restante do sistema depende dessas entidades.

---

# 14. organization

## Responsabilidade

Representa uma organização responsável por administrar competições esportivas.

Exemplos:

- FIFA
- UEFA
- CONMEBOL
- FIA
- ACO

---

## Herança

```
BaseEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|----------|------|------|------------|
| id | BIGINT | Não | PK |
| parent_organization_id | BIGINT | Sim | FK organization |
| name | VARCHAR(200) | Não | Nome oficial |
| short_name | VARCHAR(50) | Sim | Nome abreviado |
| acronym | VARCHAR(20) | Sim | FIFA, FIA, UEFA... |
| description | TEXT | Sim | |
| website | VARCHAR(500) | Sim | |
| location_id | BIGINT | Sim | Sede principal |
| active | BOOLEAN | Não | Default TRUE |

---

## Constraints

```
pk_organization

fk_organization_parent

fk_organization_location
```

---

## Índices

```
idx_organization_parent

idx_organization_name
```

---

## Relacionamentos

Organization, uma organização pode ter várias organizações derivadas

```
1 -> N Organization
```

Organization, uma organização pode ter várias competições

```
1 -> N Competition
```

---

# 15. sport

## Responsabilidade

Representa uma modalidade esportiva.

Não contém regras.

Não contém competições.

Apenas representa o esporte.

---

## Herança

```
BaseEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|----------|------|------|------------|
| id | BIGINT | Não | PK |
| name | VARCHAR(120) | Não | |
| slug | VARCHAR(120) | Não | Identificador único |
| description | TEXT | Sim | |
| active | BOOLEAN | Não | |

---

## Constraints

```
pk_sport

uk_sport_slug
```

---

## Índices

```
idx_sport_name

idx_sport_slug
```

---

## Relacionamentos

Sport, um esporte pode ter várias competições

```
1 -> N Competition
```

---

# 16. competition

## Responsabilidade

Representa um campeonato permanente.

Nunca representa uma temporada.

---

## Herança

```
BaseEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|----------|------|------|------------|
| id | BIGINT | Não | PK |
| organization_id | BIGINT | Sim | FK |
| sport_id | BIGINT | Não | FK |
| name | VARCHAR(200) | Não | |
| short_name | VARCHAR(100) | Sim | |
| slug | VARCHAR(200) | Não | |
| description | TEXT | Sim | |
| official_website | VARCHAR(500) | Sim | |
| active | BOOLEAN | Não | |

---

## Constraints

```
pk_competition

fk_competition_organization

fk_competition_sport

uk_competition_slug
```

---

## Índices

```
idx_competition_name

idx_competition_sport

idx_competition_organization
```

---

## Relacionamentos

Competition

```
N -> 1 Sport
```

Competition

```
N -> 1 Organization
```

Competition

```
1 -> N CompetitionTemplate
```

Competition

```
1 -> N CompetitionEdition
```

---

# 17. competition_template

## Responsabilidade

Representa um modelo reutilizável para criação de temporadas.

Não representa uma competição em execução.

---

## Herança

```
SoftDeleteEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|----------|------|------|------------|
| id | BIGINT | Não | PK |
| competition_id | BIGINT | Não | FK |
| name | VARCHAR(200) | Não | |
| version | INTEGER | Não | Versão funcional do template |
| description | TEXT | Sim | |
| active | BOOLEAN | Não | |

---

## Constraints

```
pk_competition_template

fk_competition_template_competition

uk_competition_template_name_version
```

---

## Índices

```
idx_competition_template_competition
```

---

## Relacionamentos

CompetitionTemplate

```
N -> 1 Competition
```

CompetitionTemplate

```
1 -> N CompetitionStageTemplate
```

CompetitionTemplate

```
1 -> N CompetitionRule
```

---

# 18. competition_edition

## Responsabilidade

Representa uma realização específica de uma Competition.

Toda informação operacional pertence a esta entidade.

---

## Herança

```
BaseEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|----------|------|------|------------|
| id | BIGINT | Não | PK |
| competition_id | BIGINT | Não | FK |
| competition_template_id | BIGINT | Não | FK |
| competition_engine_id | BIGINT | Não | FK |
| name | VARCHAR(200) | Não | |
| season | VARCHAR(50) | Não | Ex.: 2026, 2025/26 |
| status | VARCHAR(30) | Não | Enum |
| start_date | DATE | Sim | |
| end_date | DATE | Sim | |
| registration_start | DATE | Sim | |
| registration_end | DATE | Sim | |

---

## Constraints

```
pk_competition_edition

fk_competition_edition_competition

fk_competition_edition_template

fk_competition_edition_engine
```

---

## Índices

```
idx_competition_edition_competition

idx_competition_edition_status

idx_competition_edition_season
```

---

## Relacionamentos

CompetitionEdition

```
N -> 1 Competition
```

CompetitionEdition

```
N -> 1 CompetitionTemplate
```

CompetitionEdition

```
N -> 1 CompetitionEngine
```

CompetitionEdition

```
1 -> N CompetitionStage
```

CompetitionEdition

```
1 -> N CompetitionEntry
```

CompetitionEdition

```
1 -> N CompetitionEvent
```

CompetitionEdition

```
1 -> N Classification
```

---

# Resumo

O Core do banco é composto pelas seguintes tabelas:

```
organization

sport

competition

competition_template

competition_edition
```

Essas tabelas representam a estrutura permanente do domínio e servem como base para todas as demais entidades do sistema.

---

# 19. Rule Tables

As Rule Tables representam toda a estrutura reutilizável utilizada para construir uma CompetitionEdition.

Nenhuma dessas tabelas representa uma competição em execução.

Seu objetivo é definir:

- regras;
- engines;
- fases;
- slots;
- calendário.

---

# 20. competition_engine

## Responsabilidade

Representa uma implementação da CompetitionEngine.

Cada CompetitionEdition referencia exatamente uma CompetitionEngine.

Uma mesma engine pode ser reutilizada por diversas competições.

---

## Herança

```
SoftDeleteEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|---------|------|------|------------|
| id | BIGINT | Não | PK |
| code | VARCHAR(100) | Não | Identificador técnico |
| name | VARCHAR(200) | Não | |
| engine_version | INTEGER | Não | Versão funcional |
| description | TEXT | Sim | |
| active | BOOLEAN | Não | |

---

## Constraints

```
pk_competition_engine

uk_competition_engine_code_version
```

---

## Índices

```
idx_competition_engine_code
```

---

## Relacionamentos

CompetitionEngine

```
1 -> N CompetitionEdition
```

---

# 21. competition_templete_rule

## Responsabilidade

Representa o regulamento utilizado por uma Competition (global) ou Stage da competição.

As regras são interpretadas pela CompetitionEngine.

A entidade nunca contém comportamento.

Apenas configuração.

---

## Herança

```
BaseEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|---------|------|------|------------|
| id | BIGINT | Não | PK |
| scope_type | VARCHAR(20) | Não | TEMPLATE ou EDITION |
| competition_template_id | BIGINT | Sim | FK |
| competition_edition_id | BIGINT | Sim | FK |
| competition_stage_template_id | BIGINT | Sim | FK |
| rule_type | ENUM | Não | Tipo de regra |
| rule_version | INTEGER | Não | |
| configuration | JSONB | Não | Configuração da engine |
| description | TEXT | Sim | |

---

## Constraints

```
pk_competition_rule

fk_competition_rule_template

fk_competition_rule_edition

ck_competition_rule_scope
```

---

## Check Constraint

Apenas um escopo pode existir.

```
TEMPLATE

↓

competition_template_id preenchido

competition_edition_id NULL
```

ou

```
EDITION

↓

competition_edition_id preenchido

competition_template_id NULL
```

---

## Índices

```
idx_competition_template_rule_competition_template

idx_competition_template_rule_competition_stage_template

idx_competition_templete_rule_edition
```

---

## Relacionamentos

CompetitionTemplate

```
1 -> N CompetitionTemplate
```

CompetitionEdition

```
1 -> N CompetitionEdition
```

---

# 22. competition_stage_template

## Responsabilidade

Representa a definição de uma fase da competição.

Uma CompetitionStageTemplate será transformada em uma CompetitionStage durante a criação da CompetitionEdition.

---

## Herança

```
SoftDeleteEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|---------|------|------|------------|
| id | BIGINT | Não | PK |
| competition_template_id | BIGINT | Não | FK |
| parent_stage_template_id | BIGINT | Sim | Auto relacionamento |
| name | VARCHAR(200) | Não | |
| stage_type | VARCHAR(40) | Não | Enum |
| display_order | INTEGER | Não | |
| expected_participants | INTEGER | Sim | |
| expected_events | INTEGER | Sim | |

---

## Constraints

```
pk_competition_stage_template

fk_stage_template_template

fk_stage_template_parent
```

---

## Índices

```
idx_stage_template_template

idx_stage_template_parent

idx_stage_template_order
```

---

## Relacionamentos

CompetitionTemplate

```
N -> 1 CompetitionTemplate
```

CompetitionStageTemplate

```
N -> 1 CompetitionStageTemplate
```

CompetitionStageTemplate

```
1 -> N StageSlotTemplate
```

CompetitionStageTemplate

```
1 -> N CompetitionEventTemplate
```

---

# 23. stage_slot_template

## Responsabilidade

Representa uma origem esperada para um participante.

Ela não representa um participante real.

---

## Herança

```
BaseEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|---------|------|------|------------|
| id | BIGINT | Não | PK |
| competition_stage_template_id | BIGINT | Não | FK |
| slot_order | INTEGER | Não | |
| slot_type | VARCHAR(40) | Não | Enum |
| source_stage_template_id | BIGINT | Sim | FK |
| source_position | INTEGER | Sim | |
| description | VARCHAR(200) | Sim | |

---

## Exemplos

```
Winner Group A

Runner-up Group B

Best Third Place

Winner Match 17
```

---

## Constraints

```
pk_stage_slot_template

fk_slot_template_stage

fk_slot_template_source_stage
```

---

## Índices

```
idx_slot_template_stage

idx_slot_template_source_stage
```

---

## Relacionamentos

CompetitionStageTemplate

```
1 -> N StageSlotTemplate
```

---

# 24. competition_event_template

## Responsabilidade

Representa a definição de um evento esportivo.

Não possui participantes.

Não possui resultado.

Não possui estatísticas.

---

## Herança

```
BaseEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|---------|------|------|------------|
| id | BIGINT | Não | PK |
| competition_stage_template_id | BIGINT | Não | FK |
| event_order | INTEGER | Não | |
| name | VARCHAR(200) | Sim | |
| event_type | VARCHAR(40) | Não | Enum |
| home_slot_template_id | BIGINT | Sim | FK |
| away_slot_template_id | BIGINT | Sim | FK |
| venue_id | BIGINT | Sim | FK padrão |
| configuration | JSONB | Sim | Configurações específicas |

---

## Constraints

```
pk_competition_event_template

fk_event_template_stage

fk_event_template_home_slot

fk_event_template_away_slot

fk_event_template_venue
```

---

## Índices

```
idx_event_template_stage

idx_event_template_order
```

---

## Relacionamentos

CompetitionStageTemplate

```
1 -> N CompetitionEventTemplate
```

CompetitionEventTemplate

```
N -> 1 Venue
```

StageSlotTemplate

```
1 -> N CompetitionEventTemplate
```

---

# Resumo

As Rule Tables são compostas pelas seguintes entidades:

```
competition_engine

competition_rule

competition_stage_template

stage_slot_template

competition_event_template
```

Essas tabelas definem toda a estrutura reutilizável de uma competição.

Nenhuma delas representa execução.

Toda CompetitionEdition é criada a partir dessas definições.

---

# 25. Runtime Tables

As Runtime Tables representam uma CompetitionEdition em execução.

Todas as informações presentes nessas tabelas pertencem exclusivamente a uma edição específica.

Elas representam:

- participantes inscritos;
- fases criadas;
- slots resolvidos;
- eventos competitivos;
- participantes envolvidos em eventos.

Nenhuma dessas tabelas representa uma definição reutilizável.

As definições pertencem às tabelas de Template.

---

# 26. competition_entry

## Responsabilidade

Representa a inscrição de um Participant em uma CompetitionEdition.

Uma CompetitionEntry representa a participação de um participante específico dentro de uma edição de campeonato.

Exemplos:

- Brasil na Copa do Mundo 2026;
- Ferrari na Fórmula 1 2026;
- Carro #51 no WEC 2025.

---

## Herança

```
BaseEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|---|---|---|---|
| id | BIGINT | Não | PK |
| competition_edition_id | BIGINT | Não | FK |
| participant_id | BIGINT | Não | FK |
| seed | INTEGER | Sim | Cabeça de chave |
| registration_number | VARCHAR(50) | Sim | Número oficial |
| status | VARCHAR(30) | Não | Enum |
| registered_at | TIMESTAMP WITH TIME ZONE | Sim | |

---

## Constraints

```
pk_competition_entry

fk_competition_entry_edition

fk_competition_entry_participant

uk_competition_entry_participant
```

---

## Índices

```
idx_competition_entry_edition

idx_competition_entry_participant

idx_competition_entry_status
```

---

## Observação

Um Participant pode participar de diversas CompetitionEditions.

Porém, uma CompetitionEdition possui apenas uma CompetitionEntry para cada Participant.

---

# 27. competition_stage

## Responsabilidade

Representa uma fase real dentro de uma CompetitionEdition.

Uma CompetitionStage é criada a partir de uma CompetitionStageTemplate.

---

## Herança

```
BaseEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|---|---|---|---|
| id | BIGINT | Não | PK |
| competition_edition_id | BIGINT | Não | FK |
| competition_stage_template_id | BIGINT | Não | FK |
| parent_stage_id | BIGINT | Sim | Auto relacionamento |
| name | VARCHAR(200) | Não | |
| stage_type | VARCHAR(40) | Não | Enum |
| display_order | INTEGER | Não | |
| status | VARCHAR(30) | Não | Enum |

---

## Constraints

```
pk_competition_stage

fk_stage_edition

fk_stage_template

fk_stage_parent
```

---

## Índices

```
idx_stage_edition

idx_stage_order

idx_stage_status
```

---

## Relacionamentos

CompetitionStage:

```
1 -> N StageSlot
```

CompetitionStage:

```
1 -> N CompetitionEvent
```

---

# 28. stage_slot

## Responsabilidade

Representa uma posição real dentro de uma CompetitionStage.

Um StageSlot inicialmente representa uma posição indefinida.

Após a resolução da fase, ele referencia uma CompetitionEntry.

---

## Herança

```
BaseEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|---|---|---|---|
| id | BIGINT | Não | PK |
| competition_stage_id | BIGINT | Não | FK |
| stage_slot_template_id | BIGINT | Não | FK |
| competition_entry_id | BIGINT | Sim | FK |
| slot_order | INTEGER | Não | |

---

## Constraints

```
pk_stage_slot

fk_stage_slot_stage

fk_stage_slot_template

fk_stage_slot_entry
```

---

## Índices

```
idx_stage_slot_stage

idx_stage_slot_entry
```

---

## Observação

Um StageSlot pode permanecer sem CompetitionEntry quando a definição do participante depender de um resultado futuro.

Exemplo:

```
Winner Group A
```

antes do término da fase de grupos.

---

# 29. competition_event

## Responsabilidade

Representa uma unidade competitiva dentro de uma CompetitionEdition.

Um CompetitionEvent não assume quantidade fixa de participantes.

A participação dos competidores é registrada através de CompetitionEventParticipant.

---

## Exemplos

Um evento pode representar:

- partida de futebol;
- corrida de Fórmula 1;
- prova do WEC;
- bateria;
- etapa;
- disputa com múltiplos participantes.

---

## Herança

```
BaseEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|---|---|---|---|
| id | BIGINT | Não | PK |
| competition_edition_id | BIGINT | Não | FK |
| competition_stage_id | BIGINT | Não | FK |
| competition_event_template_id | BIGINT | Sim | FK |
| venue_id | BIGINT | Sim | FK |
| event_order | INTEGER | Não | |
| status | VARCHAR(30) | Não | Enum |
| scheduled_at | TIMESTAMP WITH TIME ZONE | Sim | |
| started_at | TIMESTAMP WITH TIME ZONE | Sim | |
| finished_at | TIMESTAMP WITH TIME ZONE | Sim | |

---

## Constraints

```
pk_competition_event

fk_event_edition

fk_event_stage

fk_event_template

fk_event_venue
```

---

## Índices

```
idx_event_stage

idx_event_status

idx_event_schedule

idx_event_edition
```

---

## Observação

CompetitionEvent não armazena:

- placar;
- vencedor;
- posição final;
- estatísticas.

Essas informações são derivadas dos fatos registrados no evento.

---

# 30. competition_event_participant

## Responsabilidade

Representa a participação de uma CompetitionEntry em um CompetitionEvent.

Essa entidade permite que qualquer esporte seja representado sem assumir uma estrutura fixa.

---

## Exemplos

### Futebol

```
Brasil

role = HOME
```

```
Argentina

role = AWAY
```

---

### Fórmula 1

```
Red Bull Car #1

role = COMPETITOR
```

```
Ferrari Car #16

role = COMPETITOR
```

---

### Atletismo

```
Atleta A

lane = 4
```

---

## Herança

```
BaseEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|---|---|---|---|
| id | BIGINT | Não | PK |
| competition_event_id | BIGINT | Não | FK |
| competition_entry_id | BIGINT | Não | FK |
| stage_slot_id | BIGINT | Sim | FK |
| participant_role | VARCHAR(40) | Não | Enum |
| lane | INTEGER | Sim | Raia, grid ou posição inicial |
| result_status | VARCHAR(40) | Sim | Enum |

---

## Constraints

```
pk_competition_event_participant

fk_event_participant_event

fk_event_participant_entry

fk_event_participant_stage_slot
```

---

## Índices

```
idx_event_participant_event

idx_event_participant_entry

idx_event_participant_status
```

---

## Result Status

Exemplos:

```
FINISHED

DNF

DNS

DISQUALIFIED

WITHDRAWN
```

---

## Observação

Informações como:

- posição final;
- pontuação obtida;
- tempo;
- diferença para vencedor;

não pertencem a esta tabela.

Essas informações serão armazenadas ou calculadas através das tabelas de resultados e estatísticas.

---

# Resumo

As Runtime Tables são:

```
competition_entry

competition_stage

stage_slot

competition_event

competition_event_participant
```

Essas tabelas representam uma CompetitionEdition em execução.

A estrutura permanece genérica para suportar:

- esportes com dois participantes;
- esportes individuais;
- esportes com múltiplos participantes;
- esportes com equipes;
- esportes com veículos como participantes.

---

# 31. Event Fact Tables

As Event Fact Tables representam fatos ocorridos durante uma CompetitionEvent.

Essas tabelas são a fonte primária para cálculos estatísticos e classificações derivadas.

Um fato registrado nunca deve ser sobrescrito para atualizar uma estatística.

As estatísticas devem ser recalculadas a partir dos fatos armazenados.

---

# 32. event_action

## Responsabilidade

Representa uma ação ocorrida durante um CompetitionEvent.

Uma ação pode representar qualquer ocorrência relevante dentro de uma competição.

---

## Exemplos

### Futebol

```
GOAL

YELLOW_CARD

RED_CARD

SUBSTITUTION

PENALTY
```

---

### Fórmula 1

```
LAP_COMPLETED

PIT_STOP

PENALTY

FASTEST_LAP

POSITION_CHANGE
```

---

### WEC

```
PIT_STOP

DRIVER_CHANGE

PENALTY

LAP_COMPLETED
```

---

## Herança

```
BaseEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|---|---|---|---|
| id | BIGINT | Não | PK |
| competition_event_id | BIGINT | Não | FK |
| competition_event_participant_id | BIGINT | Sim | Participante envolvido |
| participant_id | BIGINT | Sim | Participante global |
| action_type | VARCHAR(50) | Não | Enum |
| occurred_at | TIMESTAMP WITH TIME ZONE | Sim | Momento da ação |
| sequence_number | INTEGER | Sim | Ordem dentro do evento |
| value | NUMERIC | Sim | Valor numérico opcional |
| metadata | JSONB | Sim | Dados específicos |

---

## Constraints

```
pk_event_action

fk_event_action_event

fk_event_action_event_participant

fk_event_action_participant
```

---

## Índices

```
idx_event_action_event

idx_event_action_type

idx_event_action_participant

idx_event_action_occured_at
```

---

# 33. event_action_type

## Responsabilidade

Define os tipos possíveis de ações registradas.

---

## Estratégia

O valor será armazenado como:

```
VARCHAR
```

e validado pela aplicação.

---

## Exemplos

```
GOAL

ASSIST

YELLOW_CARD

RED_CARD

PENALTY

LAP_COMPLETED

PIT_STOP

POSITION_CHANGE
```

---

## Observação

A lista de ações deve evoluir conforme novas CompetitionEngines forem adicionadas.

---

# 34. event_action_metadata

## Responsabilidade

Os detalhes específicos de uma ação ficam armazenados em JSONB.

A tabela `event_action` mantém apenas os campos comuns.

---

## Exemplos

---

## Gol no futebol

```json
{
  "minute": 74,
  "assist_player_id": 123
}
```

---

## Pit stop na Fórmula 1

```json
{
  "lap": 32,
  "duration_seconds": 2.4,
  "tyre_compound": "soft"
}
```

---

## Penalização

```json
{
  "reason": "TRACK_LIMITS",
  "seconds": 5
}
```

---

## Objetivo

Permitir que novos esportes adicionem informações específicas sem alterar o schema principal.

---

# 35. participant_event_role

## Responsabilidade

Representa o papel de um Participant dentro de uma ação.

---

## Exemplos

Um gol pode possuir:

```
SCORER

ASSIST
```

Uma corrida pode possuir:

```
DRIVER

CAR

TEAM
```

---

## Observação

Inicialmente pode ser representado dentro do JSON metadata.

Caso a complexidade aumente, pode evoluir para uma entidade própria.

---

# 36. event_action_relation

## Responsabilidade

Representa relacionamento entre ações.

Alguns fatos dependem de outros fatos.

---

## Exemplos

```
GOAL

↓

ASSIST

```

ou

```
PENALTY

↓

APPEAL

```

---

## Status

Reservado para evolução futura.

Inicialmente não será implementado.

---

# 37. Dados Históricos Incompletos

O modelo suporta eventos históricos sem detalhamento completo.

Exemplo:

Copa do Mundo de 1970:

Disponível:

```
Brasil 4 x 1 Itália
```

Mas sem:

```
jogador do gol

cartões

substituições
```

Nesse caso:

```
competition_event
        |
        |
event_action
```

pode existir apenas parcialmente.

---

## Exemplo

Existe:

```
GOAL
```

Sem:

```
competition_event_participant_id
```

---

Isso permite:

- armazenar resultados antigos;
- calcular estatísticas disponíveis;
- preservar dados incompletos.

---

# Resumo

As Event Fact Tables são:

```
event_action
```

Essas tabelas representam fatos imutáveis ocorridos durante eventos esportivos.

Todas as estatísticas futuras devem ser calculadas a partir desses fatos.

---

# 38. Statistics Tables

As Statistics Tables representam informações derivadas dos fatos registrados no sistema.

Essas tabelas não são a fonte primária dos dados.

Todos os valores podem ser reconstruídos através de:

- CompetitionEvent;
- EventAction;
- resultados dos eventos;
- CompetitionRules.

---

# 39. participant_statistic

## Responsabilidade

Representa uma estatística agregada de um Participant dentro de uma CompetitionEdition.

---

## Exemplos

Futebol:

```
Goals

Yellow Cards

Red Cards

Matches Played
```

---

Fórmula 1:

```
Wins

Podiums

Points

Fastest Laps
```

---

WEC:

```
Wins

Podiums

Completed Laps
```

---

## Herança

```
BaseEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|---|---|---|---|
| id | BIGINT | Não | PK |
| competition_edition_id | BIGINT | Não | FK |
| competition_entry_id | BIGINT | Não | FK |
| statistic_type | VARCHAR(50) | Não | Enum |
| value | NUMERIC | Não | Valor acumulado |
| calculated_at | TIMESTAMP WITH TIME ZONE | Não | Último cálculo |

---

## Constraints

```
pk_participant_statistic

fk_participant_statistic_edition

fk_participant_statistic_entry

uk_participant_statistic_type
```

---

## Índices

```
idx_participant_statistic_edition

idx_participant_statistic_entry

idx_participant_statistic_type
```

---

## Observação

A mesma CompetitionEntry pode possuir diversas estatísticas.

Exemplo:

```
Brazil

GOALS = 12

YELLOW_CARDS = 4

MATCHES_PLAYED = 7
```

---

# 40. classification

## Responsabilidade

Representa uma classificação dentro de uma CompetitionEdition.

Uma competição pode possuir múltiplas classificações.

---

## Exemplos

Futebol:

```
Tabela do campeonato

Artilharia

Fair Play
```

---

Fórmula 1:

```
Drivers Championship

Constructors Championship
```

---

WEC:

```
Drivers Championship

Manufacturers Championship
```

---

## Herança

```
BaseEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|---|---|---|---|
| id | BIGINT | Não | PK |
| competition_edition_id | BIGINT | Não | FK |
| name | VARCHAR(200) | Não | |
| classification_type | VARCHAR(50) | Não | Enum |
| status | VARCHAR(30) | Não | Enum |
| calculated_at | TIMESTAMP WITH TIME ZONE | Não | |

---

## Constraints

```
pk_classification

fk_classification_edition
```

---

## Índices

```
idx_classification_edition

idx_classification_type
```

---

# 41. classification_entry

## Responsabilidade

Representa um participante dentro de uma classificação.

---

## Herança

```
BaseEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|---|---|---|---|
| id | BIGINT | Não | PK |
| classification_id | BIGINT | Não | FK |
| competition_entry_id | BIGINT | Não | FK |
| position | INTEGER | Não | Posição atual |
| points | NUMERIC | Não | Pontuação |
| wins | INTEGER | Sim | |
| ties | INTEGER | Sim | |
| losses | INTEGER | Sim | |
| additional_data | JSONB | Sim | Dados específicos |

---

## Constraints

```
pk_classification_entry

fk_classification_entry_classification

fk_classification_entry_competition_entry

uk_classification_entry_participant
```

---

## Índices

```
idx_classification_entry_classification

idx_classification_entry_position

idx_classification_entry_points
```

---

# 42. Classification Calculation

## Responsabilidade

A classificação é calculada utilizando:

```
CompetitionRule

+

CompetitionEngine

+

CompetitionEvents

+

EventActions
```

---

## Exemplo

Campeonato Brasileiro:

Eventos:

```
Vitória

Empate

Derrota

Gols
```

Regra:

```
Vitória = 3 pontos

Empate = 1 ponto

Derrota = 0 pontos
```

Resultado:

```
ClassificationEntry.points
```

---

# 43. Estatísticas não materializadas

O sistema deve permitir consultas sem persistir estatísticas.

Exemplo:

Pergunta:

```
Quem fez mais gols na Copa de 1970?
```

Fluxo:

```
CompetitionEvent

↓

EventAction

↓

COUNT(GOAL)

↓

Resultado
```

---

# 44. Materialização futura

Quando necessário, as tabelas:

```
participant_statistic

classification_entry
```

podem ser atualizadas por:

- jobs;
- eventos de domínio;
- filas;
- triggers.

---

## Exemplo

Novo gol:

```
EventAction(GOAL)
```

Evento:

```
GoalRegistered
```

Consumidor:

```
StatisticsUpdater
```

Atualiza:

```
participant_statistic
```

---

# Resumo

As Statistics Tables são:

```
participant_statistic

classification

classification_entry
```

Elas representam informações calculadas e podem ser reconstruídas a qualquer momento a partir dos fatos.

---

# 45. Classification Metric Tables

As Classification Metric Tables representam os valores utilizados para construir e ordenar uma classificação.

Uma Classification não possui regras fixas de pontuação.

As métricas utilizadas são definidas pela CompetitionRule associada à CompetitionEdition.

---

# 46. classification_metric

## Responsabilidade

Representa uma métrica de uma CompetitionEntry dentro de uma Classification.

Uma ClassificationEntry pode possuir diversas métricas.

---

## Exemplos

### Campeonato Brasileiro

```
Flamengo

POINTS = 75

WINS = 22

GOAL_DIFFERENCE = 35

GOALS_FOR = 60
```

---

### Fórmula 1

```
Max Verstappen

POINTS = 350

WINS = 8

PODIUMS = 15

FASTEST_LAPS = 4
```

---

### WEC

```
Car #51

POINTS = 180

WINS = 5

PODIUMS = 8
```

---

## Herança

```
BaseEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|---|---|---|---|
| id | BIGINT | Não | PK |
| classification_entry_id | BIGINT | Não | FK |
| metric_type | VARCHAR(50) | Não | Enum |
| value | NUMERIC | Não | Valor calculado |
| calculated_at | TIMESTAMP WITH TIME ZONE | Não | Última atualização |

---

## Constraints

```
pk_classification_metric

fk_classification_metric_entry

uk_classification_metric_type
```

---

## Índices

```
idx_classification_metric_entry

idx_classification_metric_type
```

---

# 47. classification_metric_type

## Responsabilidade

Define os tipos de métricas disponíveis para uma classificação.

---

## Estratégia

Será armazenado como:

```
VARCHAR
```

Validado pela aplicação.

---

## Exemplos

```
POINTS

WINS

LOSSES

DRAWS

GOALS_FOR

GOALS_AGAINST

GOAL_DIFFERENCE

PODIUMS

FASTEST_LAPS

LAPS_COMPLETED

TIME_TOTAL
```

---

## Observação

Novos tipos podem ser adicionados conforme novas CompetitionEngines forem criadas.

---

# 48. Ranking Configuration

## Responsabilidade

Define como uma Classification deve ordenar seus participantes.

Essa configuração pertence à CompetitionRule.

---

## Exemplo

Brasileirão:

```json
{
  "classification": {
    "metrics": [
      {
        "type": "POINTS",
        "order": "DESC"
      },
      {
        "type": "WINS",
        "order": "DESC"
      },
      {
        "type": "GOAL_DIFFERENCE",
        "order": "DESC"
      },
      {
        "type": "GOALS_FOR",
        "order": "DESC"
      }
    ]
  }
}
```

---

## Exemplo

Fórmula 1:

```json
{
  "classification": {
    "metrics": [
      {
        "type": "POINTS",
        "order": "DESC"
      },
      {
        "type": "WINS",
        "order": "DESC"
      }
    ]
  }
}
```

---

# 49. Classification Generation

## Responsabilidade

A CompetitionEngine é responsável por gerar uma Classification.

O processo:

```
CompetitionEvent

↓

EventAction

↓

Calculation Engine

↓

ClassificationMetric

↓

ClassificationEntry Position
```

---

## Exemplo

Novo resultado:

```
Brasil venceu Argentina
```

Eventos:

```
CompetitionEvent

EventAction(GOAL)

Event Result
```

Processamento:

```
POINTS +3

WINS +1

GOALS_FOR +2
```

Atualização:

```
ClassificationMetric
```

---

# 50. Materialização

ClassificationMetric pode existir em dois modos:

## Dinâmico

Calculado em tempo real:

```
query events

↓

calculate metrics

↓

return classification
```

---

## Materializado

Atualizado por:

- eventos de domínio;
- jobs;
- processamento assíncrono.

---

## Objetivo

Permitir:

- páginas live;
- dashboards;
- consultas históricas rápidas.

---

# Resumo

As Classification Metric Tables são:

```
classification

classification_entry

classification_metric
```

Elas permitem representar qualquer sistema de classificação sem assumir regras específicas de um esporte.

---

# 51. Competition Rules Execution

A CompetitionRule define como uma CompetitionEdition deve ser calculada.

Ela não contém código.

Ela contém configuração consumida pela CompetitionEngine.

---

# 52. Responsibility Separation

O sistema possui três camadas:

```
CompetitionRule

↓

CompetitionEngine

↓

Competition Result
```

---

## CompetitionRule

Responsabilidade:

Armazenar configuração.

Exemplos:

- sistema de pontuação;
- critérios de desempate;
- quantidade de participantes;
- estrutura das fases;
- regras de avanço.

---

## CompetitionEngine

Responsabilidade:

Executar regras.

Exemplos:

- calcular pontos;
- gerar classificação;
- definir vencedor;
- criar próximas fases.

---

## Result

Responsabilidade:

Representar o estado calculado.

Exemplos:

- classificação atual;
- participantes classificados;
- campeão.

---

# 53. Competition Engine Architecture

Cada CompetitionEngine possui uma implementação específica.

Exemplos:

```
LeagueEngine

KnockoutEngine

RaceEngine

HybridEngine
```

---

## LeagueEngine

Usado em competições por pontos corridos.

Exemplos:

- Brasileirão;
- campeonatos nacionais.

Responsabilidades:

- calcular pontos;
- calcular tabela;
- aplicar desempates.

---

## KnockoutEngine

Usado em competições eliminatórias.

Exemplos:

- Copa do Mundo;
- Champions League.

Responsabilidades:

- gerar confrontos;
- determinar classificados;
- criar próximas fases.

---

## RaceEngine

Usado em esportes de corrida.

Exemplos:

- Fórmula 1;
- WEC.

Responsabilidades:

- calcular pontos por posição;
- separar pilotos, carros e equipes;
- considerar abandonos e penalizações.

---

# 54. Rule Configuration

A configuração é armazenada em JSONB.

Exemplo:

```json
{
  "scoring": {
    "win": 3,
    "draw": 1,
    "loss": 0
  }
}
```

---

# 55. League Configuration

Exemplo Brasileirão:

```json
{
  "type": "LEAGUE",

  "rounds": 2,

  "classification": {
    "metrics": [
      {
        "type": "POINTS",
        "order": "DESC"
      },
      {
        "type": "WINS",
        "order": "DESC"
      },
      {
        "type": "GOAL_DIFFERENCE",
        "order": "DESC"
      }
    ]
  }
}
```

---

# 56. Knockout Configuration

Exemplo Copa do Mundo:

```json
{
  "type": "KNOCKOUT",

  "stages": [
    {
      "name": "Round of 32",
      "participants": 32
    },
    {
      "name": "Round of 16",
      "participants": 16
    },
    {
      "name": "Quarter Finals",
      "participants": 8
    },
    {
      "name": "Semi Finals",
      "participants": 4
    },
    {
      "name": "Final",
      "participants": 2
    }
  ]
}
```

---

# 57. Advancement Rules

A CompetitionStage define como participantes avançam.

Exemplo:

Grupo:

```
Group A

1st place
2nd place

↓

Round of 16
```

Configuração:

```json
{
  "advancement": {
    "type": "POSITION",

    "positions": [
      1,
      2
    ]
  }
}
```

---

Exemplo:

Eliminatória:

```
Match Winner

↓

Next Stage
```

Configuração:

```json
{
  "advancement": {
    "type": "EVENT_WINNER"
  }
}
```

---

# 58. Points Configuration

Pontuação deve ser configurável.

Exemplo Fórmula 1:

```json
{
  "points": [
    {
      "position": 1,
      "value": 25
    },
    {
      "position": 2,
      "value": 18
    },
    {
      "position": 3,
      "value": 15
    }
  ]
}
```

---

Exemplo MotoGP:

```json
{
  "points": [
    {
      "position": 1,
      "value": 25
    }
  ]
}
```

---

# 59. Entity Relationship

A relação entre configuração e execução:

```
Competition

    |

CompetitionTemplate

    |

CompetitionRule

    |

CompetitionEngine

    |

CompetitionEdition

    |

Classification
```

---

# 60. Código vs Configuração

Nem toda regra deve ser configurável.

---

## Configuração

Exemplos:

- pontos;
- número de fases;
- quantidade de classificados;
- ordem de desempate.

---

## Código

Exemplos:

- cálculo de melhor terceiro colocado;
- regras complexas de grid;
- regulamentos específicos de corrida;
- exceções históricas.

---

## Princípio

O sistema deve permitir:

```
Adicionar uma nova competição

sem alterar o banco

e sem criar uma nova versão da aplicação

quando a regra já é suportada.
```

---

# 61. Participant Tables

As Participant Tables representam qualquer entidade que possa participar de uma competição.

O modelo não diferencia previamente entre:

- equipe;
- jogador;
- piloto;
- carro;
- fabricante;
- seleção;
- indivíduo.

Todos são representados como Participant.

A diferenciação ocorre através de:

- participant_type;
- relações entre participantes;
- regras da competição.

---

# 62. participant

## Responsabilidade

Representa uma entidade participante dentro do Sports Engine.

Um Participant pode existir independentemente de uma competição.

---

## Exemplos

### Futebol

```
Brazil National Team

Real Madrid

Player X
```

---

### Fórmula 1

```
Max Verstappen

Red Bull Racing

RB21 Car #1
```

---

### WEC

```
Ferrari AF Corse

Car #51

Driver X
```

---

## Herança

```
BaseEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|---|---|---|---|
| id | BIGINT | Não | PK |
| sport_id | BIGINT | Sim | Esporte principal |
| participant_type | VARCHAR(50) | Não | Enum |
| name | VARCHAR(200) | Não | Nome oficial |
| short_name | VARCHAR(100) | Sim | Nome abreviado |
| acronym | VARCHAR(20) | Sim | |
| location_id | BIGINT | Sim | Origem |
| active | BOOLEAN | Não | |

---

## Constraints

```
pk_participant

fk_participant_sport

fk_participant_location
```

---

## Índices

```
idx_participant_sport

idx_participant_type

idx_participant_name
```

---

# 63. participant_type

## Responsabilidade

Define o tipo de um Participant.

---

## Estratégia

Armazenado como:

```
VARCHAR
```

validado pela aplicação.

---

## Exemplos

```
PERSON

TEAM

CAR

MANUFACTURER

NATIONAL_TEAM

CLUB

CONSTRUCTOR

OTHER
```

---

## Observação

Novos tipos não devem exigir alteração estrutural no banco.

---

# 64. participant_membership

## Responsabilidade

Representa relações entre Participants.

Essa entidade permite modelar hierarquias e associações.

---

## Exemplos

### Fórmula 1

```
Driver

belongs_to

Car
```

```
Car

belongs_to

Constructor
```

---

### Futebol

```
Player

belongs_to

Club
```

---

### WEC

```
Driver

participates_for

Car
```

---

## Herança

```
BaseEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|---|---|---|---|
| id | BIGINT | Não | PK |
| parent_participant_id | BIGINT | Não | Participante origem |
| child_participant_id | BIGINT | Não | Participante destino |
| membership_type | VARCHAR(50) | Não | Enum |
| valid_from | DATE | Sim | Início da relação |
| valid_until | DATE | Sim | Fim da relação |

---

## Constraints

```
pk_participant_relation

fk_relation_source

fk_relation_target

ck_relation_different_participants
```

---

## Índices

```
idx_participant_membership_parent_participant

idx_participant_membership_child_participant

idx_participant_membership_membership_type
```

---

# 65. participant_relation_type

## Responsabilidade

Define os tipos de relações possíveis entre participantes.

---

## Exemplos

```
MEMBER_OF

OWNS

DRIVES

USES

REPRESENTS

MANUFACTURED_BY

PART_OF
```

---

## Exemplos reais

F1:

```
Driver

DRIVES

Car
```

```
Car

MANUFACTURED_BY

Constructor
```

---

Futebol:

```
Player

MEMBER_OF

Club
```

---

# 66. Competition Participation

A participação em uma competição nunca ocorre diretamente através de Participant.

Ela sempre ocorre através de:

```
Participant

        |

CompetitionEntry

        |

CompetitionEdition
```

---

## Motivo

O mesmo Participant pode possuir diferentes estados em diferentes competições.

Exemplo:

```
Ferrari

CompetitionEdition:
F1 2025

status = ACTIVE


Ferrari

CompetitionEdition:
F1 2026

status = ACTIVE
```

---

# 67. Participant Hierarchy Example

Exemplo Fórmula 1:

```
Constructor

Ferrari

    |
    |
    +---- Car #16
    |
    +---- Car #55


Driver

Charles Leclerc

    |
    |
    drives

    |
    |
Car #16
```

---

Exemplo WEC:

```
Manufacturer

Ferrari

    |
    |
Car #51

    |
    |
Driver A

Driver B
```

---

# 68. Historical Relations

As relações possuem período de validade.

Exemplo:

```
Driver A

DRIVES

Car X

2025-01-01

até

2025-12-31
```

Isso permite preservar histórico.

---

# Resumo

As Participant Tables são:

```
participant

participant_relation
```

Elas permitem representar qualquer entidade participante sem criar tabelas específicas por esporte.

---

# 69. Infrastructure Tables

As Infrastructure Tables representam entidades físicas e geográficas utilizadas pelas competições.

Elas não possuem regras esportivas.

Sua responsabilidade é fornecer informações de localização e infraestrutura.

---

# 70. location

## Responsabilidade

Representa uma localização geográfica.

Pode representar:

- país;
- estado;
- cidade;
- região.

---

## Exemplos

```
Brazil

São Paulo

Monza, Italy
```

---

## Herança

```
BaseEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|---|---|---|---|
| id | BIGINT | Não | PK |
| parent_location_id | BIGINT | Sim | Hierarquia |
| location_type | VARCHAR(40) | Não | Enum |
| name | VARCHAR(200) | Não | |
| country_code | VARCHAR(10) | Sim | ISO |
| latitude | NUMERIC(10,7) | Sim | |
| longitude | NUMERIC(10,7) | Sim | |

---

## Constraints

```
pk_location

fk_location_parent
```

---

## Índices

```
idx_location_parent

idx_location_type

idx_location_name
```

---

## Relacionamentos

Location:

```
1 -> N Location
```

Exemplo:

```
Brazil

 └── São Paulo

      └── São Paulo City
```

---

# 71. location_type

## Responsabilidade

Define o nível geográfico de uma localização.

---

## Valores possíveis

```
COUNTRY

STATE

CITY

REGION

OTHER
```

---

# 72. venue

## Responsabilidade

Representa uma instalação física onde eventos podem ocorrer.

---

## Exemplos

Futebol:

```
Maracanã
```

---

Fórmula 1:

```
Autódromo de Interlagos
```

---

WEC:

```
Circuit de la Sarthe
```

---

## Herança

```
SoftDeleteEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|---|---|---|---|
| id | BIGINT | Não | PK |
| location_id | BIGINT | Não | FK |
| name | VARCHAR(200) | Não | |
| venue_type | VARCHAR(50) | Não | Enum |
| capacity | INTEGER | Sim | |
| length_meters | NUMERIC | Sim | Circuitos |
| active | BOOLEAN | Não | |

---

## Constraints

```
pk_venue

fk_venue_location
```

---

## Índices

```
idx_venue_location

idx_venue_type

idx_venue_name
```

---

# 73. venue_type

## Responsabilidade

Define o tipo da instalação esportiva.

---

## Valores possíveis

```
STADIUM

CIRCUIT

ARENA

GYMNASIUM

COURT

TRACK

OTHER
```

---

# 74. Venue Configuration

Alguns esportes possuem informações específicas sobre o local.

Essas informações não devem ser adicionadas diretamente na tabela venue.

---

## Exemplo

Circuito:

```json
{
  "laps": 53,
  "track_length_km": 5.793
}
```

---

Estádio:

```json
{
  "field_surface": "GRASS",
  "roof": false
}
```

---

Caso necessário, pode ser criado futuramente:

```
venue_configuration
```

---

# 75. Event Location

A relação entre evento e local ocorre através de:

```
CompetitionEvent

        |

Venue
```

---

## Motivo

O mesmo Venue pode receber:

- diversas competições;
- diversas temporadas;
- diversos eventos.

---

# 76. Historical Data

Venue e Location devem preservar histórico.

Exemplo:

```
Autódromo

recebeu

Formula 1 1990

Formula 1 2026
```

O evento histórico deve continuar apontando para o mesmo local.

---

# Resumo

As Infrastructure Tables são:

```
location

venue
```

Elas fornecem suporte geográfico e físico para todas as modalidades esportivas.

---

# 77. Database Constraints and Integrity Rules

Este documento define as regras gerais de integridade do banco de dados.

As constraints devem garantir consistência estrutural.

As regras de negócio complexas permanecem na aplicação através das CompetitionEngines.

---

# 78. Primary Keys

Todas as entidades devem possuir uma chave primária.

Padrão:

```
id BIGINT
```

---

## Estratégia

O identificador deve ser gerado pelo banco.

Exemplo PostgreSQL:

```
GENERATED BY DEFAULT AS IDENTITY
```

---

## Motivo

Permitir:

- alto volume de registros;
- inserções concorrentes;
- simplicidade no backend.

---

# 79. Base Entity

Todas as tabelas principais devem possuir:

```
created_at

updated_at

created_by

updated_by
```

---

## Tipos

```
created_at TIMESTAMP WITH TIME ZONE NOT NULL

updated_at TIMESTAMP WITH TIME ZONE NOT NULL

created_by UUID NULL

updated_by UUID NULL
```

---

## Observação

`created_by` e `updated_by` referenciam usuários de um serviço externo.

O banco não possui Foreign Key.

---

# 80. Soft Delete

Entidades de configuração e cadastro devem suportar exclusão lógica.

Exemplos:

```
participant

venue

competition_template
```

---

## Coluna

```
deleted_at TIMESTAMP WITH TIME ZONE NULL
```

---

## Regra

Dados históricos nunca devem ser apagados.

---

## Exemplos

Permitido:

```
Desativar um estádio
```

Não permitido:

```
Apagar um evento histórico da Copa do Mundo
```

---

# 81. Historical Data Protection

Dados relacionados a eventos finalizados devem ser preservados.

Exemplo:

```
CompetitionEvent

FINISHED
```

não deve ser removido.

---

## Tabelas protegidas

```
competition_event

event_action

competition_entry

classification
```

---

# 82. Unique Constraints

## Competition Entry

Um participante não pode ser registrado duas vezes na mesma edição.

```
UNIQUE(
    competition_edition_id,
    participant_id
)
```

---

## Classification Metric

Uma métrica deve existir apenas uma vez por classificação.

```
UNIQUE(
    classification_entry_id,
    metric_type
)
```

---

## Participant Relation

Evitar relacionamento duplicado:

```
UNIQUE(
    source_participant_id,
    target_participant_id,
    relation_type,
    valid_from
)
```

---

# 83. Foreign Key Rules

## Configuração

Exemplo:

```
CompetitionTemplate

↓

CompetitionRule
```

Delete:

```
RESTRICT
```

---

## Histórico

Exemplo:

```
CompetitionEvent

↓

EventAction
```

Delete:

```
RESTRICT
```

---

## Dependências temporárias

Exemplo:

```
StageSlot

↓

CompetitionEntry
```

Delete:

```
SET NULL
```

---

# 84. Enum Storage

Todos os enums serão armazenados como:

```
VARCHAR
```

---

## Motivo

Evitar migrations frequentes para pequenas alterações.

Exemplo:

Adicionar:

```
SPRINT_RACE
```

não deve exigir alteração de schema.

---

## Validação

Responsabilidade:

- aplicação;
- testes;
- migrations de dados quando necessário.

---

# 85. JSONB Usage Rules

JSONB será utilizado apenas para dados extensíveis.

---

## Permitido

Exemplo:

```
event_action.metadata
```

Dados específicos de esporte.

---

## Não permitido

Dados pesquisados frequentemente.

Exemplo:

Errado:

```json
{
  "participant_id":123
}
```

Correto:

```
participant_id BIGINT
```

---

# 86. Index Strategy

## Todas as Foreign Keys

Devem possuir índice.

Exemplo:

```
competition_event.competition_stage_id
```

---

## Tabelas de alto volume

Prioridade:

```
event_action

competition_event_participant
```

---

## Índices temporais

Adicionar índices para consultas:

```
occurred_at

scheduled_at

created_at
```

---

# 87. Event Action Scaling

A tabela:

```
event_action
```

é potencialmente a maior tabela do sistema.

---

## Estratégias futuras

Possíveis otimizações:

### Particionamento por temporada

Exemplo:

```
event_action_2026
event_action_2025
```

---

### Particionamento por competição

Exemplo:

```
event_action_world_cup
event_action_formula1
```

---

### TimescaleDB

Possível evolução caso o volume justifique.

---

# 88. Transaction Rules

Operações críticas devem ser transacionais.

---

## Exemplo

Final de partida:

```
Update CompetitionEvent

+

Insert EventAction

+

Update ClassificationMetric
```

Tudo dentro da mesma transação.

---

# 89. Consistency Rules

O banco deve garantir:

---

## Um evento pertence a uma fase

```
CompetitionEvent

must have

CompetitionStage
```

---

## Uma fase pertence a uma edição

```
CompetitionStage

must have

CompetitionEdition
```

---

## Uma classificação pertence a uma edição

```
Classification

must have

CompetitionEdition
```

---

## Uma ação pertence a um evento

```
EventAction

must have

CompetitionEvent
```

---

# Resumo

As principais regras de banco são:

```
Primary Keys

Foreign Keys

Unique Constraints

Soft Delete

Historical Protection

Indexes

JSONB Rules

Transaction Boundaries
```

O banco garante integridade estrutural.

A lógica esportiva permanece na CompetitionEngine.

---

# 90. Competition Execution Support

Esta seção define as entidades responsáveis por armazenar como uma CompetitionEdition deve ser executada.

O banco não contém a implementação das regras esportivas.

Ele contém:

- identificação da estratégia de cálculo;
- configuração das regras;
- parâmetros necessários para execução.

A execução é responsabilidade da aplicação.

---

# 91. competition_engine

## Responsabilidade

Representa uma estratégia de execução de competição suportada pelo sistema.

Uma CompetitionEngine define qual tipo de processamento será utilizado para calcular resultados, classificações e progressões.

---

## Exemplos

```
LEAGUE_ENGINE

KNOCKOUT_ENGINE

RACE_ENGINE

HYBRID_ENGINE
```

---

## Herança

```
BaseEntity
```

---

## Colunas

| Coluna | Tipo | Null | Observação |
|---|---|---|---|
| id | BIGINT | Não | PK |
| code | VARCHAR(50) | Não | Identificador único |
| name | VARCHAR(200) | Não | Nome da engine |
| description | TEXT | Sim | |
| active | BOOLEAN | Não | |

---

## Constraints

```
pk_competition_engine

uk_competition_engine_code
```

---

## Índices

```
idx_competition_engine_code

idx_competition_engine_active
```

---

# 92. Competition Engine Association

A CompetitionTemplate deve possuir uma engine responsável por sua execução.

Relacionamento:

```
CompetitionTemplate

        |

CompetitionEngine
```

---

## Regra

Uma CompetitionTemplate possui uma CompetitionEngine principal.

Exemplo:

```
World Cup Template

ENGINE = KNOCKOUT_ENGINE
```

---

```
Formula 1 Template

ENGINE = RACE_ENGINE
```

---

# 93. Competition Rule Configuration

As regras específicas de uma competição são armazenadas como configuração.

A configuração deve ser flexível para suportar diferentes esportes.

---

## Armazenamento

Formato:

```
JSONB
```

---

## Exemplos

---

## Pontuação de futebol

```json
{
  "scoring": {
    "win": 3,
    "draw": 1,
    "loss": 0
  }
}
```

---

## Pontuação de Fórmula 1

```json
{
  "points": [
    {
      "position": 1,
      "value": 25
    },
    {
      "position": 2,
      "value": 18
    }
  ]
}
```

---

## Estrutura de fases

```json
{
  "stages": [
    {
      "name": "Group Stage"
    },
    {
      "name": "Round of 16"
    },
    {
      "name": "Final"
    }
  ]
}
```

---

# 94. Configuration vs Logic

O sistema deve separar configuração de comportamento.

---

## Configuração armazenada no banco

Exemplos:

- quantidade de pontos;
- quantidade de participantes;
- fases existentes;
- ordem dos critérios de desempate;
- quantidade de classificados.

---

## Lógica implementada na aplicação

Exemplos:

- cálculo de melhor terceiro colocado;
- regras específicas de desempate;
- regulamentos históricos;
- exceções de campeonatos.

---

# 95. Competition Rule Resolution

O fluxo de execução:

```
CompetitionEdition

        |

CompetitionTemplate

        |

CompetitionRule

        |

CompetitionEngine

        |

Calculated Results
```

---

# 96. Engine Independence

O banco não deve conhecer detalhes de implementação.

Não deve existir:

```
java_class_name

package_name

implementation_reference
```

---

## Motivo

Permitir:

- trocar linguagem;
- trocar arquitetura;
- criar novos serviços;
- manter o banco independente.

---

# 97. Future Simulation Support

O modelo permite simulações.

Exemplo:

Copa do Mundo em andamento:

```
Current Events

↓

Update EventAction

↓

Recalculate Classification

↓

Generate Next Stage
```

---

Exemplo:

Simulação:

```
Assumir vitória do Brasil

↓

Recalcular grupo

↓

Gerar possíveis confrontos
```

---

# Resumo

As entidades relacionadas à execução são:

```
competition_engine

competition_rule
```

Elas armazenam a definição necessária para que uma CompetitionEdition possa ser calculada.

A execução das regras pertence à camada de aplicação.

---