package converter;

import crawler.Offer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;


/*This class uses JAXB lib for converting the result to xml file*/
public class ConvertToXML {


    public static void convert(List<Offer> offers) throws JAXBException {

        /*Class Result is described by xml annotation for correct saving*/
        Result result = new Result();
        result.setOffers(offers);

        JAXBContext jc = JAXBContext.newInstance(Result.class);

        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        /*File will be saved in the same place where is the .jar*/
        m.marshal(result, new File("Offers.xml"));

    }

}
