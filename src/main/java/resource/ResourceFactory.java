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
        ResourceLoader loader = new ResourceLoader();
        if(s_cachedResources == null){
            s_cachedResources = loader.loadResources(resourceSource);
        }
        Resource resource = s_cachedResources.get(resourceSource);
        if(resource == null){
            s_cachedResources.putAll(loader.loadResources(resourceSource));
            resource = s_cachedResources.get(resourceSource);
        }
        return resource;
    }
}
