import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<Aluno> alunos = new ArrayList<>();
        ArrayList<Professor> professores = new ArrayList<>();
        ArrayList<Disciplina> disciplinas = new ArrayList<>();
        ArrayList<Curso> cursos = new ArrayList<>();
        ArrayList<Turma> turmas = new ArrayList<>();
        ArrayList<Matricula> matriculas = new ArrayList<>();
        char operacao;

        while (true) {

            System.out.println("\n========= MENU PRINCIPAL =========");
            System.out.println("1 - Aluno");
            System.out.println("2 - Professor");
            System.out.println("3 - Curso");
            System.out.println("4 - Disciplina");
            System.out.println("5 - Turma");
            System.out.println("6 - Sair");
            System.out.print("Digite uma opção: ");

            operacao = lerOpcao(scanner);

            switch (operacao) {

                // =========================
                // MENU ALUNO
                // =========================
                case '1':

                    while (true) {

                        System.out.println("\n========= ALUNO =========");
                        System.out.println("1 - Cadastrar aluno");
                        System.out.println("2 - Exibir dados do aluno");
                        System.out.println("3 - Histórico acadêmico");
                        System.out.println("4 - Excluir aluno");
                        System.out.println("5 - Voltar");
                        System.out.print("Digite uma opção valida: ");

                        operacao = lerOpcao(scanner);

                        switch (operacao) {

                            // CADASTRAR ALUNO
                            case '1':

                                Aluno aluno = new Aluno();

                                // Nome
                                while (true) {
                                    System.out.print("Digite seu nome completo: ");
                                    String nome = scanner.nextLine();

                                    if (nome.matches("[a-zA-ZÀ-ÿ ]+")) {
                                        aluno.setNome(nome);
                                        break;
                                    } else {
                                        System.out.println("Nome inválido! Digite apenas letras.");
                                    }
                                }

                                // CPF
                                while (true) {
                                    System.out.print("Digite seu CPF: ");
                                    String cpf = scanner.nextLine();

                                    if (cpf.matches("\\d{11}")) {
                                        aluno.setCpf(cpf);
                                        break;
                                    } else {
                                        System.out.println("CPF inválido! Digite apenas 11 números.");
                                    }
                                }

                                // Curso
                                while (true) {

                                    System.out.print("Digite o código do seu curso: ");
                                    String curso = scanner.nextLine();

                                    boolean encontrado = false;

                                    for (int i = 0; i < cursos.size(); i++) {

                                        if (curso.equals(cursos.get(i).getCodigo())) {

                                            aluno.setCurso(cursos.get(i).getNome());
                                            encontrado = true;
                                            break;
                                        }
                                    }

                                    if (encontrado) {
                                        System.out.println("Curso cadastrado com sucesso!");
                                        break;
                                    } else {
                                        System.out.println("Curso não encontrado. Digite um código válido.");
                                    }
                                }

                                // Data de nascimento
                                while (true) {
                                    System.out.print("Digite sua data de nascimento (AAAA-MM-DD): ");
                                    String input = scanner.nextLine();

                                    if (!input.matches("\\d{4}-\\d{2}-\\d{2}")) {
                                        System.out.println("Formato inválido!");
                                        continue;
                                    }

                                    LocalDate data = LocalDate.parse(input);
                                    aluno.setDataNasc(data);
                                    break;
                                }

                                // Matrícula
                                System.out.println("Sua matrícula é: " + aluno.getMatricula());

                                // Email acadêmico
                                String email = aluno.getNome().toLowerCase()
                                        .replace(" ", ".")
                                        + aluno.getMatricula()
                                        + "@universidade.com";

                                aluno.setEmail(email);

                                System.out.println("Seu email acadêmico é: " + email);

                                alunos.add(aluno);

                                System.out.println("\nAluno cadastrado com sucesso!");

                                break;

                            // EXIBIR DADOS DO ALUNO
                            case '2':

                                System.out.print("Digite a matrícula do aluno: ");
                                int matricula = Integer.parseInt(scanner.nextLine());

                                boolean encontrado = false;

                                for (Aluno alunoCadastrado : alunos) {

                                    if (matricula == alunoCadastrado.getMatricula()) {

                                        System.out.println("\n========= DADOS DO ALUNO =========");
                                        System.out.println("Nome: " + alunoCadastrado.getNome());
                                        System.out.println("CPF: " + alunoCadastrado.getCpf());
                                        System.out.println("Curso: " + alunoCadastrado.getCurso());
                                        System.out.println("Email: " + alunoCadastrado.getEmail());
                                        System.out.println("Data de nascimento: " + alunoCadastrado.getDataNasc());

                                        encontrado = true;
                                        break;
                                    }
                                }

                                if (!encontrado) {
                                    System.out.println("Aluno não encontrado.");
                                }

                                break;

                            // HISTÓRICO ACADÊMICO
                            case '3':

                                System.out.print("Digite a matrícula do aluno: ");
                                int matriculaHist = Integer.parseInt(scanner.nextLine());

                                Aluno alunoHist = null;
                                for (Aluno a : alunos) {
                                    if (a.getMatricula() == matriculaHist) {
                                        alunoHist = a;
                                        break;
                                    }
                                }

                                if (alunoHist == null) {
                                    System.out.println("Aluno não encontrado.");
                                    break;
                                }

                                boolean temHistorico = false;

                                System.out.println("\n========= HISTÓRICO ACADÊMICO =========");
                                for (Matricula m : matriculas) {
                                    if (m.getAluno() == alunoHist) {
                                        temHistorico = true;
                                        System.out.println("Disciplina: " + m.getDisciplina().getNome());
                                        System.out.println("Notas: " + m.getNotas());
                                        System.out.println("Média: " + m.getMedia());
                                        System.out.println("Situação: " + (m.isAprovado() ? "Aprovado" : "Reprovado"));
                                        System.out.println("---");
                                    }
                                }

                                if (!temHistorico) {
                                    System.out.println("Nenhuma disciplina cursada ainda.");
                                }

                                break;

                            // EXCLUIR ALUNO
                            case '4':

                                System.out.println("digite a matricula que deseja excluir");
                                int matriculaExcluir = Integer.parseInt(scanner.nextLine());

                                boolean removidoAluno = false;

                                for (int i = 0; i < alunos.size(); i++) {
                                    if (matriculaExcluir == alunos.get(i).getMatricula()) {
                                        alunos.remove(i);
                                        System.out.println("Aluno excluido com sucesso");
                                        removidoAluno = true;
                                        break;
                                    }
                                }

                                if (!removidoAluno) {
                                    System.out.println("aluno não encontrado");
                                }

                                break;

                            // VOLTAR
                            case '5':

                                System.out.println("Voltando ao menu principal...");

                                break;

                            default:

                                System.out.println("Opção inválida!");

                                break;
                        }

                        if (operacao == '5') {
                            break;
                        }
                    }

                    break;

                // =========================
                // PROFESSOR
                // =========================
                case '2':

                    while (true) {

                        System.out.println("\n========= Professor =========");
                        System.out.println("1 - Cadastrar Profesor");
                        System.out.println("2 - Exibir dados do Professor");
                        System.out.println("3- Lançar notas");
                        System.out.println("4 - Excluir Professor");
                        System.out.println("5 - Voltar");
                        System.out.print("Digite uma opção valida: ");

                        operacao = lerOpcao(scanner);

                        switch (operacao) {

                            // CADASTRAR PROFESSOR
                            case '1':

                                Professor professor = new Professor();

                                // Nome
                                while (true) {
                                    System.out.print("Digite o nome completo do professor: ");
                                    String nome = scanner.nextLine();

                                    if (nome.matches("[a-zA-ZÀ-ÿ ]+")) {
                                        professor.setNome(nome);
                                        break;
                                    } else {
                                        System.out.println("Digite um nome válido!");
                                    }
                                }

                                // CPF
                                while (true) {
                                    System.out.print("Digite o CPF do professor: ");
                                    String cpf = scanner.nextLine();

                                    if (cpf.matches("\\d{11}")) {
                                        professor.setCpf(cpf);
                                        break;
                                    } else {
                                        System.out.println("Digite um CPF válido!");
                                    }
                                }

                                // Telefone
                                while (true) {
                                    System.out.print("Digite o telefone do professor: ");
                                    String telefone = scanner.nextLine();

                                    if (telefone.matches("\\d{11}")) {
                                        professor.setTelefone(telefone);
                                        break;
                                    } else {
                                        System.out.println("Digite um telefone válido!");
                                    }
                                }

                                while (true) {
                                    System.out.println("Crie uma senha com no mínimo 8 caracteres:");
                                    String senha = scanner.nextLine();

                                    if (senha.matches(".{8,}")) {
                                        professor.setSenha(senha);
                                        break;
                                    } else {
                                        System.out.println("Senha inválida! Ela deve ter pelo menos 8 caracteres.");
                                    }
                                }

                                // Matrícula
                                System.out.println("Matrícula do professor: " + professor.getMatricula());

                                // Email
                                String email = professor.getNome()
                                        .toLowerCase()
                                        .replace(" ", ".")
                                        + professor.getMatricula()
                                        + "@email.prof.com";

                                professor.setEmail(email);

                                System.out.println("Email do professor: " + professor.getEmail());

                                // Especialidade
                                while (true) {
                                    System.out.print("Digite a especialidade do professor: ");
                                    String especialidade = scanner.nextLine();

                                    if (especialidade.matches("[a-zA-ZÀ-ÿ ]+")) {
                                        professor.setEspecialidade(especialidade);
                                        break;
                                    } else {
                                        System.out.println("Digite uma especialidade válida!");
                                    }
                                }

                                professores.add(professor);

                                System.out.println("\nProfessor cadastrado com sucesso!");
                                break;

                            // EXIBIR DADOS DO PROFESSOR
                            case '2':
                                System.out.print("Digite a matrícula do professor: ");
                                int matricula = Integer.parseInt(scanner.nextLine());

                                boolean encontrado = false;

                                for (Professor professorCadastrado : professores) {

                                    if (matricula == professorCadastrado.getMatricula()) {

                                        System.out.println("\n========= DADOS DO PROFESSOR =========");
                                        System.out.println("Nome: " + professorCadastrado.getNome());
                                        System.out.println("CPF: " + professorCadastrado.getCpf());
                                        System.out.println("Telefone: " + professorCadastrado.getTelefone());
                                        System.out.println("Email: " + professorCadastrado.getEmail());
                                        System.out.println("Especialidade: " + professorCadastrado.getEspecialidade());
                                        System.out.println("Disciplinas: " + professorCadastrado.getDisciplinasProf());
                                        encontrado = true;
                                        break;
                                    }
                                }

                                if (!encontrado) {
                                    System.out.println("Professor não encontrado!");
                                }

                                break;

                            // LANÇAR NOTAS
                            case '3':

                                System.out.print("Digite a matrícula do aluno: ");
                                int matriculaAluno = Integer.parseInt(scanner.nextLine());

                                Aluno alunoNota = null;
                                for (Aluno a : alunos) {
                                    if (a.getMatricula() == matriculaAluno) {
                                        alunoNota = a;
                                        break;
                                    }
                                }

                                if (alunoNota == null) {
                                    System.out.println("Aluno não encontrado!");
                                    break;
                                }

                                System.out.print("Digite o código da disciplina: ");
                                String codigoDisciplinaNota = scanner.nextLine();

                                Disciplina disciplinaNota = null;
                                for (Disciplina d : disciplinas) {
                                    if (d.getCodigo().equals(codigoDisciplinaNota)) {
                                        disciplinaNota = d;
                                        break;
                                    }
                                }

                                if (disciplinaNota == null) {
                                    System.out.println("Disciplina não encontrada!");
                                    break;
                                }

                                Matricula matriculaAluno2 = null;
                                for (Matricula m : matriculas) {
                                    if (m.getAluno() == alunoNota && m.getDisciplina() == disciplinaNota) {
                                        matriculaAluno2 = m;
                                        break;
                                    }
                                }

                                if (matriculaAluno2 == null) {
                                    matriculaAluno2 = new Matricula(alunoNota, disciplinaNota);
                                    matriculas.add(matriculaAluno2);
                                }

                                System.out.print("Digite a nota a ser lançada: ");
                                double nota = Double.parseDouble(scanner.nextLine());
                                matriculaAluno2.adicionarNota(nota);
                                matriculaAluno2.atualizarSituacao();

                                System.out.println("Nota lançada com sucesso!");
                                System.out.println("Média atual: " + matriculaAluno2.getMedia());
                                System.out.println("Situação: " + (matriculaAluno2.isAprovado() ? "Aprovado" : "Reprovado"));

                                break;

                            // EXCLUIR PROFESSOR
                            case '4':

                                System.out.print("Digite a matrícula do professor para removê-lo: ");
                                int matriculaEx = Integer.parseInt(scanner.nextLine());

                                boolean removido = false;

                                for (int i = 0; i < professores.size(); i++) {
                                    if (professores.get(i).getMatricula() == matriculaEx) {
                                        professores.remove(i);
                                        System.out.println("Professor excluído com sucesso!");
                                        removido = true;
                                        break;
                                    }
                                }

                                if (!removido) {
                                    System.out.println("Matrícula não encontrada!");
                                }

                                break;

                            // VOLTAR
                            case '5':

                                System.out.println("Voltando ao menu principal...");
                                break;

                            default:

                                System.out.println("Opção inválida!");
                                break;
                        }

                        if (operacao == '5') {
                            break;
                        }
                    }

                    break;

                // =========================
                // CURSO
                // =========================
                case '3':

                    while (true) {

                        System.out.println("\n========= Curso =========");
                        System.out.println("1 - Cadastrar Curso");
                        System.out.println("2 - Adicionar Turma");
                        System.out.println("3 - Exibir dados do Curso");
                        System.out.println("4 - Turmas do curso");
                        System.out.println("5 - Voltar");
                        System.out.print("Digite uma opção valida: ");

                        operacao = lerOpcao(scanner);

                        switch (operacao) {

                            // CADASTRAR CURSO
                            case '1':

                                Curso curso = new Curso();

                                while (true) {
                                    System.out.print("Nome do curso: ");
                                    String nome = scanner.nextLine();
                                    if (nome.matches("[a-zA-ZÀ-ÿ ]+")) {
                                        curso.setNome(nome);
                                        break;
                                    } else {
                                        System.out.println("Digite um nome válido.");
                                    }
                                }

                                while (true) {
                                    System.out.print("Digite o período do curso (máximo 10): ");
                                    int periodo = Integer.parseInt(scanner.nextLine());
                                    if (periodo > 0 && periodo <= 10) {
                                        curso.setPeriodos(periodo);
                                        break;
                                    } else {
                                        System.out.println("Digite um valor válido.");
                                    }
                                }

                                System.out.println("O código do curso é: " + curso.getCodigo());
                                cursos.add(curso);
                                System.out.println("Curso cadastrado com sucesso!");

                                break;

                            // ADICIONAR TURMA AO CURSO
                            case '2':

                                if (cursos.isEmpty()) {
                                    System.out.println("Nenhum curso cadastrado.");
                                    break;
                                }
                                if (turmas.isEmpty()) {
                                    System.out.println("Nenhuma turma cadastrada ainda.");
                                    break;
                                }

                                System.out.print("Digite o código do curso: ");
                                String codigoCursoAdd = scanner.nextLine();

                                Curso cursoAdd = null;
                                for (Curso c : cursos) {
                                    if (c.getCodigo().equals(codigoCursoAdd)) {
                                        cursoAdd = c;
                                        break;
                                    }
                                }

                                if (cursoAdd == null) {
                                    System.out.println("Curso não encontrado.");
                                    break;
                                }

                                System.out.println("Turmas disponíveis:");
                                for (int i = 0; i < turmas.size(); i++) {
                                    System.out.println(i + " - Disciplina: " + turmas.get(i).getDisciplina()
                                            + " | Período: " + turmas.get(i).getPeriodo());
                                }

                                System.out.print("Digite o número da turma desejada: ");
                                int indiceTurma = Integer.parseInt(scanner.nextLine());

                                if (indiceTurma >= 0 && indiceTurma < turmas.size()) {
                                    cursoAdd.adicionarTurmas(turmas.get(indiceTurma));
                                    System.out.println("Turma adicionada ao curso com sucesso!");
                                } else {
                                    System.out.println("Número inválido.");
                                }

                                break;

                            // EXIBIR DADOS DO CURSO
                            case '3':

                                System.out.print("Digite o código do curso: ");
                                String codigoCursoExibir = scanner.nextLine();

                                boolean encontradoCurso = false;

                                for (Curso c : cursos) {
                                    if (c.getCodigo().equals(codigoCursoExibir)) {
                                        System.out.println("\n========= DADOS DO CURSO =========");
                                        System.out.println(c);
                                        System.out.println("Disciplinas: " + c.getDisciplinasCurso().size());
                                        System.out.println("Turmas: " + c.getTurmas().size());
                                        encontradoCurso = true;
                                        break;
                                    }
                                }

                                if (!encontradoCurso) {
                                    System.out.println("Curso não encontrado.");
                                }

                                break;

                            // TURMAS DO CURSO
                            case '4':

                                System.out.print("Digite o código do curso: ");
                                String codigoCursoTurmas = scanner.nextLine();

                                Curso cursoTurmas = null;
                                for (Curso c : cursos) {
                                    if (c.getCodigo().equals(codigoCursoTurmas)) {
                                        cursoTurmas = c;
                                        break;
                                    }
                                }

                                if (cursoTurmas == null) {
                                    System.out.println("Curso não encontrado.");
                                    break;
                                }

                                if (cursoTurmas.getTurmas().isEmpty()) {
                                    System.out.println("Este curso ainda não tem turmas.");
                                } else {
                                    System.out.println("\n========= TURMAS DO CURSO =========");
                                    for (Turma t : cursoTurmas.getTurmas()) {
                                        System.out.println("Disciplina: " + t.getDisciplina()
                                                + " | Período: " + t.getPeriodo()
                                                + " | Alunos: " + t.getAlunos().size());
                                    }
                                }

                                break;

                            // VOLTAR
                            case '5':
                                System.out.println("Voltando ao menu principal...");
                                break;

                            default:
                                System.out.println("Opção inválida!");
                                break;
                        }

                        if (operacao == '5') {
                            break;
                        }
                    }

                    break;

                // =========================
                // DISCIPLINA
                // =========================
                case '4':

                    while (true) {

                        System.out.println("\n========= Disciplina =========");
                        System.out.println("1 - Cadastrar Disciplina");
                        System.out.println("2 - Atualizar materia");
                        System.out.println("3 - Exibir Cursos da Disciplina");
                        System.out.println("4 - Excluir Disciplina");
                        System.out.println("5 - Voltar");
                        System.out.print("Digite uma opção valida: ");

                        operacao = lerOpcao(scanner);

                        switch (operacao) {

                            case '1':

                                Disciplina disciplina = new Disciplina();

                                while (true) {
                                    System.out.println("Nome da disciplina");
                                    String nome = scanner.nextLine();

                                    if (nome.matches("[a-zA-ZÀ-ÿ ]+")) {
                                        disciplina.setNome(nome);
                                        break;
                                    } else {
                                        System.out.println("Nome inválido, digite novamente.");
                                    }
                                }

                                System.out.println("Digite a ementa da matéria:");
                                String ementa = scanner.nextLine();
                                disciplina.setEmenta(ementa);

                                while (true) {
                                    System.out.println("Digite a carga horária semanal da matéria (máximo 30 horas):");
                                    int cargaHoraria = Integer.parseInt(scanner.nextLine());

                                    if (cargaHoraria <= 30) {
                                        disciplina.setCargaHoraria(cargaHoraria);
                                        break;
                                    } else {
                                        System.out.println("Digite novamente.");
                                    }
                                }

                                while (true) {
                                    System.out.println("Digite a matrícula do professor da matéria:");
                                    int matricula = Integer.parseInt(scanner.nextLine());

                                    boolean encontrado = false;

                                    for (Professor professor : professores) {
                                        if (professor.getMatricula() == matricula) {
                                            disciplina.setProfessor(professor);
                                            professor.adicionarDisciplina(disciplina.getNome());

                                            encontrado = true;
                                            System.out.println("Professor vinculado com sucesso!");
                                            break;
                                        }
                                    }

                                    if (encontrado) {
                                        break;
                                    } else {
                                        System.out.println("Professor não encontrado. Digite novamente.");
                                    }
                                }

                                System.out.println("O codigo da disciplina é: " + disciplina.getCodigo());
                                disciplinas.add(disciplina);
                                System.out.println("Disciplina cadastrada com sucesso!");

                                break;

                            case '2':

                                while (true) {
                                    System.out.print("Digite o código da matéria que deseja atualizar: ");
                                    String codigoAtualizar = scanner.nextLine();

                                    boolean encontrada = false;

                                    for (int i = 0; i < disciplinas.size(); i++) {

                                        if (codigoAtualizar.equals(disciplinas.get(i).getCodigo())) {

                                            encontrada = true;

                                            System.out.println("""
                        Deseja atualizar:
                        1 - Professor
                        2 - Carga Horária
                        3 - Ementa
                        """);

                                            char opcao = lerOpcao(scanner);

                                            switch (opcao) {
                                                case '1':
                                                    System.out.println("Atualizar professor");
                                                    while (true) {
                                                        System.out.println("Digite a matrícula do professor da matéria:");
                                                        int matriculaNova = Integer.parseInt(scanner.nextLine());

                                                        boolean encontrado = false;

                                                        for (Professor professor : professores) {
                                                            if (professor.getMatricula() == matriculaNova) {
                                                                disciplinas.get(i).setProfessor(professor);
                                                                encontrado = true;
                                                                System.out.println("Professor vinculado com sucesso!");
                                                                break;
                                                            }
                                                        }

                                                        if (encontrado) {
                                                            break;
                                                        } else {
                                                            System.out.println("Professor não encontrado. Digite novamente.");
                                                        }
                                                    }

                                                    break;

                                                case '2':
                                                    System.out.println("Atualizar carga horária");

                                                    while (true) {
                                                        System.out.print("Digite a nova carga horária: ");
                                                        int novaCarga = Integer.parseInt(scanner.nextLine());

                                                        if (novaCarga <= 30) {
                                                            disciplinas.get(i).setCargaHoraria(novaCarga);
                                                            System.out.println("Carga horária atualizada com sucesso!");
                                                            break;
                                                        } else {
                                                            System.out.println("Carga horária inválida! Digite um valor menor ou igual a 30.");
                                                        }
                                                    }
                                                    break;

                                                case '3':
                                                    System.out.println("Atualizar ementa");
                                                    System.out.println("digite a nova ementa");
                                                    String novaEmenta = scanner.nextLine();

                                                    disciplinas.get(i).setEmenta(novaEmenta);
                                                    System.out.println("Ementa atualizada com sucesso!");
                                                    break;

                                                default:
                                                    System.out.println("Opção inválida.");
                                            }

                                            break;
                                        }
                                    }

                                    if (encontrada) {
                                        break;
                                    } else {
                                        System.out.println("Código não encontrado. Tente novamente.");
                                    }
                                }
                                break;

                            case '3':

                                while (true) {
                                    System.out.print("Digite o código da matéria cadastrada: ");
                                    String codigo = scanner.nextLine();

                                    boolean encontrada = false;

                                    for (int i = 0; i < disciplinas.size(); i++) {

                                        if (codigo.equals(disciplinas.get(i).getCodigo())) {

                                            Disciplina disciplinaEncontrada = disciplinas.get(i);

                                            System.out.println("\n===== DADOS DA DISCIPLINA =====");
                                            System.out.println("Nome: " + disciplinaEncontrada.getNome());
                                            System.out.println("Código: " + disciplinaEncontrada.getCodigo());
                                            System.out.println("Ementa: " + disciplinaEncontrada.getEmenta());
                                            System.out.println("Carga horária: " + disciplinaEncontrada.getCargaHoraria() + " horas");
                                            System.out.println("Professor: " + disciplinaEncontrada.getProfessor().getNome());
                                            System.out.println("Matrícula do professor: " + disciplinaEncontrada.getProfessor().getMatricula());

                                            encontrada = true;
                                            break;
                                        }
                                    }

                                    if (encontrada) {
                                        break;
                                    } else {
                                        System.out.println("Código não encontrado. Tente novamente.");
                                    }
                                }

                                break;

                            case '4':
                                while (true) {
                                    boolean encontrado = false;
                                    System.out.println("digite o codigo da disciplina");
                                    String codigo = scanner.nextLine();
                                    for (int i = 0; i < disciplinas.size(); i++) {
                                        if (codigo.equals(disciplinas.get(i).getCodigo())) {
                                            disciplinas.remove(i);
                                            encontrado = true;
                                            System.out.println("materia excluida com sucesso");
                                            break;
                                        }
                                    }
                                    if (!encontrado) {
                                        System.out.println("digite um codigo valido");
                                    }
                                    break;
                                }
                                break;

                            case '5':
                                System.out.println("Voltando ao menu principal...");
                                break;

                            default:
                                System.out.println("Opção inválida.");
                        }

                        if (operacao == '5') {
                            break;
                        }
                    }

                    break;

                // =========================
                // TURMA
                // =========================
                case '5':

                    while (true) {

                        System.out.println("\n========= Turma  =========");
                        System.out.println("1 - Cadastrar Turma");
                        System.out.println("2 - Atualizar Turma");
                        System.out.println("3 - Exibir Alunos da turma");
                        System.out.println("4 - Excluir Turma");
                        System.out.println("5 - Voltar");
                        System.out.print("Digite uma opção valida: ");

                        operacao = lerOpcao(scanner);

                        switch (operacao) {

                            // CADASTRAR TURMA
                            case '1':

                                Turma turma = new Turma();

                                while (true) {

                                    System.out.println("Digite o código da Disciplina da turma");
                                    String turmaDisciplina = scanner.nextLine();

                                    boolean disciplinaEncontrada = false;

                                    for (Disciplina d : disciplinas) {
                                        if (turmaDisciplina.equals(d.getCodigo())) {
                                            System.out.println("Disciplina encontrada!");
                                            System.out.println("Nome: " + d.getNome());
                                            System.out.println("Código: " + d.getCodigo());
                                            turma.setDisciplina(d.getNome());
                                            turma.adicionarDisciplina(d);
                                            disciplinaEncontrada = true;
                                            break;
                                        }
                                    }

                                    if (disciplinaEncontrada) {
                                        break;
                                    } else {
                                        System.out.println("Disciplina não encontrada. Digite novamente.");
                                    }
                                }

                                while (true) {

                                    System.out.println("Digite o período dessa turma (um único caractere, ex: 1, 2, 3...)");
                                    String periodoInput = scanner.nextLine();

                                    if (periodoInput.length() == 1) {
                                        turma.setPeriodo(periodoInput.charAt(0));
                                        break;
                                    } else {
                                        System.out.println("Período inválido! Digite apenas um caractere.");
                                    }
                                }

                                while (true) {
                                    System.out.println("Digite 1 para cadastrar aluno ou 0 para sair");
                                    int cadastro = Integer.parseInt(scanner.nextLine());

                                    if (cadastro == 1) {
                                        System.out.println("Digite a matrícula do aluno para adicionar na turma");
                                        int matricula = Integer.parseInt(scanner.nextLine());

                                        boolean encontrado = false;

                                        for (Aluno a : alunos) {
                                            if (matricula == a.getMatricula()) {
                                                turma.adicionarAluno(a);
                                                System.out.println("Aluno " + a.getNome() + " cadastrado com sucesso!");
                                                encontrado = true;
                                                break;
                                            }
                                        }

                                        if (!encontrado) {
                                            System.out.println("Aluno não encontrado.");
                                        }

                                    } else if (cadastro == 0) {
                                        System.out.println("Saindo ....");
                                        break;
                                    } else {
                                        System.out.println("Opção inválida");
                                    }
                                }

                                turmas.add(turma);
                                System.out.println("Turma cadastrada com sucesso!");

                                break;

                            // ATUALIZAR TURMA
                            case '2':

                                System.out.println("Atualizar turma ainda não implementado.");
                                break;

                            // EXIBIR ALUNOS DA TURMA
                            case '3':

                                if (turmas.isEmpty()) {
                                    System.out.println("Nenhuma turma cadastrada.");
                                    break;
                                }

                                System.out.println("Turmas cadastradas:");
                                for (int i = 0; i < turmas.size(); i++) {
                                    System.out.println(i + " - Disciplina: " + turmas.get(i).getDisciplina()
                                            + " | Período: " + turmas.get(i).getPeriodo());
                                }

                                System.out.print("Digite o número da turma: ");
                                int indiceExibir = Integer.parseInt(scanner.nextLine());

                                if (indiceExibir >= 0 && indiceExibir < turmas.size()) {
                                    ArrayList<Aluno> alunosTurma = turmas.get(indiceExibir).getAlunos();

                                    if (alunosTurma.isEmpty()) {
                                        System.out.println("Esta turma ainda não tem alunos.");
                                    } else {
                                        System.out.println("\n========= ALUNOS DA TURMA =========");
                                        for (Aluno a : alunosTurma) {
                                            System.out.println("Matrícula: " + a.getMatricula() + " | Nome: " + a.getNome());
                                        }
                                    }
                                } else {
                                    System.out.println("Número inválido.");
                                }

                                break;

                            // EXCLUIR TURMA
                            case '4':

                                if (turmas.isEmpty()) {
                                    System.out.println("Nenhuma turma cadastrada.");
                                    break;
                                }

                                System.out.println("Turmas cadastradas:");
                                for (int i = 0; i < turmas.size(); i++) {
                                    System.out.println(i + " - Disciplina: " + turmas.get(i).getDisciplina()
                                            + " | Período: " + turmas.get(i).getPeriodo());
                                }

                                System.out.print("Digite o número da turma que deseja excluir: ");
                                int indiceExcluir = Integer.parseInt(scanner.nextLine());

                                if (indiceExcluir >= 0 && indiceExcluir < turmas.size()) {
                                    turmas.remove(indiceExcluir);
                                    System.out.println("Turma excluída com sucesso!");
                                } else {
                                    System.out.println("Número inválido.");
                                }

                                break;

                            // VOLTAR
                            case '5':
                                System.out.println("Voltando ao menu principal...");
                                break;

                            default:
                                System.out.println("Opção inválida!");
                                break;
                        }

                        if (operacao == '5') {
                            break;
                        }
                    }

                    break;


                // =========================
                // SAIR
                // =========================
                case '6':

                    System.out.println("Programa encerrado. Até mais!");

                    scanner.close();

                    return;

                default:

                    System.out.println("Opção inválida!");

                    break;
            }
        }
    }

    /**
     * Lê uma opção de menu com segurança: repete a leitura enquanto o
     * usuário apertar Enter sem digitar nada, evitando o
     * StringIndexOutOfBoundsException do charAt(0) em string vazia.
     */
    private static char lerOpcao(Scanner scanner) {
        String entrada;

        do {
            entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                System.out.print("Opção inválida! Digite novamente: ");
            }

        } while (entrada.isEmpty());

        return entrada.charAt(0);
    }
}