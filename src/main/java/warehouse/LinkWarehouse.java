package warehouse;

import utils.ConcurrentHashSet;

public class LinkWarehouse {
    private static  LinkWarehouse instance;

    public static LinkWarehouse getInstance() {
        LinkWarehouse localInstance = instance;
        if (localInstance == null) {
            synchronized (LinkWarehouse.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new LinkWarehouse();
                }
            }
        }
        return localInstance;
    }

    private ConcurrentHashSet<String> links = new ConcurrentHashSet<>();

    public ConcurrentHashSet<String> getStatistics() {
        return links;
    }
}
