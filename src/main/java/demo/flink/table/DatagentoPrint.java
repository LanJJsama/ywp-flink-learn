package demo.flink.table;

import org.apache.flink.table.api.*;

public class DatagentoPrint {

    public static void main(String[] args) {
        EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .inStreamingMode()
                //.inBatchMode()
                .build();

        TableEnvironment tableEnv = TableEnvironment.create(settings);

        tableEnv.executeSql("CREATE TABLE datagen (\n" +
                " f_sequence INT,\n" +
                " f_random INT,\n" +
                " f_random_str STRING\n" +
                ") WITH (\n" +
                " 'connector' = 'datagen',\n" +
                "\n" +
                " -- optional options --\n" +
                "\n" +
                " 'rows-per-second'='5',\n" +
                "\n" +
                " 'fields.f_sequence.kind'='sequence',\n" +
                " 'fields.f_sequence.start'='1',\n" +
                " 'fields.f_sequence.end'='1000',\n" +
                "\n" +
                " 'fields.f_random.min'='1',\n" +
                " 'fields.f_random.max'='1000',\n" +
                "\n" +
                " 'fields.f_random_str.length'='10'\n" +
                ")");


        tableEnv.executeSql("CREATE TABLE print_table WITH ('connector' = 'print')\n" +
                "LIKE datagen (EXCLUDING ALL)");


        tableEnv.executeSql("insert into print_table select * from datagen");





//       tEnv.executeSql("select 1");


    }



}
