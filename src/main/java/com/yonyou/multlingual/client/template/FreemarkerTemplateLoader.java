package com.yonyou.multlingual.client.template;

import com.yonyou.multlingual.client.template.err.TemplateException;
import freemarker.template.*;

import java.io.StringWriter;
import java.util.Map;

public class FreemarkerTemplateLoader  implements TemplateLoader {

    private static String DEFAULT_ENCODING = "utf-8";

    private Configuration tplConfig;

    public FreemarkerTemplateLoader()
    {
        tplConfig = new Configuration();
        tplConfig.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        tplConfig.setObjectWrapper(ObjectWrapper.DEFAULT_WRAPPER);
        tplConfig.setTemplateUpdateDelay(0);
        tplConfig.setDefaultEncoding(DEFAULT_ENCODING);
       // tplConfig.setLocale(new java.util.Locale("zh_CN"));
        tplConfig.setNumberFormat("0.##");
        tplConfig.setClassicCompatible(true);
    }




    @Override
    public String render(Map<String, Object> data, String template) {
        try
        {
            tplConfig.setTemplateLoader(new ExTemplateLoader(template));
            StringWriter writer = new StringWriter();
            freemarker.template.Template tl = tplConfig.getTemplate(template);//,  tplConfig.getLocale());
            SimpleHash root = new SimpleHash(new DefaultObjectWrapper());
            if (data!=null)
            {
                for (String key : data.keySet())
                {
                    root.put(key, data.get(key));
                }
            }
            tl.process(root, writer);
            return writer.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new TemplateException(e);
        }
    }


}
