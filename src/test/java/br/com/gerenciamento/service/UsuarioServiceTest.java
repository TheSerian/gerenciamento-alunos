package br.com.gerenciamento.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {
	
	@Autowired
	private ServiceUsuario serviceUsuario;
	
	@Test
	public void salvarUsuario() throws Exception {
		Usuario usuario = new Usuario();
		usuario.setUser("usuarioManeiro");
		usuario.setEmail("usuariomaneiro@gmail.com");
		usuario.setSenha("senhamaneira");
		
		serviceUsuario.salvarUsuario(usuario);
		
		Assert.assertNotNull(usuario.getId());
	}
	
	@Test(expected = EmailExistsException.class)
	public void salvarUsuarioComEmailDuplicado() throws Exception {
		Usuario u1 = new Usuario();
		u1.setUser("bigPoppa");
		u1.setEmail("emailduplicado@teste.com");
		u1.setSenha("123456");
		serviceUsuario.salvarUsuario(u1);
		
		Usuario u2 = new Usuario();
		u2.setUser("smallPoppa");
		u2.setEmail("emailduplicado@teste.com");
		u2.setSenha("789123");
		serviceUsuario.salvarUsuario(u2); //esperado que lance EmailExistsException
	}
	
	@Test
	public void loginUserSenhaIncorreta() throws Exception {
		Usuario usuario = new Usuario();
		usuario.setUser("binho");
		usuario.setEmail("binhoogatinho@miau.com");
		usuario.setSenha("1234");
		serviceUsuario.salvarUsuario(usuario);
		
		Usuario resultado = serviceUsuario.loginUser("binho","senhaErrada");
		
		Assert.assertNull(resultado);
	}
	@Test
	public void loginUserNaoEncontrado() {
		Usuario resultado = serviceUsuario.loginUser("usuarioFake", "senhaFake");
		
		Assert.assertNull(resultado);
	}
}