package crawler.factory;

import crawler.page.Page;
import org.jsoup.nodes.Document;

public interface PageFactory {

    Page getPageProvider(Document document) throws IllegalAccessException, InstantiationException;

    String getPattern(Document document);

}
