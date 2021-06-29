package crawler.page.product;

import crawler.ResultEntity;
import crawler.page.Page;
import org.jsoup.nodes.Document;

import java.util.Set;

public interface ProductPage extends Page {

    Set<ResultEntity> retrieveResultEntities(Document document);
    String extractBusinessName(Document document);
    String extractPhone(Document document);
    String extractAddress(Document document);
    String extractUrl(Document document);
    String extractMenuLink(Document document);
    String extractHours(Document document);
    String extractCuisineType(Document document);
}
