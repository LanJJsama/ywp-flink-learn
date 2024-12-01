package demo.flink.datastream;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author: ywp
 * @create: 2024-12-01
 * @description: 测试DataStreamSource
 **/

public class DatagentoPrint {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> stringDataStreamSource = env.addSource(new RandomSource());
        System.out.println("stringDataStreamSource打印内容到控制台");
        stringDataStreamSource.print();
        SingleOutputStreamOperator<String> map = stringDataStreamSource.map(new MapFunction<String, String>() {
            @Override
            public String map(String s) throws Exception {
                return "anonymous function input : " + s + ", output : " + (s + "hahaha");
            }
        });

        // 设置文本数据源
        // DataStream<String> text = env.readTextFile("G:\\Test\\新建文本文档.txt");
        // 打印内容到控制台
        // text.print();
        System.out.println("map后的内容打印内容到控制台");
        map.print();
        env.execute("Window WordCount");
    }

}
