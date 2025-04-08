// OrcamentoService.java + PDF + Menu integrado
package lealgarage.services;

import lealgarage.models.Cliente;
import lealgarage.models.Orcamento;
import lealgarage.models.Servico;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class OrcamentoService {
    private final List<Orcamento> orcamentos = new ArrayList<>();

    public void adicionarOrcamento(Orcamento orcamento) {
        orcamentos.add(orcamento);
    }

    public List<Orcamento> listarOrcamentos() {
        return orcamentos;
    }

    public void exibirOrcamentoDetalhado(Orcamento orcamento) {
        System.out.println(orcamento.toString());
    }

    public Orcamento criarOrcamento(Cliente cliente, List<Servico> servicos) {
        Orcamento orcamento = new Orcamento(cliente);
        for (Servico servico : servicos) {
            orcamento.adicionarServico(servico);
        }
        adicionarOrcamento(orcamento);
        return orcamento;
    }

    public void gerarPdfOrcamento(Orcamento orcamento, String caminhoArquivo) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo));
            document.open();

            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font fontCorpo = new Font(Font.FontFamily.HELVETICA, 12);

            document.add(new Paragraph("Orçamento de Serviço", fontTitulo));
            document.add(new Paragraph("Cliente: " + orcamento.getCliente().getNome(), fontCorpo));
            document.add(new Paragraph("Veículo: " + orcamento.getCliente().getVeiculo() + " | Placa: " + orcamento.getCliente().getPlaca(), fontCorpo));
            document.add(new Paragraph("Data: " + orcamento.getData().toString(), fontCorpo));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(3);
            table.addCell("Serviço");
            table.addCell("Descrição");
            table.addCell("Valor (R$)");

            for (Servico s : orcamento.getServicos()) {
                table.addCell(s.getNome());
                table.addCell(s.getDescricao());
                table.addCell(String.format("%.2f", s.getValor()));
            }

            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total: R$ " + String.format("%.2f", orcamento.getValorTotal()), fontCorpo));

        } catch (Exception e) {
            System.err.println("Erro ao gerar PDF: " + e.getMessage());
        } finally {
            document.close();
        }
    }

    public void criarOrcamentoViaTerminal(List<Cliente> clientes, List<Servico> servicos) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("== Criar Orçamento ==");

        if (clientes.isEmpty() || servicos.isEmpty()) {
            System.out.println("É necessário ter pelo menos 1 cliente e 1 serviço cadastrado.");
            return;
        }

        System.out.println("Clientes disponíveis:");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println((i + 1) + ". " + clientes.get(i));
        }
        System.out.print("Escolha o número do cliente: ");
        int clienteIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        Cliente cliente = clientes.get(clienteIndex);
        List<Servico> selecionados = new ArrayList<>();

        System.out.println("Serviços disponíveis:");
        for (int i = 0; i < servicos.size(); i++) {
            System.out.println((i + 1) + ". " + servicos.get(i));
        }

        System.out.print("Digite os números dos serviços (ex: 1,2,3): ");
        String[] escolhas = scanner.nextLine().split(",");
        for (String escolha : escolhas) {
            int idx = Integer.parseInt(escolha.trim()) - 1;
            selecionados.add(servicos.get(idx));
        }

        Orcamento orcamento = criarOrcamento(cliente, selecionados);
        exibirOrcamentoDetalhado(orcamento);

        System.out.print("Salvar como PDF? (s/n): ");
        String resposta = scanner.nextLine();
        if (resposta.equalsIgnoreCase("s")) {
            String nomeArquivo = "orcamento-" + cliente.getNome().replaceAll(" ", "_").toLowerCase() + ".pdf";
            gerarPdfOrcamento(orcamento, nomeArquivo);
            System.out.println("PDF gerado: " + nomeArquivo);
        }
    }
}