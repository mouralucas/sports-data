# ADR-009 — Historical Data and Partial Information Model

## Status

Em revisão

## Data

2026-07-18

---

# Context

A Sports Data Platform possui como objetivo suportar tanto:

* competições em andamento;
* competições futuras;
* competições históricas.

Durante a modelagem surgiu uma diferença importante entre dados operacionais e dados históricos.

Em uma competição atual, normalmente temos acesso a informações completas.

Exemplo:

```text
Match

Home Team

Away Team

Players

Goals

Cards

Substitutions

Statistics
```

Porém, ao importar competições antigas, frequentemente existem limitações.

Exemplos:

* partidas antigas sem escalação completa;
* jogadores desconhecidos;
* estatísticas não disponíveis;
* eventos sem detalhamento;
* informações registradas apenas no nível da equipe.

---

# Decision

O modelo permitirá informações incompletas quando elas não forem conhecidas.

Campos que dependem de dados históricos poderão aceitar valores nulos.

A ausência de informação será representada como ausência de dado, não como uma estimativa ou valor artificial.

---

# Examples

## Complete Modern Match

```text
Competition Event

Home Participant

Away Participant


Actions:

Goal
    player_id = 123

Yellow Card
    player_id = 456
```

---

## Historical Match

```text
Competition Event

Home Participant

Away Participant


Actions:

Goal
    player_id = NULL
```

O sistema sabe que houve um gol, mas não possui informação confiável sobre o jogador responsável.

---

# Rationale

Existe uma diferença entre:

```text
Unknown information
```

e

```text
Known absence
```

Exemplo:

Um gol sem jogador identificado significa:

```text
player_id = NULL
```

Não significa:

```text
player_id = "Unknown Player"
```

Criar entidades artificiais para representar informações desconhecidas prejudica:

* consultas;
* estatísticas;
* integridade histórica;
* qualidade dos dados.

---

# Data Quality Principle

O sistema seguirá o princípio:

> É melhor possuir um dado incompleto e correto do que um dado completo e incorreto.

---

# Consequences

## Positivas

* Permite importar competições antigas;
* Mantém fidelidade histórica;
* Evita criação de dados fictícios;
* Facilita evolução gradual da base;
* Permite enriquecer informações futuramente.

---

## Negativas

* Consultas precisam tratar valores nulos;
* Algumas estatísticas podem não estar disponíveis;
* A API deve deixar claro quando uma informação não existe.

---

# Alternatives Considered

## Tornar todas as informações obrigatórias

Exemplo:

```text
Goal

player_id NOT NULL
```

### Motivo da rejeição

Impossibilitaria representar competições históricas onde essas informações nunca foram registradas.

---

## Criar entidades genéricas para dados desconhecidos

Exemplo:

```text
Player

Unknown Player
```

### Motivo da rejeição

Mistura ausência de informação com uma entidade real.

Isso poderia gerar:

* estatísticas incorretas;
* rankings artificiais;
* problemas de integridade.

---

# Historical Import

Durante importações históricas, o sistema deve aceitar diferentes níveis de detalhamento.

Exemplo:

## Level 1 — Resultado básico

```text
Competition Event

Score

Winner
```

---

## Level 2 — Estatísticas

```text
Goals

Cards

Possession

Shots
```

---

## Level 3 — Dados completos

```text
Lineups

Players

Substitutions

Detailed Actions
```

A Competition Engine deve funcionar independentemente do nível de detalhamento disponível.

---

# Impact

Esta decisão influencia:

* database.md;
* API responses;
* importadores históricos;
* Competition Engine;
* estatísticas;
* relatórios.

---

# Implementation Guidelines

Novos modelos devem avaliar cuidadosamente se um campo representa:

## Informação obrigatória do domínio

Exemplo:

```text
Competition Event Date
```

ou

## Informação opcional dependente de disponibilidade

Exemplo:

```text
Player involved in action
```

Campos opcionais devem possuir documentação explicando quando podem ser nulos.

---

# References

Documentos relacionados:

* `database.md`
* `engine.md`
* `api.md`

---

# Decision Summary

A Sports Data Platform aceita dados históricos parcialmente completos, preservando somente informações conhecidas e evitando criação de dados artificiais para preencher lacunas históricas.
