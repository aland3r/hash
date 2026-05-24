import java.util.List;

public class TesteEficiencia {

    private TabelaHash tabela;
    private String nome;

    public TesteEficiencia(TabelaHash tabela, String nome) {
        this.tabela = tabela;
        this.nome = nome;
    }

    public long medirInsercao(List<String> nomes) {
        long inicio = System.nanoTime();
        for (String nome : nomes) {
            tabela.inserir(nome);
        }
        long fim = System.nanoTime();
        return fim - inicio;
    }

    public long medirBusca(List<String> nomes) {
        long inicio = System.nanoTime();
        for (String nome : nomes) {
            tabela.buscar(nome);
        }
        long fim = System.nanoTime();
        return fim - inicio;
    }

    public void imprimirRelatorio(long tempoInsercao, long tempoBusca) {
        System.out.println("============================================================");
        System.out.println("  RELATÓRIO - " + nome);
        System.out.println("============================================================");
        System.out.printf("  Capacidade final da tabela : %d%n", tabela.getCapacidade());
        System.out.printf("  Elementos inseridos         : %d%n", tabela.getTamanho());
        System.out.printf("  Total de colisões           : %d%n", tabela.getColisoes());
        System.out.printf("  Tempo de inserção           : %.3f ms%n", tempoInsercao / 1_000_000.0);
        System.out.printf("  Tempo de busca              : %.3f ms%n", tempoBusca / 1_000_000.0);
        System.out.println();
        System.out.println("  Distribuição das chaves por posição:");
        imprimirDistribuicao();
        System.out.println("============================================================");
        System.out.println();
    }

    private void imprimirDistribuicao() {
        int[] dist = tabela.distribuicao();
        int capacidade = tabela.getCapacidade();

        // Agrupa posições em blocos de 100 para não lotar o console
        int bloco = 100;
        for (int i = 0; i < capacidade; i += bloco) {
            int fim = Math.min(i + bloco, capacidade);
            int count = 0;
            for (int j = i; j < fim; j++) {
                count += dist[j];
            }
            System.out.printf("  [%5d - %5d] : %d chaves%n", i, fim - 1, count);
        }
    }
}