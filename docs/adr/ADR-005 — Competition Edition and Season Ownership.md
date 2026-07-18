# ADR-005 — Competition Edition and Season Ownership

## Status

Em revisão

## Date

2026-07-18

---

# Context

Durante a modelagem do domínio de competições esportivas, surgiu a necessidade de representar temporadas.

Exemplos:

* Campeonato Brasileiro 2026;
* Copa do Mundo 2026;
* Fórmula 1 2026;
* WEC 2026.

Uma abordagem comum seria criar uma entidade global:

```text id="c9fj0l"
Season

id
year
startDate
endDate
```

e relacionar todas as competições a ela.

Porém, ao analisar o domínio esportivo, percebeu-se que o conceito de temporada possui significado apenas dentro de uma competição específica.

---

# Decision

A temporada será representada através da entidade `Competition Edition`.

A relação será:

```text id="z7h5nq"
Competition

    |
    |
    +---- Competition Edition 2024
    |
    +---- Competition Edition 2025
    |
    +---- Competition Edition 2026
```

Não existirá uma entidade `Season` global.

---

# Example

## Competition

```text id="3w2i4m"
Formula One World Championship
```

Possui:

```text id="k2d6s1"
Competition Edition 2026

Competition Edition 2025

Competition Edition 2024
```

---

## Different Calendars

Essa decisão permite representar corretamente competições com calendários diferentes.

Exemplo:

```text id="39v9xk"
Formula 1 2026

01/03/2026
        |
        |
20/12/2026
```

Enquanto:

```text id="7u6t3m"
European Football Season 2025/2026

01/08/2025
        |
        |
31/05/2026
```

Ambos são "temporadas", mas possuem significados diferentes.

---

# Rationale

O domínio principal é a competição.

A temporada é apenas uma ocorrência daquela competição em um determinado período.

A pergunta correta não é:

> "Qual temporada é essa?"

Mas sim:

> "Qual edição desta competição está sendo disputada?"

---

# Data Model

Relacionamento:

```text id="ev5l7k"
Competition

1
|
|
N

Competition Edition
```

Exemplo:

```java id="u4l1pj"
@Entity
class CompetitionEntity {

}

@Entity
class CompetitionEditionEntity {

    private CompetitionEntity competition;

    private String season;

}
```

---

# Consequences

## Positivas

* Modelo mais alinhado ao domínio esportivo;
* Suporta diferentes calendários;
* Evita uma entidade global artificial;
* Facilita consultas históricas;
* Mantém contexto da temporada.

---

## Negativas

* Consultas envolvendo várias competições precisam considerar a Competition;
* Não existe uma tabela única de temporadas;
* Algumas integrações externas podem exigir transformação.

---

# Alternatives Considered

## Criar entidade Season global

Exemplo:

```text id="z9s9r2"
Season

2026
2025
2024
```

Relacionamentos:

```text id="x9tqz8"
Season

    |
    +---- Competition Edition
```

### Motivo da rejeição

Uma temporada global assume que todas as competições compartilham o mesmo conceito temporal.

Isso não é verdade.

Exemplos:

* Copa do Mundo ocorre a cada 4 anos;
* Fórmula 1 segue ano civil;
* Futebol europeu atravessa dois anos;
* Competições podem possuir calendários personalizados.

---

# Impact

Esta decisão influencia:

* modelo de banco;
* API;
* consultas históricas;
* Competition Engine;
* importação de dados.

Todos os recursos relacionados a uma temporada devem utilizar `Competition Edition`.

---

# Implementation Guidelines

Ao criar uma nova edição de competição:

Obrigatório:

* Competition;
* Competition Template;
* período da edição;
* identificação da temporada.

Exemplo:

```json id="6x3pj0"
{
    "competitionId": 10,
    "competitionTemplateId": 3,
    "season": "2026"
}
```

---

# References

Documentos relacionados:

* `database.md`
* `engine.md`
* `api.md`

---

# Decision Summary

A temporada não é uma entidade global. Cada Competition possui suas próprias Competition Editions, representando suas ocorrências históricas e mantendo o contexto correto do domínio.
