package resource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ivan on 25.10.15.
 */
public final class ResourceFactory {
    private static Map<String,Resource> s_cachedResources;
    private ResourceFactory() {}
    public static Resource getResource(String resourceSource){
        if(s_cachedResources == null){
            s_cachedResources = new HashMap<>();
            new ResourceLoader().loadResources(s_cachedResources);
        }
        return s_cachedResources.get("src/main/resources/"+resourceSource);
    }
}
