package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/* Class is developed for getting all available offers by certain request
 */

public class AllOffers {

    /*Collections.synchronizedList() makes the List of offers thread-safe*/
    /*ArrayList for all offers*/
    static List<Offer> listAllOffers = Collections.synchronizedList(new ArrayList<Offer>());


     public List<Offer> getAllOffersByRequest(Document document) throws IOException {


         /*These threads will be used for processing products*/
         Thread thread = null;
         Thread thread1 = null;
         Thread thread2 = null;
         Thread thread3 = null;

         boolean nextPage = true;

         /*Iterator of pages*/
         PagesIterator iterator;

             /*This iterator gets info if there is the next page*/
             iterator = new PagesIterator(document);

             do {
                 /*Gets all links for products that are shown on current page*/
                 List<String> listOfProductLinks = new ArrayList<>(new ParserOfPage(document).getAllLinksByPage());

                 /*Begin parsing each of them and gets all offers from certain product
                 * Each offer has various combination of size and color of a product*/
                 for (String link : listOfProductLinks
                         ) {


                     HTMLParser.numberOfHTTPrequests++;
                        boolean isProcessed = false;

                        /*Four threads process these links*/
                        while(isProcessed == false) {
                            if ((thread == null || !thread.isAlive()) && isProcessed == false) {
                                thread = new Thread(new ParserOfProduct(Jsoup.connect(link).timeout(10 * 1000).get()));
                                thread.start();
                                HTMLParser.numberOfExtractedProducts++;
                                isProcessed = true;
                                continue;
                            }
                            if ((thread1 == null || thread1.isAlive()) && isProcessed == false) {
                                thread1 = new Thread(new ParserOfProduct(Jsoup.connect(link).timeout(10 * 1000).get()));
                                thread1.start();
                                HTMLParser.numberOfExtractedProducts++;
                                isProcessed = true;
                                continue;
                            }
                            if ((thread2 == null || !thread2.isAlive()) && isProcessed == false) {
                                thread2 = new Thread(new ParserOfProduct(Jsoup.connect(link).timeout(10 * 1000).get()));
                                thread2.start();
                                HTMLParser.numberOfExtractedProducts++;
                                isProcessed = true;
                                continue;
                            }
                            if ((thread3 == null || !thread3.isAlive()) && isProcessed == false) {
                                thread3 = new Thread(new ParserOfProduct(Jsoup.connect(link).timeout(10 * 1000).get()));
                                thread3.start();
                                HTMLParser.numberOfExtractedProducts++;
                                isProcessed = true;
                                continue;
                            }

                        }

                 }


                 /*Changes current page on the next one if it exists*/
                 if(iterator.hasNext()) {
                     document = iterator.next();
                 }else {
                     nextPage = false;
                 }
             }while (nextPage);

         try {
             if(thread != null && thread.isAlive())
                thread.join();
             if(thread1 != null && thread1.isAlive())
                thread1.join();
             if(thread2 != null && thread2.isAlive())
                thread2.join();
             if(thread3 != null && thread3.isAlive())
                thread3.join();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }


         /*Due to the reason that other threads can not save any their changes with this variable
         * it was decided to add to the variable the number of all offers
         * because for processing one offer is used one http request*/
         HTMLParser.numberOfHTTPrequests += listAllOffers.size();
         return listAllOffers;

     }



}



