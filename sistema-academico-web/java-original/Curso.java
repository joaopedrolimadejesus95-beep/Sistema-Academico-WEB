import java.util.ArrayList;
public class Curso {

    private static int proximoCodigo = 1;

    private String nome;
    private int periodos;
    private String codigo;

    private ArrayList<Turma> turmas = new ArrayList<>();
    private ArrayList<Disciplina> disciplinasCurso = new ArrayList<>();

    public Curso() {
        this.codigo = "CRS" + String.format("%03d", proximoCodigo++);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setPeriodos(int periodos) {
        this.periodos = periodos;
    }

    public int getPeriodos() {
        return periodos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void adicionarTurmas(Turma turma) {
        turmas.add(turma);
    }

    public ArrayList<Turma> getTurmas() {
        return turmas;
    }

    public void adicionarDisciplina(Disciplina disciplina) {
        disciplinasCurso.add(disciplina);
    }

    public ArrayList<Disciplina> getDisciplinasCurso() {
        return disciplinasCurso;
    }

    @Override
    public String toString() {
        return "Código: " + codigo +
                "\nNome: " + nome +
                "\nPeríodos: " + periodos;
    }
}