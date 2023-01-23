package br.com.yanktest.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Arrays;
import java.util.List;

public class BrowserUtils {


    private final String URL = "https://www.4devs.com.br/gerador_de_nicks";
    WebDriver browser;

    public List<String> obterNickBrowser(String metodo, int quantidadeNicks, int limiteLetras){

        browser = new ChromeDriver();
        browser.navigate().to(URL);

        browser.findElement(By.id("method")).sendKeys(metodo);
        browser.findElement(By.id("quantity")).clear();
        browser.findElement(By.id("quantity")).sendKeys(String.valueOf(quantidadeNicks));
        browser.findElement(By.id("limit")).sendKeys(String.valueOf(limiteLetras));

        WebElement botao = browser.findElement(By.id("bt_gerar_nick"));
        JavascriptExecutor js = (JavascriptExecutor)browser;
        js.executeScript("arguments[0].click()", botao);


        return Arrays.asList(browser.findElement(By.id("nicks")).getText());
    }

    public void fecharBrowser(){
        browser.quit();
    }

}
