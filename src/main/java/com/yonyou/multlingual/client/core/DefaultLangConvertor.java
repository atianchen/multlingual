package com.yonyou.multlingual.client.core;

import com.yonyou.multlingual.client.core.err.ConvertException;
import com.yonyou.multlingual.client.template.FreemarkerTemplateLoader;
import com.yonyou.multlingual.client.template.TemplateLoader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class DefaultLangConvertor implements  LangConvertor{

    private static final Logger logger = LoggerFactory.getLogger(DefaultLangConvertor.class);

    private TemplateLoader templateLoader;

    @Override
    public void convert(ConvertConfig cfg,String input, String tpl, String output) {
        File outputDirectory = new File(output);
        if (!outputDirectory.exists() || !outputDirectory.isDirectory())
            throw new ConvertException("output path is not directory or not exist");
        FileInputStream is = null;
        try {
            Map<LangCell,Map<String,String>> depot = new HashMap<LangCell, Map<String,String>>();
            is = new FileInputStream(input);
            POIFSFileSystem fs = new POIFSFileSystem(is);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            int rowNum = sheet.getLastRowNum();
            HSSFRow row = sheet.getRow(0);
            for (int i=1;i<row.getLastCellNum();i++)
            {
                String lang = getStringCellValue(row.getCell(i));
                if (lang!=null && StringUtils.isNotBlank(lang))
                {
                    depot.put(new LangCell(lang,i),new HashMap<String,String>());
                }
                else
                {
                    break;
                }
            }
            String val,indexName = null;
            for (int i = 1; i <= rowNum; i++) {
                row = sheet.getRow(i);
                indexName = this.getStringCellValue(row.getCell(0));
                if (StringUtils.isNotBlank(indexName)) {
                    for (LangCell lang : depot.keySet()) {
                        val = this.getStringCellValue(row.getCell(lang.getSeq()));
                        depot.get(lang).put(indexName, (val != null && StringUtils.isNotBlank(val)) ? val : StringUtils.EMPTY);
                    }
                }
                else
                {
                    break;
                }
            }

            Map<String,Object> data  = new HashMap<String,Object>();
            for (LangCell lang : depot.keySet())
            {
                data.put("data",depot.get(lang));
                outputJsFile(cfg,this.getTemplateLoader().render(data,tpl),output,lang.getCode());
            }
            logger.info("Convert Successful");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error(e.getMessage());
            logger.info("Convert Error:"+e.getMessage());
        }
        finally
        {
            try
            {  is.close();  } catch (Exception ex) { ex.printStackTrace(); }
        }
    }

    private void outputJsFile(ConvertConfig cfg,String content,String path,String fileName) throws IOException
    {
        File file = new File(path,cfg.getPrefix()+fileName+"."+cfg.getSuffix());
        if (file.exists())
            file.delete();
        FileUtils.writeStringToFile(file,content,"utf-8");
    }

    public TemplateLoader getTemplateLoader()
    {
        if (templateLoader==null)
            templateLoader = new FreemarkerTemplateLoader();
        return templateLoader;
    }

    private String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        if (cell!=null) {
            switch (cell.getCellType()) {
                case STRING:
                    strCell = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    strCell = String.valueOf(cell.getNumericCellValue());
                    break;
                case BOOLEAN:
                    strCell = String.valueOf(cell.getBooleanCellValue());
                    break;
                case BLANK:
                    strCell = "";
                    break;
                default:
                    strCell = "";
                    break;
            }
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }
}
