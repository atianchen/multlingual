package com.yonyou.multlingual.client.template;

import java.util.Map;

public interface TemplateLoader {

    String render(Map<String,Object> data, String template);
}
