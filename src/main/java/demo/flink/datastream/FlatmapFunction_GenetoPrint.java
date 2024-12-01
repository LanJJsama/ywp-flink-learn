package demo.flink.datastream;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.Arrays;

public class FlatmapFunction_GenetoPrint {
    public static void main(String[] args) throws Exception
    {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> stringDataStreamSource = env.addSource(new RandomSource());
        stringDataStreamSource.print();
        // 匿名函数书写flatmap函数
        SingleOutputStreamOperator<String> flatMapStreamDataStream = stringDataStreamSource.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String s, Collector<String> collector) throws Exception {
                String[] split = s.split("a");
                for (String s1 : split) {
                    collector.collect(s1);
                }
            }
        });
        //使用flatmap函数，使用lambda表达式写一个代码
//        stringDataStreamSource.flatMap((String a, Collector<String> out) -> Arrays.stream(a.split("a")).forEach(x -> out.collect(x)))
//                .returns(String.class).print();

        SingleOutputStreamOperator<String> flatMap = stringDataStreamSource.flatMap((String a, Collector<String> out) -> Arrays.stream(a.split("a")).forEach(x -> out.collect(x))).returns(String.class);
        flatMap.print();


        env.execute("FlatmapFunction_Test");

    }
}
