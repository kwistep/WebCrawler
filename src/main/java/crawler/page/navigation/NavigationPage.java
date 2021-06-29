package crawler.page.navigation;

import crawler.page.Page;
import org.jsoup.nodes.Document;

import java.util.Set;

public interface NavigationPage extends Page {

    Set<String> extractProductLinks(Document navigationPage);
}
