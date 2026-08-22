import java.util.ArrayList;
public class Matricula {

    private Aluno aluno;
    private Disciplina disciplina;

    private ArrayList<Double> notas = new ArrayList<>();
    private double media;
    private boolean aprovado;


    public void adicionarNota(double nota) {
        this.notas.add(nota);
    }

    public void calcularMedia() {

        double soma = 0;

        for (double nota : notas) {
            soma += nota;
        }

        if (!notas.isEmpty()) {
            media = soma / notas.size();
        }
    }

    public void calcularAprovacao() {
        if (media >= 6.0) {
            this.aprovado = true;
        } else {
            this.aprovado = false;
        }
    }
    public Matricula(Aluno aluno, Disciplina disciplina) {
        this.aluno = aluno;
        this.disciplina = disciplina;
    }
    public Aluno getAluno() {
        return aluno;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public double getMedia() {
        return media;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public ArrayList<Double> getNotas() {
        return notas;
    }
    public void atualizarSituacao() {
        calcularMedia();
        calcularAprovacao();
    }
}





