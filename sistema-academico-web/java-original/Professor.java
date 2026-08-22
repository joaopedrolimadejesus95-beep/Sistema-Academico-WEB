
import java.util.ArrayList;

public class Professor {

    private static int proxMatricula = 1;

    private String nome;
    private String cpf;
    private int matricula;
    private String email;
    private String telefone;
    private String especialidade;
    private String senha;
    private ArrayList<String> disciplinasProf = new ArrayList<>();

    public void adicionarDisciplina(String disciplina) {
        disciplinasProf.add(disciplina);
    }

    public ArrayList<String> getDisciplinasProf() {
        return disciplinasProf;
    }



    // senhap
    public void setSenha(String senha){
        this.senha = senha;
    }

    public String getSenha(){
        return senha;
    }

    // Nome
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    // CPF
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    // Matrícula


    public Professor(){
        this.matricula = proxMatricula++;

    }

    public int getMatricula() {
        return matricula;
    }

    // E-mail
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    // Telefone
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefone() {
        return telefone;
    }

    // Especialidade
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getEspecialidade() {
        return especialidade;
    }
}