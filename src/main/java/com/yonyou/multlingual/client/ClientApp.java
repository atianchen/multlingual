package com.yonyou.multlingual.client;

import com.yonyou.multlingual.client.core.ConvertConfig;
import com.yonyou.multlingual.client.core.LangConvertor;
import com.yonyou.multlingual.client.template.ExTemplateLoader;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

import static org.fusesource.jansi.Ansi.Color.RED;
import static org.fusesource.jansi.Ansi.ansi;

@SpringBootApplication
public class ClientApp implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ClientApp.class);
    @Autowired
    private LangConvertor convertor;


    public static void main(String[] args) {
            SpringApplication app = new SpringApplication(ClientApp.class);
            app.setWebApplicationType(WebApplicationType.NONE);
            app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        File f = new File("");
        String currentPath = f.getCanonicalPath();
        Options options = new Options();
        options.addOption("i", true, "input excel file");
        options.addOption("o", true, "spec output directory");
        options.addOption("t", true, "spec template file");
        options.addOption("suffix", true, "spec output file suffiex");
        options.addOption("prefix", true, "spec output file prefix");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse( options, args);

        String input = cmd.getOptionValue("i");
        String tpl = cmd.getOptionValue("t");
        String output = cmd.getOptionValue("o");
        if (StringUtils.isBlank(input))
        {
            logger.info("Please Spec Input XLS File" );
            System.exit(0);
        }
        if (StringUtils.isBlank(output))
        {
            logger.info("Please Spec Ouput Directory for js");
            System.exit(0);
        }
        File inputFile =  ((input.indexOf("/")>=0 || input.indexOf("\\")>=0))?new File(input): new File(currentPath,input);
        if (!inputFile.exists())
        {
            logger.info("Input File Not Exists");
            System.exit(0);
        }
        File tplFile = null;
        if (StringUtils.isNotBlank(tpl))
        {
            tplFile = ((tpl.indexOf("/")>=0 || tpl.indexOf("\\")>=0))?new File(tpl): new File(currentPath,tpl);
            if (!tplFile.exists())
            {
                logger.info("Template File Not Exists");
                System.exit(0);
            }
        }
        File outputPath = ((output.indexOf("/")>=0 || output.indexOf("\\")>=0))?new File(output): new File(currentPath+"\\"+output);
        if (!outputPath.exists() || !outputPath.isDirectory())
        {
            logger.info("Output Path Not Exists or Not Directory:"+outputPath.getPath());
            System.exit(0);
        }
        ConvertConfig config = new ConvertConfig();
        String suffix = cmd.getOptionValue("suffix");
        String prefix = cmd.getOptionValue("prefix");
        if (StringUtils.isNotBlank(suffix))
            config.setSuffix(suffix);
        if (StringUtils.isNotBlank(prefix))
            config.setPrefix(prefix);
        convertor.convert(config,inputFile.getPath(),(tplFile!=null)?tplFile.getPath():"default.ftl",outputPath.getPath());
    }
}
