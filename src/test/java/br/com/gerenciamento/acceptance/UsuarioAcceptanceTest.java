package br.com.gerenciamento.acceptance;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UsuarioAcceptanceTest {
	
	private WebDriver driver;
	
	@Before
	public void setup() {
		driver = new FirefoxDriver();
	}
	
	@After
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
	
	@Test
	public void realizarCadastroAndLogin() throws InterruptedException {
		//fazendo cadastro
		driver.get("http://localhost:8080/cadastro");
		
		driver.findElement(By.name("user")).sendKeys("jeronimo");
		driver.findElement(By.name("email")).sendKeys("jeronimo@teste.com");
		driver.findElement(By.name("senha")).sendKeys("jacabou");
		
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		
		Thread.sleep(1500);
		
		String urlLogin = driver.getCurrentUrl();
		Assert.assertTrue(urlLogin.endsWith("/") || urlLogin.contains("login"));
		
		//fazendo login
		WebElement inputUsuario = driver.findElement(By.name("user"));
		WebElement inputSenha = driver.findElement(By.name("senha"));
		
		inputUsuario.clear();
		inputSenha.clear();
		
		inputUsuario.sendKeys("jeronimo");
		inputSenha.sendKeys("jacabou");
		
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		
		Thread.sleep(2000);
		
		//validando login
		String pageSource = driver.getPageSource();
		
		boolean validarLogin = pageSource.contains("Sistema de Gerenciamento de Alunos") ||
				pageSource.contains("CADASTRAR ALUNO") || 
				pageSource.contains("ENCONTRAR ALUNO");
		
		
		Assert.assertTrue(validarLogin);
	}
}