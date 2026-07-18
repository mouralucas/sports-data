# ADR-003 — Competition Engine as Reactive Domain Processor

## Status

Em revisão

## Date

2026-07-18

---

# Context

Durante a definição da API e da Competition Engine, surgiu a necessidade de decidir como o avanço de uma competição seria controlado.

Uma abordagem tradicional seria expor comandos explícitos para cada etapa do fluxo.

Exemplos:

```http
POST /competition-event/{id}/process

POST /competition-stage/{id}/finish

POST /competition-stage/{id}/generate-next-stage
```

Neste modelo, consumidores externos seriam responsáveis por informar à aplicação quando uma etapa deveria ser executada.

Entretanto, o domínio da aplicação possui uma característica importante:

O estado da competição é uma consequência dos dados registrados.

Exemplo:

```text
Resultado de uma partida registrado

        ↓

Classificação atualizada

        ↓

Critérios de desempate aplicados

        ↓

Classificados definidos

        ↓

Próxima fase gerada
```

Essas operações representam regras do domínio e não ações que um consumidor externo deveria controlar.

---

# Decision

A Competition Engine será implementada como um processador reativo de alterações do domínio.

A API será responsável apenas por alterar o estado persistido.

A Engine será responsável por:

* interpretar alterações;
* aplicar regras;
* atualizar dados derivados;
* evoluir automaticamente a competição.

---

# Domain Flow

O fluxo principal será:

```text
API Request

      ↓

Domain Resource Updated

      ↓

Competition Engine

      ↓

Business Rules Applied

      ↓

Derived State Updated
```

---

# Example

Atualização de uma partida:

```http
PATCH /api/v1/competition-event/52
```

Payload:

```json
{
    "homeScore": 2,
    "awayScore": 1,
    "status": "FINISHED"
}
```

A API apenas registra a alteração.

A Engine executa:

```text
Competition Event atualizado

        ↓

Atualiza estatísticas

        ↓

Atualiza classificação

        ↓

Verifica critérios de desempate

        ↓

Determina classificados

        ↓

Atualiza Stage Slots

        ↓

Cria próximos eventos quando aplicável
```

---

# Rationale

Esta decisão mantém a regra de negócio centralizada.

Sem esta abordagem, diferentes consumidores poderiam executar fluxos diferentes:

Exemplo:

```text
Frontend:

Atualiza resultado
Chama process
Chama finish stage


Importador:

Atualiza resultado
Esquece process


Job:

Executa outra sequência
```

Isso criaria múltiplas formas de alterar o estado da competição.

Com a Engine reativa:

```text
Qualquer alteração válida

          ↓

Mesmo processamento

          ↓

Mesmo resultado
```

---

# Consequences

## Positivas

* Regras de negócio centralizadas;
* Menor acoplamento entre API e domínio;
* Menor risco de inconsistência;
* Facilita integrações externas;
* Importações históricas utilizam o mesmo fluxo;
* Frontend não precisa conhecer regras internas.

---

## Negativas

* A Engine possui maior responsabilidade;
* Algumas operações podem ser assíncronas;
* Debug exige boa observabilidade;
* O fluxo interno precisa ser bem documentado.

---

# Alternatives Considered

## API comandando explicitamente a competição

Exemplo:

```http
POST /competition-stage/{id}/finish
POST /competition-stage/{id}/generate-next-stage
```

### Motivo da rejeição

O consumidor passaria a conhecer detalhes internos da regra da competição.

Além disso, seria possível executar comandos em estados inválidos.

Exemplo:

```text
Finalizar fase antes de todos os jogos terminarem
```

A própria Engine possui contexto suficiente para decidir quando uma ação é válida.

---

## Criar um workflow externo controlando etapas

Exemplo:

```text
Scheduler

    ↓

Generate Stage

    ↓

Process Events

    ↓

Finish Stage
```

### Motivo da rejeição

O workflow externo duplicaria conhecimento de domínio.

O fluxo pertence à competição, não ao mecanismo que dispara as ações.

---

# Impact

Esta decisão influencia:

* arquitetura da aplicação;
* desenho da API;
* implementação dos services;
* processamento assíncrono;
* importação histórica;
* testes da Competition Engine.

Novas regras de negócio devem ser adicionadas à Engine, nunca aos consumidores da API.

---

# Implementation Guidelines

A implementação deve seguir os seguintes princípios:

* alterações de domínio devem ser processadas de forma determinística;
* operações devem ser idempotentes quando possível;
* dados derivados devem poder ser reconstruídos;
* eventos importantes devem possuir rastreabilidade;
* qualquer cliente que altere o domínio deve produzir o mesmo resultado.

---

# References

Documentos relacionados:

* `architecture.md`
* `engine.md`
* `api.md`

---

# Decision Summary

A Competition Engine é responsável por evoluir automaticamente o estado das competições. A API apenas altera o domínio persistido; todas as regras e transições de estado pertencem à Engine.
