package cn.leon.kits;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
import org.apache.hbase.thirdparty.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import cn.leon.config.HbaseConfig;
import cn.leon.domain.form.HtableOpsForm;
import lombok.SneakyThrows;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/5 9:20
 */
@DependsOn("hbaseConfig")
@Component
public class Hbasekit {
    @Autowired
    private HbaseConfig hbaseConfig;
    private static Connection connection = null;
    private static Admin admin = null;

    @PostConstruct
    @SneakyThrows(IOException.class)
    private void init() {
        if (connection != null) {
            return;
        }
        connection = ConnectionFactory.createConnection(hbaseConfig.getConfig());
        admin = connection.getAdmin();
    }

    @SneakyThrows(IOException.class)
    public void createTable(HtableOpsForm form) {
        TableName name = TableName.valueOf(form.getTableName());
        boolean isExists = this.tableExists(form.getTableName());
        if (!isExists) {
            TableDescriptorBuilder descriptorBuilder = TableDescriptorBuilder.newBuilder(name);
            List<ColumnFamilyDescriptor> columnFamilyList = new ArrayList<>();
            ColumnFamilyDescriptor columnFamilyDescriptor = ColumnFamilyDescriptorBuilder.newBuilder(form.getColumnFamilies().getBytes()).build();
            columnFamilyList.add(columnFamilyDescriptor);
            descriptorBuilder.setColumnFamilies(columnFamilyList);
            TableDescriptor tableDescriptor = descriptorBuilder.build();
            admin.createTable(tableDescriptor);
        }
    }

    @SneakyThrows(IOException.class)
    public void insertColumn(String tableName, String rowKey, String columnFamily, Map<String, String> map) {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(rowKey));
        for (Map.Entry<String, String> entry : map.entrySet()) {
            put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(entry.getKey()), Bytes.toBytes(entry.getValue()));
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
