# ADR-006 — Generic Participant Model

## Status

Em revisão

## Date

2026-07-18

---

# Context

Durante a modelagem do domínio esportivo, surgiu a necessidade de representar os participantes de uma competição.

Inicialmente, uma abordagem específica por esporte poderia criar entidades próprias.

Exemplo:

```text id="wqg0eu"
Football

Team
Player


Formula 1

Driver
Car
Constructor


WEC

Car
Team
Driver
```

Embora esse modelo represente bem cada esporte individualmente, ele cria um problema de escalabilidade.

Cada novo esporte exigiria novas entidades, relacionamentos e regras específicas.

---

# Decision

O sistema utilizará uma entidade genérica `Participant` como representação de qualquer entidade competitiva.

A entidade será associada ao contexto da competição através de `Competition Entry`.

---

# Domain Model

Estrutura:

```text id="m38d6p"
Participant

        |
        |
        N

Competition Entry

        |
        |
        N

Competition Edition
```

---

# Examples

## Football

```text id="2rj6r1"
Participant

    Brazil National Team

    Argentina National Team
```

---

## Formula 1

```text id="m6r3g7"
Participant

    Max Verstappen

    Ferrari Car #16

    Mercedes Constructor
```

---

## WEC

```text id="c4b9x5"
Participant

    Toyota GR010 #7

    Ferrari 499P #50
```

---

# Rationale

A competição não precisa saber qual tipo específico de entidade está competindo.

Ela precisa apenas saber:

* quem participa;
* como pontua;
* como se classifica;
* como se relaciona com eventos.

Exemplo:

```text id="4tx8i8"
Competition Event

Participant A

versus

Participant B
```

O significado de "Participant" depende do esporte e da configuração da competição.

---

# Participant Types

O tipo do participante será representado através de configuração ou enumeração controlada.

Exemplos:

```text id="9v0qyp"
TEAM

PLAYER

DRIVER

CAR

CONSTRUCTOR

NATIONAL_TEAM
```

---

# Consequences

## Positivas

* Suporte a múltiplos esportes;
* Redução de entidades específicas;
* Modelo mais extensível;
* Facilita criação de novos formatos;
* Mantém Competition Engine genérica.

---

## Negativas

* Consultas podem exigir filtros pelo tipo;
* Algumas regras específicas precisam conhecer o tipo do participante;
* A modelagem inicial é menos intuitiva para desenvolvedores acostumados a sistemas específicos.

---

# Alternatives Considered

## Criar entidades específicas por esporte

Exemplo:

```text id="z0xv2m"
Team

Player

Driver

Car
```

### Motivo da rejeição

O modelo não escala.

Adicionar um novo esporte exigiria:

* novas tabelas;
* novas entidades;
* novos relacionamentos;
* novas regras na Engine.

Isso contradiz o objetivo principal do projeto: uma plataforma genérica de competições.

---

## Criar uma hierarquia de herança JPA

Exemplo:

```java id="0qg9e2"
Participant

    TeamParticipant

    DriverParticipant

    CarParticipant
```

### Motivo da rejeição

Embora possível, adiciona complexidade desnecessária.

Grande parte do comportamento é definido pela competição, não pelo participante isoladamente.

---

# Relationship With Competition Entry

A entidade `Competition Entry` representa a participação de um Participant em uma edição específica.

Isso é necessário porque o mesmo participante pode possuir diferentes contextos.

Exemplo:

```text id="0j4f5q"
Participant

Brazil National Team


Competition Entry

World Cup 2026

```

Outro exemplo:

```text id="7f8w2d"
Participant

Ferrari Car #16


Competition Entry

Formula 1 2026
```

---

# Impact

Esta decisão influencia:

* banco de dados;
* API;
* Competition Engine;
* classificação;
* eventos;
* importação histórica.

Todos os recursos competitivos devem utilizar `Participant` como referência principal.

---

# Implementation Guidelines

Novos esportes não devem criar novas entidades de participante sem uma necessidade real.

Antes de criar uma entidade específica, avaliar se:

* o comportamento pertence ao participante;
* ou se pertence à configuração da competição.

A preferência deve ser manter a lógica na Competition Engine e utilizar a abstração existente.

---

# References

Documentos relacionados:

* `database.md`
* `engine.md`
* `api.md`

---

# Decision Summary

O sistema utiliza um modelo genérico de Participant para representar qualquer entidade competitiva, permitindo suportar múltiplos esportes através de uma única arquitetura.
