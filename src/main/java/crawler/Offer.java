package crawler;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/*This class describes properties of an offer*/
/*It is described by xml annotation for correct saving*/

@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.NONE)
public class Offer {

    @XmlElement(name = "name")
    private String name = null;
    @XmlElement(name = "brand")
    private String brand = null;
    @XmlElement(name = "color")
    private String color = null;
    @XmlElement(name = "size")
    private String size = null;
    @XmlElement(name = "price")
    private String price = null;
    @XmlElement(name = "initial_price")
    private String initialPrice = null;
    @XmlElement(name = "description")
    private String description = null;
    @XmlElement(name = "article_id")
    private String articleID = null;
    @XmlElement(name = "shipping_cost")
    private String shippingCost = null;


    public void setName(String name) {
        this.name = name;
    }


    public void setBrand(String brand) {
        this.brand = brand;
    }


    public void setColor(String color) {
        this.color = color;
    }


    public void setSize(String size) {
        this.size = size;
    }


    public void setPrice(String price) {
        this.price = price;
    }


    public void setInitialPrice(String initialPrice) {
        this.initialPrice = initialPrice;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public void setArticleID(String articleID) {
        this.articleID = articleID;
    }


    public void setShippingCost(String shippingCost) {
        this.shippingCost = shippingCost;
    }
}
