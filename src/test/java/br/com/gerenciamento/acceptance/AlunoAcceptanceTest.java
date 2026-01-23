package br.com.gerenciamento.acceptance;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AlunoAcceptanceTest {
	
	private WebDriver driver;
	
	@Autowired
	private ServiceUsuario serviceUsuario;
	
	@Before
	public void setup() {
		Usuario usuario = new Usuario();
		usuario.setUser("jonas");
		usuario.setEmail("jona@teste.com");
		usuario.setSenha("joninha");
		
		try {
			serviceUsuario.salvarUsuario(usuario);
		} catch (Exception e) {
			
		}
		
		driver = new ChromeDriver();
	}
	
	@After
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
	
	@Test
	public void cadastrarAlunoAndVerificarLista() throws InterruptedException {
		//fazendo login
		driver.get("http://localhost:8080/");
		
		driver.findElement(By.name("user")).sendKeys("jonas");
		driver.findElement(By.name("senha")).sendKeys("joninha");
		
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		
		Thread.sleep(1000);
		
		//cadastrando aluno
		driver.get("http://localhost:8080/inserirAlunos");
		
		driver.findElement(By.name("nome")).sendKeys("Odair José");
		driver.findElement(By.name("matricula")).sendKeys("777777");
		driver.findElement(By.name("turno")).sendKeys("NOTURNO");
		driver.findElement(By.name("curso")).sendKeys("BIOMEDICINA");
		driver.findElement(By.name("status")).sendKeys("ATIVO");
		
		driver.findElement(By.xpath("//button[text()='Salvar']")).click();
		
		Thread.sleep(2000);
		
		//validando listagem
		String urlAtual = driver.getCurrentUrl();
		String conteudoPagina = driver.getPageSource();
		
		Assert.assertTrue(urlAtual.contains("/alunos-adicionados"));
		
		Assert.assertTrue(conteudoPagina.contains("Odair José"));
	}
}