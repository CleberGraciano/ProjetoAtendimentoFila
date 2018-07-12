package dao;

import java.util.List;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Ignore;
import org.junit.Test;

import domain.Pessoa;
import domain.Usuario;

public class UsuarioDaoTest {
	@Test
	public void salvar() {
		Long codigoPessoa = 5L;
		PessoaDao pessoaDao = new PessoaDao();
		Pessoa pessoa = pessoaDao.Buscar(codigoPessoa);

		System.out.println("Pessoa encontrada ");
		System.out.println("Nome: " + pessoa.getNome());
		System.out.println("Nome: " + pessoa.getCpf());
		
		

		Usuario usuario = new Usuario();

		usuario.setAtivo(true);
		usuario.setPessoa(pessoa);
		usuario.setSenhaSemCriptografia("q1w2e3");
		
		SimpleHash hash = new SimpleHash("md5", usuario.getSenhaSemCriptografia());
		usuario.setSenha(hash.toHex());
		usuario.setTipo('A');

		UsuarioDao usuarioDao = new UsuarioDao();

		usuarioDao.salvar(usuario);

	}
	
	
	@Test
	@Ignore
	public void autenticar() {
		String cpf="418.848.738-04";
		String senha="q1w2e3";
		
		UsuarioDao usuarioDao = new UsuarioDao();
	
		Usuario usuario = usuarioDao.autenticar(cpf, senha);
		
		System.out.println("Usuário autenticado: " + usuario);
	}
	

	@Test
	@Ignore
	public void listar() {

		UsuarioDao usuarioDao = new UsuarioDao();
		List<Usuario> resultado = usuarioDao.listar();

		System.out.println("O total de Usuarios cadastrados são: " + resultado.size());

		for (Usuario usuario : resultado) {

			System.out.println("Nome Usuario: " + usuario.getPessoa().getNome() + " - Tipo: " + usuario.getTipo()
					+ " - Status: " + usuario.getAtivo() + " - Senha: " + usuario.getSenha());
		}

	}

	@Test
	@Ignore
	public void buscar() {

		Long codigoUsuario = 1L;

		UsuarioDao usuarioDao = new UsuarioDao();
		Usuario usuario = usuarioDao.Buscar(codigoUsuario);

		if (usuario == null) {

			System.out.println("Usuário não existe");

		} else {
			System.out.println("Nome Usuario: " + usuario.getPessoa().getNome() + " - Tipo: " + usuario.getTipo()
					+ " - Status: " + usuario.getAtivo() + " - Senha: " + usuario.getSenha());
		}
	}
	
	@Test
	@Ignore
	public void excluir() {

		Long codigoUsuario = 1L;
		UsuarioDao usuarioDao = new UsuarioDao();
		Usuario usuario = usuarioDao.Buscar(codigoUsuario);

		if (usuario == null) {
			System.out.println("Usuario não Existe");
		} else {
			
			usuarioDao.excluir(usuario);
			System.out.println("Usuario Excluido com sucesso");
			System.out.println("Nome Usuario: "+usuario.getPessoa().getNome()+" - Tipo: "+usuario.getTipo()+" - Status: "+usuario.getAtivo()+" - Senha: "+usuario.getSenha());
			
			

		}

	}
	
	@Test
	@Ignore
	public void editar() {
		
		Long codigoPessoa = 1L;
		PessoaDao pessoaDao = new PessoaDao();
		Pessoa pessoa = pessoaDao.Buscar(codigoPessoa);
		
		Long codigoUsuario = 1L;
		UsuarioDao usuarioDao = new UsuarioDao();		
		Usuario usuario = usuarioDao.Buscar(codigoUsuario);
		
		System.out.println("Usuario a ser alterado: ");
		
		System.out.println("Nome Usuario: " + usuario.getPessoa().getNome() + " - Tipo: " + usuario.getTipo()
		+ " - Status: " + usuario.getAtivo() + " - Senha: " + usuario.getSenha());
		
		usuario.setAtivo(true);
		usuario.setPessoa(pessoa);
		usuario.setSenha("q1w2e4");
		usuario.setTipo('G');
		
		usuarioDao.editar(usuario);
		
		System.out.println("Usuario Alterado com sucesso");
		
		System.out.println("Nome Usuario: " + usuario.getPessoa().getNome() + " - Tipo: " + usuario.getTipo()
		+ " - Status: " + usuario.getAtivo() + " - Senha: " + usuario.getSenha());
		
		
		
	}

}
