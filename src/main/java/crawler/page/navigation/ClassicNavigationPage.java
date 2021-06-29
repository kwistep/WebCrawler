package crawler.page.navigation;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;


/*This class parses a given page with products. It provides information about link on each
 * product that is shown on given page*/
public class ClassicNavigationPage  implements NavigationPage {

    private Document document;
    private final Set<String> linksOfProducts = new HashSet<>();

    @Override
    public Set<String> extractProductLinks(Document navigationPage) {

        // TODO EXAMPLE
        if (document.select("div.styles__container--1bqmB").first() != null) {
            Element tagWithLinks = document.select("div.styles__container--1bqmB").first();

            if (tagWithLinks.select("div.styles__tile--2s8XN.col-sm-6.col-md-4.col-lg-4") != null) {
                Elements elements = tagWithLinks.select("div.styles__tile--2s8XN.col-sm-6.col-md-4.col-lg-4");

                if (elements.select("a[href]") != null) {
                    Elements links = elements.select("a[href]");
                    for (Element link : links) {
                        linksOfProducts.add("https://www.aboutyou.de" + link.attr("href"));
                    }
                }
            }

        }
        return linksOfProducts;
    }

}
