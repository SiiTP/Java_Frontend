package resource;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
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
    private final Logger logger = LogManager.getLogger(ResourceLoader.class);
    public void loadResources(Map<String,Resource> map) {

        ArrayList<JSONObject> configObjects = loadJsonConfig();
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
                String filename = configFile.getString("filename");
                logger.info("resource file " + filename + " loaded");
                map.put(filename, (Resource) object);
            }
        }
    }
    private Queue<File> getConfigFiles(){
        String source = "resources/data";
        logger.info("start scan files from " + source);
        File file = new File(source);
        Queue<File> files = new LinkedList<>();
        File[] filesArray = file.listFiles();
        if(filesArray != null) {
            for (File f : filesArray) {
                String fileName = f.getName();
                if (f.isDirectory()) {
                    File[] additionalFiles = f.listFiles();
                    if(additionalFiles != null) {
                        for (File dirFiles : additionalFiles) {
                            files.offer(dirFiles);
                        }
                    }
                    logger.info("find dirictory " + fileName);
                } else {
                    files.offer(f);
                    logger.info("load " + fileName);
                }
            }
        }else{
            logger.error("no files in " + source);
        }
        logger.info("loaded " + files.size() + " files");
        return files;
    }
    private ArrayList<JSONObject> loadJsonConfig() {

        ArrayList<JSONObject> configList = new ArrayList<>();
        for(File f:getConfigFiles()) {
            StringBuilder builder = new StringBuilder();
            try(BufferedReader reader = new BufferedReader(new FileReader(f))) {
                String app;
                while ((app = reader.readLine()) != null) {
                    builder.append(app);
                }
            } catch (IOException e) {
                logger.error("error whule read from " + f.getName(),e);
                e.printStackTrace();
            }
            String jsonString = builder.toString();
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                jsonObject.put("filename",f.getPath());
                configList.add(jsonObject);
            }catch (JSONException exc){
                logger.error("cant parse json:\n"+jsonString,exc);
            }

        }
        logger.info(configList.size() +" JSON files are parsed");
        return configList;
    }

}
