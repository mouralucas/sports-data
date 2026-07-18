# ADR-007 — Classification as a Configurable Domain Concept

## Status

Em revisão

## Date

2026-07-18

---

# Context

Durante a modelagem da Competition Engine, surgiu a necessidade de representar classificações.

Uma abordagem simples seria criar uma tabela fixa:

```text id="9s8f3a"
classification

id
participant_id
points
position
```

Esse modelo funciona para alguns esportes, porém não representa adequadamente a diversidade de formatos competitivos.

Exemplos:

## Futebol

Uma classificação normalmente considera:

```text id="4x8f9p"
Points

Wins

Draws

Losses

Goals For

Goals Against

Goal Difference
```

---

## Fórmula 1

Uma classificação pode considerar:

```text id="x3m9qv"
Points

Wins

Podiums

Pole Positions

Fastest Laps
```

---

## WEC

Pode considerar:

```text id="j5z6hs"
Points

Category Position

Race Results

Endurance Bonuses
```

---

Portanto, a classificação não é uma estrutura fixa. Ela é uma interpretação das métricas produzidas pela competição.

---

# Decision

A classificação será modelada como um conceito configurável dentro da Competition Engine.

A estrutura será dividida em:

* Classification Template;
* Classification Metrics;
* Classification Result.

---

# Domain Model

```text id="x7q2kp"
Competition Template

        |
        |
        N

Classification Template

        |
        |
        N

Classification Metric


Competition Edition

        |
        |
        N

Classification Result
```

---

# Classification Template

Define como uma classificação deve ser calculada.

Exemplo:

```json id="6k3q9a"
{
    "type": "LEAGUE_TABLE",
    "metrics": [
        "POINTS",
        "WINS",
        "GOAL_DIFFERENCE"
    ],
    "ordering": [
        "POINTS",
        "GOAL_DIFFERENCE",
        "GOALS_SCORED"
    ]
}
```

---

# Classification Metrics

As métricas representam valores utilizados para cálculo ou ordenação.

Exemplos:

```text id="l8q9rf"
POINTS

WINS

LOSSES

GOALS_SCORED

GOAL_DIFFERENCE

PODIUMS

FASTEST_LAPS

RACE_POSITION
```

---

# Metric Type

As métricas possuirão um tipo definido.

Exemplos:

```text id="p4z6xm"
ACCUMULATED

COUNT

AVERAGE

POSITION

BOOLEAN
```

Exemplo:

```text id="g1q7sx"
POINTS

type = ACCUMULATED


WINS

type = COUNT
```

---

# Rationale

A classificação deve responder:

> "Como esta competição determina a posição dos participantes?"

Essa resposta depende do esporte e da competição.

Portanto, a regra não pertence ao Participant e nem ao Sport.

Ela pertence ao contexto competitivo.

---

# Consequences

## Positivas

* Suporte a diferentes esportes;
* Suporte a formatos históricos;
* Permite novos critérios sem alterar tabelas;
* Mantém a Competition Engine genérica;
* Facilita simulações e reconstruções.

---

## Negativas

* Implementação inicial mais complexa;
* Necessidade de validação das configurações;
* Consultas podem exigir processamento da Engine.

---

# Alternatives Considered

## Criar uma classificação fixa

Exemplo:

```text id="7kq1cm"
participant_id

points

position
```

### Motivo da rejeição

Esse modelo assume que todos os esportes possuem o mesmo conceito de classificação.

Isso não é verdadeiro.

---

## Criar tabelas específicas por esporte

Exemplo:

```text id="g2t6pc"
football_standing

formula1_standing

wec_standing
```

### Motivo da rejeição

Criaria dependência direta entre o sistema e esportes específicos.

Novas modalidades exigiriam novas estruturas.

---

# Relationship With Competition Engine

A Competition Engine utiliza os dados registrados nos eventos para calcular classificações.

Fluxo:

```text id="n5w8qa"
Competition Event

        ↓

Event Actions

        ↓

Metrics Calculation

        ↓

Classification Result

        ↓

Ranking
```

---

# Impact

Esta decisão influencia:

* database.md;
* engine.md;
* Competition Template;
* Competition Rule;
* API de classificação;
* relatórios e estatísticas.

---

# Implementation Guidelines

Novas métricas devem ser adicionadas somente quando representarem um conceito esportivo reutilizável.

A implementação deve evitar criar lógica específica dentro de entidades de domínio.

Exemplo:

Evitar:

```java
if (sport == FORMULA_1) {
    calculateFastestLap();
}
```

Preferir:

```text
Classification Metric

FASTEST_LAPS
```

com comportamento definido pela Engine.

---

# References

Documentos relacionados:

* `database.md`
* `engine.md`
* `api.md`

---

# Decision Summary

Classificação é um conceito configurável da competição, composto por métricas e regras de ordenação definidas pelo Competition Template, permitindo que diferentes esportes utilizem o mesmo modelo de domínio.
