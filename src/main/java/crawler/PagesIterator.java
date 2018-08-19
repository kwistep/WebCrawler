package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;
import java.util.function.Consumer;


/*This class provides information about existing of the next pages and
* gives a link on it.*/

public class PagesIterator implements Iterator {

    private Document document;

    public PagesIterator(Document document) {
        this.document = document;
    }

    public boolean hasNext() {

        /*Checks if there is a link on the next page*/
        Element element = document.select("li.styles__buttonNext--3YXvj").first();
        if(element != null){
            if(element.select("a[href]").first() != null) {
                Element  link = element.select("a[href]").first();
                return true;
            }
        }

        return false;
    }

    public Document next() {

        /*Gives a link on the next page and, at the same time, changes its position on the next page, too*/

        Element element = document.select("li.styles__buttonNext--3YXvj").first();

        Elements link = element.select("a[href]");

        String linkNextPage = "https://www.aboutyou.de" + link.attr("href");

        try {
            document = Jsoup.connect(linkNextPage).timeout(10*1000).get();
            HTMLParser.numberOfHTTPrequests++;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return document;
    }

    public void remove() {

    }

    public void forEachRemaining(Consumer action) {

    }
}
