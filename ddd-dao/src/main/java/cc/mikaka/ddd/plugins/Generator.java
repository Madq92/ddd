package cc.mikaka.ddd.plugins;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Generator {
    public static void main(String[] args) throws Exception {
        File configFile = new File("auction-dao/src/main/resources/MybatisGeneratorConfig.xml");

        List<String> warnings = new ArrayList<String>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);

        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, null, warnings);
        myBatisGenerator.generate(null);
    }
}
