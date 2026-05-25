package io.alander.ra3hash;

public class TabelaHashDJB2 extends TabelaHash {

    public TabelaHashDJB2(int capacidadeInicial) {
        super(capacidadeInicial);
    }

    @Override
    protected int funcaoHash(String chave) {
        long hash = 5381; //valor sementecomo
        for (int i = 0; i < chave.length(); i++) {
            hash = ((hash << 5) + hash) + chave.charAt(i); // hash * 33 + c
        }
        return (int) Math.abs(hash % capacidade);
    }
}
