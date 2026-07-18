# ADR-001 — Database Table Naming Convention

## Status

Em revisão

## Date

2026-07-17

---

# Context

Durante a modelagem inicial do banco de dados da Sports Data Platform, surgiu a discussão sobre a convenção de nomes das tabelas.

A convenção mais comum em aplicações relacionais é utilizar nomes no plural, representando uma coleção de registros.

Exemplos:

```text
sports
competitions
participants
organizations
```

Entretanto, neste projeto, as tabelas representam conceitos de domínio e não apenas coleções de registros.

Cada tabela representa uma entidade conceitual do sistema.

Exemplo:

```text
sport
competition
participant
organization
```

O domínio `sport` representa o conceito de esporte. A existência de múltiplos esportes é uma característica dos dados armazenados, mas não altera a representação conceitual da entidade.

---

# Decision

Todas as tabelas do banco de dados utilizarão nomes no singular.

Exemplos:

```text
sport
organization
competition
competition_template
competition_edition
participant
competition_event
```

Esta convenção será aplicada a todas as novas tabelas criadas no sistema.

---

# Rationale

A escolha pelo singular está alinhada com a forma como as entidades são representadas no código da aplicação.

Exemplo:

```java
@Entity
class SportEntity {
}
```

Representação:

```text
Sport
```

Banco:

```text
sport
```

Dessa forma, existe uma correspondência direta entre:

```text
Domain Concept
        |
        |
Application Entity
        |
        |
Database Table
```

A tabela representa o conceito, não a quantidade de registros existentes.

---

# Consequences

## Positivas

* Maior alinhamento entre domínio, código e banco;
* Facilita o entendimento do modelo por desenvolvedores;
* Mantém consistência entre Entity, Model e tabela;
* Evita diferenças artificiais entre camadas.

---

## Negativas

* Pode fugir de convenções populares em projetos SQL;
* Desenvolvedores acostumados com plural podem estranhar inicialmente;
* Algumas ferramentas de geração automática podem assumir pluralização.

---

# Alternatives Considered

## Usar tabelas no plural

Exemplo:

```text
sports
competitions
participants
```

### Motivo da rejeição

Embora seja uma convenção comum, ela representa melhor uma visão de coleção do que uma visão de domínio.

Como este sistema possui um modelo de domínio rico, onde entidades possuem comportamento e regras próprias, optamos por manter a nomenclatura alinhada ao conceito da entidade.

---

# Impact

Esta decisão influencia:

* schema PostgreSQL;
* entidades JPA;
* migrations;
* queries;
* documentação técnica;
* futuras integrações.

Todas as novas entidades devem seguir esta convenção.

---

# References

Documentos relacionados:

* `database.md`
* `architecture.md`
* `api.md`

---

# Decision Summary

As tabelas da Sports Data Platform utilizam nomes no singular porque representam conceitos de domínio, mantendo consistência entre banco de dados, aplicação e arquitetura.
