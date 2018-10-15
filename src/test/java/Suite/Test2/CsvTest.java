package Suite.Test2;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;


@RunWith(Parameterized.class)
public class CsvTest {

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Parameterized.Parameters
    public static List<DataTest> dataCsv() throws IOException {
        System.out.println("datacsv");
        return readFromCsv();
    }

    public static List<DataTest> readFromCsv() throws IOException {
        ArrayList<DataTest> data = new ArrayList<DataTest>();
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(DataTest.class);

        File csvFile = new File(CsvTest.class.getResource("/data.csv").getFile());
        System.out.println(csvFile);

        MappingIterator<DataTest> iterator = mapper.readerFor(DataTest.class).with(schema).readValues(csvFile);
        System.out.println("x");
        while (iterator.hasNext()){
            DataTest row = iterator.next();
            System.out.println(row);
            data.add(row);

        }
        System.out.println(data);
        System.out.println(data.getClass().getSimpleName());
        return data;
    }




    @Parameterized.Parameter
    public DataTest data1;

    @Test
    public void assertions(){
//        collector.checkThat(data1.getReal(),equalTo(data1.getExpect()));
        System.out.println(data1.getExpect());
    }




}
