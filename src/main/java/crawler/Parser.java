package crawler;

import crawler.factory.NavigationPageFactory;
import crawler.factory.ProductPageFactory;
import crawler.page.navigation.NavigationPage;
import crawler.page.product.ProductPage;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;

/*It is the main class which handle all actions*/

public class Parser {

    /*A variable that counts a number of extracted products during execution of the program*/
    public static int numberOfExtractedProducts = 0;

    /*It was decided to use variable for counting a number of http requests,
    due to to the fact it takes little memory*/
    public static int numberOfHTTPrequests = 0;

    /*For counting memory footprint*/
    private static final long MEGABYTE = 1024L * 1024L;
    private static final NavigationPageFactory navigationPageFactory = new NavigationPageFactory();
    private static final ProductPageFactory productPageFactory = new ProductPageFactory();



    public static void main(String[] args) {

        LocalDateTime started = LocalDateTime.now();
        System.out.printf("Started at: %s%n", started);

        //TODO add some start urls
        List<String> navigationUrls = Collections.emptyList();

        List<String> extractedLinks = navigationUrls.stream()
                .map(url -> {
                    List<String> links = Collections.emptyList();
                    try {
                        Document document = connect(url, Connection.Method.GET, emptyMap(), emptyMap());
                        NavigationPage navigationPage = (NavigationPage) navigationPageFactory.getPageProvider(document);
                        links.addAll(navigationPage.extractProductLinks(document));
                    } catch (IOException | IllegalAccessException | InstantiationException e) {
                        e.printStackTrace();
                    }
                    return links;
                }).collect(Collectors.toList()).stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());


        List<ResultEntity> results = extractedLinks.stream()
                .map(productUrl -> {
                    Set<ResultEntity> resultEntities = new HashSet<>();
                    try {
                        Document document = connect(productUrl, Connection.Method.GET, emptyMap(), emptyMap());
                        ProductPage productPage = (ProductPage) productPageFactory.getPageProvider(document);
                        resultEntities.addAll(productPage.retrieveResultEntities(document));
                    } catch (IOException | IllegalAccessException | InstantiationException e) {
                        e.printStackTrace();
                    }
                    return resultEntities;
                }).collect(Collectors.toList()).stream()
                .flatMap(Set::stream)
                .collect(Collectors.toList());


        /*
            TODO write the results down ???
         */

        LocalDateTime finished = LocalDateTime.now();
        System.out.printf("Finished at: %s%n", finished);
    }

    private static Document connect(String url, Connection.Method httpMethod, Map<String, String> cookies,
                                    Map<String, String> headers) throws IOException {
        return Jsoup.connect(url)
                .method(httpMethod)
                .cookies(cookies)
                .headers(headers)
                .timeout(10 * 1000)
                .get();
    }

}
