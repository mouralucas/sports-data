# ADR-020 — Domain Ownership and Service Boundaries

## Status

Em revisão

## Date

2026-07-18

---

# Context

Durante a definição da arquitetura da Sports Data Platform, surgiu a necessidade de estabelecer os limites de responsabilidade entre serviços.

A aplicação possui integração futura com outros domínios, especialmente:

* usuários;
* autenticação;
* permissões;
* identidade.

Um exemplo é o campo:

```text id="r8x3mq"
created_by UUID
```

que referencia um usuário pertencente a outro serviço.

A dúvida principal:

> A Sports Data Platform deve armazenar e gerenciar todos esses dados externos?

---

# Decision

Cada serviço será responsável pelo seu próprio domínio.

A Sports Data Platform será proprietária dos dados relacionados a:

```text id="m5q9vz"
Sports

Competitions

Participants

Events

Results

Classifications

Rules
```

Outros serviços serão responsáveis pelos seus próprios contextos.

---

# Domain Ownership

## Sports Data Platform

Responsável por:

```text id="z7p2mx"
Sport

Competition

Competition Template

Competition Edition

Competition Event

Competition Rules

Classification
```

---

## User Service

Responsável por:

```text id="q4v8pn"
User

Authentication

Identity

Permissions
```

---

# Integration Example

Quando um usuário cria uma competição:

Fluxo:

```text id="h9m3kw"
User Service

        |
        | user_id
        ↓

Sports Data Platform

        |
        ↓

Competition created_by
```

A plataforma esportiva armazena apenas:

```text id="n6x2qp"
created_by = UUID
```

Ela não replica:

```text id="p8w5zr"
name

email

password

permissions
```

---

# Rationale

Cada domínio deve possuir uma única fonte de verdade.

Duplicar dados externos gera problemas:

Exemplo:

User Service:

```text id="k3q7vm"
Lucas

email@example.com
```

Sports Data Platform:

```text id="f5n8rx"
Lucas

old-email@example.com
```

Agora existem duas versões da mesma informação.

---

# Consequences

## Positivas

* Menor acoplamento;
* Serviços independentes;
* Evolução separada;
* Melhor organização de responsabilidades;
* Facilita escalabilidade.

---

## Negativas

* Algumas consultas exigem chamadas entre serviços;
* Dados externos podem precisar de cache;
* Requer contratos de integração bem definidos.

---

# Alternatives Considered

## Centralizar todos os dados em um único serviço

Exemplo:

```text id="w6p2mz"
Sports Service

Users

Competitions

Permissions
```

### Motivo da rejeição

Mistura domínios diferentes.

Com o crescimento do sistema:

* aumenta acoplamento;
* dificulta manutenção;
* reduz autonomia dos serviços.

---

## Replicar informações completas de outros serviços

Exemplo:

```text id="y7m2px"
Competition

created_by_name

created_by_email
```

### Motivo da rejeição

Cria duplicação e problemas de sincronização.

---

# Relationship With Database Model

As entidades armazenam referências externas apenas quando necessário.

Exemplo:

```text id="x9v4qn"
Competition

id

name

created_by UUID
```

Não existe:

```text id="b3m8pz"
created_by User Entity
```

---

# Impact

Esta decisão influencia:

* arquitetura de serviços;
* banco de dados;
* autenticação;
* APIs;
* integrações futuras.

---

# Implementation Guidelines

Ao adicionar uma nova referência externa:

Avaliar:

1. Este dado pertence ao domínio esportivo?
2. O serviço externo já é dono dessa informação?
3. Precisamos armazenar apenas uma referência?

A preferência deve ser:

```text id="m7q2vx"
Store Identifier

Integrate When Needed
```

---

# References

Documentos relacionados:

* `architecture.md`
* `database.md`
* `api.md`
* `ADR-008 — Base Entity and Audit Fields`
* `ADR-019 — Identifier Strategy and UUID Usage`

---

# Decision Summary

A Sports Data Platform mantém propriedade exclusiva sobre o domínio esportivo. Serviços externos fornecem referências e integrações, mas não têm seus dados replicados ou controlados pela plataforma.
