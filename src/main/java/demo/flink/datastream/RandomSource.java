package demo.flink.datastream;

import java.util.Random;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

//帮我写一段注释为下面的这个类
/**
 * 随机生成字符串
 */

public class RandomSource implements SourceFunction<String>{
    private volatile boolean running = true;
    private static final Random random = new Random();

    @Override
    public void run(SourceContext<String> sourceContext) throws Exception {
        while (running){

            int length = random.nextInt(5) + 5; //帮我写一段注释
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                sb.append((char) (random.nextInt(26) + 'a'));
            }
            sourceContext.collect(sb.toString());
            Thread.sleep(1000);
        }
    }

    @Override
    public void cancel() {

    }
}
