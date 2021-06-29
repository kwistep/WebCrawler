package crawler.page.product;

import crawler.ResultEntity;
import org.jsoup.nodes.Document;

import java.util.Collections;
import java.util.Set;


/*This class parses each product into offers, getting certain parameters
 * Each offer can differ from product. It has unique set of size and color*/

public class ClassicProductPage implements ProductPage {

    private Document document;

    public ClassicProductPage(Document document) {
        this.document = document;
    }

    @Override
    public Set<ResultEntity> retrieveResultEntities(Document document) {

        Set<ResultEntity> resultEntities = Collections.emptySet();

        //TODO please extract what's possible

        return resultEntities;
    }

    @Override
    public String extractBusinessName(Document document) {
        return null;
    }

    @Override
    public String extractPhone(Document document) {
        return null;
    }

    @Override
    public String extractAddress(Document document) {
        return null;
    }

    @Override
    public String extractUrl(Document document) {
        return null;
    }

    @Override
    public String extractMenuLink(Document document) {
        return null;
    }

    @Override
    public String extractHours(Document document) {
        return null;
    }

    @Override
    public String extractCuisineType(Document document) {
        return null;
    }


}
