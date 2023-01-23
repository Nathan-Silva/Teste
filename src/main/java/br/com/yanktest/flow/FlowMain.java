package br.com.yanktest.flow;

import br.com.yanktest.model.dto.ArquivoDTO;
import br.com.yanktest.utils.BrowserUtils;
import br.com.yanktest.utils.ExcelUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.apache.commons.exec.util.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FlowMain {

    private  ExcelUtils excelUtils;

    private BrowserUtils browserUtils;

    private final Gson gson = new Gson();

    public FlowMain(){

        this.excelUtils = new ExcelUtils();



        File file = new File("C:\\nick.xlsx");

        JsonArray planilha = excelUtils.lerExcel(file, "qtd_nicks", 0);

        List<ArquivoDTO> arquivoDTOList = Arrays.asList(gson.fromJson(planilha, ArquivoDTO[].class));


    }

}
