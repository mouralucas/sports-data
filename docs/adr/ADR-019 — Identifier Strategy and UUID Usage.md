# ADR-019 — Identifier Strategy and UUID Usage

## Status

Em revisão

## Date

2026-07-18

---

# Context

Durante a modelagem da Sports Data Platform, surgiu a necessidade de definir como as entidades seriam identificadas.

Uma abordagem tradicional em bancos relacionais é utilizar IDs sequenciais.

Exemplo:

```text id="c5q8mz"
sport

id = 1


competition

id = 10
```

Esse modelo funciona bem em aplicações monolíticas, porém possui limitações em ambientes distribuídos.

A arquitetura da plataforma considera:

* futuros microserviços;
* integração com serviço de usuários;
* importação de dados externos;
* possibilidade de múltiplas fontes.

---

# Decision

Todas as entidades persistidas utilizarão UUID como identificador primário.

Exemplo:

```text id="n8m4rx"
Sport

id:

550e8400-e29b-41d4-a716-446655440000
```

---

# Identifier Characteristics

Os identificadores devem ser:

* únicos globalmente;
* gerados sem dependência do banco;
* seguros para exposição em APIs;
* independentes entre serviços.

---

# Database Example

Modelo:

```sql id="r7q3mp"
sport

id UUID PRIMARY KEY

name VARCHAR
```

---

# Entity Example

Java:

```java id="k5x8vq"
@Entity
public class SportEntity extends BaseEntity {

    @Id
    private UUID id;

}
```

---

# Rationale

Em uma arquitetura distribuída, IDs sequenciais possuem limitações.

Exemplo:

Serviço A:

```text id="v6q9kp"
User

id = 100
```

Serviço B:

```text id="m3x8rz"
Participant

id = 100
```

Existe colisão conceitual quando entidades de diferentes contextos são combinadas.

UUID evita esse problema.

---

# Relationship With External Systems

Importações externas também podem possuir seus próprios identificadores.

Exemplo:

Fonte externa:

```text id="h8p2mx"
Player ID:

12345
```

Sistema interno:

```text id="z4q7vn"
Participant ID:

550e8400-e29b
```

O identificador externo deve ser armazenado como referência externa, não substituindo o UUID interno.

---

# Consequences

## Positivas

* Compatível com microserviços;
* Facilita integrações;
* Permite geração antes da persistência;
* Evita exposição de sequência interna;
* Facilita sincronização entre sistemas.

---

## Negativas

* UUID ocupa mais espaço que BIGINT;
* Índices podem ser maiores;
* Ordenação temporal não é natural em UUID puro.

---

# Alternatives Considered

## BIGINT auto increment

Exemplo:

```sql
id BIGSERIAL
```

### Motivo da rejeição

Embora eficiente, cria dependência do banco para geração de IDs.

Além disso:

* dificulta sincronização entre serviços;
* expõe sequência interna;
* complica importações distribuídas.

---

## Usar UUID apenas em APIs

Exemplo:

Banco:

```text
id BIGINT
```

API:

```text
public_id UUID
```

### Motivo da rejeição

Mantém duas identidades para a mesma entidade.

Isso aumenta complexidade e pode gerar inconsistências.

---

# UUID Generation

A geração do UUID deve ocorrer preferencialmente na aplicação.

Fluxo:

```text id="q8v3mx"
Create Entity

        ↓

Generate UUID

        ↓

Persist Entity
```

---

# Impact

Esta decisão influencia:

* todas as tabelas;
* entidades JPA;
* APIs;
* integrações;
* migrações;
* relacionamentos.

---

# Implementation Guidelines

Novas entidades devem:

* utilizar UUID como PK;
* utilizar UUID em FKs;
* não criar IDs numéricos artificiais;
* manter referências externas separadas.

Exemplo:

```text id="x7m2pv"
participant

id UUID

external_id VARCHAR
```

---

# References

Documentos relacionados:

* `database.md`
* `architecture.md`
* `api.md`
* `ADR-008 — Base Entity and Audit Fields`

---

# Decision Summary

A plataforma utiliza UUID como identificador primário de entidades persistidas, garantindo independência entre serviços, integração futura e flexibilidade para múltiplas fontes de dados.
