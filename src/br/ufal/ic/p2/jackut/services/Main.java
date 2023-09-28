package br.ufal.ic.p2.jackut.services;

import easyaccept.EasyAccept;
/**
 * A classe Main é o ponto de entrada do programa. Responsável por executar os testes de aceitação usando a biblioteca EasyAccept.
 *
 * @author Gustavo Gaia
 */
public class Main {
    /**
     * O método principal (ponto de entrada) do programa.
     * Ele executa testes de aceitação na classe Facade com base nos arquivos de teste fornecidos.
     *
     * @param args Argumentos da linha de comando.
     */
    public static void main(String[] args) {
        String[] args2 = {"br.ufal.ic.p2.jackut.services.Facade",
         //       "tests/us1_1.txt", "tests/us1_2.txt",
         //       "tests/us2_1.txt", "tests/us2_2.txt",
         //       "tests/us3_1.txt", "tests/us3_2.txt",
         //       "tests/us4_1.txt", "tests/us4_2.txt",
                "tests/us5_1.txt", "tests/us5_2.txt",
         //       "tests/us6_1.txt", "tests/us6_2.txt",
         //       "tests/us7_1.txt", "tests/us7_2.txt",
         //       "tests/us8_1.txt", "tests/us8_2.txt",

        };
        EasyAccept.main(args2);
    }
}