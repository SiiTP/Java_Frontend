package resource;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static reflection.ObjectConstruct.constructFromName;
import static reflection.ObjectConstruct.setFields;
/**
 * Created by ivan on 25.10.15.
 */
public class ResourceLoader {

    public void loadResources(Map<String,Resource> map) {

        ArrayList<JSONObject> configObjects = loadJsonConfig("data");
        if(configObjects != null) {
            for (JSONObject configFile : configObjects) {
                Object object = constructFromName(configFile.getString("class"));
                if (object != null) {
                    Iterator<String> s = configFile.keys();
                    while (s.hasNext()) {
                        String key = s.next();
                        if (!key.equals("class") && !key.equals("filename")) {
                            try {
                                setFields(object, key, configFile.get(key));
                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                map.put(configFile.getString("filename"), (Resource) object);
            }
        }

    }
    private Queue<File> getConfigFiles(String source){
        File file = new File("resources/"+source);
        Queue<File> files = new LinkedList<>();
        File[] filesArray = file.listFiles();
        if(filesArray != null) {
            for (File f : filesArray) {
                if (f.isDirectory()) {
                    File[] additionalFiles = f.listFiles();
                    if(additionalFiles != null) {
                        for (File dirFiles : additionalFiles) {
                            files.offer(dirFiles);
                        }
                    }
                } else {
                    files.offer(f);
                }
            }
        }
        return files;
    }
    private ArrayList<JSONObject> loadJsonConfig(String source) {
        ArrayList<JSONObject> configList = new ArrayList<>();
        for(File f:getConfigFiles(source)) {
            StringBuilder builder = new StringBuilder();
            try(BufferedReader reader = new BufferedReader(new FileReader(f))) {
                String app;
                while ((app = reader.readLine()) != null) {
                    builder.append(app);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject jsonObject = new JSONObject(builder.toString());
            jsonObject.put("filename",f.getPath());
            configList.add(jsonObject);
        }
        return configList;
    }
}
