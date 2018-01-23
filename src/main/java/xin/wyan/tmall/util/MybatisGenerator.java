package xin.wyan.tmall.util;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MybatisGenerator {
    /**
        生成代码成功，只能执行一次，以后执行会覆盖掉mapper,pojo,xml 等文件上做的修改。
        所以在程序开始，把today变量修改为今天才可以执行，这样明天再执行就无法运行了，
        以免以后对Category类做了改动，不小心运行了MybatisGenerator 导致Category类上做的手动改动被覆盖掉了。
     */
    public static void main(String[] args) throws ParseException, IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
        String today = "2018-1-22";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = sdf.parse(today);
        Date d = new Date();

        if (d.getTime() > now.getTime() + 1000 * 60 * 60 * 24) {
            System.err.println("——————未成成功运行——————");
            System.err.println("——————未成成功运行——————");
            System.err.println("本程序具有破坏作用，应该只运行一次，如果必须要再运行，需要修改today变量为今天，如:" + sdf.format(new Date()));
            return;
        }

        if (false) {
            return;
        }
        List<String> warnings = new ArrayList<>();
        boolean overwrite=true;
        InputStream is=MybatisGenerator.class.getClassLoader().getResource("generatorConfig.xml").openStream();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration configuration = cp.parseConfiguration(is);
        is.close();
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, callback, warnings);
        myBatisGenerator.generate(null);
        System.out.println("生成代码成功，只能执行一次，以后执行会覆盖掉mapper,pojo,xml 等文件上做的修改");
    }
}
