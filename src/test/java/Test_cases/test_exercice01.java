package Test_cases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class test_exercice01 {
	@Test
	public void test_endtoend() throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://demo.opencart.com/admin/index.php?route=common/login");
		// login
		WebElement user = driver.findElement(By.name("username"));
		user.clear();
		user.sendKeys("demo");
		WebElement pass = driver.findElement(By.name("password"));
		pass.clear();
		pass.sendKeys("demo");
		driver.findElement(By.xpath("//button[text()=' Login']")).click();
		// gérer la modal
		if (driver.findElement(By.cssSelector(".btn-close")).isDisplayed()) {
			driver.findElement(By.cssSelector(".btn-close")).click();
		}
		// valider l'acces a la page
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Logout']")).isDisplayed());
		// gérer les clients
		driver.findElement(By.xpath("//a[@href='#collapse-5']")).click();
		driver.findElement(By.xpath("//ul[@id='collapse-5']/li[1]/a")).click();
		Thread.sleep(4000);
		String text = driver.findElement(By.xpath("(//div[@class='col-sm-6 text-end'])[1]")).getText();
		System.out.println(text);

		int total_pages = Integer.parseInt(text.substring(text.indexOf("(") + 1, text.indexOf("Pages") - 1));
		// System.out.println(text.length()+"Nombres de page"+text.substring(26, 36));
		//parcourire toutes les page et affiché le contenue 
		
		for (int i = 1; i < 6; i++) {
			if (total_pages>=1) {
				WebElement page_numbre=driver.findElement(By.xpath("//ul[@class='pagination']//li//*[text()="+i+"]"));
				System.out.println("le numéro de page est :"+page_numbre.getText());
				page_numbre.click();
				Thread.sleep(3000);	
				
			
			}
			//récupere les valeur du client et mail et staut 
			int nombre_lignes=driver.findElements(By.xpath("//table[@class='table table-bordered table-hover']/tbody/tr")).size();
			System.out.println("nombre de lignes du tableau est:"+nombre_lignes);
			for (int j = 1; j < nombre_lignes; j++) {
				String nom_client= driver.findElement(By.xpath("//table[@class='table table-bordered table-hover']/tbody/tr["+j+"]/td[2]")).getText();
				String email_client= driver.findElement(By.xpath("//table[@class='table table-bordered table-hover']/tbody/tr[\"+j+\"]/td[3]")).getText();
				String statut_client= driver.findElement(By.xpath("//table[@class='table table-bordered table-hover']/tbody/tr[\"+j+\"]/td[5]")).getText();
				System.out.println(nom_client+"---"+email_client+"---"+statut_client);
			}
		}
		
		
		
		
		driver.quit();
	}
}
