import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Labirinto {
    // Array do labirinto
    private char[][] labirinto;

    // adionando cores para o caminho percorrido
    private final String ANSI_GREEN = "\u001B[32m";
    private final String ANSI_RESET = "\u001B[0m";

    // Construtor da classe Labirinto
    public Labirinto(String filename) {
        criaLabirinto(filename);
    }

    // Método para criar o labirinto a partir do arquivo FileName.txt
    private void criaLabirinto(String filename) {
        try {
            // Lê todas as linhas do arquivo e armazena em uma lista
            List<String> linhas = Files.readAllLines(Paths.get(filename));
            labirinto = new char[linhas.size()][];
            for (int i = 0; i < linhas.size(); i++) {
                // Converte a linha em um array de caracteres e armazena no labirinto
                labirinto[i] = linhas.get(i).toCharArray();
            }
        } catch (IOException e) {
            // Imprime a pilha de chamadas para ajudar na depuração se ocorrer um erro
            e.printStackTrace();
        }
    }

    // Método para percorrer o labirinto
    public boolean percorreLabirinto() {
        // Inicia o labirinto a partir da posição (0, 0)
        boolean resultado = percorreLabirinto(0, 0);
        imprimeLabirinto();
        return resultado;
    }

    // Método recursivo para percorrer o labirinto
    private boolean percorreLabirinto(int x, int y) {
        // Verifica se a posição atual está fora dos limites do labirinto
        if (x < 0 || y < 0 || x >= labirinto.length || y >= labirinto[x].length) {
            return false;
        }
        // Verifica se a posição atual é o destino
        if (labirinto[x][y] == 'D') {
            return true;
        }
        // Verifica se a posição atual é uma parede ou já foi percorrida
        if (labirinto[x][y] == 'X' || labirinto[x][y] == '$') {
            return false;
        }
        // Marca a posição atual como percorrida
        labirinto[x][y] = '$';

        // Limpa a tela e imprime o labirinto atualizado (assim não é necessário rolar a tela)
        limpaTela();
        imprimeLabirinto();
        // Adiciona um pausa 500ms para visualização do percurso
        espera(500);

        // Movimentação para todas as direções (cima, baixo, esquerda, direita)
        if (percorreLabirinto(x - 1, y) || percorreLabirinto(x + 1, y) ||
            percorreLabirinto(x, y - 1) || percorreLabirinto(x, y + 1)) {
            return true;
        }

        // Se não for válida a direção, desmarca a posição atual e retorna false
        labirinto[x][y] = ' ';
        limpaTela();
        imprimeLabirinto();
        espera(500);
        return false;
    }

    // Método para imprimir o labirinto no console
    private void imprimeLabirinto() {
        for (char[] linha : labirinto) {
            for (char c : linha) {
                // Imprime o $ em verde para indicar o caminho percorrido
                if (c == '$') {
                    System.out.print(ANSI_GREEN + c + ANSI_RESET);
                } else {
                    System.out.print(c);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // Método para pausar a execução por um determinado número de milissegundos
    private void espera(int milissegundos) {
        try {
            Thread.sleep(milissegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Método para limpar a tela do console
    private void limpaTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}