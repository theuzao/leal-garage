// MainMenu.java com geração de orçamento e PDF
package lealgarage.ui;

import lealgarage.models.Cliente;
import lealgarage.models.Servico;
import lealgarage.services.ClienteService;
import lealgarage.services.ServicoService;
import lealgarage.services.OrcamentoService;

import java.util.Scanner;

public class MainMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final ClienteService clienteService = new ClienteService();
    private final ServicoService servicoService = new ServicoService();
    private final OrcamentoService orcamentoService = new OrcamentoService();

    public void exibirMenu() {
        int opcao;

        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Cadastrar Serviço");
            System.out.println("4. Listar Serviços");
            System.out.println("5. Criar Orçamento + Gerar PDF");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar o buffer

            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> listarClientes();
                case 3 -> cadastrarServico();
                case 4 -> listarServicos();
                case 5 -> criarOrcamentoComPdf();
                case 6 -> System.out.println("Encerrando sistema...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 6);
    }

    private void cadastrarCliente() {
        System.out.println("== Cadastro de Cliente ==");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Veículo: ");
        String veiculo = scanner.nextLine();
        System.out.print("Placa: ");
        String placa = scanner.nextLine();

        boolean sucesso = clienteService.cadastrarCliente(new Cliente(nome, telefone, veiculo, placa));
        System.out.println(sucesso ? "Cliente cadastrado!" : "Erro ao cadastrar.");
    }

    private void listarClientes() {
        System.out.println("== Lista de Clientes ==");
        clienteService.listarTodosClientes().forEach(System.out::println);
    }

    private void cadastrarServico() {
        System.out.println("== Cadastro de Serviço ==");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Valor: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        boolean sucesso = servicoService.cadastrarServico(new Servico(nome, descricao, valor));
        System.out.println(sucesso ? "Serviço cadastrado!" : "Erro ao cadastrar.");
    }

    private void listarServicos() {
        System.out.println("== Lista de Serviços ==");
        servicoService.listarTodosServicos().forEach(System.out::println);
    }

    private void criarOrcamentoComPdf() {
        orcamentoService.criarOrcamentoViaTerminal(
                clienteService.listarTodosClientes(),
                servicoService.listarTodosServicos()
        );
    }
}
