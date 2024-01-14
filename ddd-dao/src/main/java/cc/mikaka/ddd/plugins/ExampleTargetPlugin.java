package cc.mikaka.ddd.plugins;

import cc.mikaka.ddd.plugins.utils.BasePlugin;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;

import java.util.List;
import java.util.Properties;

public class ExampleTargetPlugin extends BasePlugin {
    public static final String PRO_TARGET_PACKAGE = "targetPackage";  // 配置targetPackage名
    private String targetPackage;   // 目标包

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(List<String> warnings) {
        // 获取配置的目标package
        Properties properties = getProperties();
        this.targetPackage = properties.getProperty(PRO_TARGET_PACKAGE);
        if (this.targetPackage == null) {
            warnings.add("请配置com.itfsw.mybatis.generator.plugins.ExampleTargetPlugin插件的目标包名(targetPackage)！");
            return false;
        }
        return super.validate(warnings);
    }

    /**
     * 初始化阶段
     * 具体执行顺序 http://www.mybatis.org/generator/reference/pluggingIn.html
     *
     * @param introspectedTable
     */
    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        super.initialized(introspectedTable);
        String exampleType = introspectedTable.getExampleType();
        // 修改包名
        Context context = getContext();
        JavaModelGeneratorConfiguration configuration = context.getJavaModelGeneratorConfiguration();
        String targetPackage = configuration.getTargetPackage();
        String newExampleType = exampleType.replace(targetPackage, this.targetPackage);

        introspectedTable.setExampleType(newExampleType);

        logger.debug("itfsw(Example 目标包修改插件):修改" + introspectedTable.getExampleType() + "的包到" + this.targetPackage);
    }

}
