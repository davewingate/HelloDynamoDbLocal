import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
@DynamoDBTable(tableName = "Foo")
public class Foo {

    @DynamoDBHashKey(attributeName = "Bar")
    public String bar;

    @DynamoDBRangeKey(attributeName = "Baz")
    public String baz;

    @DynamoDBAttribute(attributeName = "Biz")
    public Integer biz;

    public Foo() {}

    public Foo(String bar, String baz, Integer biz) {
        this.bar = bar;
        this.baz = baz;
        this.biz = biz;
    }
}
