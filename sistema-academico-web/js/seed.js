/**
 * seed.js — popula dados de demonstração (opcional).
 * Útil para testar o fluxo completo sem precisar cadastrar tudo manualmente.
 */

function popularDemo() {
  if (DB.Curso.listar().length > 0) {
    return { ja_existia: true };
  }

  const curso = DB.Curso.criar({ nome: 'Ciência da Computação', periodos: 8 });

  const prof = DB.Professor.criar({
    nome: 'Marcos Andrade',
    cpf: '11122233344',
    telefone: '44988887777',
    senha: 'professor123',
    especialidade: 'Estruturas de Dados',
  });

  const disc1 = DB.Disciplina.criar({
    nome: 'Estrutura de Dados',
    ementa: 'Listas, pilhas, filas, árvores e grafos.',
    cargaHoraria: 20,
    professorMatricula: prof.matricula,
    cursoCodigo: curso.codigo,
  });

  const disc2 = DB.Disciplina.criar({
    nome: 'Banco de Dados',
    ementa: 'Modelagem relacional e SQL.',
    cargaHoraria: 18,
    professorMatricula: prof.matricula,
    cursoCodigo: curso.codigo,
  });

  const turma1 = DB.Turma.criar({ disciplinaCodigo: disc1.codigo, periodo: '3' });
  const turma2 = DB.Turma.criar({ disciplinaCodigo: disc2.codigo, periodo: '3' });
  DB.Curso.adicionarTurma(curso.codigo, turma1.codigo);
  DB.Curso.adicionarTurma(curso.codigo, turma2.codigo);

  const aluno = DB.Aluno.criar({
    nome: 'Ana Beatriz Ramos',
    cpf: '55566677788',
    senha: 'aluno1234',
    cursoCodigo: curso.codigo,
    dataNasc: '2003-04-12',
  });

  DB.Aluno.matricularEmTurma(aluno.matricula, turma1.codigo);
  DB.Aluno.matricularEmTurma(aluno.matricula, turma2.codigo);

  DB.Matricula.lancarNota(aluno.matricula, disc1.codigo, 8.0);
  DB.Matricula.lancarNota(aluno.matricula, disc1.codigo, 7.5);
  DB.Matricula.definirFrequencia(aluno.matricula, disc1.codigo, 92);

  DB.Matricula.lancarNota(aluno.matricula, disc2.codigo, 5.0);
  DB.Matricula.lancarNota(aluno.matricula, disc2.codigo, 6.0);
  DB.Matricula.definirFrequencia(aluno.matricula, disc2.codigo, 78);

  return {
    ja_existia: false,
    curso,
    professor: { matricula: prof.matricula, senha: 'professor123', email: prof.email },
    aluno: { matricula: aluno.matricula, senha: 'aluno1234', email: aluno.email },
  };
}
