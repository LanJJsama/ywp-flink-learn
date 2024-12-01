package demo.flink.datastream;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.typeinfo.TypeInformation;

public class PublicClass_StockPrice {
    public static void main(String[] args) {
        System.out.println(TypeInformation.of(JavaPOJO_StockPrice.class).createSerializer(new ExecutionConfig()));
    }
}
