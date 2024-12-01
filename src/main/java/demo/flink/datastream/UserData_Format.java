package demo.flink.datastream;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class UserData_Format {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Test_Stuent_Score> testStuentScoreDataStreamSource =
                env.fromElements(Test_Stuent_Score.of("小王", "语文", 90),
                Test_Stuent_Score.of("小王", "数学", 80),
                Test_Stuent_Score.of("小王", "英语", 70));

        SingleOutputStreamOperator<Test_Stuent_Score> name = testStuentScoreDataStreamSource.keyBy("name")
                .reduce(new MyReduceFunction());
        name.print();
        env.execute();

    }

    public static class MyReduceFunction implements ReduceFunction<Test_Stuent_Score> {
        public Test_Stuent_Score reduce(Test_Stuent_Score value1, Test_Stuent_Score value2) throws Exception {
            return new Test_Stuent_Score(value1.name, value1.course, value1.score + value2.score);
        }
    }
}
