package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/*This class parses each product into offers, getting certain parameters
* Each offer can differ from product. It has unique set of size and color*/

public class ParserOfProduct implements Runnable{

    private String name;
    private String brand;
    private String color;
    private List<String> sizes = new ArrayList<String>();
    private String price;
    private String initialPrice;
    private String descriptionString="";
    private List<String> linksForEachColor = new ArrayList<String>();
    private String articleID;
    private String shippingCost;
    private List<Offer> offerList = new ArrayList<Offer>();



    private Document document;

    public ParserOfProduct(Document document) {
        this.document = document;
    }


    @Override
    public void run() {

        /*gets links on all pages of given product, considering color*/
        parseProductByColor();

        /*parses each parameter of an offer
        * and returns ArrayList of all offers for certain product*/
        Document linkOnPageByColor;
        for (String link : linksForEachColor
                ) {
            try {
                /*this link indicate on a certain color of a certain product*/
                linkOnPageByColor = Jsoup.connect(link).timeout(10*1000).get();

                getNameAndBrand(linkOnPageByColor);
                getColors(linkOnPageByColor);
                getSizes(linkOnPageByColor);
                getPrice(linkOnPageByColor);
                getInitialPrice(linkOnPageByColor);
                getDescription(linkOnPageByColor);
                getShippingCost(linkOnPageByColor);
                offerList.addAll(getOffersByProduct());

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        /*In the on of processing, a thread put all offers of one product to the list of all offers of request*/
        AllOffers.listAllOffers.addAll(offerList);
    }

    public List<String> parseProductByColor(){

         /*Gets links on all pages of given product, considering color*/
        if(document.select("div.styles__thumbnailWrapper--3uDnG") != null) {
            Elements elements = document.select("div.styles__thumbnailWrapper--3uDnG");
            if (elements.select("a[href]") != null) {
                Elements links = elements.select("a[href]");

                for (Element link : links) {
                    linksForEachColor.add("https://www.aboutyou.de" + link.attr("href"));
                }
            }
        }
        return linksForEachColor;

    }

    private void getNameAndBrand(Document document){

        if(document.select("div.col-sm.styles__titleContainer--33zw2").first() != null) {

            Element productInfo = document.select("div.col-sm.styles__titleContainer--33zw2").first();
            if(productInfo.select("h1").first() != null) {
                Element fullName = productInfo.select("h1").first();
                if(fullName != null){
                    String[] tokensForFullName = fullName.text().split("\\|");

                    name = tokensForFullName[0].trim();
                    brand = tokensForFullName[1].trim();

                }
            }
        }

    }

    private void getColors (Document document){
        if(document.select("span.styles__title--UFKYd").first() != null) {
            Element colorInfo = document.select("span.styles__title--UFKYd").first();
            if(colorInfo != null){
                color = colorInfo.text();

            }
        }
    }

    private void getPrice (Document document){
        if(document.select("div.productPrices.priceStyles__normal--3aCVn").first() != null) {
            Element priceInfo = document.select("div.productPrices." +
                    "priceStyles__normal--3aCVn").first();
            if(priceInfo != null){
                price = priceInfo.text();

            }
        }
    }

    public void getInitialPrice(Document document){

        if(document.select("div.priceStyles__strike--PSBGK").first() != null) {
            Element initialPriceInfo = document.select("div.priceStyles__strike--PSBGK").first();
            if(initialPriceInfo != null){
                initialPrice = initialPriceInfo.text();
            }
        }
    }

    public void getDescription (Document document) {
        List<String> description = new ArrayList<String>();

        /*As for creating of all offers is used only object for saving memory,
        * this string should be clear before parsing the next offer*/
        descriptionString = "";

        if(document.select("div.styles__contentContainer--3qTt-") != null) {
            Elements designAndExtrasfullInfo = document.select("div.styles__contentContainer--3qTt-");

            Elements firstPart = designAndExtrasfullInfo.select("li");
            Elements secondPart = designAndExtrasfullInfo.select("td");

            if (firstPart != null) {
                for (Element string : firstPart) {
                    description.add(string.text());
                }

                if (description.size() > 0) {
                    if (description.get(description.size() - 1).matches("(.*)Artikel-Nr:(.*)"))
                        articleID = description.get(description.size() - 1);
                    description.remove(description.size() - 1);
                }

            }

            if (secondPart != null) {
                for (int i = 0; i + 1 < secondPart.size(); i += 2) {

                    String key = secondPart.get(i).text();
                    String value = secondPart.get(i + 1).text();
                    description.add(key + " " + value);
                }
            }

            for (String s : description
                    ) {
            /*Tabulation of description for xml*/
                descriptionString += s + "\n\t\t";
            }
        }

    }

    private void getShippingCost(Document document){

        if(document.select("span.styles__label--1cfc7").first() != null) {
            Element shippingCostInfo = document.select("span.styles__label--1cfc7").not("" +
                    "span.styles__labelRed-2Qbgz").first();
            if(shippingCostInfo != null){
                shippingCost = shippingCostInfo.text().trim();

            }
        }

    }

    private void getSizes(Document document){

         /*As for creating of all offers is used only object for saving memory,
        * this ArrayList should be clear before parsing the next offer*/
        sizes.clear();

        if(document.select("div.styles__row--sWR75").not("div.styles__soldOut--1Iump") != null) {
            Elements sizesInfo = document.select("div.styles__row--sWR75").not("div.styles__soldOut--1Iump");

            if (sizesInfo != null) {
                for (Element size : sizesInfo
                        ) {
                    sizes.add(size.text());
                }
            }
        }

        if(sizes.size() == 0){
            if(document.select("div.styles__oneSize--1WC_O") != null){
                Element sizeInfo = document.select("div.styles__oneSize--1WC_O").first();
                if(sizeInfo != null){
                    sizes.add(sizeInfo.text());
                }
            }
        }

    }

    private List<Offer> getOffersByProduct(){

        /*All given offers of one product are packed into ArrayList*/
        List<Offer> offerByProduct = new ArrayList<Offer>();

        /*This cycle exists for dividing offers by size*/
        for (String size: sizes
             ) {
            Offer offer = new Offer();

            if(name != null)
                offer.setName(name);
            if(brand != null)
                offer.setBrand(brand);
            if(color != null)
                offer.setColor(color);
            if(size != null)
                offer.setSize(size);
            if(price != null)
                offer.setPrice(price);
            if(initialPrice != null)
                offer.setInitialPrice(initialPrice);
            if(descriptionString != null)
                offer.setDescription(descriptionString);
            if(articleID != null)
                offer.setArticleID(articleID);
            if(shippingCost != null)
                offer.setShippingCost(shippingCost);

            offerByProduct.add(offer);
        }
        return offerByProduct;

    }

}
