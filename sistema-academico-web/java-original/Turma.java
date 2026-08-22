import java.util.ArrayList;

public class Turma {

    private String disciplina;
    private char periodo;

    private ArrayList<Aluno> alunos = new ArrayList<>();
    private ArrayList<Professor> professores = new ArrayList<>();
    private ArrayList<Disciplina> disciplinas = new ArrayList<>();

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setPeriodo(char periodo) {
        this.periodo = periodo;
    }

    public char getPeriodo() {
        return periodo;
    }

    public void adicionarAluno(Aluno aluno) {
        alunos.add(aluno);
    }

    public ArrayList<Aluno> getAlunos() {
        return alunos;
    }

    public void adicionarProfessor(Professor professor) {
        professores.add(professor);
    }

    public ArrayList<Professor> getProfessores() {
        return professores;
    }

    public void adicionarDisciplina(Disciplina disciplina) {
        disciplinas.add(disciplina);
    }

    public ArrayList<Disciplina> getDisciplinas() {
        return disciplinas;
    }
}