package demo.flink.datastream;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

import java.util.Random;

public class KeybyFunction_GenetoPrint {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        ParameterTool parameterTool = ParameterTool.fromArgs(args);
        env.getConfig().setGlobalJobParameters(parameterTool);
        env.setParallelism(1);
        DataStreamSource<String> stringDataStreamSource = env.addSource(new RandomSource()).setParallelism(1);
        SingleOutputStreamOperator<Tuple2<String, Integer>> s1 = stringDataStreamSource.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String s, Collector<String> collector) throws Exception {
                String[] split = s.split("a");
                for (String s1 : split) {
                    collector.collect(s1);
                }

            }
        }).map(new MapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> map(String s) throws Exception {
                Tuple2<String, Integer> tuple2 = Tuple2.of(s, 1);
                return tuple2;
            }
        }).keyBy(new KeySelector<Tuple2<String, Integer>, String>() {
            @Override
            public String getKey(Tuple2<String, Integer> stringIntegerTuple2) throws Exception {
                String key = stringIntegerTuple2.f0;
                return key;
            }
        }).sum(1);
//        s1.print();


        SingleOutputStreamOperator<Integer> process = stringDataStreamSource.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String s, Collector<String> collector) throws Exception {
                String[] split = s.split("a");
                for (String s1 : split) {
                    collector.collect(s1);
                }

            }
        }).map(new MapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> map(String s) throws Exception {
                Tuple2<String, Integer> tuple2 = Tuple2.of(s, 1);
                return tuple2;
            }
        }).keyBy(new KeySelector<Tuple2<String, Integer>, String>() {
            @Override
            public String getKey(Tuple2<String, Integer> stringIntegerTuple2) throws Exception {
                String key = stringIntegerTuple2.f0;
                return key;
            }
        }).process(new MyKeyedProcessFunction()).name("MyKeyedProcessFunction");

        process.print();


        DataStream<Tuple2<Integer, Double>> data_from_env = env.fromElements(Tuple2.of(1, 1.0), Tuple2.of(1, 2.0), Tuple2.of(3, 3.0));
        SingleOutputStreamOperator<Tuple2<Integer, Double>> sum = data_from_env.keyBy(0).sum(1);
//        sum.print();

        env.execute();
    }

    public static class MyKeyedProcessFunction extends KeyedProcessFunction<String, Tuple2<String, Integer>, Integer> {
        private transient int count;
        private transient long waitTime;
        private transient ValueState<Integer> valueState;
        private transient ParameterTool params;

        @Override
        public void open(Configuration parameters) throws Exception {
            params = (ParameterTool)getRuntimeContext().getExecutionConfig().getGlobalJobParameters();
            waitTime = params.getLong("waitTime", 1000);
            ValueStateDescriptor<Integer> integerValueStateDescriptor = new ValueStateDescriptor<>("time-state", TypeInformation.of(new TypeHint<Integer>() {
            }));
            valueState = getRuntimeContext().getState(integerValueStateDescriptor);
            System.out.println("初始化的valueState值为:" + valueState);


        }

        @Override
        public void processElement(Tuple2<String, Integer> value, KeyedProcessFunction<String, Tuple2<String, Integer>, Integer>.Context ctx, Collector<Integer> out) throws Exception {
            System.out.println("valueState的值为:" + valueState.value());
            if (valueState.value() == null) {
                int random_state = new Random().nextInt(10000000);
                valueState.update(random_state);
                System.out.println("更新后的valueState值为:" + valueState.value());
            }
        }

        @Override
        public void onTimer(long timestamp, KeyedProcessFunction<String, Tuple2<String, Integer>, Integer>.OnTimerContext ctx, Collector<Integer> out) throws Exception {
            out.collect(valueState.value());
            System.out.println("定时器触发了,valueState的值为:" + valueState.value());
        }
    }
}
