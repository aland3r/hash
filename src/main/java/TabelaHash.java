public abstract class TabelaHash {

    protected String[] tabela;
    protected int capacidade;
    protected int tamanho;
    protected int colisoes;
    protected static final double FATOR_DE_CARGA = 0.75;
    protected static final String REMOVIDO = "__REMOVIDO__";

    public TabelaHash(int capacidadeInicial) {
        this.capacidade = capacidadeInicial;
        this.tabela = new String[capacidade];
        this.tamanho = 0;
        this.colisoes = 0;
    }

    // Cada subclasse implementa sua própria função hash
    protected abstract int funcaoHash(String chave);

    public void inserir(String chave) {
        if (tamanho >= (int) (capacidade * FATOR_DE_CARGA)) {
            redimensionar();
        }

        int indice = funcaoHash(chave);
        int tentativas = 0;

        while (tabela[indice] != null && !tabela[indice].equals(REMOVIDO)) {
            colisoes++;
            tentativas++;
            indice = (indice + 1) % capacidade; // sondagem linear
            if (tentativas >= capacidade) return; // tabela cheia (segurança)
        }

        tabela[indice] = chave;
        tamanho++;
    }

    public boolean buscar(String chave) {
        int indice = funcaoHash(chave);
        int tentativas = 0;

        while (tabela[indice] != null) {
            if (tabela[indice].equals(chave)) return true;
            tentativas++;
            indice = (indice + 1) % capacidade;
            if (tentativas >= capacidade) break;
        }

        return false;
    }

    public boolean remover(String chave) {
        int indice = funcaoHash(chave);
        int tentativas = 0;

        while (tabela[indice] != null) {
            if (tabela[indice].equals(chave)) {
                tabela[indice] = REMOVIDO;
                tamanho--;
                return true;
            }
            tentativas++;
            indice = (indice + 1) % capacidade;
            if (tentativas >= capacidade) break;
        }

        return false;
    }

    private void redimensionar() {
        int novaCapacidade = capacidade * 2;
        String[] novaTabela = new String[novaCapacidade];
        String[] tabelaAntiga = tabela;
        int capacidadeAntiga = capacidade;

        this.capacidade = novaCapacidade;
        this.tabela = novaTabela;
        this.tamanho = 0;

        for (int i = 0; i < capacidadeAntiga; i++) {
            if (tabelaAntiga[i] != null && !tabelaAntiga[i].equals(REMOVIDO)) {
                inserir(tabelaAntiga[i]);
            }
        }
    }

    public int[] distribuicao() {
        int[] dist = new int[capacidade];
        for (int i = 0; i < capacidade; i++) {
            if (tabela[i] != null && !tabela[i].equals(REMOVIDO)) {
                dist[i] = 1;
            }
        }
        return dist;
    }

    public int getColisoes()    { return colisoes; }
    public int getTamanho()     { return tamanho; }
    public int getCapacidade()  { return capacidade; }
}