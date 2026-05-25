package io.alander.ra3hash;

import java.io.BufferedReader; //lê o arquivo em blocos
import java.io.FileReader; //abre o arquivo físico no disco
import java.io.IOException; //tipo de erro que pode ocorrer ao ler o arquivo
import java.util.ArrayList; //lista dinamica q vai guardar os nomes
import java.util.List; //a interface q ArrayList implementa

public class LeitorCSV {

    public static List<String> lerNomes(String caminho) {
        //static pq não precisar criar um objeto para chamar o metodo

        List<String> nomes = new ArrayList<>(); //cria lista vazia para acumular os nomes lidos, retorna ela no final

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false; // pula cabeçalho
                    continue;
                }
                String nome = linha.trim();
                if (!nome.isEmpty()) {
                    nomes.add(nome);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return nomes;
    }
}
