package converter;

import crawler.Offer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/*This class describes the way that list of offers would be saved*/
@XmlRootElement(name = "offers")
@XmlAccessorType(XmlAccessType.NONE)
public class Result {


    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    @XmlElement(name = "offer")
    private List<Offer> offers = new ArrayList<Offer>();

}
