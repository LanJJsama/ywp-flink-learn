package demo.flink.datastream;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.typeinfo.TypeInformation;

public class JavaPOJO_StockPrice {
    public String symbol;
    public double price;
    public long timestamp;

    public JavaPOJO_StockPrice() {}

    public JavaPOJO_StockPrice(String symbol, double price, long timestamp) {
        this.symbol = symbol;
        this.price = price;
        this.timestamp = timestamp;
    }


}



