package demo.flink.datastream;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;




public class DatagentoPrint {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置文本数据源
        DataStream<String> text = env.readTextFile("G:\\GitHub\\github-recovery-codes.txt");
        // 打印内容到控制台
        text.print();
        env.execute("Window WordCount");
    }

}
