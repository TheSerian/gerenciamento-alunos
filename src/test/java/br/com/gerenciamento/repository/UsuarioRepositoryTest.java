package br.com.gerenciamento.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.model.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Test
	public void findByEmailTeste() {
		Usuario usuario = new Usuario();
		usuario.setUser("Afonso Lima");
		usuario.setEmail("testando123@email.com");
		usuario.setSenha("juvencio");
		usuarioRepository.save(usuario);
		
		Usuario usuarioEncontrado = usuarioRepository.findByEmail("testando123@email.com");
		
		Assert.assertNotNull(usuarioEncontrado);
		Assert.assertEquals("testando123@email.com", usuarioEncontrado.getEmail());
		Assert.assertEquals("Afonso Lima", usuarioEncontrado.getUser());
	}
	
	@Test
	public void findByEmailInexistente() {
		Usuario usuarioEncontrado = usuarioRepository.findByEmail("naoexisto@email.com");
		
		Assert.assertNull(usuarioEncontrado);
	}
	
	@Test
	public void buscarLoginTeste() {
		Usuario usuario = new Usuario();
		usuario.setUser("Laura");
		usuario.setEmail("laura78@gmail.com");
		usuario.setSenha("senhamuitoforte");
		usuarioRepository.save(usuario);
		
		Usuario usuarioLogado = usuarioRepository.buscarLogin("Laura", "senhamuitoforte");
		
		Assert.assertNotNull(usuarioLogado);
		Assert.assertEquals("Laura", usuarioLogado.getUser());
	}
	
	@Test
	public void buscarLoginSenhaIncorreta() {
		Usuario usuario = new Usuario();
		usuario.setUser("hera");
		usuario.setEmail("heraagatinha@miau.com");
		usuario.setSenha("4321");
		usuarioRepository.save(usuario);
		
		Usuario usuarioLogado = usuarioRepository.buscarLogin("hera", "senhaErrada");
		
		Assert.assertNull(usuarioLogado);
	}
}