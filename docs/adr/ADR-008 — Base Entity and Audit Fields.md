## ADR-008 — Base Entity and Audit Fields

## Status

Accepted

## Date

2026-07-18

---

# Context

Durante a modelagem das entidades persistidas da Sports Data Platform, identificou-se que diversas tabelas possuem informações comuns relacionadas ao ciclo de vida dos registros.

Exemplos:

* data de criação;
* data da última alteração;
* usuário responsável pela criação.

Inicialmente, essas colunas poderiam ser adicionadas individualmente em cada tabela.

Exemplo:

```text
sport

id
name
created_at
updated_at
created_by


competition

id
name
created_at
updated_at
created_by
```

Porém, essa abordagem gera:

* duplicação de código;
* inconsistência entre entidades;
* maior esforço de manutenção.

---

# Decision

Todas as entidades persistidas herdarão uma entidade base contendo os campos comuns de auditoria.

Modelo:

```text
BaseEntity

    id

    created_at

    updated_at

    created_by
```

Todas as entidades de domínio utilizarão essa estrutura.

---

# Entity Example

Exemplo em Java:

```java
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    private UUID id;

    private Instant createdAt;

    private Instant updatedAt;

    private UUID createdBy;
}
```

Exemplo:

```java
@Entity
public class SportEntity extends BaseEntity {

    private String name;

}
```

---

# Field Definition

## id

Identificador único da entidade.

Características:

* UUID;
* gerado pela aplicação;
* utilizado como referência entre serviços.

---

## created_at

Representa o momento em que o registro foi criado.

Características:

* preenchido automaticamente;
* não deve ser alterado após criação.

---

## updated_at

Representa o momento da última alteração.

Características:

* atualizado automaticamente;
* utilizado para rastreamento de modificações.

---

## created_by

Representa o usuário responsável pela criação do registro.

Tipo:

```text
UUID
```

O identificador pertence ao microserviço de usuários.

---

# Rationale

A aplicação possui uma arquitetura distribuída onde usuários serão gerenciados por um serviço independente.

Portanto, a aplicação principal não deve armazenar informações completas do usuário.

Exemplo:

Não armazenar:

```text
created_by_name

created_by_email
```

Armazenar:

```text
created_by UUID
```

A resolução dos dados do usuário será responsabilidade do serviço de usuários.

---

# Consequences

## Positivas

* Padronização entre entidades;
* Auditoria consistente;
* Facilita integração com autenticação futura;
* Menos duplicação;
* Facilita criação de novas tabelas.

---

## Negativas

* Nem todas as tabelas necessariamente precisam de todos os campos;
* Pode exigir tratamento especial para dados importados historicamente;
* Requer integração futura com serviço de usuários.

---

# Alternatives Considered

## Repetir campos em todas as entidades

Exemplo:

```java
@Entity
class CompetitionEntity {

    private Instant createdAt;

    private Instant updatedAt;

}
```

### Motivo da rejeição

Cria duplicação e aumenta risco de inconsistência.

---

## Criar tabela de auditoria separada

Exemplo:

```text
entity_audit

entity_id

action

user_id

timestamp
```

### Motivo da rejeição

Esse modelo é útil para histórico completo de alterações, mas não substitui informações básicas de ciclo de vida.

Uma auditoria detalhada poderá ser adicionada futuramente sem remover os campos básicos.

---

# Historical Data Consideration

Dados históricos importados podem não possuir usuário de criação conhecido.

Nestes casos:

```text
created_by = NULL
```

é permitido.

O sistema deve preservar a informação histórica sem inventar um usuário.

---

# Impact

Esta decisão influencia:

* todas as entidades JPA;
* migrations;
* API responses;
* importação histórica;
* integração com autenticação.

---

# Implementation Guidelines

Novas entidades persistidas devem:

* estender `BaseEntity`;
* utilizar UUID como identificador;
* utilizar timestamps com timezone;
* não duplicar campos de auditoria.

Exceções devem ser justificadas em documentação.

---

# References

Documentos relacionados:

* `database.md`
* `architecture.md`
* `api.md`

---

# Decision Summary

Todas as entidades persistidas utilizam uma entidade base com campos comuns de identificação e auditoria, garantindo consistência, rastreabilidade e preparação para integração futura com o serviço de usuários.
