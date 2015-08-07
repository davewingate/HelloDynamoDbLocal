import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FooITCase {

    private AmazonDynamoDB dbClient;
    private DynamoDBMapper dynamoDBMapper;

    @Before
    public void setUp() throws Exception {
        DynamoDBMapperConfig dbConfig = new DynamoDBMapperConfig.Builder().build();

        dbClient = new AmazonDynamoDBClient(new BasicAWSCredentials("key", "secret"));
        dbClient.setEndpoint(String.format("http://localhost:%s", System.getProperty("dynamodb.port")));

        dynamoDBMapper = new DynamoDBMapper(dbClient, dbConfig);
    }

    @Test
    public void testListTables() {
        ListTablesResult tables = dbClient.listTables();
        assertEquals(1, tables.getTableNames().size());
        assertEquals("Foo", tables.getTableNames().get(0));
    }

    @Test
    public void testDbMapper() {
        Collection<Foo> initialFoos = dynamoDBMapper.scan(Foo.class, new DynamoDBScanExpression());
        assertTrue(initialFoos.isEmpty());

        Foo newFoo = Foo.builder().bar("bar").baz("baz").biz("biz".hashCode()).build();
        dynamoDBMapper.save(newFoo);

        Collection<Foo> newFoos = dynamoDBMapper.scan(Foo.class, new DynamoDBScanExpression());
        assertEquals(1, newFoos.size());
        assertEquals(newFoo, newFoos.iterator().next());
    }
}
