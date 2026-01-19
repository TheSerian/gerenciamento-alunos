package br.com.gerenciamento.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Test
	public void findByStatusAtivoTeste() {
		Aluno a1 = new Aluno();
        a1.setId(1L);
        a1.setNome("Ativo da Silva");
        a1.setTurno(Turno.NOTURNO);
        a1.setCurso(Curso.ADMINISTRACAO);
        a1.setStatus(Status.ATIVO);
        a1.setMatricula("123456");
        this.alunoRepository.save(a1);
		
        Aluno a2 = new Aluno();
        a2.setId(2L);
        a2.setNome("Inativo da Silva");
        a2.setTurno(Turno.MATUTINO);
        a2.setCurso(Curso.INFORMATICA);
        a2.setStatus(Status.INATIVO);
        a2.setMatricula("654321");
        this.alunoRepository.save(a2);
        
        List<Aluno> listaAtivos = alunoRepository.findByStatusAtivo();
        Assert.assertFalse(listaAtivos.isEmpty());
        
        //garantir que os alunos retornados são ativos
        for(Aluno aluno : listaAtivos) {
        	Assert.assertEquals(Status.ATIVO, aluno.getStatus());
        }
	}
	
	@Test
	public void FindByStatusInativoTeste() {
		Aluno a1 = new Aluno();
        a1.setId(1L);
        a1.setNome("Ativo da Silva");
        a1.setTurno(Turno.NOTURNO);
        a1.setCurso(Curso.ADMINISTRACAO);
        a1.setStatus(Status.ATIVO);
        a1.setMatricula("123456");
        this.alunoRepository.save(a1);
		
        Aluno a2 = new Aluno();
        a2.setId(2L);
        a2.setNome("Inativo da Silva");
        a2.setTurno(Turno.MATUTINO);
        a2.setCurso(Curso.INFORMATICA);
        a2.setStatus(Status.INATIVO);
        a2.setMatricula("654321");
        this.alunoRepository.save(a2);
        
        List<Aluno> listaInativos = alunoRepository.findByStatusInativo();
        Assert.assertFalse(listaInativos.isEmpty());
        
        //garantir que os alunos retornados são inativos
        for(Aluno aluno : listaInativos) {
        	Assert.assertEquals(Status.INATIVO, aluno.getStatus());
        }
	}
	
	@Test
	public void FindByNomeContainingIgnoreCaseTeste() {
		Aluno aluno = new Aluno();
        aluno.setId(3L);
        aluno.setNome("Juvencio");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.BIOMEDICINA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("777777");
        this.alunoRepository.save(aluno);
        
        List<Aluno> resultado = alunoRepository.findByNomeContainingIgnoreCase("JUVENCIO");
        
        Assert.assertFalse(resultado.isEmpty());
        Assert.assertTrue(resultado.get(0).getNome().contains("Juvencio"));
	}
	
	@Test
	public void SaveAndFindByIdTeste() {
		Aluno aluno = new Aluno();
        aluno.setNome("Hernandez Hernando");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.CONTABILIDADE);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("666666");
        this.alunoRepository.save(aluno);
        
        Aluno alunoEncontrado = alunoRepository.findById(aluno.getId()).orElse(null);
	
        Assert.assertNotNull(alunoEncontrado);
        Assert.assertEquals("Hernandez Hernando", alunoEncontrado.getNome());
	}
}