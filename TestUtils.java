package utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestUtils {
    public static void main(String[] args) throws SQLException {
        Connection conection = JDBCUtilsByDruid.getConection();
        System.out.println(conection);

    }
}
