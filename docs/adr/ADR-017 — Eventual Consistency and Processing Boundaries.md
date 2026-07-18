# ADR-017 — Eventual Consistency and Processing Boundaries

## Status

Em revisão

## Date

2026-07-18

---

# Context

A arquitetura da Sports Data Platform possui uma separação entre:

* API;
* persistência;
* Competition Engine;
* dados derivados.

Uma alteração em um recurso pode desencadear diversos processamentos.

Exemplo:

```text id="7p4k8m"
Competition Event Updated

        ↓

Validate Result

        ↓

Update Metrics

        ↓

Update Classification

        ↓

Generate Next Stage
```

Surgiu então a necessidade de definir quando uma operação é considerada concluída.

---

# Decision

O sistema utilizará um modelo híbrido de consistência:

## Dados de domínio

Devem possuir consistência imediata.

## Dados derivados

Podem utilizar processamento eventual quando necessário.

---

# Domain Data

São considerados dados primários:

```text id="m8x3qv"
Competition Event

Participant

Competition Entry

Event Action

Competition Configuration
```

Quando alterados, devem ser persistidos de forma consistente.

---

# Derived Data

São considerados dados derivados:

```text id="q7n2mz"
Classification Result

Statistics

Rankings

Generated Information
```

Esses dados podem ser recalculados ou atualizados posteriormente.

---

# Initial Implementation

A primeira implementação utilizará processamento síncrono dentro do fluxo principal.

Exemplo:

```text id="h5r8kp"
HTTP Request

    ↓

Transaction Begin

    ↓

Update Domain

    ↓

Execute Engine

    ↓

Persist Results

    ↓

Transaction Commit
```

---

# Future Evolution

Caso operações se tornem custosas, poderão utilizar eventos internos.

Exemplo:

```text id="p9x4vw"
HTTP Request

    ↓

Persist Domain Change

    ↓

Publish Domain Event

    ↓

Competition Engine Worker

    ↓

Update Derived Data
```

---

# Rationale

Nem todas as alterações possuem o mesmo nível de criticidade.

Exemplo:

Registrar o resultado de uma partida:

```text id="z8q2mv"
Home Team 2 x 1 Away Team
```

precisa ser imediatamente consistente.

Porém:

Gerar estatísticas detalhadas de uma temporada inteira pode aguardar processamento posterior.

---

# Consequences

## Positivas

* Implementação inicial mais simples;
* Mantém consistência do domínio;
* Permite evolução para processamento assíncrono;
* Facilita escalabilidade futura.

---

## Negativas

* Modelo híbrido exige documentação clara;
* Processamentos assíncronos exigem monitoramento;
* Pode existir diferença temporária em dados derivados.

---

# Alternatives Considered

## Processar tudo de forma síncrona

Exemplo:

```text id="x7m3pk"
API

↓

Todas as regras

↓

Todos os cálculos

↓

Resposta
```

### Motivo da rejeição como solução definitiva

Pode gerar tempos de resposta elevados conforme o volume aumenta.

---

## Processar tudo de forma assíncrona

Exemplo:

```text id="k5q9mz"
API

↓

Queue

↓

Engine
```

### Motivo da rejeição inicial

Adicionar complexidade distribuída antes da necessidade real.

Além disso, algumas operações exigem confirmação imediata.

---

# Transaction Boundary

A transação deve proteger alterações de domínio.

Exemplo:

```text id="m6q9rx"
BEGIN TRANSACTION

Update Competition Event

Update Classification

Commit
```

Caso alguma regra falhe:

```text id="v3p8kh"
Rollback
```

---

# Domain Events

Eventos internos poderão ser introduzidos futuramente.

Exemplos:

```text id="q8w4mn"
CompetitionEventFinished

ClassificationUpdated

StageCompleted
```

Eles não substituem o domínio; apenas comunicam mudanças.

---

# Impact

Esta decisão influencia:

* Spring transactions;
* services;
* Competition Engine;
* jobs futuros;
* filas/eventos;
* cache.

---

# Implementation Guidelines

Ao criar novos processos:

Perguntar:

1. Isso altera o estado principal?
2. Precisa estar disponível imediatamente?
3. Pode ser reconstruído?

Se altera o domínio:

```text id="w5k8px"
Processamento síncrono
```

Se é derivado:

```text id="n4m7qy"
Pode ser eventual
```

---

# References

Documentos relacionados:

* `architecture.md`
* `engine.md`
* `api.md`
* `ADR-003 — Competition Engine as Reactive Domain Processor`
* `ADR-010 — Materialized Data and Derived State Strategy`

---

# Decision Summary

A plataforma utiliza consistência imediata para dados primários do domínio e permite processamento eventual para informações derivadas, mantendo simplicidade inicial e capacidade de evolução arquitetural.
