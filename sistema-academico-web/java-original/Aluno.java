import java.time.LocalDate;

public class Aluno {

    private static int proximaMatricula = 1000;

    private String nome;
    private String cpf;
    private int matricula;
    private String curso;
    private String turma;
    private String email;
    private LocalDate dataNasc;
    private String cursoMatriculado;


    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }
    public Aluno(){
        this.matricula = proximaMatricula++;
    }

    public int getMatricula() {
        return matricula;
    }

    public String getCurso() {
        return curso;
    }

    public String getTurma() {
        return turma;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }
}