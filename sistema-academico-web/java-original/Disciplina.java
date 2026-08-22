import java.util.ArrayList;

public class Disciplina {

    private static int proximoCodigo = 1;

    private String nome;
    private String codigo;
    private int cargaHoraria;
    private String ementa;
    private String curso;
    private Professor professor;



    public Disciplina() {
        this.codigo = "DISC" + String.format("%03d", proximoCodigo++);
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }

    public String getEmenta() {
        return ementa;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getCurso() {
        return curso;
    }
    @Override
    public String toString() {
        return "Código: " + codigo +
                "\nNome: " + nome +
                "\nCurso: " + curso +
                "\nCarga horária: " + cargaHoraria + "h" +
                "\nEmenta: " + ementa;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}