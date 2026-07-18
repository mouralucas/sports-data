# ADR-015 — Competition Slots and Template-Driven Structure

## Status

Em revisão

## Date

2026-07-18

---

# Context

Durante a modelagem da Competition Engine, surgiu a necessidade de representar diferentes formatos de competição.

Exemplos:

## Campeonato de pontos corridos

```text id="r7q3km"
Competition

    Round 1

        Match 1

        Match 2

    Round 2

        Match 3

        Match 4
```

---

## Copa do Mundo

```text id="p4x8vn"
Group Stage

    Group A

        Team 1

        Team 2


Knockout Stage

    Quarter Final

    Semi Final

    Final
```

---

Uma modelagem fixa baseada apenas em partidas teria dificuldade para representar:

* número variável de fases;
* quantidade variável de participantes;
* regras de classificação;
* geração dinâmica dos próximos confrontos.

---

# Decision

A estrutura da competição será dividida em duas camadas:

## Templates

Definem a estrutura esperada.

## Runtime Entities

Representam uma competição real em execução.

---

# Domain Model

```text id="z8w4mq"
Competition Template

        |

        ↓

Competition Stage Template

        |

        ↓

Stage Slot Template

        |

        ↓

Competition Event Template


        generates


Competition Edition

        |

        ↓

Competition Stage

        |

        ↓

Stage Slot

        |

        ↓

Competition Event
```

---

# Template Layer

A camada de template descreve como uma competição deve funcionar.

Exemplo:

```text id="h5m8rp"
World Cup Template

Stages:

1. Group Stage
2. Round of 32
3. Round of 16
4. Quarter Final
5. Semi Final
6. Final
```

---

# Stage Template

Define uma fase genérica.

Exemplo:

```text id="k9x3vd"
Knockout Stage
```

Configura:

* tipo de fase;
* quantidade de participantes;
* regra de avanço.

---

# Stage Slot Template

Define posições dentro de uma fase.

Exemplo:

```text id="m3q7yz"
Quarter Final

Slot 1:

Winner Group A

vs

Runner-up Group B
```

---

# Competition Event Template

Define como um evento deve ser criado.

Exemplo:

```text id="w6p2ns"
Quarter Final Match

Home:

Stage Slot A


Away:

Stage Slot B
```

---

# Runtime Layer

Quando uma Competition Edition é criada, a Engine gera as entidades reais.

Exemplo:

Template:

```text id="c8r5qx"
Quarter Final

Slot:

Winner QF1
Winner QF2
```

Gera:

```text id="t9v3km"
Competition Stage

Quarter Final 2026


Competition Event

Brazil vs France
```

---

# Rationale

A competição não deve ser codificada.

Evitar:

```java id="s5j7mh"
if(worldCup){
    createGroups();
    createKnockout();
}
```

ou:

```java id="w8p4nv"
if(formula1){
    createRaces();
}
```

A estrutura deve ser interpretada pela Engine através da configuração.

---

# Consequences

## Positivas

* Suporta diferentes formatos;
* Permite novos esportes;
* Reduz código específico;
* Facilita criação de templates;
* Permite alterações futuras.

---

## Negativas

* Modelo mais complexo;
* Engine precisa interpretar configurações;
* Debug pode ser mais difícil;
* Requer boa documentação dos templates.

---

# Alternatives Considered

## Criar tabelas fixas para cada fase

Exemplo:

```text id="f6q2pw"
group_stage

quarter_final

semi_final

final
```

### Motivo da rejeição

Esse modelo assume formatos conhecidos.

Novas competições exigiriam mudanças estruturais.

---

## Gerar eventos diretamente sem templates

Exemplo:

```text id="g7x2mp"
Competition Event

Brazil vs Argentina
```

### Motivo da rejeição

Perde a capacidade de representar:

* eventos futuros;
* posições ainda não definidas;
* regras de classificação;
* geração automática.

---

# Relationship With Competition Engine

A Engine utiliza templates para gerar e evoluir competições.

Fluxo:

```text id="v4m9kx"
Competition Template

        ↓

Competition Edition Created

        ↓

Generate Stages

        ↓

Generate Slots

        ↓

Generate Events

        ↓

Process Results

        ↓

Advance Participants
```

---

# Impact

Esta decisão influencia:

* database.md;
* engine.md;
* Competition Template;
* Competition Edition;
* geração automática;
* classificação.

---

# Implementation Guidelines

Templates devem:

* ser versionáveis;
* possuir validação;
* evitar regras específicas de uma única competição;
* representar padrões reutilizáveis.

Entidades runtime devem:

* preservar o estado real;
* nunca alterar o template original;
* permitir reconstrução.

---

# References

Documentos relacionados:

* `database.md`
* `engine.md`
* `api.md`

---

# Decision Summary

O sistema utiliza uma arquitetura baseada em templates para definir estruturas competitivas e entidades runtime para representar execuções reais, permitindo que a Competition Engine suporte múltiplos formatos esportivos de forma configurável.
