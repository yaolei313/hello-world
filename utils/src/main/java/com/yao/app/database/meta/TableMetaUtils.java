package com.yao.app.database.meta;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TableMetaUtils {
    
    public static void main(String[] args) {
        String tableName = "user_info";
        String className = "UserInfo";
        String fileName = "D:/" + className + ".java";
        
        try{
            PrintWriter pw =
                    new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8")));
            pw.println("public class " + className + "{");
            
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                    "classpath*:database/application.xml");
            PlatformTransactionManager txManager = context.getBean("transactionManager", PlatformTransactionManager.class);
            DataSource ds = context.getBean("dataSource", DataSource.class);

            DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
            definition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
            definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

            TransactionStatus ts = txManager.getTransaction(definition);
            Connection conn = DataSourceUtils.getConnection(ds);
            
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println(conn.getCatalog());
            ResultSet rs = meta.getColumns(conn.getCatalog(), "%", tableName, "%");
            while(rs.next()){
                String s1 = rs.getString("TABLE_CAT");
                String s2 = rs.getString("TABLE_SCHEM");
                String s3 = rs.getString("TABLE_NAME");
                String s4 = rs.getString("COLUMN_NAME");
                int s5 = rs.getInt("DATA_TYPE");
                String s6 = rs.getString("TYPE_NAME");
                int s7 = rs.getInt("COLUMN_SIZE");
                String s8 ="";
                int s9 = rs.getInt("DECIMAL_DIGITS");
                int s10 = rs.getInt("NUM_PREC_RADIX");
                int s11= rs.getInt("NULLABLE");
                String s12= rs.getString("REMARKS");
                String s13= rs.getString("COLUMN_DEF");
                String s14 = "";
                String s15= "";
                int s16= rs.getInt("CHAR_OCTET_LENGTH");
                int s17= rs.getInt("ORDINAL_POSITION");
                String s18= rs.getString("IS_NULLABLE");
                String s19= "";//rs.getString("SCOPE_CATLOG");
                String s20= rs.getString("SCOPE_SCHEMA");
                String s21= rs.getString("SCOPE_TABLE");
                short s22= rs.getShort("SOURCE_DATA_TYPE");
                String s23= rs.getString("IS_AUTOINCREMENT");
                
                String s24 = getColumnClassName(s5);
                
                String fieldType = getColumnClassName(s5);
                String simpleFieldType =
                        fieldType.contains(".") ? fieldType.substring(fieldType.lastIndexOf(".") + 1) : fieldType;
                String fieldName = handleName(s4);

                pw.println();
                pw.println("private " + simpleFieldType + " " + fieldName + ";");
                pw.println();
            }
            
            pw.println("}");
            pw.close();
            
            context.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    private static String handleName(String s4) {
        StringBuilder sb = new StringBuilder();
        String[] strs = s4.split("_");
        for (int i = 0; i < strs.length; i++) {
            if (i == 0) {
                sb.append(strs[i].substring(0, 1).toLowerCase() + strs[i].substring(1));
            } else {
                sb.append(strs[i].substring(0, 1).toUpperCase() + strs[i].substring(1));
            }
        }
        return sb.toString();
    }
    
    public static String getColumnClassName(int sqlType) throws SQLException {
        String className = String.class.getName();

        switch (sqlType) {

        case Types.NUMERIC:
        case Types.DECIMAL:
            className = java.math.BigDecimal.class.getName();
            break;

        case Types.BIT:
            className = java.lang.Boolean.class.getName();
            break;

        case Types.TINYINT:
            className = java.lang.Byte.class.getName();
            break;

        case Types.SMALLINT:
            className = java.lang.Short.class.getName();
            break;

        case Types.INTEGER:
            className = java.lang.Integer.class.getName();
            break;

        case Types.BIGINT:
            className = java.lang.Long.class.getName();
            break;

        case Types.REAL:
            className = java.lang.Float.class.getName();
            break;

        case Types.FLOAT:
        case Types.DOUBLE:
            className = java.lang.Double.class.getName();
            break;

        case Types.BINARY:
        case Types.VARBINARY:
        case Types.LONGVARBINARY:
            className = "byte[]";
            break;

        case Types.DATE:
            className = java.sql.Date.class.getName();
            break;

        case Types.TIME:
            className = java.sql.Time.class.getName();
            break;

        case Types.TIMESTAMP:
            className = java.sql.Timestamp.class.getName();
            break;

        case Types.BLOB:
            className = java.sql.Blob.class.getName();
            break;

        case Types.CLOB:
            className = java.sql.Clob.class.getName();
            break;
        }

        return className;
    }
}
