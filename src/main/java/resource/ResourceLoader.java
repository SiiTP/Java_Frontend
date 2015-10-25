package resource;


import jdk.nashorn.internal.ir.WhileNode;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

/**
 * Created by ivan on 25.10.15.
 */
public class ResourceLoader {
    Map<String,Resource> cachedResources;

    public ResourceLoader() {
        cachedResources = new HashMap<>();
    }
    public void loadResources(){

    }
    public static void main(String[] args) throws IOException, FileNotFoundException {
        File file = new File("resources/data/");
        List<File> files = Arrays.asList(file.listFiles());
        for(File f:files) {
            if(f.isDirectory()){
                File[] newFiles = f.listFiles();
                if(newFiles != null) {
                    Collections.addAll(files,newFiles);
                }
            }else {
                BufferedReader reader = new BufferedReader(new FileReader(f));
                StringBuilder builder = new StringBuilder();
                String app;
                while ((app = reader.readLine()) != null) {
                    builder.append(app);
                }
                JSONObject jsonObject = new JSONObject(builder.toString());


                Iterator<String> s = jsonObject.keys();
                while (s.hasNext()) {
                    String key = s.next();
                    JSONObject o = jsonObject.optJSONObject(key);
                    System.out.println(key + ' ' + jsonObject.opt(key));
                }
            }
        }
    }
}
