package com.fdu.mockinterview.utility;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.List;

public class MyBatisGeneratorDemo {

    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        
        Configuration config = new Configuration();

        Context context = new Context(ModelType.FLAT);
        context.setId("MyBatis3Simple");
        context.setTargetRuntime("MyBatis3Simple");
        config.addContext(context);

        // JDBC connection configuration
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setDriverClass("org.sqlite.JDBC");
        jdbcConnectionConfiguration.setConnectionURL("jdbc:sqlite:MockInterview.db");
        jdbcConnectionConfiguration.setUserId("");
        jdbcConnectionConfiguration.setPassword("");
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        // Java model generator
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetPackage("com.fdu.mockinterview.entity");
        javaModelGeneratorConfiguration.setTargetProject("src/main/java");
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

//        // SQL map generator (if you want XML configuration, otherwise skip this part)
//        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
//        sqlMapGeneratorConfiguration.setTargetPackage("com.example.demo.mapper");
//        sqlMapGeneratorConfiguration.setTargetProject("src/main/resources");
//        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        // Java client generator (Mapper interfaces)
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setConfigurationType("ANNOTATEDMAPPER");
        javaClientGeneratorConfiguration.setTargetPackage("com.fdu.mockinterview.mapper");
        javaClientGeneratorConfiguration.setTargetProject("src/main/java");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        // Table configuration
        TableConfiguration tableConfiguration = new TableConfiguration(context);

//        tableConfiguration.setTableName("resume");
//        tableConfiguration.setDomainObjectName("Resume");

        context.addTableConfiguration(tableConfiguration);

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);

        for (String warning : warnings) {
            System.out.println(warning);
        }
    }
}
