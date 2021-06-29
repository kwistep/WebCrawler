package crawler.writer;

import crawler.ResultEntity;
import org.apache.commons.io.FileUtils;
import org.beanio.BeanWriter;
import org.beanio.StreamFactory;
import org.beanio.builder.StreamBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class CsvWriter {

    public static final String LOVE_YOU = "love_you";
    private final StreamFactory factory;


    CsvWriter()
    {
        factory = StreamFactory.newInstance();
        factory.define(new StreamBuilder(LOVE_YOU)
                .format("csv")
                .addRecord(ResultEntity.class));
    }

    public void writeCsv(Set<ResultEntity> results)
    {

        File file = new File("src/main/resources/results.csv");
        try {
            FileUtils.touch(file);
            BeanWriter writer = factory.createWriter(LOVE_YOU, file);
            results.forEach(writer::write);
            writer.flush();
        }
        catch (IOException e) {
            System.out.printf("Something went wrong :(, message: %s%n",e.getMessage());
        }

    }


}
