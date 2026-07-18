# ADR-018 — Importing External and Historical Data

## Status

Em revisão

## Date

2026-07-18

---

# Context

A Sports Data Platform possui como objetivo armazenar não apenas competições futuras ou em andamento, mas também dados históricos.

Exemplos:

* Copas do Mundo anteriores;
* temporadas antigas de campeonatos;
* campeonatos históricos de automobilismo;
* resultados importados de fontes externas.

Esses dados podem vir de diferentes origens:

* APIs esportivas;
* arquivos estruturados;
* entrada manual;
* processos de migração.

---

Durante a modelagem surgiu uma questão:

Devemos criar um modelo separado para dados históricos?

Exemplo:

```text id="y7k4mz"
Historical Match

Historical Participant

Historical Result
```

ou utilizar o mesmo modelo de domínio?

---

# Decision

Dados históricos e dados operacionais utilizarão o mesmo modelo de domínio.

Não haverá entidades paralelas para histórico.

O processo de importação será responsável por transformar dados externos em entidades internas.

---

# Import Flow

Fluxo:

```text id="r6p9wx"
External Source

        ↓

Importer

        ↓

Domain Validation

        ↓

Domain Entities

        ↓

Competition Engine
```

---

# Example

Fonte externa:

```json id="v8m3qy"
{
    "home": "Brazil",
    "away": "Italy",
    "score": "3-2"
}
```

Após importação:

```text id="p5x7kn"
Competition Event

Brazil

Italy

Score:

3-2
```

O evento possui a mesma estrutura de uma partida criada manualmente.

---

# Rationale

Criar um modelo separado para histórico causaria dois sistemas diferentes.

Exemplo:

```text id="w2q8mv"
Current Competition

uses Competition Event


Historical Competition

uses Historical Match
```

Consequências:

* regras duplicadas;
* APIs diferentes;
* Engine diferente;
* dificuldade para comparar períodos.

---

# Import Validation

Dados importados devem passar pelas mesmas validações do domínio.

Exemplo:

Validar:

* participante pertence à competição;
* evento pertence à fase correta;
* resultado é válido;
* regras da competição permitem aquela alteração.

---

# Import Confidence

Dados externos podem possuir diferentes níveis de confiança.

Exemplo:

```text id="k8q4vp"
Official Source

High confidence


Community Source

Medium confidence


Manual Entry

Variable confidence
```

Futuramente, metadados de origem poderão ser armazenados.

---

# Historical Reconstruction

Uma competição histórica deve permitir reconstrução:

```text id="m9w4xp"
Competition Edition

        ↓

Imported Events

        ↓

Competition Engine

        ↓

Calculated Classification
```

A classificação não precisa ser importada como verdade absoluta.

Ela pode ser recalculada.

---

# Consequences

## Positivas

* Um único modelo de domínio;
* Menor duplicação;
* Histórico e presente seguem as mesmas regras;
* Facilita validação;
* Permite reconstrução.

---

## Negativas

* Importadores possuem maior responsabilidade;
* Dados externos precisam ser adaptados;
* Nem todas as fontes possuem informação suficiente.

---

# Alternatives Considered

## Criar modelo separado para histórico

Exemplo:

```text id="h3q7nm"
HistoricalCompetition

HistoricalEvent
```

### Motivo da rejeição

Criaria uma bifurcação permanente no sistema.

---

## Importar apenas resultados finais

Exemplo:

```text id="z4m8kp"
Champion = Brazil
```

### Motivo da rejeição

Perde capacidade de análise e reconstrução.

O sistema foi projetado para armazenar eventos e regras, não apenas resultados finais.

---

# Relationship With Competition Engine

A Engine não deve saber a origem dos dados.

Para ela:

```text id="n5r8qx"
Imported Event

=

API Created Event
```

Ambos representam mudanças válidas no domínio.

---

# Impact

Esta decisão influencia:

* importadores;
* integrações externas;
* API;
* Competition Engine;
* banco de dados;
* qualidade dos dados.

---

# Implementation Guidelines

Importadores devem:

* nunca escrever diretamente em tabelas ignorando regras;
* utilizar services de domínio;
* validar dados antes da persistência;
* preservar origem quando possível;
* aceitar dados incompletos.

---

# References

Documentos relacionados:

* `database.md`
* `engine.md`
* `api.md`
* `ADR-009 — Historical Data and Partial Information Model`

---

# Decision Summary

Dados históricos e externos devem ser convertidos para o mesmo modelo de domínio utilizado pela aplicação, permitindo que a Competition Engine trate qualquer informação de forma consistente.
