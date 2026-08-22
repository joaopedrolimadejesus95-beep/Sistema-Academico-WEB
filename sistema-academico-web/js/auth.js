/**
 * auth.js — utilidades de sessão usadas em todas as páginas.
 */

function exigirLogin(tipoEsperado, urlLogin) {
  const sessao = DB.Sessao.atual();
  if (!sessao || sessao.tipo !== tipoEsperado) {
    window.location.href = urlLogin;
    return null;
  }
  const usuario = DB.Sessao.usuarioAtual();
  if (!usuario) {
    DB.Sessao.sair();
    window.location.href = urlLogin;
    return null;
  }
  return usuario;
}

function configurarLogout(botaoId, urlDestino) {
  const btn = document.getElementById(botaoId);
  if (!btn) return;
  btn.addEventListener('click', () => {
    DB.Sessao.sair();
    window.location.href = urlDestino;
  });
}

function mostrarErro(elId, mensagem) {
  const el = document.getElementById(elId);
  if (!el) return;
  el.textContent = mensagem;
  el.classList.add('show');
}

function esconderErro(elId) {
  const el = document.getElementById(elId);
  if (!el) return;
  el.classList.remove('show');
  el.textContent = '';
}

function iniciais(nome) {
  return nome
    .split(' ')
    .filter(Boolean)
    .slice(0, 2)
    .map((p) => p[0].toUpperCase())
    .join('');
}
