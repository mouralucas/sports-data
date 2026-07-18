# ADR-011 — Competition Template and Competition Edition Separation

## Status

Em revisão

## Date

2026-07-18

---

# Context

Durante a modelagem de competições esportivas, surgiu a necessidade de diferenciar dois conceitos:

* a definição de uma competição;
* uma realização específica dessa competição.

Exemplo:

A Copa do Mundo possui um conceito permanente:

```text id="g2k4pw"
World Cup
```

Porém, cada edição possui características próprias:

```text id="v7m1sx"
World Cup 2018

World Cup 2022

World Cup 2026
```

Cada edição possui:

* participantes diferentes;
* resultados diferentes;
* datas diferentes;
* eventos diferentes;
* eventualmente regras diferentes.

---

# Decision

O modelo será dividido em duas entidades principais:

```text id="w9q4ht"
Competition Template

        |
        |
        N

Competition Edition
```

---

# Competition Template

Representa a definição da estrutura e das regras da competição.

Responsabilidades:

* formato da competição;
* fases existentes;
* regras de classificação;
* critérios de pontuação;
* configuração da Engine.

Exemplo:

```text id="x6m8pd"
World Cup Template

Stages:

- Group Stage
- Round of 32
- Round of 16
- Quarter Final
- Semi Final
- Final
```

---

# Competition Edition

Representa uma ocorrência específica da competição.

Responsabilidades:

* período da competição;
* participantes inscritos;
* eventos realizados;
* resultados;
* estado atual.

Exemplo:

```text id="q3h8jw"
World Cup 2026 Edition

Template:

World Cup Template


Participants:

48 teams


Events:

104 matches
```

---

# Rationale

Sem essa separação, cada edição precisaria armazenar sua própria definição completa.

Exemplo:

```text id="r4s7zn"
World Cup 2022

Stages

Rules

Scoring


World Cup 2026

Stages

Rules

Scoring
```

Isso causaria:

* duplicação;
* dificuldade de manutenção;
* inconsistência entre edições.

---

# Example

## Same Template

```text id="m5q8xp"
Competition Template

Formula 1 Championship
```

Pode gerar:

```text id="z7p2vc"
Competition Edition

Formula 1 2025


Competition Edition

Formula 1 2026
```

---

## Historical Rule Changes

Caso uma competição altere seu formato:

Exemplo:

Copa do Mundo 2026:

```text id="y4s9kv"
48 teams

Group Stage

Round of 32
```

Copa do Mundo 2022:

```text id="p6r3mn"
32 teams

Group Stage

Round of 16
```

A edição pode utilizar uma versão diferente do template.

---

# Template Versioning

Alterações estruturais no formato da competição devem gerar novas versões do template quando forem incompatíveis.

Exemplo:

```text id="w8k1ds"
World Cup Template v1

32 teams


World Cup Template v2

48 teams
```

Uma Competition Edition sempre deve apontar para a versão utilizada durante sua criação.

---

# Consequences

## Positivas

* Histórico preservado;
* Reutilização de configurações;
* Suporte a mudanças de formato;
* Facilita criação de novas competições;
* Evita duplicação de regras.

---

## Negativas

* Modelo possui uma camada adicional;
* Requer controle de versões;
* Criação de uma competição envolve mais etapas.

---

# Alternatives Considered

## Colocar todas as informações diretamente em Competition Edition

Exemplo:

```text id="s8m2yk"
Competition Edition

rules

stages

scoring

events
```

### Motivo da rejeição

Mistura definição e execução.

Além disso, cada nova edição precisaria replicar toda a configuração.

---

## Criar somente Competition

Exemplo:

```text id="c2w6rs"
Competition

World Cup 2026

World Cup 2030
```

### Motivo da rejeição

Não representa corretamente que existe uma competição recorrente com múltiplas execuções.

---

# Impact

Esta decisão influencia:

* banco de dados;
* Competition Engine;
* criação de competições;
* importação histórica;
* API;
* versionamento de regras.

---

# Implementation Guidelines

Ao criar uma Competition Edition:

Obrigatório informar:

* Competition;
* Competition Template utilizado;
* período;
* temporada;
* participantes.

A edição deve manter referência para o template utilizado, mesmo que novos templates sejam criados futuramente.

---

# References

Documentos relacionados:

* `database.md`
* `engine.md`
* `api.md`

---

# Decision Summary

Competition Template representa a definição reutilizável de uma competição. Competition Edition representa uma ocorrência específica, preservando histórico, estado e regras utilizadas naquela execução.
