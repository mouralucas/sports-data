# ADR-002 — API Resource Naming and Filtering Convention

## Status

Em revisão

## Date

2026-07-17

---

# Context

Durante a definição da Sports Data API, foi necessário estabelecer uma convenção para identificação e consulta dos recursos.

O padrão REST mais comum utiliza o mesmo caminho base tanto para coleções quanto para recursos individuais.

Exemplo:

```http
GET /sports

GET /sports/{id}
```

Entretanto, durante a modelagem do domínio, surgiu a necessidade de diferenciar claramente dois conceitos:

* consulta de uma coleção de recursos;
* acesso a uma única entidade específica.

Além disso, o sistema possui diversos recursos onde filtros serão necessários para consultas complexas.

Exemplos:

* buscar esportes ativos;
* buscar competições de uma organização;
* buscar eventos de uma edição;
* buscar participantes de uma competição.

---

# Decision

A API utilizará a seguinte convenção:

## Collections

Coleções sempre serão representadas no plural.

Exemplo:

```http
GET /api/v1/sports
```

Retorna uma lista de esportes.

Filtros serão sempre realizados utilizando query parameters.

Exemplo:

```http
GET /api/v1/sports?active=true
```

Exemplo:

```http
GET /api/v1/competitions?organizationId=10
```

---

## Single Resource

Recursos individuais serão representados no singular.

O identificador fará parte do caminho.

Exemplo:

```http
GET /api/v1/sport/{sportId}
```

Retorna uma única entidade.

Exemplo:

```http
GET /api/v1/competition/{competitionId}
```

---

# Examples

## Collection

Consulta geral:

```http
GET /api/v1/sports
```

Resposta:

```json
{
    "content": [
        {
            "id": 1,
            "name": "Football"
        },
        {
            "id": 2,
            "name": "Formula 1"
        }
    ]
}
```

---

## Collection with Filters

Consulta filtrada:

```http
GET /api/v1/competitions?sportId=1&active=true
```

A mesma URL representa a coleção, independentemente da quantidade de resultados.

Pode retornar:

* zero registros;
* um registro;
* vários registros.

---

## Single Resource

Consulta específica:

```http
GET /api/v1/competition/15
```

Retorno:

```json
{
    "id": 15,
    "name": "World Cup",
    "sportId": 1
}
```

---

# Rationale

A separação entre coleção e entidade individual torna explícita a intenção da chamada.

Comparação:

## Coleção

```http
GET /sports?name=football
```

Significado:

> "Procure esportes que correspondam aos critérios informados."

---

## Entidade

```http
GET /sport/1
```

Significado:

> "Busque exatamente o esporte identificado por este ID."

---

Essa separação evita que o consumidor precise interpretar se um endpoint pode retornar uma lista ou um objeto único.

---

# Consequences

## Positivas

* Contrato de API mais previsível;
* Filtros ilimitados sem criação de múltiplas URLs;
* Facilidade para evolução dos recursos;
* Clareza entre busca e identificação;
* Padronização para todos os domínios.

---

## Negativas

* Difere do padrão REST mais comum;
* Desenvolvedores acostumados com `/resource/{id}` precisarão se adaptar;
* Algumas ferramentas geradoras podem assumir outro padrão.

---

# Alternatives Considered

## Usar plural para ambos

Exemplo:

```http
GET /sports
GET /sports/{id}
```

### Motivo da rejeição

Embora seja amplamente utilizado, mistura dois conceitos diferentes:

* coleção;
* entidade individual.

Como a API possui muitos recursos consultáveis e filtros complexos, a separação explícita melhora a compreensão.

---

## Usar filtros no caminho

Exemplo:

```http
GET /sports/active
GET /competitions/organization/10
```

### Motivo da rejeição

Esse modelo cria uma explosão de endpoints conforme novos filtros são adicionados.

Exemplo:

```text
/competitions/by-sport
/competitions/by-organization
/competitions/by-season
/competitions/by-status
```

Query parameters permitem evolução sem alterar o contrato.

---

# Impact

Esta decisão influencia:

* todos os endpoints REST;
* documentação da API;
* clientes frontend;
* integrações externas;
* geração futura de OpenAPI.

Todos os novos endpoints devem seguir esta convenção.

---

# References

Documentos relacionados:

* `api.md`
* `architecture.md`

---

# Decision Summary

A API utiliza endpoints no plural para coleções filtráveis e endpoints no singular para acesso direto a uma entidade específica, mantendo filtros exclusivamente através de query parameters.
