package crawler.factory;

import crawler.page.Page;
import crawler.page.navigation.ClassicNavigationPage;
import crawler.page.navigation.NavigationPage;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;

public class NavigationPageFactory implements PageFactory {

    private static final Map<String, Class<?>> layoutToNavPage = new HashMap<>()
    {{
        put("pattern", ClassicNavigationPage.class);
    }};

    public Page getPageProvider(Document document) throws IllegalAccessException, InstantiationException {
        return (NavigationPage) layoutToNavPage.get(getPattern(document)).newInstance();
    }

    public String getPattern(Document document) {
        //TODO implement
        return "pattern";
    }
}
