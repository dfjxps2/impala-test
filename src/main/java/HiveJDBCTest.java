import java.sql.*;

import static java.lang.System.exit;

public class HiveJDBCTest {
    private static final String DRIVER = "org.apache.hive.jdbc.HiveDriver";
    private static final String PASSWORD = "hive";
    private static final String USER = "hive";
    public static void main(String[] args) {
        try {
            String ip = null;
            String port = null;

            System.out.println(args.toString());

            if (args.length > 1){
                ip = args[0];
                port = args[1];
            }else{
                System.out.println("Please specify the IP and port of Impala jdbc endpoint.");
                exit(-1);
            }

            Class.forName(DRIVER);

            String URL = "jdbc:hive2://" + ip + ":" + port + "/default;auth=noSasl";

            Connection conn = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement st = conn.createStatement();

//            // create table
//            String sql = "create external table if not exists hive_test(name string,id int) partitioned by (ds string)";
//            st.execute(sql);
            // show tables
            String sql = "show tables";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                System.out.println(rs.getString(1));
            }

//            // describe table
//            sql = "desc hive_test";
//            rs = st.executeQuery(sql);
//            while(rs.next()){
//                System.out.println(rs.getString(1));
//            }
//            // load hdfs data into table
//            sql ="alter table hive_test drop if exists partition (datetime='16-02-17-13')";
//            st.execute(sql);
//            sql = "load data inpath '/user/hadoop/flume/16-02-17-13/*' overwrite into table hive_test partition (datetime='16-02-17-13')";
//            st.execute(sql);
//            // load data local inpath into table
//            sql = "load data local inpath '/home/watch.log' overwrite into table hive_test partition (datetime='16-02-17-16')";
//            st.execute(sql);
//            // select  query
//            sql = "select * from hive_test";
//            rs = st.executeQuery(sql);
//            while(rs.next()){
//                System.out.println(rs.getString(1));
//            }
//            // drop table
//            sql = "drop table if exists hive_test ";
//            st.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
