package com.yonyou.multlingual.client.template;

import freemarker.cache.FileTemplateLoader;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

public class ExTemplateLoader implements freemarker.cache.TemplateLoader{

    private static final Logger logger = LoggerFactory.getLogger(ExTemplateLoader.class);

    private UrlTemplateLoader urlTemplateLoader;

    private FileTemplateLoader fileTemplateLoader;

    public ExTemplateLoader(String path)
    {
        try {
            urlTemplateLoader = new UrlTemplateLoader("tpl/");
            fileTemplateLoader = new FileTemplateLoader(new File(FilenameUtils.getFullPath(path)));
        }
        catch (Exception e)
        {
            logger.warn(e.getMessage());
        }
    }

    @Override
    public Object findTemplateSource(String name) throws IOException {
       Object rs = null;
        try
        { rs = urlTemplateLoader.findTemplateSource(name);}
        catch (Exception e)
        { logger.warn(e.getMessage());}
        try
        { if (rs==null)rs = fileTemplateLoader.findTemplateSource(FilenameUtils.getName(name));}
        catch (Exception e)
        { logger.warn(e.getMessage());}
        return rs;
    }

    @Override
    public long getLastModified(Object templateSource) {
        long rs = 0;
        try
        { rs = urlTemplateLoader.getLastModified(templateSource);}
        catch (Exception e)
        { logger.warn(e.getMessage());}
        try
        { if (rs==0)rs = fileTemplateLoader.getLastModified(templateSource);}
        catch (Exception e)
        { logger.warn(e.getMessage());}
        return rs;
    }

    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException {
        Reader rs = null;
        try
        { rs = urlTemplateLoader.getReader(templateSource,encoding);}
        catch (Exception e)
        { logger.warn(e.getMessage());}
        try
        { if (rs==null)rs = fileTemplateLoader.getReader(templateSource,encoding);}
        catch (Exception e)
        { logger.warn(e.getMessage());}
        return rs;
    }

    @Override
    public void closeTemplateSource(Object templateSource) throws IOException {
        try
        { urlTemplateLoader.closeTemplateSource(templateSource);}
        catch (Exception e)
        {logger.warn(e.getMessage());}
        try
        { fileTemplateLoader.closeTemplateSource(templateSource);}
        catch (Exception e)
        {logger.warn(e.getMessage());}
    }
}
