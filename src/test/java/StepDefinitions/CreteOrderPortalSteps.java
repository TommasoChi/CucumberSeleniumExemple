package StepDefinitions;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;

public class CreteOrderPortalSteps {

	WebDriver driver = null;

	@Given("broswer aperto")
	public void broswer_aperto() {

		System.out.println("Step: broswer aperto");
		String projectPath = System.getProperty("user.dir");
		System.out.println("Project path is : "+projectPath);
		System.setProperty("webdriver.chrome.driver", projectPath+"/src/test/resources/drivers/chromedriver.exe");

		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		//driver.manage().window().maximize();
	}

	@And("pagina di home caricata")
	public void pagina_di_home_caricata() throws InterruptedException {
		System.out.println("Step: pagina di home caricata");
		
		driver.navigate().to("http://wwwdevpub01.dc.ngi.it:8080/home/casa.html");
		//driver.navigate().to("https://www-qa2.eolo.it/");
		Thread.sleep(2000);
	}

	@When("seleziono offerta")
	public void seleziono_offerta() throws InterruptedException {
		System.out.println("Step: seleziono offerta");
		//driver.findElement(By.name("q")).sendKeys("corriere della sera");
		List<WebElement> findElements = driver.findElements(By.linkText("Scopri l’offerta"));

		Iterator<WebElement> elementInterator = findElements.iterator();
		while (elementInterator.hasNext()) {
			WebElement element = elementInterator.next();
			System.out.println("Step: seleziono offerta element: "+ element.getAttribute("href"));	
			if ( (element.getAttribute("href")).contains("eolo-piu")) 
			{
				element.click();
				break;
			}
		}	
		Thread.sleep(2000);
	}


	@Then("pagina di offerta caricata")
	public void caricata_pagina_offerta() {
		System.out.println("Step: Caricata pagina offerta");
		boolean pageLoaded = driver.getPageSource().contains("<span class=\"price\">24,90€</span>");
		System.out.println("Step: Caricata pagina offerta: "+pageLoaded);
	}


	@When("seleziono configuro offerta")
	public void seleziono_configuro_offerta() throws InterruptedException {
		System.out.println("Step: seleziono configuro offerta");

		List<WebElement> findElements = driver.findElements(By.linkText("Configura l’offerta")); 
		Iterator<WebElement> elementInterator = findElements.iterator();
		while (elementInterator.hasNext()) {
			WebElement element = elementInterator.next();
			System.out.println("Step: seleziono configuro offerta url:"+ element.getAttribute("href"));	
			if ( (element.getAttribute("href")).contains("eolo-piu")) 
			{
				element.click();
				break;
			}
		}	
		Thread.sleep(2000);
	}


	@Then("carico pagina copertura")
	public void carico_pagina_copertura() {

		System.out.println("Step: carico pagina copertura");

		boolean pageCopertura = driver.getCurrentUrl().contains("copertura-prodotto.html");
		System.out.println("Step: carico pagina copertura pageCopertura: "+pageCopertura);		

		boolean titoloCopertura = driver.findElement(By.id("titlePageCopertura")).getText().contains("1. Verifica copertura");
		System.out.println("Step: carico pagina copertura titoloCopertura: "+titoloCopertura);		
	}


	@When("^edito coperturta comune:(.*) strada:(.*) civico:(.*)$")
	public void edito_coperturta(String comune, String strada, String civico) throws InterruptedException {
		System.out.println("Step: edito coperturta");

		driver.findElement(By.id("city_label")).sendKeys(comune);
		Thread.sleep(1000);
		driver.findElement(By.id("ui-id-1")).click();

		driver.findElement(By.id("address")).sendKeys(strada);
		Thread.sleep(1000);
		driver.findElement(By.id("ui-id-4")).click();

		new Actions(driver).moveToElement(driver.findElement(By.id("number"))).perform();
		driver.findElement(By.id("number")).sendKeys("33");

		WebElement findElement = driver.findElement(By.xpath("//button[@class=\"btn btn-verify pull-right\"]"));
		//System.out.println(findElements.getSize());
		new Actions(driver).moveToElement(findElement).perform();
		findElement.sendKeys(Keys.ENTER);

		Thread.sleep(2000);

		
	}


	@Then("carico pagina configurazione")
	public void carico_pagina_configurazione() throws InterruptedException {
		System.out.println("Step: carico pagina configurazione");
		boolean configurationPage = driver.getPageSource().contains("<h1 class=\"animated \">2. Configurazione</h1>");		
		for ( int i=0; i < 5 &&  !configurationPage; i++) {
			Thread.sleep(2000);
			configurationPage = driver.getPageSource().contains("<h1 class=\"animated \">2. Configurazione</h1>");
		}
		System.out.println("Step: carico pagina configurazione: "+configurationPage);
	}


	@And("verifico prezzo base")
	public void verifico_prezzo_base() {

		System.out.println("Step: verifico prezzo base");
		boolean priceDiscount = driver.getPageSource().contains("24,90€");		
		System.out.println("Step: verifico prezzo base priceDiscount: "+priceDiscount);		
		
	}

	
	@When("^configura addon Intrattenimento:(.*) Studio:(.*) Security:(.*)$")
	public void configura_addon(String intrattenimento, String studio , String security) throws InterruptedException {
		System.out.println("Step: configura addon intrattenimento:"+intrattenimento+" studio:"+studio+ "security;"+security);
		
		WebElement findElement = driver.findElement(By.linkText("Salta"));
		new Actions(driver).moveToElement(findElement).perform();
		findElement.sendKeys(Keys.ENTER);
		Thread.sleep(2000);
			
		System.out.println("Step: intrattenimento:"+intrattenimento);
		if(intrattenimento.equals("No")) {
			WebElement findElement1 = driver.findElement(By.linkText("Prosegui senza aggiungere"));
			new Actions(driver).moveToElement(findElement1).perform();
			findElement1.sendKeys(Keys.ENTER);
			Thread.sleep(2000);	
		} else  {
			WebElement findElement1 = driver.findElement(By.id("add-package-button-self-PKG_ENTERTAINMENT"));
			new Actions(driver).moveToElement(findElement1).perform();
			findElement1.sendKeys(Keys.ENTER);
			findElement1.click();
			Thread.sleep(2000);	
		}
			
		System.out.println("Step: studio:"+studio);
		if(studio.equals("No")) {
			WebElement findElement1 = driver.findElement(By.linkText("Prosegui senza aggiungere"));
			new Actions(driver).moveToElement(findElement1).perform();
			findElement1.sendKeys(Keys.ENTER);
			Thread.sleep(2000);	
		} else  {
			System.out.println("Step: Studio e Lavoro");
			WebElement findElement2 = driver.findElement(By.id("add-package-button-self-PKG_SMARTWORKING"));
			new Actions(driver).moveToElement(findElement2).perform();
			findElement2.sendKeys(Keys.ENTER);
			findElement2.click();
			Thread.sleep(2000);	
		}
		
		
		System.out.println("Step: Sicurezza:"+security);
		//WebElement findElement3 = driver.findElement(By.xpath("//button[@class=\"btn gotoaccessory-btn \"]"));
		//System.out.println(findElement3.getSize());
		//new Actions(driver).moveToElement(findElement3).perform();
		//findElement3.sendKeys(Keys.ENTER);
		//findElement3.click();
		//Thread.sleep(2000);
		
	}
	
	@Then("configura device")
	public void configura_device() throws InterruptedException {
		
		System.out.println("Step: configura device");
		WebElement findElement3 = driver.findElement(By.xpath("//button[@class=\"btn gotoaccessory-btn \"]"));
		System.out.println(findElement3.getSize());
		new Actions(driver).moveToElement(findElement3).perform();
		findElement3.sendKeys(Keys.ENTER);
		findElement3.click();
		Thread.sleep(2000);
		//driver.close();
		//driver.quit();
	
	}
	
	
	@When("configurazione telefonica")
	public void configurazione_telefonica() throws InterruptedException {
		System.out.println("Step: configurazione telefonica");
		WebElement findElement = driver.findElement(By.id("ctaNextStepTrigger"));
		new Actions(driver).moveToElement(findElement).perform();
		findElement.sendKeys(Keys.ENTER);
		findElement.click();
		Thread.sleep(2000);	
	}
	
	
	@When("configurazione router")
	public void configurazione_router() throws InterruptedException {
		System.out.println("Step: configurazione router");
		WebElement findElement2 = driver.findElement(By.id("ctaNextStepTrigger"));
		new Actions(driver).moveToElement(findElement2).perform();
		findElement2.sendKeys(Keys.ENTER);
		findElement2.click();
		Thread.sleep(2000);	
	}
	
	@When("configurazione installazione")
	public void configurazione_installazione() throws InterruptedException {
		System.out.println("Step: configurazione installazione");
		WebElement findElement = driver.findElement(By.id("ctaNextStepTrigger"));
		new Actions(driver).moveToElement(findElement).perform();
		findElement.sendKeys(Keys.ENTER);
		findElement.click();
		Thread.sleep(2000);	
	}
	
	
	@When("configurazione ripetitore")
	public void configurazione_ripetitore() throws InterruptedException {
		System.out.println("Step: configurazione ripetitore");
		WebElement findElement = driver.findElement(By.id("ctaNextStepTrigger"));
		new Actions(driver).moveToElement(findElement).perform();
		findElement.sendKeys(Keys.ENTER);
		findElement.click();
		Thread.sleep(2000);	
	}
		
}
