# ADR-012 — Event and Action Modeling Strategy

## Status

Em revisão

## Date

2026-07-18

---

# Context

Durante a modelagem da Sports Data Platform, surgiu a necessidade de representar eventos esportivos de diferentes modalidades.

Cada esporte possui acontecimentos próprios.

Exemplos:

## Futebol

Uma partida possui:

```text id="9w3m1q"
Goals

Cards

Substitutions

Penalty Kicks

VAR Decisions
```

---

## Fórmula 1

Uma corrida possui:

```text id="p7k2vx"
Lap Results

Pit Stops

Penalties

Fastest Lap

Overtakes
```

---

## WEC

Uma corrida possui:

```text id="r5d8nk"
Stints

Driver Changes

Penalties

Category Results
```

Uma abordagem específica criaria estruturas separadas:

```text id="s6m9kp"
football_match_event

formula1_race_event

wec_race_event
```

Porém, isso impediria uma arquitetura genérica.

---

# Decision

O domínio utilizará dois conceitos principais:

```text id="k8q4mv"
Competition Event

        |
        |
        N

Event Action
```

---

# Competition Event

Representa a unidade competitiva principal.

Exemplos:

```text id="m2x8zr"
Football:

Match


Formula 1:

Race


WEC:

Race
```

Responsabilidades:

* participantes envolvidos;
* local/data;
* status;
* resultado principal;
* vínculo com uma fase da competição.

---

# Event Action

Representa acontecimentos dentro de um Competition Event.

Exemplos:

```text id="q5j7pw"
Football:

Goal
Yellow Card


Formula 1:

Pit Stop
Penalty


WEC:

Driver Change
Penalty
```

---

# Example

## Football

Competition Event:

```text id="n7h2kd"
Brazil 2 x 1 Argentina
```

Actions:

```text id="v4m8pc"
Goal

minute: 30

participant: Brazil


Goal

minute: 75

participant: Argentina
```

---

## Formula 1

Competition Event:

```text id="z6x9qm"
Monaco Grand Prix 2026
```

Actions:

```text id="b8r4ty"
Fastest Lap

participant: Driver A


Penalty

participant: Driver B
```

---

# Rationale

A separação permite que a Competition Engine trabalhe com conceitos genéricos.

A Engine não precisa conhecer:

```java id="u3q7pk"
if (sport == FOOTBALL)
```

ou:

```java id="f5w8qn"
if (sport == FORMULA_1)
```

Ela processa:

```text id="w9m4cx"
Competition Event

        ↓

Event Actions

        ↓

Metrics

        ↓

Classification
```

---

# Action Type

Os tipos de ação serão configuráveis.

Exemplo:

```text id="r8p3mv"
GOAL

CARD

PIT_STOP

PENALTY

FASTEST_LAP

POSITION_CHANGE
```

Novos esportes podem adicionar novos tipos sem alterar o conceito principal.

---

# Consequences

## Positivas

* Modelo único para múltiplos esportes;
* Suporte a dados históricos;
* Engine genérica;
* Fácil expansão para novos tipos de evento;
* Melhor rastreabilidade.

---

## Negativas

* Algumas ações possuem atributos específicos;
* Pode exigir JSONB para detalhes adicionais;
* Consultas podem precisar interpretar tipos de ação.

---

# Alternatives Considered

## Criar tabelas específicas por esporte

Exemplo:

```text id="j8k2wd"
football_goal

formula1_pit_stop

wec_stint
```

### Motivo da rejeição

Criaria acoplamento entre o banco e esportes específicos.

Cada nova modalidade exigiria novas estruturas.

---

## Armazenar tudo diretamente no Competition Event

Exemplo:

```text id="w4p7cz"
match

goals

cards

substitutions
```

### Motivo da rejeição

Mistura o evento principal com detalhes variáveis.

A estrutura ficaria impossível de evoluir para diferentes esportes.

---

# Relationship With Engine

A Competition Engine utiliza Event Actions como entrada para cálculos.

Fluxo:

```text id="e8x4mz"
Competition Event

        ↓

Event Actions

        ↓

Classification Metrics

        ↓

Competition State
```

---

# Historical Data Consideration

Nem todas as ações precisam estar disponíveis.

Exemplo:

Uma partida histórica pode possuir:

```text id="q2m7sx"
Score:

2 x 1


Actions:

NULL
```

O evento continua válido mesmo sem detalhamento interno.

---

# Impact

Esta decisão influencia:

* database.md;
* engine.md;
* importadores;
* estatísticas;
* classificação;
* API.

---

# Implementation Guidelines

Novos tipos de ação devem ser avaliados considerando:

* se representam um conceito esportivo reutilizável;
* se possuem impacto em métricas;
* se pertencem ao evento ou ao participante.

Detalhes específicos devem permanecer extensíveis sem alterar o modelo principal.

---

# References

Documentos relacionados:

* `database.md`
* `engine.md`
* `api.md`

---

# Decision Summary

Competition Event representa a unidade competitiva principal, enquanto Event Action representa acontecimentos internos. Essa separação permite que a mesma arquitetura suporte diferentes esportes mantendo a Competition Engine genérica.
