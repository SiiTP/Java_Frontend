package servlets;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Map;


public class PageGenerator {
    private static final String HTML_DIR = "public_html";
    @NotNull
    private static final Configuration  CFG = new Configuration();

    public static String getPage(String filename) throws IOException{
        Writer stream = new StringWriter();
        try {
            Template template = CFG.getTemplate(HTML_DIR + File.separator + filename);
            template.process(null, stream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return stream.toString();
    }
    public static String getHtmlPage(String filename) throws IOException {
        BufferedReader reader = null;
        try {
            //почитай в документации про reader, Bufferedreader читает строки из файла
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(HTML_DIR + File.separator + filename)));
            StringBuilder builder = new StringBuilder();
            String temp = null;
            //если конец файла, то readLine вернет null в temp
            while ((temp = reader.readLine()) != null) {
                //стрингбилдер лучше всего подходит для собирания строки из кусков
                builder.append(temp);
            }
            return builder.toString();
        }finally {//по завершению надо закрыть поток
            if (reader != null) {
                reader.close();
            }
        }
    }
}
