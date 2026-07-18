# ADR-016 — Sport-Specific Data Without Domain Coupling

## Status

Em revisão

## Date

2026-07-18

---

# Context

Durante a evolução do modelo de domínio, surgiu a necessidade de representar informações específicas de determinadas modalidades esportivas.

Exemplos:

## Futebol

Informações específicas:

```text id="c8m5ry"
Player Position

Jersey Number

Squad Number
```

---

## Fórmula 1

Informações específicas:

```text id="r7x2kp"
Car Number

Chassis

Engine Supplier

Constructor
```

---

## WEC

Informações específicas:

```text id="n6q9mv"
Car Category

Class

Driver Lineup

Vehicle Specification
```

Uma abordagem inicial poderia adicionar esses campos diretamente nas entidades genéricas.

Exemplo:

```java
class Participant {

    private String name;

    private String driverName;

    private String carModel;

    private Integer jerseyNumber;

}
```

Porém, isso criaria acoplamento entre o modelo principal e esportes específicos.

---

# Decision

Dados específicos de modalidades esportivas não serão adicionados diretamente às entidades genéricas do domínio.

O modelo principal permanecerá focado nos conceitos compartilhados.

Informações específicas deverão utilizar mecanismos de extensão.

---

# Core Domain Model

O modelo comum contém apenas informações universais.

Exemplo:

```text id="k4w7mx"
Participant

id

name

type
```

---

# Extension Model

Dados específicos podem ser representados através de extensões.

Exemplos:

```text id="q9m5vd"
Football Participant Extension

    position

    shirt_number
```

---

```text id="z6p8rx"
Motorsport Participant Extension

    vehicle_number

    constructor

    category
```

---

# Rationale

O sistema possui como objetivo suportar múltiplos esportes.

Portanto, entidades centrais devem representar conceitos compartilhados.

A pergunta correta é:

> "Esse dado existe para todos os participantes?"

Se sim:

```text id="h7n3pm"
Core Entity
```

Se não:

```text id="v8q5xz"
Extension
```

---

# Example

## Shared Data

```text id="t4k8qy"
Participant

name

country

type
```

Aplica-se a:

* jogador;
* piloto;
* equipe;
* carro.

---

## Sport Specific

```text id="m3x7pw"
Driver

license_number

championship_points_category
```

ou:

```text id="p9v4ks"
Football Player

position

shirt_number
```

---

# JSONB Usage

Quando uma extensão possuir baixa estabilidade ou alta variação, JSONB poderá ser utilizado.

Exemplo:

```json id="y8m4qx"
{
    "vehicle": {
        "engine": "Toyota",
        "chassis": "GR010"
    }
}
```

Entretanto, informações frequentemente consultadas devem preferencialmente possuir modelagem relacional.

---

# Consequences

## Positivas

* Mantém o domínio genérico;
* Evita explosão de entidades;
* Permite novos esportes;
* Reduz alterações estruturais;
* Mantém a Competition Engine desacoplada.

---

## Negativas

* Consultas podem exigir joins adicionais;
* Algumas extensões precisam de regras próprias;
* O modelo inicial possui mais abstração.

---

# Alternatives Considered

## Adicionar todos os campos em Participant

Exemplo:

```text id="f5q8nx"
Participant

name

team

driver

car

player_position

category
```

### Motivo da rejeição

Criaria uma entidade com responsabilidades incompatíveis.

Além disso:

* muitos campos seriam nulos;
* novas modalidades exigiriam alterações constantes;
* aumentaria acoplamento.

---

## Criar entidades completamente separadas

Exemplo:

```text id="w6m3pz"
FootballPlayer

Formula1Driver

RaceCar
```

### Motivo da rejeição

Embora represente bem cada esporte, quebra a abstração de participante e dificulta a reutilização da Competition Engine.

---

# Relationship With Competition Engine

A Engine deve operar principalmente sobre conceitos genéricos.

Exemplo:

```text id="x5q9mv"
Participant

        ↓

Competition Entry

        ↓

Metrics

        ↓

Classification
```

Detalhes específicos devem ser utilizados apenas quando uma regra da competição exigir.

---

# Impact

Esta decisão influencia:

* database.md;
* Competition Engine;
* Participant model;
* APIs futuras;
* novos esportes.

---

# Implementation Guidelines

Antes de adicionar um campo ao modelo principal, avaliar:

1. O conceito existe em todos os esportes?
2. É utilizado pela Engine genericamente?
3. Possui significado independente da modalidade?

Se a resposta for não, deve ser tratado como extensão.

---

# References

Documentos relacionados:

* `database.md`
* `engine.md`
* `ADR-006 — Generic Participant Model`

---

# Decision Summary

Informações específicas de cada esporte devem ser modeladas como extensões, mantendo o domínio central genérico e evitando acoplamento entre a arquitetura e modalidades específicas.
