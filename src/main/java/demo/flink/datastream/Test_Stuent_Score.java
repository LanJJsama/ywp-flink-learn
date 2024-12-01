package demo.flink.datastream;

public class Test_Stuent_Score {
    public String name;
    public String course;
    public int score;
    public Test_Stuent_Score() {
    }
    public Test_Stuent_Score(String name, String course, int score) {
        this.name = name;
        this.course = course;
        this.score = score;
    }
    //帮我解释一下下面代码的作用
    public static Test_Stuent_Score of(String name, String course, int score){
        return new Test_Stuent_Score(name, course, score);
    }

    @Override
    public String toString() {
        return "Test_Stuent_Score{" +
                "name='" + name + '\'' +
                ", course='" + course + '\'' +
                ", score=" + score +
                '}';
    }
}
