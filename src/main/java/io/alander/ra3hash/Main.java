package io.alander.ra3hash;

import java.util.List;

public class Main {

    private static final int CAPACIDADE_INICIAL = 16;
    private static final String CAMINHO_CSV = "src/main/resources/nomes.csv";

    public static void main(String[] args) {

        System.out.println("Lendo arquivo CSV...");
        List<String> nomes = LeitorCSV.lerNomes(CAMINHO_CSV);
        System.out.println("Total de nomes carregados: " + nomes.size());
        System.out.println();

        TabelaHash tabelaSoma = new TabelaHashSoma(CAPACIDADE_INICIAL);
        TabelaHash tabelaDJB2 = new TabelaHashDJB2(CAPACIDADE_INICIAL);

        TesteEficiencia testeSoma = new TesteEficiencia(tabelaSoma, "Hash Soma de Char");
        long tempoInsercaoSoma = testeSoma.medirInsercao(nomes);
        long tempoBuscaSoma    = testeSoma.medirBusca(nomes);

        TesteEficiencia testeDJB2 = new TesteEficiencia(tabelaDJB2, "Hash DJB2");
        long tempoInsercaoDJB2 = testeDJB2.medirInsercao(nomes);
        long tempoBuscaDJB2    = testeDJB2.medirBusca(nomes);

        testeSoma.imprimirRelatorio(tempoInsercaoSoma, tempoBuscaSoma);
        testeDJB2.imprimirRelatorio(tempoInsercaoDJB2, tempoBuscaDJB2);

        System.out.println("============================================================");
        System.out.println("  COMPARATIVO FINAL");
        System.out.println("============================================================");
        System.out.printf("  %-30s %-15s %-15s%n", "Métrica", "Soma de Char", "DJB2");
        System.out.printf("  %-30s %-15d %-15d%n", "Colisões",
                tabelaSoma.getColisoes(), tabelaDJB2.getColisoes());
        System.out.printf("  %-30s %-15.3f %-15.3f%n", "Tempo inserção (ms)",
                tempoInsercaoSoma / 1_000_000.0, tempoInsercaoDJB2 / 1_000_000.0);
        System.out.printf("  %-30s %-15.3f %-15.3f%n", "Tempo busca (ms)",
                tempoBuscaSoma / 1_000_000.0, tempoBuscaDJB2 / 1_000_000.0);
        System.out.printf("  %-30s %-15d %-15d%n", "Capacidade final",
                tabelaSoma.getCapacidade(), tabelaDJB2.getCapacidade());
        System.out.println("============================================================");
    }
}
