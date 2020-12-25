package tests;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.PoklonDucanCartPage;
import pages.PoklonDucanHomePage;
import pages.PoklonDucanLoginPage;
import pages.PoklonDucanPricePage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PoklonDucanTest extends BaseTest
{
    @Test
    public void positiveLogIn()
    {
        PoklonDucanLoginPage homepage= new PoklonDucanLoginPage(driver);
        homepage.LogIn("anci.anci432@gmail.com", "anica123");
        wdWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"top\"]/body/div[1]/div/div[1]/div/div[3]/ul/li[3]")));
        WebElement odjavaButton = driver.findElement(By.xpath("//*[@id=\"top\"]/body/div[1]/div/div[1]/div/div[3]/ul/li[3]"));
        Assert.assertTrue( "Log out button is not displayed", odjavaButton.isDisplayed());
        wdWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("hello")));
        WebElement message = driver.findElement(By.className("hello"));
        Assert.assertTrue("Message is not displayed", message.getText().contains("Zdravo, Ana Markovic!"));
    }

    @Test
    public void negativeLogIn ()
    {
        PoklonDucanLoginPage homepage= new PoklonDucanLoginPage(driver);
        homepage.LogIn("anci.anci432@gmail.com", "ujfiejf");
        wdWait.until(ExpectedConditions.presenceOfElementLocated(By.className("messages")));
        WebElement message= driver.findElement(By.className("messages"));
        Assert.assertTrue("Message is not displayed", message.getText().contains("Nevažeća mejl adresa ili lozinka."));
    }

    @Test
    public void searchResultList()
    {
        String term= "PRIVEZAK";
        PoklonDucanHomePage homepage= new PoklonDucanHomePage(driver);
        homepage.searchResult(term);
        wdWait.until(ExpectedConditions.presenceOfElementLocated(By.className("products-grid")));
        WebElement articlesResult = driver.findElement(By.className("products-grid"));
        Assert.assertTrue(articlesResult.getText().contains(term));
    }

    @Test
    public void priceOfArticles ()
    {
        PoklonDucanPricePage pricePage= new PoklonDucanPricePage(driver);
        pricePage.priceOfArticles();

        List<WebElement> articleList= driver.findElements(By.className("regular-price"));
          for (WebElement article: articleList)
      {
          double prices = Double.parseDouble(article.getText().replace("din", "").replace(".", "").replace(",", "."));
          Assert.assertTrue( prices>=0 && prices <=100000);
      }

        Assert.assertTrue("Message is not displayed", pricePage.messagePrice().contains("Cena: 0,00 din. - 100.000,00 din."));

    }

    @Test
    public void emptyCart() throws InterruptedException {
       PoklonDucanLoginPage logInpage= new PoklonDucanLoginPage(driver);
       logInpage.LogIn("anci.anci432@gmail.com", "anica123");

       PoklonDucanHomePage homePage= new PoklonDucanHomePage(driver);
       homePage.searchResult("Ogrlica");
       wdWait.until(ExpectedConditions.presenceOfElementLocated(By.className("products-grid")));

       PoklonDucanCartPage emptyCartpage= new PoklonDucanCartPage(driver);
       emptyCartpage.emptyCart();
       WebElement continueShoppingButton= driver.findElement(By.className("iwd_opc_button"));

       Assert.assertTrue("Message is not displayed", emptyCartpage.emptyCartMessage().contains("Vaša korpa je prazna"));
       Assert.assertTrue("Continue button is nod displayed", continueShoppingButton.isDisplayed());


        Thread.sleep(3000); //pauza zbog poruke na kraju testa
    }

}


