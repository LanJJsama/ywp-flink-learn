package demo.flink.datastream;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FilterFunction_GenetoPrint {
    public static void main(String[] args) throws Exception
    {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> stringDataStreamSource = env.addSource(new RandomSource());
        //使用匿名函数
        SingleOutputStreamOperator<String> filterNimingResult = stringDataStreamSource.filter(new FilterFunction<String>() {
            @Override
            public boolean filter(String s) throws Exception {

                return s.contains("a");
            }
        });
        filterNimingResult.print();
        //使用lambda表达式
        SingleOutputStreamOperator<String> filterLambdaResult = stringDataStreamSource.filter(input -> input.contains("a"));
        filterLambdaResult.print();
        env.execute();

    }
}
