# Sistema Acadêmico Web

Sistema acadêmico que roda direto no navegador, com portais para **estudante**, **professor** e **coordenação**. Cobre matrículas, lançamento de notas, frequência e situação de aprovação.

🔗 **Acesse:** https://sistema-academico-web.vercel.app

> Na página inicial, clique em **"Carregar dados de demonstração"** para criar um curso, um professor e uma aluna de exemplo e testar o fluxo completo sem cadastrar nada.

## De onde veio este projeto

Esta é a **versão 2** de um projeto que comecei em Java.

A **versão 1** é o [Sistema-Academico-](https://github.com/joaopedrolimadejesus95-beep/Sistema-Academico-): um programa de console em Java com as classes `Aluno`, `Professor`, `Curso`, `Disciplina`, `Turma` e `Matricula`. Ele funcionava só pelo terminal e não guardava nenhum dado quando o programa fechava.

Na versão web, transformei aquele projeto em uma aplicação com interface gráfica e login. O código Java original está na pasta [`java-original/`](sistema-academico-web/java-original) para comparação.

### O que foi mantido da versão Java

As regras de negócio foram portadas para JavaScript (`js/db.js`):

- Matrícula do aluno começa em 1000; a do professor, em 1
- Códigos gerados automaticamente: `CRS001` para cursos, `DISC001` para disciplinas
- Validação de nome (só letras), CPF e telefone (11 dígitos)
- Senha com no mínimo 8 caracteres e carga horária máxima de 30h por disciplina
- E-mail institucional gerado a partir do nome e da matrícula
- Média = soma das notas ÷ quantidade; **aprovado com média ≥ 6,0**

### O que foi adicionado na versão web

- **Persistência** com `localStorage`, para os dados continuarem salvos após fechar o navegador
- **Login de aluno**, que não existia no Java
- **Código próprio para turmas**, para facilitar referenciar cada uma
- **Vínculo entre curso e disciplina** de fato salvo (no Java, o método existia mas nunca era chamado)
- **Controle de frequência**, conceito que não existia na versão original

## Funcionalidades

| Portal | O que dá pra fazer |
|---|---|
| 🏛️ Coordenação | Cadastrar cursos, disciplinas e turmas |
| 📖 Professor | Ver suas turmas e alunos, lançar notas e frequência |
| 🎓 Estudante | Consultar disciplinas, notas, frequência e situação em cada matéria |

## Tecnologias

- HTML, CSS e JavaScript puro (sem framework)
- `localStorage` como banco de dados no navegador
- Deploy na Vercel
- Java (versão original, em `java-original/`)

## Estrutura

```
sistema-academico-web/
├── index.html          # página inicial com os três portais
├── admin/              # área da coordenação
├── aluno/              # login, cadastro e painel do estudante
├── professor/          # login, cadastro e painel do professor
├── css/style.css
├── js/
│   ├── db.js           # camada de dados e regras de negócio
│   ├── auth.js         # sessão, login e logout
│   └── seed.js         # dados de demonstração
└── java-original/      # código da versão 1, em Java
```

## Como rodar localmente

Não precisa instalar nada:

1. Clone o repositório
2. Abra `sistema-academico-web/index.html` no navegador

## Limitações conhecidas

Este é um projeto de estudo, então algumas escolhas não serviriam em produção:

- Os dados ficam **só no navegador** de quem usa. Outra pessoa ou outro computador não vê os mesmos dados.
- As senhas são salvas **sem criptografia** no `localStorage`.
- Não há back-end: toda a validação acontece no front-end.

## Próximos passos

- [ ] Criar um back-end em Java (Spring Boot) com banco PostgreSQL
- [ ] Guardar senhas com hash (bcrypt)
- [ ] Adicionar testes para as regras de média e aprovação

---

Feito por **João Pedro Lima de Jesus** · [LinkedIn](https://www.linkedin.com/in/jo%C3%A3o-pedro-lima-de-jesus-17b798322/)
