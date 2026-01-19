package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoServiceTest {

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void getById() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(1L);
        Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
    }

    @Test
    public void salvarSemNome() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
                this.serviceAluno.save(aluno);});
    }
    
    @Test
    public void findAll() {
        Aluno aluno = new Aluno();
        aluno.setId(2L);
        aluno.setNome("Marcelinho");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.DIREITO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("987654");
        this.serviceAluno.save(aluno);

        java.util.List<Aluno> listaAlunos = this.serviceAluno.findAll();
        Assert.assertFalse(listaAlunos.isEmpty());
    }

    @Test
    public void findByStatusInativo() {
        Aluno aluno = new Aluno();
        aluno.setId(3L);
        aluno.setNome("Jenoveva");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.CONTABILIDADE);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("123123");
        this.serviceAluno.save(aluno);

        java.util.List<Aluno> alunosInativos = this.serviceAluno.findByStatusInativo();
        Assert.assertFalse(alunosInativos.isEmpty());
        Assert.assertEquals(Status.INATIVO, alunosInativos.get(0).getStatus());
    }

    @Test
    public void findByNomeContaining() {
        Aluno aluno = new Aluno();
        aluno.setId(4L);
        aluno.setNome("John Doe da Silva");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.BIOMEDICINA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("321321");
        this.serviceAluno.save(aluno);

        java.util.List<Aluno> listaPesquisa = this.serviceAluno.findByNomeContainingIgnoreCase("john");
        Assert.assertFalse(listaPesquisa.isEmpty());
        Assert.assertTrue(listaPesquisa.get(0).getNome().contains("John"));
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void deleteById() {
        Aluno aluno = new Aluno();
        aluno.setId(5L);
        aluno.setNome("João Apagado");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ENFERMAGEM);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("654321");
        this.serviceAluno.save(aluno);

        this.serviceAluno.deleteById(5L);
        this.serviceAluno.getById(5L); //esperado que dispare uma exceção
    }
}