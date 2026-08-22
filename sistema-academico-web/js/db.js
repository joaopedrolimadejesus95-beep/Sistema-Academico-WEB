/**
 * db.js — Camada de dados do Sistema Acadêmico
 *
 * Porta fiel das regras do projeto Java original (Aluno, Professor, Curso,
 * Disciplina, Turma, Matricula) para rodar no navegador, usando localStorage
 * como persistência (o programa Java original não tinha nenhuma).
 *
 * Regras mantidas do Java:
 *  - Matrícula do aluno começa em 1000 e incrementa (Aluno.java)
 *  - Matrícula do professor começa em 1 e incrementa (Professor.java)
 *  - Código do curso: "CRS" + 3 dígitos (Curso.java)
 *  - Código da disciplina: "DISC" + 3 dígitos (Disciplina.java)
 *  - Nome: apenas letras e espaços -> /^[a-zA-ZÀ-ÿ ]+$/ (validações do Main.java)
 *  - CPF: 11 dígitos numéricos
 *  - Telefone do professor: 11 dígitos numéricos
 *  - Senha do professor: mínimo 8 caracteres
 *  - Carga horária da disciplina: máximo 30h
 *  - E-mail do aluno: nome.sobrenome + matrícula + "@universidade.com"
 *  - E-mail do professor: nome.sobrenome + matrícula + "@email.prof.com"
 *  - Média = soma das notas / quantidade; aprovado se média >= 6.0 (Matricula.java)
 *
 * Extensões necessárias para a versão web (não existiam no Java):
 *  - Senha do aluno (para permitir login — o Java não tinha autenticação de aluno)
 *  - Código próprio para Turma (o Java não tinha, dificultava referenciar turmas)
 *  - Vínculo Curso <-> Disciplina de fato persistido (o setter existia mas nunca
 *    era chamado no menu original)
 *  - Frequência (não existia nenhum conceito disso no projeto original)
 */

const DB = (() => {
  const KEYS = {
    alunos: 'alunos',
    professores: 'professores',
    cursos: 'cursos',
    disciplinas: 'disciplinas',
    turmas: 'turmas',
    matriculas: 'matriculas',
    sessao: 'sessaoAtual',
    seq: 'sequencias',
  };

  // ---------- infraestrutura básica sobre localStorage ----------

  function loadAll(key) {
    try {
      const raw = localStorage.getItem(key);
      return raw ? JSON.parse(raw) : [];
    } catch (e) {
      console.error('Erro lendo', key, e);
      return [];
    }
  }

  function saveAll(key, arr) {
    localStorage.setItem(key, JSON.stringify(arr));
  }

  function getSeq() {
    const raw = localStorage.getItem(KEYS.seq);
    return raw
      ? JSON.parse(raw)
      : { aluno: 1000, professor: 1, curso: 1, disciplina: 1, turma: 1, matricula: 1 };
  }

  function saveSeq(seq) {
    localStorage.setItem(KEYS.seq, JSON.stringify(seq));
  }

  function nextSeq(campo) {
    const seq = getSeq();
    const valor = seq[campo];
    seq[campo] = valor + 1;
    saveSeq(seq);
    return valor;
  }

  // ---------- validações (mesmas regex do Main.java) ----------

  const REGEX = {
    nome: /^[a-zA-ZÀ-ÿ ]+$/,
    cpf: /^\d{11}$/,
    telefone: /^\d{11}$/,
  };

  const Validar = {
    nome: (v) => typeof v === 'string' && v.trim().length > 0 && REGEX.nome.test(v.trim()),
    cpf: (v) => REGEX.cpf.test(String(v || '').trim()),
    telefone: (v) => REGEX.telefone.test(String(v || '').trim()),
    senha: (v) => typeof v === 'string' && v.length >= 8,
    cargaHoraria: (v) => Number.isFinite(v) && v > 0 && v <= 30,
    periodosCurso: (v) => Number.isInteger(v) && v > 0 && v <= 10,
    nota: (v) => Number.isFinite(v) && v >= 0 && v <= 10,
    dataNasc: (v) => /^\d{4}-\d{2}-\d{2}$/.test(String(v || '')),
  };

  function slugEmail(nome) {
    return nome.trim().toLowerCase().replace(/\s+/g, '.');
  }

  // ---------- Aluno ----------

  const Aluno = {
    listar: () => loadAll(KEYS.alunos),

    criar({ nome, cpf, senha, cursoCodigo, dataNasc }) {
      if (!Validar.nome(nome)) throw new Error('Nome inválido! Digite apenas letras.');
      if (!Validar.cpf(cpf)) throw new Error('CPF inválido! Digite apenas 11 números.');
      if (!Validar.senha(senha)) throw new Error('Senha inválida! Ela deve ter pelo menos 8 caracteres.');
      if (!Validar.dataNasc(dataNasc)) throw new Error('Data de nascimento inválida.');

      const curso = Curso.buscarPorCodigo(cursoCodigo);
      if (!curso) throw new Error('Curso não encontrado. Digite um código válido.');

      const alunos = loadAll(KEYS.alunos);

      if (alunos.some((a) => a.cpf === cpf)) {
        throw new Error('Já existe um aluno cadastrado com esse CPF.');
      }

      const matricula = nextSeq('aluno');
      const email = `${slugEmail(nome)}${matricula}@universidade.com`;

      const aluno = {
        matricula,
        nome: nome.trim(),
        cpf,
        senha,
        curso: curso.nome,
        cursoCodigo: curso.codigo,
        turma: null,
        turmaCodigo: null,
        email,
        dataNasc,
        frequencia: 100,
      };

      alunos.push(aluno);
      saveAll(KEYS.alunos, alunos);
      return aluno;
    },

    buscarPorMatricula(matricula) {
      return loadAll(KEYS.alunos).find((a) => a.matricula === Number(matricula)) || null;
    },

    buscarPorEmailOuMatricula(identificador) {
      const alunos = loadAll(KEYS.alunos);
      const alvo = String(identificador).trim().toLowerCase();
      return (
        alunos.find(
          (a) => String(a.matricula) === alvo || a.email.toLowerCase() === alvo
        ) || null
      );
    },

    excluir(matricula) {
      const alunos = loadAll(KEYS.alunos);
      const idx = alunos.findIndex((a) => a.matricula === Number(matricula));
      if (idx === -1) return false;
      alunos.splice(idx, 1);
      saveAll(KEYS.alunos, alunos);
      return true;
    },

    atualizar(matricula, dados) {
      const alunos = loadAll(KEYS.alunos);
      const idx = alunos.findIndex((a) => a.matricula === Number(matricula));
      if (idx === -1) throw new Error('Aluno não encontrado.');
      alunos[idx] = { ...alunos[idx], ...dados };
      saveAll(KEYS.alunos, alunos);
      return alunos[idx];
    },

    matricularEmTurma(matriculaAluno, turmaCodigo) {
      const turma = Turma.buscarPorCodigo(turmaCodigo);
      if (!turma) throw new Error('Turma não encontrada.');
      const aluno = this.atualizar(matriculaAluno, {
        turma: turma.disciplinaNome,
        turmaCodigo: turma.codigo,
      });
      Turma.adicionarAluno(turmaCodigo, matriculaAluno);
      return aluno;
    },

    historico(matriculaAluno) {
      return Matricula.listar().filter((m) => m.alunoMatricula === Number(matriculaAluno));
    },
  };

  // ---------- Professor ----------

  const Professor = {
    listar: () => loadAll(KEYS.professores),

    criar({ nome, cpf, telefone, senha, especialidade }) {
      if (!Validar.nome(nome)) throw new Error('Digite um nome válido!');
      if (!Validar.cpf(cpf)) throw new Error('Digite um CPF válido!');
      if (!Validar.telefone(telefone)) throw new Error('Digite um telefone válido!');
      if (!Validar.senha(senha)) throw new Error('Senha inválida! Ela deve ter pelo menos 8 caracteres.');
      if (!Validar.nome(especialidade)) throw new Error('Digite uma especialidade válida!');

      const professores = loadAll(KEYS.professores);

      if (professores.some((p) => p.cpf === cpf)) {
        throw new Error('Já existe um professor cadastrado com esse CPF.');
      }

      const matricula = nextSeq('professor');
      const email = `${slugEmail(nome)}${matricula}@email.prof.com`;

      const professor = {
        matricula,
        nome: nome.trim(),
        cpf,
        telefone,
        senha,
        especialidade: especialidade.trim(),
        email,
        disciplinasProf: [],
      };

      professores.push(professor);
      saveAll(KEYS.professores, professores);
      return professor;
    },

    buscarPorMatricula(matricula) {
      return loadAll(KEYS.professores).find((p) => p.matricula === Number(matricula)) || null;
    },

    buscarPorEmailOuMatricula(identificador) {
      const professores = loadAll(KEYS.professores);
      const alvo = String(identificador).trim().toLowerCase();
      return (
        professores.find(
          (p) => String(p.matricula) === alvo || p.email.toLowerCase() === alvo
        ) || null
      );
    },

    excluir(matricula) {
      const professores = loadAll(KEYS.professores);
      const idx = professores.findIndex((p) => p.matricula === Number(matricula));
      if (idx === -1) return false;
      professores.splice(idx, 1);
      saveAll(KEYS.professores, professores);
      return true;
    },

    adicionarDisciplina(matricula, nomeDisciplina) {
      const professores = loadAll(KEYS.professores);
      const idx = professores.findIndex((p) => p.matricula === Number(matricula));
      if (idx === -1) return;
      professores[idx].disciplinasProf.push(nomeDisciplina);
      saveAll(KEYS.professores, professores);
    },

    disciplinasDe(matricula) {
      return Disciplina.listar().filter((d) => d.professorMatricula === Number(matricula));
    },

    turmasDe(matricula) {
      const codigos = this.disciplinasDe(matricula).map((d) => d.codigo);
      return Turma.listar().filter((t) => codigos.includes(t.disciplinaCodigo));
    },
  };

  // ---------- Curso ----------

  const Curso = {
    listar: () => loadAll(KEYS.cursos),

    criar({ nome, periodos }) {
      if (!Validar.nome(nome)) throw new Error('Digite um nome válido.');
      if (!Validar.periodosCurso(periodos)) throw new Error('Digite um valor válido (1 a 10).');

      const cursos = loadAll(KEYS.cursos);
      const numero = nextSeq('curso');
      const codigo = 'CRS' + String(numero).padStart(3, '0');

      const curso = {
        codigo,
        nome: nome.trim(),
        periodos,
        turmasCodigos: [],
        disciplinasCodigos: [],
      };

      cursos.push(curso);
      saveAll(KEYS.cursos, cursos);
      return curso;
    },

    buscarPorCodigo(codigo) {
      return loadAll(KEYS.cursos).find((c) => c.codigo === codigo) || null;
    },

    excluir(codigo) {
      const cursos = loadAll(KEYS.cursos);
      const idx = cursos.findIndex((c) => c.codigo === codigo);
      if (idx === -1) return false;
      cursos.splice(idx, 1);
      saveAll(KEYS.cursos, cursos);
      return true;
    },

    adicionarTurma(codigoCurso, codigoTurma) {
      const cursos = loadAll(KEYS.cursos);
      const idx = cursos.findIndex((c) => c.codigo === codigoCurso);
      if (idx === -1) throw new Error('Curso não encontrado.');
      if (!cursos[idx].turmasCodigos.includes(codigoTurma)) {
        cursos[idx].turmasCodigos.push(codigoTurma);
      }
      saveAll(KEYS.cursos, cursos);
      return cursos[idx];
    },

    adicionarDisciplina(codigoCurso, codigoDisciplina) {
      const cursos = loadAll(KEYS.cursos);
      const idx = cursos.findIndex((c) => c.codigo === codigoCurso);
      if (idx === -1) return;
      if (!cursos[idx].disciplinasCodigos.includes(codigoDisciplina)) {
        cursos[idx].disciplinasCodigos.push(codigoDisciplina);
      }
      saveAll(KEYS.cursos, cursos);
    },

    turmasDe(codigoCurso) {
      const curso = this.buscarPorCodigo(codigoCurso);
      if (!curso) return [];
      return Turma.listar().filter((t) => curso.turmasCodigos.includes(t.codigo));
    },

    disciplinasDe(codigoCurso) {
      const curso = this.buscarPorCodigo(codigoCurso);
      if (!curso) return [];
      return Disciplina.listar().filter((d) => curso.disciplinasCodigos.includes(d.codigo));
    },
  };

  // ---------- Disciplina ----------

  const Disciplina = {
    listar: () => loadAll(KEYS.disciplinas),

    criar({ nome, ementa, cargaHoraria, professorMatricula, cursoCodigo }) {
      if (!Validar.nome(nome)) throw new Error('Nome inválido, digite novamente.');
      if (!Validar.cargaHoraria(cargaHoraria)) {
        throw new Error('Carga horária inválida (máximo 30 horas).');
      }

      const professor = Professor.buscarPorMatricula(professorMatricula);
      if (!professor) throw new Error('Professor não encontrado.');

      const curso = cursoCodigo ? Curso.buscarPorCodigo(cursoCodigo) : null;
      if (cursoCodigo && !curso) throw new Error('Curso não encontrado.');

      const disciplinas = loadAll(KEYS.disciplinas);
      const numero = nextSeq('disciplina');
      const codigo = 'DISC' + String(numero).padStart(3, '0');

      const disciplina = {
        codigo,
        nome: nome.trim(),
        ementa: (ementa || '').trim(),
        cargaHoraria,
        professorMatricula: professor.matricula,
        professorNome: professor.nome,
        cursoCodigo: curso ? curso.codigo : null,
        cursoNome: curso ? curso.nome : null,
      };

      disciplinas.push(disciplina);
      saveAll(KEYS.disciplinas, disciplinas);

      Professor.adicionarDisciplina(professor.matricula, disciplina.nome);
      if (curso) Curso.adicionarDisciplina(curso.codigo, disciplina.codigo);

      return disciplina;
    },

    buscarPorCodigo(codigo) {
      return loadAll(KEYS.disciplinas).find((d) => d.codigo === codigo) || null;
    },

    excluir(codigo) {
      const disciplinas = loadAll(KEYS.disciplinas);
      const idx = disciplinas.findIndex((d) => d.codigo === codigo);
      if (idx === -1) return false;
      disciplinas.splice(idx, 1);
      saveAll(KEYS.disciplinas, disciplinas);
      return true;
    },

    atualizar(codigo, dados) {
      const disciplinas = loadAll(KEYS.disciplinas);
      const idx = disciplinas.findIndex((d) => d.codigo === codigo);
      if (idx === -1) throw new Error('Disciplina não encontrada.');

      if ('cargaHoraria' in dados && !Validar.cargaHoraria(dados.cargaHoraria)) {
        throw new Error('Carga horária inválida (máximo 30 horas).');
      }

      if ('professorMatricula' in dados) {
        const professor = Professor.buscarPorMatricula(dados.professorMatricula);
        if (!professor) throw new Error('Professor não encontrado.');
        dados.professorNome = professor.nome;
      }

      disciplinas[idx] = { ...disciplinas[idx], ...dados };
      saveAll(KEYS.disciplinas, disciplinas);
      return disciplinas[idx];
    },
  };

  // ---------- Turma ----------

  const Turma = {
    listar: () => loadAll(KEYS.turmas),

    criar({ disciplinaCodigo, periodo }) {
      const disciplina = Disciplina.buscarPorCodigo(disciplinaCodigo);
      if (!disciplina) throw new Error('Disciplina não encontrada.');
      if (!periodo || String(periodo).length < 1) throw new Error('Período inválido!');

      const turmas = loadAll(KEYS.turmas);
      const numero = nextSeq('turma');
      const codigo = 'TUR' + String(numero).padStart(3, '0');

      const turma = {
        codigo,
        disciplinaCodigo: disciplina.codigo,
        disciplinaNome: disciplina.nome,
        periodo: String(periodo),
        alunosMatriculas: [],
      };

      turmas.push(turma);
      saveAll(KEYS.turmas, turmas);
      return turma;
    },

    buscarPorCodigo(codigo) {
      return loadAll(KEYS.turmas).find((t) => t.codigo === codigo) || null;
    },

    excluir(codigo) {
      const turmas = loadAll(KEYS.turmas);
      const idx = turmas.findIndex((t) => t.codigo === codigo);
      if (idx === -1) return false;
      turmas.splice(idx, 1);
      saveAll(KEYS.turmas, turmas);
      return true;
    },

    adicionarAluno(codigoTurma, matriculaAluno) {
      const turmas = loadAll(KEYS.turmas);
      const idx = turmas.findIndex((t) => t.codigo === codigoTurma);
      if (idx === -1) throw new Error('Turma não encontrada.');
      if (!turmas[idx].alunosMatriculas.includes(Number(matriculaAluno))) {
        turmas[idx].alunosMatriculas.push(Number(matriculaAluno));
      }
      saveAll(KEYS.turmas, turmas);
      return turmas[idx];
    },

    alunosDe(codigoTurma) {
      const turma = this.buscarPorCodigo(codigoTurma);
      if (!turma) return [];
      return turma.alunosMatriculas
        .map((m) => Aluno.buscarPorMatricula(m))
        .filter(Boolean);
    },
  };

  // ---------- Matricula (notas / situação por disciplina) ----------

  const Matricula = {
    listar: () => loadAll(KEYS.matriculas),

    obterOuCriar(alunoMatricula, disciplinaCodigo) {
      const matriculas = loadAll(KEYS.matriculas);
      let m = matriculas.find(
        (x) => x.alunoMatricula === Number(alunoMatricula) && x.disciplinaCodigo === disciplinaCodigo
      );
      if (m) return m;

      const aluno = Aluno.buscarPorMatricula(alunoMatricula);
      const disciplina = Disciplina.buscarPorCodigo(disciplinaCodigo);
      if (!aluno) throw new Error('Aluno não encontrado!');
      if (!disciplina) throw new Error('Disciplina não encontrada!');

      m = {
        id: nextSeq('matricula'),
        alunoMatricula: aluno.matricula,
        alunoNome: aluno.nome,
        disciplinaCodigo: disciplina.codigo,
        disciplinaNome: disciplina.nome,
        notas: [],
        frequencia: 100,
        media: 0,
        aprovado: false,
      };
      matriculas.push(m);
      saveAll(KEYS.matriculas, matriculas);
      return m;
    },

    lancarNota(alunoMatricula, disciplinaCodigo, nota) {
      if (!Validar.nota(nota)) throw new Error('Nota inválida! Digite um valor entre 0 e 10.');

      this.obterOuCriar(alunoMatricula, disciplinaCodigo);
      const matriculas = loadAll(KEYS.matriculas);
      const idx = matriculas.findIndex(
        (x) => x.alunoMatricula === Number(alunoMatricula) && x.disciplinaCodigo === disciplinaCodigo
      );

      matriculas[idx].notas.push(nota);

      const soma = matriculas[idx].notas.reduce((a, b) => a + b, 0);
      matriculas[idx].media = matriculas[idx].notas.length
        ? soma / matriculas[idx].notas.length
        : 0;
      matriculas[idx].aprovado = matriculas[idx].media >= 6.0;

      saveAll(KEYS.matriculas, matriculas);
      return matriculas[idx];
    },

    definirFrequencia(alunoMatricula, disciplinaCodigo, frequencia) {
      this.obterOuCriar(alunoMatricula, disciplinaCodigo);
      const matriculas = loadAll(KEYS.matriculas);
      const idx = matriculas.findIndex(
        (x) => x.alunoMatricula === Number(alunoMatricula) && x.disciplinaCodigo === disciplinaCodigo
      );
      matriculas[idx].frequencia = Math.max(0, Math.min(100, Number(frequencia)));
      saveAll(KEYS.matriculas, matriculas);
      return matriculas[idx];
    },
  };

  // ---------- Sessão (login) ----------

  const Sessao = {
    entrar(tipo, identificador) {
      const dados = { tipo, identificador, quando: Date.now() };
      localStorage.setItem(KEYS.sessao, JSON.stringify(dados));
      return dados;
    },

    atual() {
      const raw = localStorage.getItem(KEYS.sessao);
      return raw ? JSON.parse(raw) : null;
    },

    sair() {
      localStorage.removeItem(KEYS.sessao);
    },

    usuarioAtual() {
      const s = this.atual();
      if (!s) return null;
      if (s.tipo === 'aluno') return Aluno.buscarPorMatricula(s.identificador);
      if (s.tipo === 'professor') return Professor.buscarPorMatricula(s.identificador);
      return null;
    },
  };

  return { Aluno, Professor, Curso, Disciplina, Turma, Matricula, Sessao, Validar, KEYS };
})();

if (typeof module !== 'undefined' && module.exports) {
  module.exports = DB;
}
