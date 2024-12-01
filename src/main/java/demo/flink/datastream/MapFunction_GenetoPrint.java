package demo.flink.datastream;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

//写一个段有关下面这个类的注释
/**
 * @author: ywp
 * @create: 2024-12-01
 * @description:
 * 测试map函数
 */

public class MapFunction_GenetoPrint {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> stringRandomSource = env.addSource(new RandomSource());
        // 使用匿名函数书写map函数
        SingleOutputStreamOperator<String> mapResoult = stringRandomSource.map(new MapFunction<String, String>() {
            @Override
            public String map(String s) throws Exception {
                return "aaa" + s;
            }
        });
        SingleOutputStreamOperator<String> mapLambdaResoult = stringRandomSource.map(input -> input + "hahaha");
        mapLambdaResoult.print();
        mapResoult.print();
        env.execute("MapFumction_Test");
    }
}
