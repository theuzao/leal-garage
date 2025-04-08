package lealgarage;

import lealgarage.database.DatabaseManager;
import lealgarage.ui.MainMenu;

public class Main {
    public static void main(String[] args) {
        System.out.println("Inicializando Sistema de Oficina Mecânica...");

        // Inicializa o banco de dados
        DatabaseManager.inicializarBancoDeDados();

        // Inicia o menu principal
        MainMenu menu = new MainMenu();
        menu.exibirMenu();

        System.out.println("Sistema finalizado.");
    }
}