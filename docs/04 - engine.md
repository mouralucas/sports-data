# Competition Engine

## 1. Objetivo

A Competition Engine é responsável por executar as regras de uma competição.

Ela transforma dados armazenados no sistema em resultados calculados, como:

* classificação;
* pontuação;
* participantes classificados;
* vencedores;
* avanço de fases;
* estatísticas derivadas.

A Engine não é responsável por armazenar dados.

Sua responsabilidade é interpretar uma configuração de competição e aplicar as regras correspondentes.

---

# 2. Responsabilidades

Uma Competition Engine deve ser capaz de:

* calcular resultados de eventos;
* aplicar sistemas de pontuação;
* atualizar classificações;
* determinar critérios de desempate;
* resolver progressão entre fases;
* gerar resultados intermediários;
* suportar simulações.

---

# 3. Separação entre Configuração e Execução

O sistema separa:

## Configuration

Representa dados configuráveis armazenados no banco.

Exemplos:

* quantidade de pontos por resultado;
* quantidade de classificados;
* fases existentes;
* critérios de desempate;
* ordem das métricas.

---

## Execution

Representa comportamento executado pela aplicação.

Exemplos:

* como calcular uma classificação;
* como interpretar um evento;
* como resolver uma fase eliminatória;
* como gerar a próxima etapa.

---

# 4. Conceito de Engine

Uma CompetitionEngine representa uma estratégia de cálculo.

Exemplos:

```
LEAGUE_ENGINE

KNOCKOUT_ENGINE

RACE_ENGINE

HYBRID_ENGINE
```

Cada engine possui uma responsabilidade específica.

---

# 5. Tipos de Engine

## 5.1 League Engine

Responsável por competições no formato liga.

Características:

* todos os participantes acumulam resultados;
* classificação contínua;
* pontos acumulados;
* critérios de desempate.

Exemplos:

* Brasileirão;
* Premier League;
* campeonatos nacionais.

Fluxo:

```
CompetitionEvent

        |

Event Result

        |

Classification Metrics

        |

Ranking
```

---

## 5.2 Knockout Engine

Responsável por competições eliminatórias.

Características:

* participantes são eliminados;
* fases possuem avanço;
* vencedor de confronto segue para próxima fase.

Exemplos:

* Copa do Mundo;
* Champions League.

Fluxo:

```
Stage

↓

Events

↓

Winners

↓

Next Stage
```

---

## 5.3 Race Engine

Responsável por competições de corrida.

Características:

* posição final gera pontuação;
* múltiplos participantes por evento;
* relações entre participantes;
* diferentes campeonatos derivados.

Exemplos:

* Fórmula 1;
* WEC.

Fluxo:

```
Race Event

↓

Participant Positions

↓

Points Calculation

↓

Championship Classification
```

---

# 6. Engine Selection

A seleção da engine é definida pela CompetitionTemplate.

Fluxo:

```
CompetitionTemplate

        |

CompetitionEngine

        |

CompetitionEdition

        |

Execution
```

---

Uma competição não escolhe manualmente como será calculada durante sua execução.

A engine pertence à definição da competição.

---

# 7. Engine Independence

Uma CompetitionEngine não deve conhecer detalhes de persistência.

Ela não deve depender diretamente de:

* tabelas;
* entidades JPA;
* controllers;
* APIs externas.

A engine recebe dados de domínio e retorna resultados calculados.

---

# 8. Princípio Fundamental

O banco define:

```
O que deve acontecer
```

A engine define:

```
Como calcular
```

Exemplo:

Banco:

```
Vitória = 3 pontos
Empate = 1 ponto
Derrota = 0 pontos
```

Engine:

```
Recebe resultado:

Brasil venceu Argentina

Calcula:

Brasil +3 pontos
Argentina +0 pontos
```

---

# 9. Engine Execution Lifecycle

Uma CompetitionEngine possui um ciclo de execução baseado em eventos.

A engine não mantém estado próprio.

O estado da competição é derivado dos dados persistidos.

---

# 10. Engine Input

A engine recebe todas as informações necessárias para calcular um estado da competição.

Entradas principais:

```
CompetitionEdition

CompetitionRule

CompetitionEvents

EventActions

CompetitionEntries

Previous Calculations
```

---

## Exemplo

Para calcular o Brasileirão:

Entrada:

```
CompetitionEdition
    |
    +-- Teams
    |
    +-- Matches
    |
    +-- Goals
    |
    +-- Rules
```

Saída:

```
League Classification
```

---

# 11. Engine Output

Uma execução da engine pode produzir diferentes resultados.

---

## 11.1 Classification Result

Representa uma classificação calculada.

Exemplo:

```
1. Flamengo
   Points: 75

2. Palmeiras
   Points: 72
```

---

## 11.2 Stage Result

Representa o resultado de uma fase.

Exemplo:

```
Group A

Brazil
Argentina

Qualified:
Brazil
Argentina
```

---

## 11.3 Event Result

Representa o resultado calculado de um evento.

Exemplo:

```
Brazil 2 x 1 Argentina

Winner:
Brazil
```

---

## 11.4 Simulation Result

Representa um estado hipotético.

Exemplo:

```
If Brazil wins next match:

Brazil qualifies
```

---

# 12. Engine Execution Modes

O sistema deve suportar diferentes modos de execução.

---

# 12.1 Full Recalculation

A engine recalcula toda a competição.

Fluxo:

```
CompetitionEdition

↓

Load all events

↓

Process events

↓

Generate classification
```

---

## Uso

* histórico;
* auditoria;
* correção de dados;
* importação inicial.

---

## Exemplo

Importando Copa do Mundo de 1970:

```
Load all matches

↓

Calculate statistics

↓

Generate final classification
```

---

# 12.2 Incremental Calculation

A engine processa apenas uma alteração.

Fluxo:

```
New EventAction

↓

Process change

↓

Update affected results
```

---

## Uso

* resultados live;
* dashboards;
* notificações.

---

## Exemplo

Gol aos 85 minutos:

Antes:

```
Brazil 1 x 1 Argentina
```

Evento:

```
GOAL
```

Depois:

```
Brazil 2 x 1 Argentina
```

Atualização:

```
Classification
```

---

# 13. Event Driven Execution

As engines devem reagir a mudanças de domínio.

Exemplos:

```
EventFinished

GoalRegistered

PenaltyApplied

RaceFinished
```

---

Fluxo:

```
Domain Event

↓

Competition Engine

↓

Recalculate State
```

---

# 14. Deterministic Calculation

A engine deve ser determinística.

Dados iguais devem gerar resultados iguais.

Exemplo:

Entrada:

```
Same matches

Same rules
```

Resultado:

```
Same classification
```

---

## Motivo

Permitir:

* auditoria;
* reconstrução histórica;
* testes;
* simulações.

---

# 15. Calculation Snapshot

Embora o resultado possa ser recalculado, o sistema pode armazenar snapshots.

Exemplo:

```
Brasileirão 2026

Round 10

Classification Snapshot
```

---

## Uso

* histórico rápido;
* comparação entre rodadas;
* dashboards.

---

## Observação

Snapshot não substitui os dados originais.

A fonte de verdade continua sendo:

```
Events

+

Actions

+

Rules
```

---

# 16. Impact Analysis

Quando um evento muda, a engine deve identificar o impacto.

---

Exemplo:

Alteração:

```
Final Brazil 2 x 1 Argentina

para

Brazil 1 x 1 Argentina
```

Impacto:

```
Event Result

↓

Classification

↓

Qualification

↓

Next Stage
```

---

Nem toda alteração precisa recalcular a competição inteira.

---

# 17. Engine Pipeline

A execução completa segue:

```
1. Load Competition Context

        |

2. Validate Rules

        |

3. Process Events

        |

4. Generate Results

        |

5. Calculate Metrics

        |

6. Generate Classification

        |

7. Resolve Next Stages
```

---

# 18. Princípio de Reprocessamento

Qualquer resultado calculado deve poder ser descartado e recriado.

A engine nunca deve depender exclusivamente de valores previamente calculados.

---

Exemplo:

```
Delete ClassificationMetric

↓

Run Engine

↓

Recreate Metrics
```

---

# 19. Internal Engine Architecture

Uma CompetitionEngine deve ser composta por componentes especializados.

A engine coordena o processo, mas não concentra todas as regras.

---

# 20. CompetitionEngine Interface

Toda engine deve possuir uma interface comum.

Conceitualmente:

```
CompetitionEngine

    calculate()

    processEvent()

    generateClassification()

    resolveNextStage()
```

---

## Responsabilidades

A interface define operações comuns entre diferentes formatos de competição.

---

## Exemplos

Implementações:

```
LeagueEngine

KnockoutEngine

RaceEngine
```

---

# 21. Engine Components

Cada engine pode utilizar componentes internos específicos.

---

# 21.1 Event Processor

## Responsabilidade

Transformar eventos esportivos em resultados intermediários.

Entrada:

```
CompetitionEvent

EventAction
```

Saída:

```
EventResult
```

---

## Exemplos

Futebol:

Entrada:

```
GOAL
GOAL
RED_CARD
```

Resultado:

```
Home Score = 2

Away Score = 0
```

---

Fórmula 1:

Entrada:

```
FINISH_POSITION
PENALTY
FASTEST_LAP
```

Resultado:

```
Driver Position

Final Points
```

---

# 21.2 Scoring Calculator

## Responsabilidade

Calcular pontuação baseada nas regras.

---

## Exemplo Futebol

Configuração:

```json
{
 "win":3,
 "draw":1
}
```

Resultado:

```
Winner +3

Loser +0
```

---

## Exemplo Fórmula 1

Configuração:

```json
{
 "position":1,
 "points":25
}
```

Resultado:

```
Position 1

=

25 points
```

---

# 21.3 Classification Calculator

## Responsabilidade

Gerar uma classificação utilizando métricas calculadas.

Entrada:

```
ClassificationMetrics
```

Saída:

```
Classification
```

---

Exemplo:

Entrada:

```
Team A

POINTS = 75
WINS = 22
```

Resultado:

```
Position 1
```

---

# 21.4 Tie Breaker Resolver

## Responsabilidade

Resolver empates.

A ordem dos critérios vem da CompetitionRule.

---

Exemplo:

```json
{
 "metrics":[
    "POINTS",
    "WINS",
    "GOAL_DIFFERENCE"
 ]
}
```

---

Processamento:

```
Compare Points

↓

Compare Wins

↓

Compare Goal Difference
```

---

# 21.5 Stage Resolver

## Responsabilidade

Determinar como participantes avançam entre fases.

---

Exemplos:

## Grupos

Entrada:

```
Group A

1. Brazil
2. Argentina
```

Saída:

```
Round of 16
```

---

## Eliminatória

Entrada:

```
Brazil 2 x 0 Argentina
```

Saída:

```
Brazil qualified
```

---

# 21.6 Participant Resolver

## Responsabilidade

Resolver quais participantes representam um resultado.

---

Exemplo:

Fórmula 1:

Evento:

```
Car #16 finished 1st
```

Pode gerar:

```
Driver points

Team points

Constructor points
```

---

WEC:

Evento:

```
Car #51 finished 1st
```

Pode gerar:

```
Car classification

Driver classification
```

---

# 22. Engine Composition

Uma engine pode combinar componentes diferentes.

---

Exemplo:

```
RaceEngine

    EventProcessor

    PositionCalculator

    ScoringCalculator

    ClassificationCalculator
```

---

Exemplo:

```
LeagueEngine

    MatchResultProcessor

    PointsCalculator

    TieBreakerResolver

    ClassificationCalculator
```

---

# 23. Rule Driven Components

Os componentes devem consumir configuração.

---

Exemplo:

Não:

```
if(win)
    points = 3;
```

---

Sim:

```
ScoringRule

WIN = 3
DRAW = 1
LOSS = 0
```

---

# 24. Código vs Configuração

## Configuração

Responsável por:

* valores;
* ordem;
* quantidade;
* parâmetros.

---

## Código

Responsável por:

* algoritmo;
* fluxo;
* comportamento.

---

Exemplo:

Configuração:

```
Third place receives 15 points
```

Código:

```
Como descobrir quem terminou em terceiro
```

---

# 25. Engine Extension

Adicionar um novo esporte deve seguir:

```
1. Criar nova CompetitionEngine

2. Criar componentes específicos

3. Registrar novo engine code

4. Criar CompetitionTemplate usando a engine
```

---

Não deve exigir:

* alteração das tabelas existentes;
* criação de tabelas específicas do esporte.

---

# 26. Competition Engine Implementations

As engines representam formatos de cálculo de competição.

Elas não representam esportes específicos.

---

# 27. League Engine

## Objetivo

Responsável por competições onde todos os participantes acumulam resultados durante uma temporada.

---

## Exemplos

* Brasileirão;
* Premier League;
* Campeonato regular de basquete;
* Fases de grupos.

---

## Características

* classificação acumulativa;
* pontuação por evento;
* ordenação por métricas;
* possibilidade de múltiplos turnos.

---

# 28. League Engine Flow

Fluxo:

```
CompetitionEvent

↓

Event Result

↓

Scoring Calculator

↓

Participant Metrics

↓

Classification
```

---

## Exemplo

Evento:

```
Team A 2 x 1 Team B
```

Resultado:

```
Team A

Points +3

Goals For +2

Goals Against +1
```

```
Team B

Points +0

Goals For +1

Goals Against +2
```

---

# 29. League Rules

A LeagueEngine deve suportar:

* quantidade de rodadas;
* ida;
* volta;
* pontos por resultado;
* critérios de desempate;
* número de participantes.

---

Exemplo:

```json
{
  "rounds": 2,

  "scoring": {
    "win": 3,
    "draw": 1,
    "loss": 0
  }
}
```

---

# 30. League Classification

A classificação é construída através de métricas.

Exemplo:

```
Points

↓

Wins

↓

Goal Difference

↓

Goals Scored
```

---

As métricas são definidas pela CompetitionRule.

---

# 31. Knockout Engine

## Objetivo

Responsável por competições eliminatórias.

---

## Exemplos

* Copa do Mundo;
* Copa Libertadores;
* Champions League.

---

## Características

* participantes são eliminados;
* fases sequenciais;
* confrontos;
* avanço de vencedores.

---

# 32. Knockout Structure

Uma competição eliminatória é formada por stages.

Exemplo:

```
Round of 32

↓

Round of 16

↓

Quarter Final

↓

Semi Final

↓

Final
```

---

A quantidade de fases não é fixa.

---

Exemplo:

Formato antigo:

```
Group Stage

↓

Round of 16

↓

Quarter Final
```

---

Formato novo:

```
Group Stage

↓

Round of 32

↓

Round of 16

↓

Quarter Final
```

---

# 33. Knockout Resolution

Cada confronto gera:

```
Winner

↓

Next Stage
```

---

Exemplo:

```
Brazil 2 x 0 Argentina

Winner:

Brazil
```

---

Critérios possíveis:

* vitória direta;
* prorrogação;
* penalidades;
* melhor campanha.

---

# 34. Group Stage Support

A KnockoutEngine também deve suportar fases de grupos.

Fluxo:

```
Group Stage

↓

Classification

↓

Qualified Participants

↓

Knockout Stage
```

---

Exemplo:

Grupo:

```
Brazil

Argentina

Chile

Peru
```

Regra:

```
Top 2 qualify
```

Resultado:

```
Brazil

Argentina
```

---

# 35. Race Engine

## Objetivo

Responsável por competições onde eventos possuem classificação por posição.

---

## Exemplos

* Fórmula 1;
* WEC;
* Indy;
* MotoGP.

---

## Características

* posições finais;
* pontuação por posição;
* múltiplos participantes;
* classificações relacionadas.

---

# 36. Race Participants

A RaceEngine utiliza o modelo de relacionamento entre participantes.

Exemplo Fórmula 1:

```
Driver

    drives

Car

    belongs_to

Team
```

---

Um evento pode gerar resultados diferentes:

```
Driver Championship

Constructor Championship

Team Championship
```

---

# 37. Race Event Processing

Entrada:

```
Race Result
```

Exemplo:

```
Car #44

Position 1
```

---

Processamento:

```
Position

↓

Points

↓

Participant Metrics
```

---

Resultado:

```
Driver +25

Team +25

Constructor +25
```

---

# 38. WEC Example

No WEC, a relação é diferente.

Exemplo:

```
Car

receives points
```

Enquanto:

```
Manufacturer

may receive points
```

depende da regra da competição.

---

A engine não assume a estrutura.

Ela utiliza:

```
Participant Relation

+

Competition Rule
```

---

# 39. Hybrid Engine

Algumas competições possuem múltiplos formatos.

---

Exemplo:

Copa do Mundo:

```
League Engine

(Group Stage)

+

Knockout Engine

(Final Stage)
```

---

Fluxo:

```
Group Classification

↓

Qualified Participants

↓

Knockout Bracket
```

---

# 40. Engine Selection

A CompetitionEdition pode possuir uma engine principal.

Porém uma competição pode possuir múltiplas fases com diferentes comportamentos.

---

Exemplo:

```
Competition Edition

World Cup 2026


Engine:

HYBRID_ENGINE


Stages:

Group Stage
    -> League Rules

Knockout Stage
    -> Knockout Rules
```

---

# 41. Engine Design Principle

Nenhuma engine deve assumir:

* esporte;
* participante;
* formato histórico;
* quantidade fixa de fases.

---

Ela deve receber:

```
Competition Rules

+

Competition Data

+

Participant Relations
```

e produzir:

```
Competition Results
```

---

# 42. Classification Engine

A Classification Engine é responsável por transformar métricas calculadas em uma classificação ordenada.

Ela não calcula eventos.

Ela recebe métricas produzidas pelas engines de competição.

---

# 43. Responsibility

A Classification Engine é responsável por:

* ordenar participantes;
* aplicar critérios de desempate;
* gerar posições;
* determinar classificados;
* criar snapshots de classificação.

---

Não é responsabilidade dela:

* interpretar gols;
* interpretar corridas;
* calcular pontuação de eventos.

Essas responsabilidades pertencem às engines específicas.

---

# 44. Classification Flow

Fluxo:

```
Competition Event

↓

Competition Engine

↓

Calculated Metrics

↓

Classification Engine

↓

Ranking
```

---

Exemplo:

```
Match Result

↓

Points = 3

↓

Classification Metric

↓

Ranking Position
```

---

# 45. Classification Model

Uma competição pode possuir múltiplas classificações.

Exemplo:

```
Formula 1 2026

├── Driver Championship
│
└── Constructor Championship
```

---

Cada classificação possui:

```
Classification Type

↓

Entries

↓

Metrics

↓

Ranking
```

---

# 46. Classification Type

Define qual entidade está sendo classificada.

---

Exemplos:

```
TEAM

PLAYER

DRIVER

CAR

CONSTRUCTOR

MANUFACTURER
```

---

Exemplo:

F1:

```
Classification Type:

DRIVER
```

Participantes:

```
Max Verstappen

Charles Leclerc
```

---

# 47. Classification Entry

Representa um participante dentro de uma classificação.

---

Exemplo:

```
Formula 1 2026

Driver Championship


Entry:

Max Verstappen
```

---

Uma Entry possui:

* posição;
* métricas;
* status.

---

# 48. Classification Metrics

As métricas representam valores utilizados para ordenar participantes.

---

Exemplos:

Futebol:

```
POINTS

WINS

GOAL_DIFFERENCE

GOALS_FOR
```

---

Fórmula 1:

```
POINTS

WINS

PODIUMS
```

---

WEC:

```
POINTS

RACE_WINS

FINISHES
```

---

# 49. Metric Calculation

Uma métrica possui:

```
Metric Type

Value

Order
```

---

Exemplo:

```
Participant:

Brazil


Metrics:

POINTS = 15

GOAL_DIFFERENCE = +5
```

---

# 50. Metric Types

Os tipos de métricas são limitados e conhecidos pelo sistema.

Exemplo:

```
POINTS

WINS

LOSSES

DRAW

GOALS_FOR

GOALS_AGAINST

GOAL_DIFFERENCE

PODIUMS

VICTORIES

POSITION
```

---

Novas métricas podem ser adicionadas quando novos esportes forem suportados.

---

# 51. Ranking Resolution

A classificação é resolvida pela ordem dos critérios.

---

Exemplo:

Regra:

```json
{
 "metrics": [
   "POINTS",
   "WINS",
   "GOAL_DIFFERENCE"
 ]
}
```

---

Execução:

```
Compare Points

↓

If equal:

Compare Wins

↓

If equal:

Compare Goal Difference
```

---

# 52. Tie Breaker Resolver

Empates devem ser tratados como uma etapa separada.

---

Exemplo:

```
Team A

70 points


Team B

70 points
```

---

Resolver:

```
Wins

↓

Goal Difference

↓

Goals Scored
```

---

# 53. Classification Snapshot

Uma classificação pode ser materializada em momentos específicos.

---

Exemplos:

```
Round 10

Final Season

Before Knockout Stage
```

---

O snapshot permite:

* histórico rápido;
* comparação;
* visualização.

---

# 54. Live Classification

A classificação pode existir em tempo real.

---

Exemplo:

Durante uma rodada:

```
Brazil 1 x 0 Argentina

Minute 60
```

---

Resultado:

```
Temporary Classification
```

---

Após finalização:

```
Final Classification
```

---

# 55. Multiple Classifications

Uma competição pode possuir diversas classificações simultâneas.

---

Exemplo:

WEC:

```
Overall Championship

↓

Car Classification


Driver Championship

↓

Driver Classification
```

---

Exemplo:

Fórmula 1:

```
Drivers Championship

Constructors Championship
```

---

# 56. Qualification Rules

Uma classificação pode gerar participantes classificados.

---

Exemplo:

Grupo:

```
Classification:

1. Brazil
2. Argentina
3. Chile
4. Peru
```

Regra:

```
Top 2 qualify
```

Resultado:

```
Qualified:

Brazil

Argentina
```

---

# 57. Classification Independence

A Classification Engine não conhece:

* futebol;
* corrida;
* grupos;
* eliminatórias.

Ela apenas recebe:

```
Participant

+

Metrics

+

Ordering Rules
```

e retorna:

```
Ranking
```

---

# 58. Final Principle

Todas as engines utilizam a Classification Engine.

```
LeagueEngine

        |

        v

ClassificationEngine


RaceEngine

        |

        v

ClassificationEngine


KnockoutEngine

        |

        v

ClassificationEngine
```

---

# 59. Simulation and Live Processing

A Competition Engine deve suportar tanto processamento histórico quanto execução em tempo real.

O sistema deve permitir:

* atualização de resultados;
* recalculo de classificação;
* projeção de fases futuras;
* simulações de cenários.

---

# 60. Live Processing

O processamento live ocorre quando novos dados são adicionados ou alterados.

Exemplos:

* gol marcado;
* cartão aplicado;
* resultado finalizado;
* posição final de corrida;
* penalização adicionada.

---

Fluxo:

```text
External Data

↓

EventAction

↓

Competition Engine

↓

Classification Update

↓

Future State Update
```

---

# 61. Source of Truth

A fonte de verdade do sistema são os eventos registrados.

Exemplo:

```
CompetitionEvent

+

EventAction

+

CompetitionRule
```

---

Classificações e projeções são dados derivados.

Podem ser recalculados quando necessário.

---

# 62. Incremental Update

A engine deve evitar recalcular toda a competição em alterações pequenas.

---

Exemplo:

Evento:

```
Brasil 1 x 0 Argentina
```

Alteração:

```
Gol removido
```

Impacto:

```
Event Result

↓

Match Classification

↓

Group Classification

↓

Qualification State
```

---

Somente os elementos afetados devem ser atualizados.

---

# 63. Dependency Graph

A competição deve ser tratada como um grafo de dependências.

Exemplo:

```
Event Result

      |

      v

Classification

      |

      v

Qualified Participants

      |

      v

Next Stage Matches

      |

      v

Future Events
```

---

Uma alteração percorre esse grafo.

---

# 64. Future State Calculation

A engine pode calcular estados futuros.

---

Exemplo:

Antes da última rodada:

```
Group A

Brazil     6 pts
Argentina  4 pts
Chile      3 pts
Peru       1 pt
```

---

Simulação:

```
Brazil vence Peru
```

---

Resultado:

```
Brazil qualified = true
```

---

# 65. Simulation Mode

A simulação não altera os dados oficiais.

Ela cria um contexto alternativo.

---

Exemplo:

Estado oficial:

```
Brazil 1 x 1 Argentina
```

---

Simulação:

```
Assume Brazil victory
```

---

Resultado:

```
Brazil reaches Quarter Final
```

---

# 66. Simulation Context

Uma simulação deve possuir:

```
Base State

+

Hypothetical Changes
```

---

Exemplo:

```json
{
  "changes": [
    {
      "event": "match_123",
      "result": "HOME_WIN"
    }
  ]
}
```

---

A engine calcula:

```
Base Competition

+

Simulation Changes

=

Simulation Result
```

---

# 67. Live Competition State

Uma competição em andamento possui estados diferentes.

---

Exemplo:

```
NOT_STARTED

RUNNING

FINISHED

CANCELLED
```

---

Durante:

```
RUNNING
```

a engine deve aceitar novos eventos.

---

# 68. Reprocessing

Qualquer competição deve poder ser reconstruída.

---

Exemplo:

Problema:

```
Resultado importado errado
```

---

Processo:

```
Remove invalid event

↓

Reload events

↓

Execute Engine

↓

Generate new classification
```

---

# 69. External Data Integration

A engine não depende da origem dos dados.

Dados podem vir de:

* entrada manual;
* API externa;
* importação histórica;
* arquivo.

---

Fluxo:

```
Data Provider

↓

Normalizer

↓

Competition Event Model

↓

Competition Engine
```

---

# 70. Real Time Example - World Cup

Cenário:

```
World Cup 2026

Group Stage
```

---

Evento:

```
Brazil 2 x 1 Argentina
```

---

Processamento:

```
Register Event

↓

Calculate Group Points

↓

Update Classification

↓

Check Qualification

↓

Generate Possible Next Matches
```

---

# 71. Real Time Example - Formula 1

Evento:

```
Race Finished
```

Resultado:

```
Driver A position 1

Driver B position 2
```

---

Processamento:

```
Calculate Race Points

↓

Update Driver Championship

↓

Update Constructor Championship

↓

Update Season Ranking
```

---

# 72. Engine Consistency

Mesmo com live updates, a engine deve manter:

* determinismo;
* reprocessamento;
* histórico;
* auditoria.

---

O mesmo conjunto de eventos sempre deve produzir o mesmo resultado.

---

# 73. Final Architecture

O fluxo completo:

```
Competition Rules

        +

Competition Data

        +

Events

        |

        v

Competition Engine

        |

        +----------------+
        |                |
        v                v

Classification      Simulation

        |

        v

Competition State
```

---

# 74. Final Principle

O sistema não armazena apenas resultados.

Ele armazena fatos.

Os resultados são consequências calculadas pelas engines.

Isso permite:

* histórico completo;
* competições atuais;
* simulações;
* previsões;
* reconstrução de estados.

---
