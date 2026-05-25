package io.alander.ra3hash;

public class TabelaHashSoma extends TabelaHash {

    public TabelaHashSoma(int capacidadeInicial) {
        super(capacidadeInicial);
    }

    @Override
    protected int funcaoHash(String chave) {
        int soma = 0;
        for (int i = 0; i < chave.length(); i++) {
            soma += chave.charAt(i);
        }
        return Math.abs(soma % capacidade);
    }
}
