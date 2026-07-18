# Sports Engine - Domain Model

> Universal Sports Competition Engine
>
> Version: **2.0**
>
> Status: Draft

---

# 1. Objetivo

Este documento descreve o modelo de domínio do Sports Engine.

Seu objetivo é definir todos os conceitos de negócio que compõem o sistema, bem como as responsabilidades de cada entidade e os relacionamentos existentes entre elas.

Este documento é independente de linguagem de programação, banco de dados ou framework.

Ele representa a verdade do domínio.

Os documentos seguintes serão derivados deste modelo:

- architecture.md
- database.md
- api.md
- engine.md

---

# 2. Objetivos do Projeto

O Sports Engine foi projetado para representar qualquer competição esportiva baseada em participantes e resultados.

O modelo deve permitir representar desde competições históricas até campeonatos em andamento, suportando atualização em tempo real e simulações futuras.

O sistema deve ser capaz de representar, entre outros:

- Futebol
- Futebol Americano
- Basquete
- Vôlei
- Tênis
- Fórmula 1
- WEC
- MotoGP
- Esportes futuros

sem necessidade de alterações estruturais no domínio.

---

# 3. Princípios do Domínio

O domínio foi construído seguindo alguns princípios fundamentais.

Esses princípios devem orientar toda evolução futura do projeto.

---

## 3.1 O sistema armazena fatos

O banco de dados nunca armazena resultados derivados como fonte de verdade.

Ele armazena fatos.

Exemplos:

```
Brasil venceu Argentina

Charles Leclerc terminou em 2º

Carro #51 abandonou

Jogador recebeu cartão amarelo
```

A interpretação desses fatos pertence à CompetitionEngine.

---

## 3.2 Regras pertencem à Engine

As entidades representam dados.

As regras representam comportamento.

Esses dois conceitos devem permanecer separados.

Exemplo:

O banco registra:

```
Brasil 2 x 1 Argentina
```

A CompetitionEngine interpreta:

```
Vitória

↓

3 pontos

↓

Brasil lidera o grupo
```

---

## 3.3 O domínio é independente do esporte

Nenhuma entidade deve representar um esporte específico.

Não devem existir entidades como:

```
FootballMatch

FormulaOneRace

SoccerTeam

BasketballPlayer
```

Em vez disso, utilizamos entidades genéricas.

Exemplos:

```
Participant

CompetitionEvent

EventAction
```

O comportamento específico é definido pelas regras da competição.

---

## 3.4 O histórico nunca deve ser perdido

O sistema deve preservar a história completa de uma competição.

Mesmo que regras mudem no futuro, uma edição antiga deve continuar produzindo exatamente os mesmos resultados.

Exemplo:

```
World Cup 2022
```

deve continuar utilizando o regulamento vigente em 2022, mesmo após mudanças ocorridas em 2026.

---

## 3.5 Dados incompletos são válidos

Competições históricas frequentemente possuem informações limitadas.

Exemplo:

```
Brasil 4 x 1 Bolívia

1930
```

Pode existir apenas o placar.

Sem:

- autor dos gols;
- cartões;
- escalações;
- substituições.

Ainda assim a competição deve ser totalmente representável.

---

## 3.6 Todo resultado pode ser reconstruído

Classificações, rankings e estatísticas nunca devem depender exclusivamente de dados materializados.

Todo resultado deve poder ser recalculado utilizando apenas:

```
CompetitionRules

+

CompetitionEvent

+

EventAction
```

Essa decisão garante consistência histórica.

---

# 4. Visão Geral do Domínio

O domínio pode ser dividido em seis grandes áreas.

```
Core Domain

↓

Rule Domain

↓

Participant Domain

↓

Competition Domain

↓

Event Domain

↓

Infrastructure Domain
```

Cada uma possui responsabilidades distintas.

---

# 5. Core Domain

O Core Domain representa os conceitos centrais do sistema.

Ele define:

- quem organiza;
- qual esporte é praticado;
- qual competição existe;
- quais temporadas existem.

As entidades pertencentes ao Core Domain são:

```
Organization

Sport

Competition

CompetitionTemplate

CompetitionEdition
```

---

# 6. Aggregate Roots

Os principais Aggregate Roots do sistema são:

```
Organization

Sport

Competition

CompetitionEdition

Participant

CompetitionEvent
```

Cada Aggregate Root é responsável por manter a consistência das entidades sob sua responsabilidade.

Exemplo:

```
CompetitionEdition

↓

CompetitionStage

↓

StageSlot

↓

CompetitionEvent
```

Nenhuma entidade externa deve modificar diretamente objetos internos sem passar pelo Aggregate Root.

---

# 7. Organization

## Responsabilidade

Representa uma entidade responsável por administrar competições.

Ela não representa um campeonato.

Ela representa uma organização esportiva.

---

## Exemplos

```
FIFA

UEFA

CONMEBOL

CBF

FIA

ACO
```

---

## Responsabilidades

Uma Organization pode:

- administrar competições;
- possuir organizações filhas;
- possuir identidade institucional;
- possuir sede;
- definir regulamentos oficiais.

---

## Hierarquia

Organizações podem formar hierarquias.

Exemplo:

```
FIFA

│

├── UEFA

├── CONMEBOL

├── AFC

└── CAF
```

Essa estrutura permite representar federações internacionais, continentais e nacionais.

---

## Relacionamentos

Uma Organization pode possuir:

- várias Competitions;
- uma Organization pai;
- várias Organizations filhas;
- uma Location principal.

---

## Regras de domínio

Uma Competition pode existir sem uma Organization definida.

Isso permite representar competições independentes ou históricas cuja entidade organizadora seja desconhecida.

---

# 8. Sport

## Responsabilidade

Representa uma modalidade esportiva.

Exemplos:

```
Football

Basketball

Motor Racing

Volleyball

Tennis
```

---

## Objetivo

Sport funciona como um agrupador de competições.

Exemplo:

```
Football

│

├── World Cup

├── Libertadores

├── Champions League

└── Brasileirão
```

---

## Responsabilidades

Sport define apenas a modalidade esportiva.

Ele não define:

- regras;
- pontuação;
- formato;
- fases.

Essas responsabilidades pertencem ao CompetitionTemplate e à CompetitionEngine.

---

## Relacionamentos

Um Sport possui:

- várias Competitions;
- vários tipos de participantes permitidos;
- vários tipos de eventos possíveis.

---

## Regras de domínio

Novos esportes devem poder ser adicionados sem necessidade de alterações estruturais no domínio.

O comportamento será definido por novas CompetitionEngines e novas configurações de regras.

---

# 9. Competition

## Responsabilidade

Representa um campeonato permanente.

Competition não representa uma temporada.

Ela representa a identidade do campeonato.

---

## Exemplos

```
FIFA World Cup

UEFA Champions League

Campeonato Brasileiro

Formula One

World Endurance Championship
```

---

## Uma Competition possui

- um Sport;
- uma Organization;
- um ou mais CompetitionTemplates;
- uma ou mais CompetitionEditions.

---

## Exemplo

```
Competition

Formula One

│

├── Template 2022

├── Template 2026

│

├── Edition 2025

├── Edition 2026

└── Edition 2027
```

Observe que uma competição pode evoluir ao longo do tempo sem perder sua identidade.

---

## Responsabilidade

Competition não conhece:

- participantes;
- resultados;
- classificação;
- calendário;
- eventos.

Ela representa apenas o conceito permanente do campeonato.

Toda informação temporal pertence à CompetitionEdition.

---

# 10. CompetitionTemplate

## Responsabilidade

Representa um modelo reutilizável utilizado para criar CompetitionEditions.

Um CompetitionTemplate define a estrutura permanente de uma competição.

Ele descreve **como** uma competição deve ser organizada, mas nunca representa uma competição em execução.

---

## Responsabilidades

Um CompetitionTemplate define:

- estrutura da competição;
- CompetitionRules padrão;
- CompetitionStageTemplates;
- tipos de participantes permitidos;
- configurações padrão.

---

## O que NÃO pertence ao Template

O Template nunca possui:

- participantes;
- confrontos;
- resultados;
- classificação;
- estatísticas;
- eventos em execução.

Esses elementos pertencem exclusivamente à CompetitionEdition.

---

## Versionamento

Templates podem evoluir.

Exemplo:

```
World Cup

├── Template V1 (32 seleções)

└── Template V2 (48 seleções)
```

Cada CompetitionEdition referencia exatamente um template.

---

# 11. CompetitionStageTemplate

## Responsabilidade

Representa a definição de uma fase dentro de um CompetitionTemplate.

Ela descreve uma fase que será criada futuramente em uma CompetitionEdition.

---

## Exemplos

```
Group Stage

Round of 32

Round of 16

Quarter Finals

Semi Finals

Final
```

---

## Responsabilidades

Uma CompetitionStageTemplate define:

- nome;
- ordem;
- tipo;
- quantidade esperada de participantes;
- StageSlotTemplates;
- CompetitionEventTemplates.

---

## Hierarquia

Stages podem possuir sub-stages.

Exemplo:

```
Group Stage

├── Group A

├── Group B

├── Group C

└── Group D
```

---

# 12. StageSlotTemplate

## Responsabilidade

Representa uma posição esperada dentro de uma CompetitionStageTemplate.

Ela nunca representa um participante real.

Representa apenas uma origem.

---

## Exemplos

```
Group A

Position 1

Position 2

Position 3

Position 4
```

---

```
Round of 16

Winner Group A

Runner-up Group B
```

---

## Objetivo

Permitir que toda a estrutura da competição seja criada antes da definição dos participantes.

---

## Estratégias de resolução

Um StageSlotTemplate pode representar:

- posição em grupo;
- vencedor de evento;
- melhor terceiro colocado;
- classificação geral;
- wildcard;
- participante manual.

A resolução pertence à CompetitionEngine.

---

# 13. CompetitionEventTemplate

## Responsabilidade

Representa a definição de um evento esportivo.

Ele descreve um evento que será criado futuramente em uma CompetitionEdition.

---

## Objetivo

Separar a estrutura do calendário da execução da competição.

---

## Exemplos

Brasileirão

```
Rodada 1

Evento 1

Evento 2

...

Evento 10
```

---

Copa do Mundo

```
Round of 16

Match 49

Match 50

...
```

---

Formula One

```
Australian GP

Chinese GP

Japanese GP
```

---

## Um CompetitionEventTemplate define

- Stage à qual pertence;
- ordem;
- tipo do evento;
- StageSlotTemplate mandante;
- StageSlotTemplate visitante (quando aplicável);
- Venue padrão (opcional);
- configurações do evento.

---

# 14. CompetitionEngine

## Responsabilidade

Representa a implementação responsável por executar uma CompetitionEdition.

Ela interpreta:

- CompetitionRules;
- CompetitionEvents;
- EventActions.

Produzindo:

- classificações;
- estatísticas;
- progressão;
- vencedores.

---

## Responsabilidades

Uma CompetitionEngine deve ser capaz de:

- validar a edição;
- criar a estrutura inicial;
- resolver StageSlots;
- interpretar eventos;
- distribuir pontos;
- calcular classificações;
- resolver desempates;
- promover participantes;
- gerar confrontos futuros.

---

## Identidade

Uma CompetitionEngine possui:

```
code

name

version

description

status
```

---

## Versionamento

Múltiplas versões podem coexistir.

Cada CompetitionEdition referencia exatamente uma versão.

---

# 15. CompetitionRules

## Responsabilidade

Representa o regulamento configurável de uma Competition.

CompetitionRules contém apenas configuração.

Nunca contém comportamento.

---

## Estrutura

```
CompetitionRules

↓

Configuration (JSON)
```

---

## Exemplos

A configuração pode definir:

- pontuação;
- desempates;
- progressão;
- quantidade de classificados;
- bônus;
- penalizações;
- critérios de eliminação.

---

## Versionamento

CompetitionRules também possuem identidade própria.

Isso garante preservação histórica.

---

# 16. Herança de Regras

CompetitionRules podem existir em dois níveis.

---

## Template

Define o regulamento padrão.

```
CompetitionTemplate

↓

CompetitionRules
```

---

## Edition

Define alterações específicas.

```
CompetitionEdition

↓

CompetitionRules
```

---

## Resolução

Durante a criação da edição:

```
Template Rules

↓

Edition Rules

↓

Merged Rules
```

A edição sobrescreve apenas os campos necessários.

---

# 17. CompetitionEdition

## Responsabilidade

Representa uma realização específica de uma Competition.

É o centro operacional do domínio.

---

## Uma CompetitionEdition possui

- Competition;
- CompetitionTemplate;
- CompetitionEngine;
- CompetitionRules (override opcional);
- CompetitionStages;
- CompetitionEntries;
- CompetitionEvents;
- Classifications.

---

## Processo de criação

```
Competition

↓

CompetitionTemplate

↓

CompetitionStageTemplates

↓

StageSlotTemplates

↓

CompetitionEventTemplates

↓

CompetitionEdition

↓

CompetitionStages

↓

StageSlots

↓

CompetitionEvents

↓

Competition pronta
```

Toda a estrutura operacional é criada a partir dos templates.

---

## Estados

```
PLANNED

REGISTRATION

IN_PROGRESS

FINISHED

CANCELLED
```

---

## Imutabilidade

Após finalizada, uma edição representa um registro histórico.

Alterações posteriores devem ocorrer através de reprocessamento da CompetitionEngine.

---

# Resumo do Modelo

```
Competition
│
├── CompetitionTemplate
│   ├── CompetitionRules
│   └── CompetitionStageTemplate
│       ├── StageSlotTemplate
│       └── CompetitionEventTemplate
│
└── CompetitionEdition
    ├── CompetitionEngine
    ├── CompetitionRules (override)
    ├── CompetitionStage
    │   ├── StageSlot
    │   └── CompetitionEvent
    ├── CompetitionEntry
    └── Classification
```

O domínio mantém uma separação clara entre definições (templates) e execução (edições), permitindo reutilização, versionamento e preservação completa do histórico.

