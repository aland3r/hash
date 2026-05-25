# RA3 — Tabela Hash em Java

Implementação comparativa de tabela hash com duas funções hash distintas, desenvolvida como atividade avaliativa (RA3) da disciplina de Resolução de Problemas Estrutuados em Computação.

## Objetivo

Demonstrar como a escolha da função hash impacta diretamente a performance de uma estrutura de dados, medindo colisões, tempo de inserção e tempo de busca com 20.000 nomes reais.

## Estrutura do Projeto

```
src/main/java/io/alander/ra3hash/
├── TabelaHash.java          # Classe abstrata — estrutura base da tabela
├── TabelaHashSoma.java      # Subclasse — função hash por soma de ASCII
├── TabelaHashDJB2.java      # Subclasse — função hash DJB2
├── LeitorCSV.java           # Leitura do arquivo com 20.000 nomes
├── TesteEficiencia.java     # Medição de tempo e geração de relatório
└── Main.java                # Ponto de entrada — executa e compara as duas tabelas

src/main/resources/
└── nomes.csv                # Dataset com 20.000 nomes

docs/
├── PBL03 - Tabela Hash.pdf  # Especificação do projeto
└── Relatorio_RA3.pdf        # Relatório com análise dos resultados
```

## Funcionamento

### Estrutura base (`TabelaHash`)

- Array de strings com capacidade inicial de 16 posições
- **Fator de carga:** 0.75 — ao atingir 75% da capacidade, o array dobra de tamanho
- **Tratamento de colisão:** sondagem linear — ao colidir, busca a próxima posição disponível circularmente via `% capacidade`
- **Remoção:** usa sentinela `__REMOVIDO__` para não quebrar a busca em posições removidas
- **Redimensionamento:** reinsere todos os elementos na nova tabela, pois o índice depende da capacidade

### Hash Soma (`TabelaHashSoma`)

Soma os valores ASCII de todos os caracteres e aplica `% capacidade`.

```java
int soma = 0;
for (int i = 0; i < chave.length(); i++) {
    soma += chave.charAt(i);
}
return Math.abs(soma % capacidade);
```

Problema: ignora a ordem das letras. Anagramas como "Ana" e "nAa" geram o mesmo índice, causando muitas colisões.

### Hash DJB2 (`TabelaHashDJB2`)

Algoritmo criado por Daniel J. Bernstein. Acumula progressivamente o hash multiplicando por 33 a cada caractere.

```java
long hash = 5381;
for (int i = 0; i < chave.length(); i++) {
    hash = ((hash << 5) + hash) + chave.charAt(i);
}
return (int) Math.abs(hash % capacidade);
```

`hash << 5` equivale a `hash * 32`, portanto `(hash << 5) + hash = hash * 33`. A ordem dos caracteres influencia o resultado, gerando índices mais distribuídos e muito menos colisões.

## Resultados

| Métrica              | Hash Soma     | DJB2        |
|----------------------|---------------|-------------|
| Total de colisões    | 272.167.038   | 53.789      |
| Tempo de inserção    | 1.538 ms      | 12 ms       |
| Tempo de busca       | 1.655 ms      | 4,7 ms      |

DJB2 gerou ~5.060x menos colisões e foi ~126x mais rápido na inserção.

## Como Executar

Requer Java 11+ e Maven.

```bash
mvn compile
mvn exec:java -Dexec.mainClass="io.alander.ra3hash.Main"
```

## Conceitos Aplicados

- Classe abstrata e herança (`TabelaHash` → `TabelaHashSoma`, `TabelaHashDJB2`)
- Polimorfismo — variável declarada como `TabelaHash`, instanciada como subclasse
- Encapsulamento — campos `protected`, acesso via getters
- Complexidade média O(1) para inserção e busca com boa função hash
