package lealgarage.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Orcamento {
    private int id;
    private Cliente cliente;
    private LocalDate data;
    private List<Servico> servicos;
    private double valorTotal;

    public Orcamento() {
        this.servicos = new ArrayList<>();
        this.data = LocalDate.now();
    }

    public Orcamento(Cliente cliente) {
        this.cliente = cliente;
        this.servicos = new ArrayList<>();
        this.data = LocalDate.now();
    }

    public Orcamento(int id, Cliente cliente, LocalDate data) {
        this.id = id;
        this.cliente = cliente;
        this.data = data;
        this.servicos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
        calcularValorTotal();
    }

    public void adicionarServico(Servico servico) {
        servicos.add(servico);
        valorTotal += servico.getValor();
    }

    public double getValorTotal() {
        return valorTotal;
    }

    private void calcularValorTotal() {
        valorTotal = 0;
        for (Servico servico : servicos) {
            valorTotal += servico.getValor();
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append("Orçamento #").append(id).append("\n");
        sb.append("Data: ").append(data.format(formatter)).append("\n");
        sb.append("Cliente: ").append(cliente.getNome()).append(" (").append(cliente.getPlaca()).append(")\n");
        sb.append("Serviços:\n");

        for (Servico servico : servicos) {
            sb.append("- ").append(servico.getNome())
                    .append(": R$ ").append(String.format("%.2f", servico.getValor())).append("\n");
        }

        sb.append("Valor Total: R$ ").append(String.format("%.2f", valorTotal));
        return sb.toString();
    }
}