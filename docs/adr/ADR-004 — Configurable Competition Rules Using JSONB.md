# ADR-004 — Configurable Competition Rules Using JSONB

## Status

Em revisão

## Data

2026-07-18

---

# Contexto

A Sports Data Platform precisa suportar diferentes modalidades esportivas e formatos de competição.

Exemplos:

## Futebol

* vitória vale 3 pontos;
* empate vale 1 ponto;
* classificação por pontos;
* critérios de desempate configuráveis.

## Copa do Mundo

* fase de grupos;
* classificação dos melhores terceiros;
* mata-mata;
* prorrogação e disputa de pênaltis.

## Fórmula 1

* pontuação por posição;
* pontos extras;
* descarte de resultados em alguns formatos históricos.

## WEC

* pontuação por categoria;
* regras específicas de endurance.

Durante a modelagem surgiu a decisão de como armazenar essas regras.

Uma alternativa seria criar tabelas específicas para cada regra.

Exemplo:

```text id="yqxj6m"
competition_scoring_rule

competition_tiebreak_rule

competition_qualification_rule

competition_penalty_rule
```

Porém, esse modelo tende a crescer indefinidamente conforme novos esportes e formatos forem adicionados.

---

# Decision

Regras variáveis de competição serão armazenadas utilizando campos JSONB no PostgreSQL.

A estrutura relacional será utilizada para informações estáveis do domínio.

As configurações variáveis serão armazenadas como documentos JSON.

---

# Example

Uma Competition Template pode possuir:

```json id="7tjw4a"
{
    "scoring": {
        "win": 3,
        "draw": 1,
        "loss": 0
    },
    "tieBreakers": [
        "points",
        "goal_difference",
        "goals_scored"
    ],
    "qualification": {
        "teamsPerGroup": 2,
        "bestThirdPlaces": 8
    }
}
```

---

# Rationale

O domínio possui duas categorias de informação.

## Estrutura estável

Informações que possuem comportamento conhecido e relacionamento claro.

Exemplos:

```text id="b0xj4m"
Competition

Sport

Participant

Competition Event
```

Essas informações pertencem ao modelo relacional.

---

## Configuração variável

Informações que podem mudar conforme:

* esporte;
* competição;
* temporada;
* regra histórica.

Exemplos:

```text id="nyj4k8"
Quantidade de classificados

Sistema de pontuação

Critérios de desempate

Formato de fase
```

Essas informações pertencem ao JSONB.

---

# Consequences

## Positivas

* Novos formatos podem ser adicionados sem alteração estrutural;
* Reduz necessidade de migrations frequentes;
* Permite representar regras históricas;
* Facilita criação de templates configuráveis;
* Mantém o modelo relacional focado no domínio principal.

---

## Negativas

* Validação precisa ser feita pela aplicação;
* Consultas SQL sobre regras ficam mais complexas;
* O schema do JSON precisa ser bem documentado;
* Alterações podem exigir versionamento da configuração.

---

# Alternatives Considered

## Criar uma tabela para cada tipo de regra

Exemplo:

```text id="s8rrkq"
scoring_rule

qualification_rule

tie_breaker_rule
```

### Motivo da rejeição

O modelo cresceria junto com cada novo esporte.

Adicionar uma nova regra exigiria:

* nova tabela;
* nova entidade;
* nova migration;
* novo código;
* novo deploy.

Isso prejudica o principal objetivo do sistema: suportar novos formatos de competição de forma configurável.

---

## Armazenar tudo em JSON

Exemplo:

```json id="5cw5fk"
{
    "sport": "football",
    "name": "World Cup",
    "rules": {}
}
```

### Motivo da rejeição

Informações centrais do domínio precisam possuir:

* integridade referencial;
* relacionamentos;
* constraints;
* consultas eficientes.

Por isso, apenas regras variáveis utilizam JSONB.

---

# Schema Versioning

Configurações JSONB devem possuir versionamento quando houver alteração estrutural.

Exemplo:

```json id="h5vklr"
{
    "version": 1,
    "scoring": {
        "win": 3,
        "draw": 1
    }
}
```

Uma mudança incompatível deve gerar uma nova versão da configuração.

---

# Impact

Esta decisão influencia:

* Competition Template;
* Competition Rule;
* Competition Stage Template;
* Competition Engine;
* validação de configurações;
* documentação dos formatos suportados.

---

# Implementation Guidelines

Toda configuração JSONB deve:

* possuir schema conhecido pela aplicação;
* ser validada antes de utilização;
* possuir documentação;
* evitar duplicação de informações existentes em tabelas relacionais;
* permitir evolução futura.

---

# References

Documentos relacionados:

* `database.md`
* `engine.md`
* `architecture.md`

---

# Decision Summary

O sistema utiliza JSONB para armazenar regras configuráveis de competição, mantendo informações estáveis no modelo relacional e permitindo evolução de formatos esportivos sem alterações estruturais frequentes.
