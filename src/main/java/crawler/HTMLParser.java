package crawler;

import converter.ConvertToXML;
import org.jsoup.Jsoup;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/*It is the main class which handle all actions*/

public class HTMLParser {

    /*A variable that counts a number of extracted products during execution of the program*/
    public static int numberOfExtractedProducts = 0;

    /*It was decided to use variable for counting a number of http requests,
    due to to the fact it takes little memory*/
    public static int numberOfHTTPrequests = 0;

    /*For counting memory footprint*/
    private static final long MEGABYTE = 1024L * 1024L;


    public static void main(String[] args) throws JAXBException, IOException {

        long startTime = System.nanoTime();

        final String SEARCH  = args[0];


        /*Object where will be saved all offers*/
        AllOffers allOffers = new AllOffers();

        try {
            /*Searching page with products*/
            org.jsoup.nodes.Document urlOfPage = Jsoup.connect("https://www.aboutyou.de/suche?term="
                    + SEARCH + "&gender=female").timeout(10*1000).get();
            HTMLParser.numberOfHTTPrequests++;

             /*Gets all offers in general and converts them to xml file*/
            ConvertToXML.convert(allOffers.getAllOffersByRequest(urlOfPage));

        }catch (IOException e){
            System.out.println("Connection is not exist!");
        }

        /*Counting data for stdout*/
        executingData(startTime);

    }

    private static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }

    private static void executingData (long startTime){
        long endTime   = System.nanoTime();
        long totalTime =  TimeUnit.NANOSECONDS.toSeconds(endTime - startTime);

        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long memory = runtime.totalMemory() - runtime.freeMemory();

        System.out.println("Number of HTTP requests = " + numberOfHTTPrequests);
        System.out.println("Run-time = " + totalTime + " seconds");
        System.out.println("Memory Footprint: " + memory + " ("+ bytesToMegabytes(memory) +" in megabytes)");
        System.out.println("Extracted products = " + numberOfExtractedProducts);
    }

}
