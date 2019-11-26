package com.learn.webdemo.test;

import com.learn.webdemo.utils.BeanInfo;
import com.learn.webdemo.utils.FieldColumnConverts;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author xgangzai
 * @Date 10/01/2019 19:38
 */
public class SQLCodeGenerator {


    /**
     * <if test="query.type!=null">
     * and type = #{query.type}
     * </if>
     *
     * @param args
     */


    //通过反射获取query对象的所有字段 生成xml
    public static void main(String[] args)throws Exception {

        String name="Fan";
        Class entityClass = Class.forName("com.learn.webdemo.model.entity."+name+"Entity");
        Class queryClass = Class.forName("com.learn.webdemo.model.query."+name+"Query");

        String table = FieldColumnConverts.camelToUnderline(name).substring(1);




        String resultMap = resultMap(entityClass);
//        print(resultMap);


        String baseColumn = baseColumn(entityClass);
//        print(baseColumn);

        String query = query(queryClass);
//        print(query);

        String insert = insert(entityClass, table);
//        print(insert);

        String insertBatch = insertBatch(entityClass, table);
//        print(insertBatch);


        String set = set(entityClass);
//        print(set);

        String xml=String.format(SQLXML,
                name+"Mapper",
                name+"Entity",
                resultMap,
                baseColumn,
                query,
                table,
                table,
                table,
                table,
                table,
                name+"Entity",
                insert,
                insertBatch,
                set,
                table
                );
        System.err.println(xml);

        //写入文件
        String path="/Users/xgang/XG/learn/web-demo/src/main/resources/mapper/";
        File file=new File(path+name+"Mapper.xml");
        FileUtils.write(file,xml, Charset.forName("UTF-8"));

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    private static void print(String s) {
        System.err.println("\n\n\n");
        System.err.println(s);

    }

    private static String query(Class clazz) {

        List<String> excludeFields = new ArrayList<>(5);

        excludeFields.add("ids");
        excludeFields.add("createdAtMinInclude");
        excludeFields.add("createdAtMaxInclude");
        excludeFields.add("updatedAtMinInclude");
        excludeFields.add("updatedAtMaxInclude");
        excludeFields.add("createdAtMinExclude");
        excludeFields.add("createdAtMaxExclude");
        excludeFields.add("updatedAtMinExclude");
        excludeFields.add("updatedAtMaxExclude");

        String sqlTemplate =
                "            <if test=\"query.%s != null\">\n" +
                        "                AND %s = #{query.%s}\n" +
                        "            </if>\n";

        BeanInfo beanInfo = new BeanInfo(clazz);

        StringBuilder sb = new StringBuilder();
        for (Field field : beanInfo.getDeclaredFields()) {
            String fieldName = field.getName();
            if (!excludeFields.contains(fieldName)) {
                String column = FieldColumnConverts.camelToUnderline(fieldName);

                sb.append(String.format(sqlTemplate, fieldName, column, fieldName));
            }
        }

        return sb.toString();
    }


    private static String resultMap(Class clazz) {

        List<String> excludeFields = new ArrayList<>(3);

        excludeFields.add("id");
        excludeFields.add("createdAt");
        excludeFields.add("updatedAt");

        String sqlTemplate = "        <result property=\"%s\" column=\"%s\"/>\n";

        BeanInfo beanInfo = new BeanInfo(clazz);

        StringBuilder sb = new StringBuilder();
        for (Field field : beanInfo.getDeclaredFields()) {
            String fieldName = field.getName();
            if (!excludeFields.contains(fieldName)) {
                String column = FieldColumnConverts.camelToUnderline(fieldName);

                sb.append(String.format(sqlTemplate, fieldName, column));
            }
        }

        return sb.toString();
    }


    private static String set(Class clazz) {

        List<String> excludeFields = new ArrayList<>(5);

        excludeFields.add("id");
        excludeFields.add("createdAt");
        excludeFields.add("updatedAt");

        String sqlTemplate =
                "           <if test=\"update.%s != null\">\n" +
                        "                %s = #{update.%s},\n" +
                        "           </if>\n";

        String updatedAtSQL =
                "           <if test=\"update.updatedAt != null\">\n" +
                        "                updated_at = #{update.updatedAt}\n" +
                        "           </if>\n";


        BeanInfo beanInfo = new BeanInfo(clazz);

        StringBuilder sb = new StringBuilder();
        for (Field field : beanInfo.getDeclaredFields()) {
            String fieldName = field.getName();
            if (!excludeFields.contains(fieldName)) {
                String column = FieldColumnConverts.camelToUnderline(fieldName);

                sb.append(String.format(sqlTemplate, fieldName, column, fieldName));
            }
        }

        sb.append(updatedAtSQL);
        return sb.toString();
    }


    private static String insertBatch(Class clazz, String table) {

        String space = "     ";
        List<String> excludeFields = new ArrayList<>(5);


        excludeFields.add("id");

        BeanInfo beanInfo = new BeanInfo(clazz);

        StringBuilder columSb = new StringBuilder();
        StringBuilder valueSb = new StringBuilder();
        String valueTemplate = "#{item.%s}";
        for (Field field : beanInfo.getDeclaredFields()) {
            String fieldName = field.getName();
            if (!excludeFields.contains(fieldName)) {
                String column = FieldColumnConverts.camelToUnderline(fieldName);

                columSb.append(space).append(column).append(",").append("\n");
                valueSb.append(space).append(String.format(valueTemplate, fieldName)).append(",").append("\n");
            }
        }

        //删除最后一个逗号
        columSb.deleteCharAt(columSb.lastIndexOf(","));
        valueSb.deleteCharAt(valueSb.lastIndexOf(","));
        return space + "INSERT INTO " + table + " (\n" + columSb + space + ")\n" +
                space + "VALUES \n"
                +space+"<foreach collection=\"entities\" item=\"item\" index=\"index\" separator=\",\">\n"
                +space+"(\n"
                + valueSb  +space+ ")\n"
                +space+"</foreach>";

    }

    private static String insert(Class clazz, String table) {

        String space = "     ";
        List<String> excludeFields = new ArrayList<>(5);


        excludeFields.add("id");

        BeanInfo beanInfo = new BeanInfo(clazz);

        StringBuilder columSb = new StringBuilder();
        StringBuilder valueSb = new StringBuilder();
        String valueTemplate = "#{%s}";
        for (Field field : beanInfo.getDeclaredFields()) {
            String fieldName = field.getName();
            if (!excludeFields.contains(fieldName)) {
                String column = FieldColumnConverts.camelToUnderline(fieldName);

                columSb.append(space).append(column).append(",").append("\n");
                valueSb.append(space).append(String.format(valueTemplate, fieldName)).append(",").append("\n");
            }
        }

        //删除最后一个逗号
        columSb.deleteCharAt(columSb.lastIndexOf(","));
        valueSb.deleteCharAt(valueSb.lastIndexOf(","));
        return space + "INSERT INTO " + table + " (\n" + columSb + space + ")\n" +
                space + "VALUES (\n"
                + valueSb + space + ")";

    }

    private static String baseColumn(Class clazz) {
        BeanInfo beanInfo = new BeanInfo(clazz);

        List<String> excludeFields = new ArrayList<>(5);

        String space = "        ";
        excludeFields.add("id");
        excludeFields.add("createdAt");
        excludeFields.add("updatedAt");
        StringBuilder columSb = new StringBuilder(space + "id,\n");
        for (Field field : beanInfo.getDeclaredFields()) {
            String fieldName = field.getName();
            if (excludeFields.contains(fieldName)) {
                continue;
            }
            String column = FieldColumnConverts.camelToUnderline(fieldName);

            columSb.append(space).append(column).append(",\n");
        }

        //删除最后一个逗号
//        columSb.deleteCharAt(columSb.lastIndexOf(","));
        columSb.append(space).append("created_at,\n").append(space).append("updated_at");
        return columSb.toString();

    }


    private static final String SQLXML=
            "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                    "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n" +
                    "<mapper namespace=\"com.learn.webdemo.mapper.%s\">\n" +
                    "\n" +
                    "    <resultMap id=\"BaseResultMap\" type=\"com.learn.webdemo.model.entity.%s\">\n" +
                    "        <id column=\"id\" property=\"id\"/>\n" +
                    "\n" +
                    "%s" +
                    "\n" +
                    "        <result column=\"created_at\" property=\"createdAt\"/>\n" +
                    "        <result column=\"updated_at\" property=\"updatedAt\"/>\n" +
                    "    </resultMap>\n" +
                    "\n" +
                    "    <sql id=\"Base_Column_List\">\n" +
                    "%s\n" +
                    "    </sql>\n" +
                    "\n" +
                    "    <sql id=\"Base_Sort\">\n" +
                    "        <if test=\"sort != null\">\n" +
                    "            ORDER BY\n" +
                    "            <foreach collection=\"sort\" item=\"order\" index=\"index\"\n" +
                    "                     open=\"\" separator=\",\" close=\"\">\n" +
                    "                ${order.property} ${order.direction}\n" +
                    "            </foreach>\n" +
                    "        </if>\n" +
                    "\n" +
                    "    </sql>\n" +
                    "\n" +
                    "    <sql id=\"Base_Page\">\n" +
                    "        <if test=\"page != null\">\n" +
                    "            limit #{page.offset}, #{page.pageSize}\n" +
                    "        </if>\n" +
                    "    </sql>\n" +
                    "\n" +
                    "    <sql id=\"Query_Where\">\n" +
                    "        <where>\n" +
                    "            1=1\n" +
                    "            <if test=\"query.ids!=null\">\n" +
                    "                and id IN\n" +
                    "                <foreach collection=\"query.ids\" item=\"item\" index=\"index\"\n" +
                    "                         open=\"(\" separator=\",\" close=\")\">\n" +
                    "                    #{item}\n" +
                    "                </foreach>\n" +
                    "            </if>\n" +
                    "%s"+
                    "            <if test=\"query.createdAtMinInclude != null\">\n" +
                    "                AND created_at <![CDATA[ >= ]]> #{query.createdAtMinInclude}\n" +
                    "            </if>\n" +
                    "            <if test=\"query.createdAtMinExclude != null\">\n" +
                    "                AND created_at <![CDATA[ > ]]> #{query.createdAtMinExclude}\n" +
                    "            </if>\n" +
                    "            <if test=\"query.createdAtMaxInclude != null\">\n" +
                    "                AND created_at <![CDATA[ <= ]]> #{query.createdAtMaxInclude}\n" +
                    "            </if>\n" +
                    "            <if test=\"query.createdAtMaxExclude != null\">\n" +
                    "                AND created_at <![CDATA[ < ]]> #{query.createdAtMaxExclude}\n" +
                    "            </if>\n" +
                    "            <if test=\"query.updatedAtMinInclude != null\">\n" +
                    "                AND updated_at <![CDATA[ >= ]]> #{query.updatedAtMinInclude}\n" +
                    "            </if>\n" +
                    "            <if test=\"query.updatedAtMinExclude != null\">\n" +
                    "                AND updated_at <![CDATA[ > ]]> #{query.updatedAtMinExclude}\n" +
                    "            </if>\n" +
                    "            <if test=\"query.updatedAtMaxInclude != null\">\n" +
                    "                AND updated_at <![CDATA[ <= ]]> #{query.updatedAtMaxInclude}\n" +
                    "            </if>\n" +
                    "            <if test=\"query.updatedAtMaxExclude != null\">\n" +
                    "                AND updated_at <![CDATA[ < ]]> #{query.updatedAtMaxExclude}\n" +
                    "            </if>\n" +
                    "        </where>\n" +
                    "    </sql>\n" +
                    "    <select id=\"selectById\" resultMap=\"BaseResultMap\" parameterType=\"java.lang.Long\">\n" +
                    "        SELECT\n" +
                    "        <include refid=\"Base_Column_List\"/>\n" +
                    "        FROM %s\n" +
                    "        WHERE id = #{id}\n" +
                    "    </select>\n" +
                    "\n" +
                    "    <select id=\"selectOne\" resultMap=\"BaseResultMap\">\n" +
                    "        SELECT\n" +
                    "        <include refid=\"Base_Column_List\"/>\n" +
                    "        FROM %s\n" +
                    "        <include refid=\"Query_Where\"/>\n" +
                    "    </select>\n" +
                    "\n" +
                    "    <select id=\"selectList\" resultMap=\"BaseResultMap\">\n" +
                    "        SELECT\n" +
                    "        <include refid=\"Base_Column_List\"/>\n" +
                    "        FROM %s\n" +
                    "        <include refid=\"Query_Where\"/>\n" +
                    "        <include refid=\"Base_Sort\"/>\n" +
                    "    </select>\n" +
                    "\n" +
                    "    <select id=\"pageList\" resultMap=\"BaseResultMap\">\n" +
                    "        SELECT\n" +
                    "        <include refid=\"Base_Column_List\"/>\n" +
                    "        FROM %s\n" +
                    "        <include refid=\"Query_Where\"/>\n" +
                    "        <include refid=\"Base_Sort\"/>\n" +
                    "        <include refid=\"Base_Page\"/>\n" +
                    "    </select>\n" +
                    "    <select id=\"count\" resultType=\"java.lang.Integer\">\n" +
                    "        SELECT\n" +
                    "        COUNT(*)\n" +
                    "        FROM %s\n" +
                    "        <include refid=\"Query_Where\"/>\n" +
                    "    </select>\n" +
                    "\n" +
                    "    <insert id=\"insert\" useGeneratedKeys=\"true\" keyColumn=\"id\" keyProperty=\"id\"\n" +
                    "            parameterType=\"com.learn.webdemo.model.entity.%s\">\n" +
                    "%s\n" +
                    "    </insert>\n" +
                    "\n" +
                    "    <insert id=\"insertBatch\" parameterType=\"java.util.Map\">\n" +
                    "%s\n"+
                    "    </insert>\n" +
                    "\n" +
                    "    <sql id=\"set\">\n" +
                    "        <set>\n" +
                    "%s" +
                    "        </set>\n" +
                    "    </sql>\n" +
                    "\n" +
                    "    <update id=\"updateById\" parameterType=\"java.util.Map\">\n" +
                    "        UPDATE %s\n" +
                    "        <include refid=\"set\"></include>\n" +
                    "        WHERE id = #{update.id}\n" +
                    "    </update>\n" +
                    "\n" +
                    "\n" +
                    "</mapper>\n";
}
