package br.com.yanktest.utils;


import com.google.gson.JsonArray;

import com.google.gson.JsonObject;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

@RequiredArgsConstructor
public class ExcelUtils {

    XSSFWorkbook xssfWorkbook;


    public JsonArray lerExcel(File file, String identificadorCabecalho, int workbook){

        JsonArray jsonExcel = lerDadosExcel(abreArquivoExcell(file, identificadorCabecalho, workbook));

        if(jsonExcel.size() > 0)
            return jsonExcel;
        else
            return new JsonArray();
    }

    public JsonArray lerDadosExcel(Sheet planilhaExcel){

        FormulaEvaluator evaluator = planilhaExcel.getWorkbook().getCreationHelper().createFormulaEvaluator();

        JsonArray dadosExcel = new JsonArray();

        for(Row linha : planilhaExcel){
            if(linha.getRowNum() != 0){
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("row", linha.getRowNum());

                for(Cell cell: linha){
                    if(planilhaExcel.getRow(0).getCell(cell.getColumnIndex()) != null){
                        String cabecalho = planilhaExcel.getRow(0).getCell(cell.getColumnIndex()).toString().toUpperCase();

                        String valorCelula = retornaValorCelula(evaluator, cell);

                        if(!valorCelula.isEmpty())
                            jsonObject.addProperty(cabecalho, valorCelula);
                    }
                }
                dadosExcel.add(jsonObject);
            }
        }

        return dadosExcel;
    }

    public String retornaValorCelula(FormulaEvaluator evaluator, Cell cell){
        String valorCelula = "";

        CellValue cellValue = evaluator.evaluate(cell);
        switch (cellValue.getCellType()){
            case BOOLEAN:
                valorCelula = String.valueOf(cell.getBooleanCellValue());
                break;
            case STRING:
                valorCelula = cell.getStringCellValue();
                break;
            case NUMERIC:
                Object o = cell;
                try{
                    valorCelula = (new BigDecimal(o.toString())).toBigIntegerExact().toString();
                }catch (Exception e){
                    valorCelula = String.valueOf(cell.getNumericCellValue());
                }
                break;

            default:
                break;
        }

        return valorCelula;
    }

    public Sheet abreArquivoExcell(File excel, String identificadorCabecalho, int workbook){
        Sheet planihaExcel = null;

        if(!excel.isDirectory()){
            try{
                FileInputStream arquivo = new FileInputStream(excel);
                xssfWorkbook = new XSSFWorkbook(arquivo);
                planihaExcel = xssfWorkbook.getSheetAt(workbook);

                int linhaCabecalho;
                linhaCabecalho = encontraLinhaCabecalho(planihaExcel, identificadorCabecalho);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return  planihaExcel;
    }

    private int encontraLinhaCabecalho(Sheet planilhaExcel, String identificadorCabecalho){
        int linhaCabecalho = -1;

        for(int i = 0; i < planilhaExcel.getLastRowNum(); i++){
            Row linha = planilhaExcel.getRow(i);
            if(Objects.nonNull(linha)){
                for(int j = 0; j < linha.getLastCellNum(); i++){
                    Cell coluna = linha.getCell(j);
                    if(Objects.nonNull(coluna) && !coluna.toString().isEmpty()){
                        String cabecalho = coluna.toString().toUpperCase();

                        if(cabecalho.equals(identificadorCabecalho.toUpperCase())){
                            linhaCabecalho = i;
                            break;
                        }
                    }
                }
            }

            if(linhaCabecalho != -1){
                break;
            }
        }

        return linhaCabecalho;
    }
}
