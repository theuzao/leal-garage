package lealgarage.models;

public class Cliente {
    private int id;
    private String nome;
    private String telefone;
    private String veiculo;
    private String placa;

    public Cliente() {}

    public Cliente(String nome, String telefone, String veiculo, String placa) {
        this.nome = nome;
        this.telefone = telefone;
        this.veiculo = veiculo;
        this.placa = placa;
    }

    public Cliente(int id, String nome, String telefone, String veiculo, String placa) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.veiculo = veiculo;
        this.placa = placa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Nome: " + nome + " | Telefone: " + telefone +
                " | Veículo: " + veiculo + " | Placa: " + placa;
    }
}