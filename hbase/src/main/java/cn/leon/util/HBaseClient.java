package cn.leon.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import cn.leon.config.HbaseConfig;
import cn.leon.domain.form.HtableOpsForm;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;

/**
 * @author mujian
 * @Desc
 * @date 2019/6/4 18:07
 */
@DependsOn("hbaseConfig")
@Component
public class HBaseClient {
    @Resource
    private HbaseConfig hbaseConfig;

    private static Connection connection = null;
    private static Admin admin = null;

    @PostConstruct
    private void init() throws IOException {
        if (connection != null) {
            return;
        }
        try {
            connection = ConnectionFactory.createConnection(hbaseConfig.configuration());
            admin = connection.getAdmin();
        } catch (IOException e) {
            //            log.error("HBase create connection failed: {}", e);
        }
    }


    /**
     * 新建表
     */
    public void createTable(HtableOpsForm form) throws IOException {
        TableName name = TableName.valueOf(form.getTableName());
        boolean isExists = this.tableExists(form.getTableName());
        if (!isExists) {
            TableDescriptorBuilder descriptorBuilder = TableDescriptorBuilder.newBuilder(name);
            List<ColumnFamilyDescriptor> columnFamilyList = new ArrayList<>();
            for (String columnFamily : form.getColumnFamilies()) {
                ColumnFamilyDescriptor columnFamilyDescriptor = ColumnFamilyDescriptorBuilder.newBuilder(columnFamily.getBytes()).build();
                columnFamilyList.add(columnFamilyDescriptor);
            }
            descriptorBuilder.setColumnFamilies(columnFamilyList);
            TableDescriptor tableDescriptor = descriptorBuilder.build();
            admin.createTable(tableDescriptor);
        }
    }

    /**
     * 插入单行，单列簇单列
     */
    public void insertColumn(String tableName, String rowKey, String columnFamily, String column, String value) throws IOException {
        this.insertColumn(tableName, rowKey, columnFamily, new String[]{column}, new String[]{value});
    }

    /**
     * 插入单行，单列簇多列
     */
    public void insertColumn(String tableName, String rowKey, String columnFamily, String[] columns, String[] values) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(rowKey));
        for (int i = 0; i < columns.length; i++) {
            put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(columns[i]), Bytes.toBytes(values[i]));
            put.setTimestamp(System.currentTimeMillis());
        }
        table.put(put);
    }

    /**
     * 插入多行
     */
    @SneakyThrows(IOException.class)
    public void insertBatch(List<Put> puts, String tableName) {
        Table table = connection.getTable(TableName.valueOf(tableName));
        table.put(puts);
    }

    public void deleteRow(String tableName, String rowKey) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Delete delete = new Delete(rowKey.getBytes());
        table.delete(delete);
    }

    public void deleteColumnFamily(String tableName, String rowKey, String columnFamily) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Delete delete = new Delete(rowKey.getBytes());
        delete.addFamily(Bytes.toBytes(columnFamily));
        table.delete(delete);
    }

    public void deleteColumn(String tableName, String rowKey, String columnFamily, String column) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Delete delete = new Delete(rowKey.getBytes());
        delete.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
        table.delete(delete);
    }

    public void deleteTable(String tableName) throws IOException {
        boolean isExists = this.tableExists(tableName);
        if (!isExists) {
            return;
        }
        TableName name = TableName.valueOf(tableName);
        admin.disableTable(name);
        admin.deleteTable(name);
    }

    /**
     * 获取列值
     *
     * @param tableName
     * @param rowkey
     * @param family
     * @param column
     * @return
     */
    public String getValue(String tableName, String rowkey, String family, String column) {
        Table table = null;
        String value = "";
        if (StringUtils.isBlank(tableName) || StringUtils.isBlank(family)
            || StringUtils.isBlank(rowkey) || StringUtils.isBlank(column)) {
            return null;
        }
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            Get g = new Get(rowkey.getBytes());
            g.addColumn(family.getBytes(), column.getBytes());
            Result result = table.get(g);
            List<Cell> ceList = result.listCells();
            if (ceList != null && ceList.size() > 0) {
                for (Cell cell : ceList) {
                    value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                table.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * 获取行数据
     *
     * @param tableName
     * @param rowKey
     * @return
     * @throws IOException
     */
    public List<String> selectOneRow(String tableName, String rowKey) throws IOException {
        List<String> list = Lists.newArrayList();
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(rowKey.getBytes());
        get.addFamily(Bytes.toBytes("cf"));
        Result result = table.get(get);
        NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> map = result.getMap();
        for (Cell cell : result.rawCells()) {
            String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
            list.add(value);
            /*
            String row = Bytes.toString(cell.getRowArray());
            String columnFamily = Bytes.toString(cell.getFamilyArray());
            String column = Bytes.toString(cell.getQualifierArray());
            String value = Bytes.toString(cell.getValueArray());
            // 可以通过反射封装成对象(列名和Java属性保持一致)
            System.out.println(row);
            System.out.println(columnFamily);
            System.out.println(column);
            System.out.println(value);*/
        }
        return list;
    }

    /**
     * 批量查询
     *
     * @param tableName
     * @param rowKeyFilter
     * @return
     * @throws IOException
     */
    public List<String> scanTable(String tableName, String rowKeyFilter) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();
        if (!StringUtils.isEmpty(rowKeyFilter)) {
            RowFilter rowFilter = new RowFilter(CompareOperator.EQUAL, new SubstringComparator(rowKeyFilter));
            scan.setFilter(rowFilter);
        }
        ResultScanner scanner = table.getScanner(scan);
        try {
            List<String> list = Lists.newArrayList();
            scanner.forEach(result -> {
                list.add(Bytes.toString(result.getRow()));
                for (Cell cell : result.rawCells()) {
                    System.out.println(Bytes.toString(cell.getValueArray()));
                }
            });
            return list;
            //            for(Result result : scanner){
            //                for (Cell cell : result.rawCells()) {
            //                    StringBuffer stringBuffer = new StringBuffer().append(Bytes.toString(cell.getRow())).append("\t")
            //                                                                  .append(Bytes.toString(cell.getFamily())).append("\t")
            //                                                                  .append(Bytes.toString(cell.getQualifier())).append("\t")
            //                                                                  .append(Bytes.toString(cell.getValue())).append("\n");
            //                    String str = stringBuffer.toString();
            //                    record += str;
            //                }
            //            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }


    /**
     * 判断表是否已经存在，这里使用间接的方式来实现
     * <p>
     * admin.tableExists() 会报NoSuchColumnFamilyException， 有人说是hbase-client版本问题
     *
     * @param tableName
     * @return
     * @throws IOException
     */
    public boolean tableExists(String tableName) throws IOException {
        TableName[] tableNames = admin.listTableNames();
        if (tableNames != null && tableNames.length > 0) {
            for (int i = 0; i < tableNames.length; i++) {
                if (tableName.equals(tableNames[i].getNameAsString())) {
                    return true;
                }
            }
        }
        return false;
    }
}
