package br.com.gerenciamento.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void insertAlunosTeste() throws Exception {
		mockMvc.perform(get("/inserirAlunos"))
		.andExpect(status().isOk())
		.andExpect(view().name("Aluno/formAluno"))
		.andExpect(model().attributeExists("aluno"));
	}
	
	@Test
	public void listagemAlunosTeste() throws Exception {
		mockMvc.perform(get("/alunos-adicionados"))
		.andExpect(status().isOk())
		.andExpect(view().name("Aluno/listAlunos"))
		.andExpect(model().attributeExists("alunosList"));
	}
	
	@Test
	public void inserirAlunoTeste() throws Exception {
		mockMvc.perform(post("/InsertAlunos")
				.param("nome", "Teste da Silva")
				.param("matricula", "123456")
				.param("turno", "NOTURNO")
				.param("curso", "INFORMATICA")
				.param("status", "ATIVO"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/alunos-adicionados"));
	}
	
	@Test
	public void pesquisarAlunoTeste() throws Exception {
		mockMvc.perform(post("/pesquisar-aluno")
				.param("nome", "Teste Oliveira"))
				.andExpect(status().isOk())
				.andExpect(view().name("Aluno/pesquisa-resultado"))
				.andExpect(model().attributeExists("ListaDeAlunos"));
	}
}