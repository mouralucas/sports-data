# ADR-013 — Organization Hierarchy Model

## Status

Em revisão

## Date

2026-07-18

---

# Context

Durante a modelagem das entidades relacionadas a organizações esportivas, surgiu a necessidade de representar diferentes níveis administrativos.

Exemplos:

```text id="x9p4kw"
FIFA

    └── CONMEBOL

            └── CBF

                    └── Campeonato Brasileiro
```

ou:

```text id="k7m2vx"
FIA

    └── Formula One Management

            └── Formula 1 Championship
```

Uma organização pode estar relacionada a outra organização que representa um nível superior.

---

# Decision

A entidade `Organization` possuirá uma relação hierárquica consigo mesma.

Modelo:

```text id="n8q3pv"
Organization

id

name

parent_organization_id
```

---

# Relationship

A cardinalidade será:

```text id="v5w8rx"
Organization

1

|

N

Organization
```

Significado:

* uma organização pode possuir várias organizações filhas;
* uma organização pode possuir no máximo uma organização pai.

---

# Example

```text id="g6t2mz"
International Federation

FIFA


Parent:

NULL


Child Organizations:

CONMEBOL

UEFA

AFC
```

---

```text id="h4r8kc"
National Federation

CBF


Parent:

CONMEBOL
```

---

# Rationale

A hierarquia representa relação de pertencimento ou governança.

Uma organização pode administrar várias outras organizações.

Porém, uma organização normalmente pertence a apenas uma estrutura superior dentro do contexto esportivo.

Exemplo:

```text id="q7m3yp"
CBF

Parent:

CONMEBOL
```

Não faria sentido:

```text id="s5x8nd"
CBF

Parent:

CONMEBOL

AND

UEFA
```

dentro da mesma hierarquia.

---

# Database Representation

A tabela utilizará uma chave estrangeira para ela mesma.

Exemplo:

```sql id="b9v4qm"
organization

id

name

parent_organization_id
```

Relacionamento:

```text id="z2k6hp"
parent_organization_id

        ↓

organization.id
```

---

# JPA Representation

Exemplo:

```java id="r8y5vn"
@Entity
public class OrganizationEntity {

    @ManyToOne
    private OrganizationEntity parentOrganization;


    @OneToMany(mappedBy = "parentOrganization")
    private List<OrganizationEntity> children;
}
```

---

# Consequences

## Positivas

* Representa estruturas reais do esporte;
* Permite consultas hierárquicas;
* Evita tabelas específicas para federações;
* Facilita expansão futura.

---

## Negativas

* Consultas hierárquicas podem exigir recursão;
* É necessário evitar ciclos inválidos;
* Algumas estruturas podem não se encaixar perfeitamente em árvore.

---

# Alternatives Considered

## Não possuir hierarquia

Exemplo:

```text id="p7m2dx"
Organization

id

name
```

### Motivo da rejeição

Perde informação importante sobre relações administrativas.

Exemplo:

Não seria possível representar que uma competição pertence a uma liga ou federação.

---

## Permitir múltiplos pais

Modelo:

```text id="k3w9fz"
Organization Parent Relationship

organization_id

parent_id
```

permitindo:

```text id="x8q4mv"
Organization A

Parent:

B

and

C
```

### Motivo da rejeição

Esse modelo representa grafos, não hierarquias.

A maioria das relações esportivas possui estrutura hierárquica simples.

Caso exista necessidade futura de múltiplas associações, poderá ser criada uma entidade específica para relacionamentos organizacionais.

---

# Impact

Esta decisão influencia:

* database.md;
* Competition;
* Sport;
* API;
* permissões futuras;
* consultas administrativas.

---

# Implementation Guidelines

Ao criar ou atualizar organizações:

* validar que uma organização não é pai de si mesma;
* evitar ciclos hierárquicos;
* manter a estrutura como uma árvore.

Exemplo inválido:

```text id="u6m8kp"
A

 parent B


B

 parent A
```

---

# References

Documentos relacionados:

* `database.md`
* `api.md`
* `architecture.md`

---

# Decision Summary

Organization utiliza uma relação auto-referenciada de um para muitos, permitindo representar estruturas administrativas esportivas onde cada organização possui no máximo um pai e pode possuir múltiplas organizações filhas.
