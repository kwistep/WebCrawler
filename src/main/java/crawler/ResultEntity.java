package crawler;

import lombok.Builder;
import lombok.Data;
import org.beanio.annotation.Field;
import org.beanio.annotation.Record;

import static java.util.UUID.randomUUID;

@Builder
@Data
@Record
public class ResultEntity {

    @Field(at = 0, rid = true)
    @Builder.Default
    private String id = String.valueOf(randomUUID());

    @Field(at = 1)
    private String businessName;

    @Field(at = 2)
    private String phone;

    @Field(at = 3)
    private String address;

    @Field(at = 4)
    private String url;

    @Field(at = 5)
    private String menuLink;
    //TODO enhance with better data structure

    @Field(at = 6)
    private String hours;

    @Field(at = 7)
    private String cuisineType;
}
