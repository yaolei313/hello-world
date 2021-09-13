package com.yao.app.mybatis;

import org.junit.jupiter.api.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class MybatisGenerator {

    @Test
    public void test(){
        try {
            System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
            System.out.println(MybatisGenerator.class.getClassLoader().getResource(""));
            mkdirAndClear();
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            // 此处路径是classpath相关，即jvm classloader规范指定的-cp
            InputStream inputStream = MybatisGenerator.class.getResourceAsStream("/mybatis/generatorConfig.xml");
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(inputStream);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
            System.out.println(warnings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mkdirAndClear() throws IOException {
        // 此处是操作系统相关，根路径是进程的work directory
        Path file = Paths.get("target/mybatis");
        System.out.println(file.toAbsolutePath());
        if (!Files.exists(file)) {
            Files.createDirectories(file);
        } else {
            if (!Files.isDirectory(file)){
                throw new RuntimeException("exist same name file");
            }
            Files.walkFileTree(file, new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.deleteIfExists(dir);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.deleteIfExists(file);
                    return FileVisitResult.CONTINUE;
                }
            });
            Files.createDirectories(file);
        }
    }

}
