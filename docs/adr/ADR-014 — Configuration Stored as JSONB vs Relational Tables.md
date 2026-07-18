# ADR-014 — Sports and Competition Independence

## Status

Em revisão

## Date

2026-07-18

---

# Context

Durante a modelagem do domínio esportivo, surgiu a necessidade de definir a relação entre esporte e competição.

Uma abordagem simples seria tratar cada competição como uma modalidade independente.

Exemplo:

```text id="v7x4mq"
Football World Cup

Formula 1

Brazilian Championship
```

Porém, essas entidades representam competições, não esportes.

O domínio possui uma relação hierárquica diferente:

```text id="q3m8wy"
Sport

    |
    |
    N

Competition
```

---

# Decision

`Sport` e `Competition` serão entidades independentes.

A relação será:

```text id="k8r2px"
Sport

1

|

N

Competition
```

---

# Sport

Representa uma modalidade esportiva.

Exemplos:

```text id="p6w9ms"
Football

Formula 1

WEC

Basketball
```

Responsabilidades:

* identificar a modalidade;
* agrupar competições relacionadas;
* fornecer contexto esportivo.

---

# Competition

Representa uma competição específica.

Exemplos:

```text id="n4y7zk"
Football

    FIFA World Cup

    Brazilian Championship

    UEFA Champions League


Motorsport

    Formula 1 World Championship

    World Endurance Championship
```

Responsabilidades:

* definir uma competição;
* possuir edições;
* possuir templates;
* possuir organização responsável.

---

# Example

Modelo:

```text id="m5q8vs"
Sport

Football


Competitions:

    World Cup

    Premier League

    Copa Libertadores
```

---

```text id="b8x3yn"
Sport

Motorsport


Competitions:

    Formula 1

    WEC

    IndyCar
```

---

# Rationale

O esporte define o contexto geral.

A competição define as regras específicas.

Exemplo:

Duas competições de futebol podem possuir formatos completamente diferentes.

```text id="z5k8pd"
Football

    World Cup

        Groups + Knockout


    Brazilian Championship

        Round Robin
```

Portanto, regras não devem pertencer ao esporte.

---

# Relationship With Competition Template

O fluxo do domínio será:

```text id="g7m2rx"
Sport

    ↓

Competition

    ↓

Competition Template

    ↓

Competition Edition
```

Cada camada possui responsabilidade diferente.

---

# Consequences

## Positivas

* Evita duplicação de modalidades;
* Permite múltiplas competições por esporte;
* Mantém regras no local correto;
* Facilita novos esportes;
* Melhora consultas e organização do domínio.

---

## Negativas

* Adiciona uma entidade extra;
* Algumas consultas precisam navegar pela relação;
* Desenvolvedores precisam diferenciar Sport de Competition.

---

# Alternatives Considered

## Sport e Competition como a mesma entidade

Exemplo:

```text id="j9m4zw"
Football World Cup

Formula 1
```

### Motivo da rejeição

Mistura conceitos diferentes.

"Football" não é uma competição.

---

## Colocar regras diretamente em Sport

Exemplo:

```text id="x5r7vm"
Sport

Football Rules
```

### Motivo da rejeição

Competições do mesmo esporte podem possuir regras diferentes.

Exemplo:

```text id="r2k8nx"
Football

World Cup:

Group Stage


Brazilian Championship:

League Table
```

---

# Impact

Esta decisão influencia:

* database.md;
* Competition Template;
* Competition Engine;
* API;
* consultas históricas.

---

# Implementation Guidelines

Novas funcionalidades devem respeitar a separação:

## Sport

Pergunta:

> "Qual modalidade é essa?"

Exemplo:

```text
Football
```

---

## Competition

Pergunta:

> "Qual competição dentro dessa modalidade?"

Exemplo:

```text
World Cup
```

---

## Competition Edition

Pergunta:

> "Qual edição específica foi disputada?"

Exemplo:

```text
World Cup 2026
```

---

# References

Documentos relacionados:

* `database.md`
* `engine.md`
* `api.md`

---

# Decision Summary

Sport representa a modalidade esportiva e Competition representa uma competição específica dentro dessa modalidade. As regras e formatos pertencem à competição, não ao esporte.
