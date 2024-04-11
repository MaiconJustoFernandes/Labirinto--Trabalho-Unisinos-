public class Main {
    public static void main(String[] args) {
        Labirinto labirinto = new Labirinto("FileName.txt");
        boolean temSaida = labirinto.percorreLabirinto();

        System.out.println("Este labirinto tem saida? " + (temSaida ? "Sim" : "Não"));
    }
}
