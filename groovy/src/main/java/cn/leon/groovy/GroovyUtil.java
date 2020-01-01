package cn.leon.groovy;

import groovy.text.Template;
import groovy.text.markup.MarkupTemplateEngine;
import groovy.text.markup.TemplateConfiguration;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * @ClassName GroovyUtil
 * @Description
 * @Author Jevon
 * @Date2019/12/31 17:24
 **/
@UtilityClass
public class GroovyUtil {

    private MarkupTemplateEngine engine;

    static {
        TemplateConfiguration configuration = new TemplateConfiguration();
        configuration.setAutoEscape(true);
        configuration.setAutoIndent(true);
        configuration.setAutoIndentString("    ");
        configuration.setAutoNewLine(true);
        configuration.setUseDoubleQuotes(true);
        configuration.setDeclarationEncoding("UTF-8");
        engine = new MarkupTemplateEngine(configuration);
    }

    public String render(Resource resource, Map<String, Object> map) throws IOException, ClassNotFoundException {
        Template template = engine.createTemplate(resource.getURL());
        StringWriter writer = new StringWriter();
        template.make(map).writeTo(writer);
        return writer.toString();
    }
}
