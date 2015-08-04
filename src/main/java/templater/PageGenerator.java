package templater;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class PageGenerator {
    private static final Logger LOGGER = LogManager.getLogger(PageGenerator.class);
    private static final String HTML_DIR = "server_tml";
    private static final Configuration CFG = new Configuration();

    public static String getPage(String filename, Map<String, Object> data) {
        Writer writer = new StringWriter();
        try {
            Template template = CFG.getTemplate(String.format("%s%s%s", HTML_DIR, File.separator, filename));
            template.process(data, writer);
         } catch (IOException | TemplateException e) {
            LOGGER.error("Can not build page", e);
        }

        return writer.toString();
    }

}
