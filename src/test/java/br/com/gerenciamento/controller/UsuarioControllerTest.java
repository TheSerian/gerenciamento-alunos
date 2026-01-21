package br.com.gerenciamento.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ServiceUsuario serviceUsuario;
	
	@Test
	public void acessoPaginaLoginTeste() throws Exception {
		mockMvc.perform(get("/"))
		.andExpect(status().isOk())
		.andExpect(view().name("login/login"))
		.andExpect(model().attributeExists("usuario"));
	}
	
	@Test
	public void cadastrarUsuarioTeste() throws Exception {
		mockMvc.perform(post("/salvarUsuario")
				.param("user", "usuarioTestado")
				.param("senha", "senhaFortona")
				.param("email", "testadeamolarfacao@email.com"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));
	}
	
	@Test
	public void loginSucesso() throws Exception {
		Usuario usuario = new Usuario();
		usuario.setUser("juaozinho");
		usuario.setSenha("eljuan");
		usuario.setEmail("juaozinho@email.com");
		serviceUsuario.salvarUsuario(usuario);
		
		mockMvc.perform(post("/login")
				.param("user", "juaozinho")
				.param("senha", "eljuan"))
				.andExpect(status().isOk())
				.andExpect(view().name("home/index"))
				.andExpect(request().sessionAttribute("usuarioLogado", notNullValue()));
	}
	
	@Test
	public void loginFalha() throws Exception {
		mockMvc.perform(post("/login")
				.param("user", "naoExisto")
				.param("senha", "tambemNaoExisto"))
				.andExpect(status().isOk())
				.andExpect(view().name("login/cadastro"))
				.andExpect(model().attributeExists("usuario"));
	}
}