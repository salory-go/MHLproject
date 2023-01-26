package dao;

import utils.JDBCUtilsByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BasicDAO<T>{
    private QueryRunner qr = new QueryRunner();

    public int update(String sql,Object... parameters){
        Connection connection = null;
        int update=0;
        try {
            connection = JDBCUtilsByDruid.getConection();

             update = qr.update(connection, sql, parameters);


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtilsByDruid.close(null,null,connection);
        }
        return update;
    }

//    返回多个对象
    public List<T> queryMulti(String sql,Class<T> clazz,Object... parameters){
        Connection connection = null;
        List<T> query =null;
        try {
            connection = JDBCUtilsByDruid.getConection();

            query = qr.query(connection, sql, new BeanListHandler<T>(clazz), parameters);



        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtilsByDruid.close(null,null,connection);
        }
        return query;
    }

//    查询单行结果
public T querySingle(String sql,Class<T>clazz, Object... parameters){
    Connection connection = null;
    T query =null;
    try {
        connection = JDBCUtilsByDruid.getConection();

        query = qr.query(connection, sql, new BeanHandler<T>(clazz), parameters);

    } catch (SQLException e) {
        e.printStackTrace();
    }finally {
        JDBCUtilsByDruid.close(null,null,connection);
    }
    return query;
}

//    查询单行单列
public Object queryScalar(String sql,Object... parameters){
    Connection connection = null;
    Object query =null;
    try {
        connection = JDBCUtilsByDruid.getConection();

        query = qr.query(connection, sql, new ScalarHandler<>(), parameters);

    } catch (SQLException e) {
        e.printStackTrace();
    }finally {
        JDBCUtilsByDruid.close(null,null,connection);
    }
    return query;
}


}
