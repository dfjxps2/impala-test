import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static java.lang.System.exit;

public class HiveJDBCWithKerberosTest {
    private static final String DRIVER = "org.apache.hive.jdbc.HiveDriver";

    public static void main(String[] args) {
        String ip = null;
        String port = null;
        String krbConfPath = null;
        String keyTabPath = null;
        String userPrinciple = null;

        if (args.length >= 5) {
            ip = args[0];
            port = args[1];
            krbConfPath = args[2];
            keyTabPath = args[3];
            userPrinciple = args[4];

        } else {
            System.out.println("ArgList: <HiveServer2/Impalad IP> <HiveServer2/Impalad Port> <KrbConfPath> <KeyTabPath> <UserPrinciple>");
            exit(-1);
        }

        try {

            //登录Kerberos账号
            System.setProperty("java.security.krb5.conf", krbConfPath);

            Configuration configuration = new Configuration();
            configuration.set("hadoop.security.authentication", "Kerberos");
            UserGroupInformation.setConfiguration(configuration);
            UserGroupInformation.loginUserFromKeytab(userPrinciple, keyTabPath);

            Class.forName(DRIVER);

            String URL = "jdbc:hive2://" + ip + ":" + port + "/default;principal=" + userPrinciple;

            Connection conn = DriverManager.getConnection(URL);
            Statement st = conn.createStatement();

//            // create table
//            String sql = "create external table if not exists hive_test(name string,id int) partitioned by (ds string)";
//            st.execute(sql);
            // show tables
            String sql = "show tables";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
