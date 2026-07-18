# ADR-010 — Materialized Data and Derived State Strategy

## Status

Em revisão

## Data

2026-07-18

---

# Context

Durante o desenvolvimento da Competition Engine, surgiu a necessidade de decidir como armazenar e atualizar informações derivadas.

Exemplos de dados derivados:

* classificações;
* rankings;
* estatísticas acumuladas;
* posições em fases;
* participantes classificados;
* métricas calculadas.

Existem duas abordagens principais.

---

## Cálculo sob demanda

Sempre que uma consulta for realizada:

```text id="f5p9dk"
Stored Data

    ↓

Competition Engine

    ↓

Calculated Result
```

---

## Materialização dos resultados

Manter tabelas contendo os resultados calculados:

```text id="y3j5qa"
Stored Data

    ↓

Competition Engine

    ↓

Materialized Data
```

---

A decisão precisa considerar:

* simplicidade inicial;
* consistência dos dados;
* performance futura;
* possibilidade de reconstrução.

---

# Decision

A fonte de verdade do sistema será sempre composta pelos dados de domínio persistidos.

Dados derivados poderão ser calculados e, futuramente, materializados para otimização.

A materialização será uma estratégia de performance, não uma mudança no modelo de verdade.

---

# Source of Truth

São considerados dados primários:

```text id="h1m4sb"
Competition

Competition Edition

Participant

Competition Event

Event Action

Competition Configuration
```

---

São considerados dados derivados:

```text id="x2k9cq"
Classification Result

Ranking

Statistics

Generated Stage State

Calculated Metrics
```

---

# Initial Implementation

A primeira versão da aplicação utilizará cálculo sob demanda.

Fluxo:

```text id="e0v7pw"
API Request

      ↓

Load Competition Data

      ↓

Competition Engine

      ↓

Calculate Result

      ↓

Return Response
```

---

# Future Implementation

Quando necessário, resultados derivados poderão ser materializados.

Exemplo:

```text id="n8k4hm"
Competition Event Updated

        ↓

Competition Engine

        ↓

Update Classification Result

        ↓

Persist Derived Data
```

---

# Rationale

Separar dados primários e derivados traz benefícios importantes.

Se uma classificação estiver incorreta:

```text id="x8q1md"
Delete/Rebuild Derived Data

        ↓

Recalculate From Source
```

A aplicação consegue reconstruir o estado correto.

---

# Consequences

## Positivas

* Evita inconsistências permanentes;
* Permite evolução gradual;
* Facilita debugging;
* Permite recalcular competições históricas;
* Mantém domínio independente de otimizações.

---

## Negativas

* Cálculos iniciais podem ser mais custosos;
* A Engine precisa ser eficiente;
* Materialização futura exige controle de sincronização.

---

# Alternatives Considered

## Tratar dados calculados como fonte de verdade

Exemplo:

```text id="m6g1rx"
classification_result
```

seria a única informação utilizada.

### Motivo da rejeição

Dados derivados podem ficar inconsistentes e seriam difíceis de reconstruir.

---

## Materializar tudo desde o início

Exemplo:

Criar tabelas para:

```text id="w5n2pc"
classification

statistics

ranking
```

e atualizar a cada alteração.

### Motivo da rejeição

Aumenta complexidade inicial antes de existir necessidade real de otimização.

---

# Rebuild Strategy

O sistema deve sempre permitir reconstrução dos dados derivados.

Exemplo:

```text id="k7v2qa"
Competition Edition

        ↓

Load Events

        ↓

Execute Rules

        ↓

Generate Classification

        ↓

Generate Statistics
```

Nenhuma informação derivada deve depender exclusivamente de seu próprio estado persistido.

---

# Impact

Esta decisão influencia:

* Competition Engine;
* performance;
* jobs futuros;
* cache;
* tabelas derivadas;
* processos de manutenção.

---

# Implementation Guidelines

Ao criar novas tabelas derivadas:

Deve ser documentado:

* qual dado primário origina o resultado;
* como o resultado pode ser reconstruído;
* quando ocorre atualização;
* se pode ser descartado.

---

# References

Documentos relacionados:

* `engine.md`
* `database.md`
* `api.md`

---

# Decision Summary

Dados primários representam a verdade do domínio. Classificações, métricas e estatísticas são informações derivadas que podem inicialmente ser calculadas sob demanda e posteriormente materializadas para otimização.
