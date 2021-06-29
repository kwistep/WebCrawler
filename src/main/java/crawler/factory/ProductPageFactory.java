package crawler.factory;

import crawler.page.Page;
import crawler.page.product.ClassicProductPage;
import crawler.page.product.ProductPage;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;

public class ProductPageFactory implements PageFactory {
    private static final Map<String, Class<?>> layoutToProductPage = new HashMap<>()
    {{
        put("pattern", ClassicProductPage.class);
    }};

    @Override
    public Page getPageProvider(Document document) throws IllegalAccessException, InstantiationException {
        return (ProductPage) layoutToProductPage.get(getPattern(document)).newInstance();
    }

    @Override
    public String getPattern(Document document) {
        //TODO implement
        return "pattern";
    }
}
