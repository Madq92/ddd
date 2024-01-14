package cc.mikaka.ddd.plugins;


import cc.mikaka.ddd.plugins.utils.BasePlugin;
import cc.mikaka.ddd.plugins.utils.EnumModelType;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LombokPlugin extends BasePlugin {
    private final static List<String> LOMBOK_FEATURES;
    private final static List<String> LOMBOK_EXPERIMENTAL_FEATURES;
    private final static Pattern LOMBOK_ANNOTATION = Pattern.compile("@([a-zA-z]+)(\\(.*\\))?");

    static {
        LOMBOK_FEATURES = Arrays.asList(
                "Getter", "Setter", "ToString", "EqualsAndHashCode", "NoArgsConstructor",
                "RequiredArgsConstructor", "AllArgsConstructor", "Data", "Value", "Builder", "Log"
        );
        LOMBOK_EXPERIMENTAL_FEATURES = Arrays.asList(
                "Accessors", "FieldDefaults", "Wither", "UtilityClass", "Helper", "FieldNameConstants", "SuperBuilder"
        );
    }

    private List<String> annotations;


    /**
     * 具体执行顺序 http://www.mybatis.org/generator/reference/pluggingIn.html
     *
     * @param warnings
     * @return
     */
    @Override
    public boolean validate(List<String> warnings) {
        Properties properties = this.getProperties();
        for (Object key : properties.keySet()) {
            String annotation = key.toString().trim();
            if (!(annotation.matches(LOMBOK_ANNOTATION.pattern()))) {
                warnings.add("itfsw:插件" + LombokPlugin.class.getTypeName() + "不能识别的注解（" + annotation + "）！");
                return false;
            }
        }

        return super.validate(warnings);
    }

    /**
     * 具体执行顺序 http://www.mybatis.org/generator/reference/pluggingIn.html
     *
     * @param introspectedTable
     * @return
     */
    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        super.initialized(introspectedTable);

        this.annotations = new ArrayList<>();
        Properties properties = this.getProperties();
        boolean findData = false;
        for (Object key : properties.keySet()) {
            String annotation = key.toString().trim();
            if (annotation.startsWith("@Data")) {
                findData = true;
            }

            if (StringUtility.isTrue(properties.getProperty(key.toString()))) {
                this.annotations.add(annotation);
            }
        }

        if (!findData) {
            this.annotations.add(0, "@Data");
        }
    }

    /**
     * 具体执行顺序 http://www.mybatis.org/generator/reference/pluggingIn.html
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        this.addAnnotations(topLevelClass, introspectedTable, EnumModelType.MODEL_BASE_RECORD);
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    /**
     * 具体执行顺序 http://www.mybatis.org/generator/reference/pluggingIn.html
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        this.addAnnotations(topLevelClass, introspectedTable, EnumModelType.MODEL_PRIMARY_KEY);
        return super.modelPrimaryKeyClassGenerated(topLevelClass, introspectedTable);
    }

    /**
     * 具体执行顺序 http://www.mybatis.org/generator/reference/pluggingIn.html
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        this.addAnnotations(topLevelClass, introspectedTable, EnumModelType.MODEL_RECORD_WITH_BLOBS);
        return super.modelRecordWithBLOBsClassGenerated(topLevelClass, introspectedTable);
    }

    /**
     * 具体执行顺序 http://www.mybatis.org/generator/reference/pluggingIn.html
     *
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        for (String annotation : this.annotations) {
            if (annotation.startsWith("@Data") || annotation.startsWith("@Getter")) {
                return false;
            }
        }
        return true;
    }

    /**
     * 具体执行顺序 http://www.mybatis.org/generator/reference/pluggingIn.html
     *
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        for (String annotation : this.annotations) {
            if (annotation.startsWith("@Data") || annotation.startsWith("@Setter")) {
                return false;
            }
        }
        return true;
    }

    /**
     * 添加注解
     *
     * @param topLevelClass
     * @param introspectedTable
     * @param modelType
     */
    private void addAnnotations(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, EnumModelType modelType) {
        for (String annotation : this.annotations) {
            // @Data
            if (annotation.startsWith("@Data")) {
                this.addAnnotation(topLevelClass, annotation);
                if (topLevelClass.getSuperClass() != null) {
                    this.addAnnotation(topLevelClass, "@EqualsAndHashCode(callSuper = true)");
                    this.addAnnotation(topLevelClass, "@ToString(callSuper = true)");
                }
            } else if (annotation.startsWith("@Builder")) {
                // TODO 配合IncrementsPlugin,以后删除
                boolean checkIncrementsPlugin = true;

                if (checkIncrementsPlugin) {
                    // 有子类或者父类
                    int count = 0;
                    if (introspectedTable.getRules().generatePrimaryKeyClass()) {
                        count++;
                    }
                    if (introspectedTable.getRules().generateBaseRecordClass()) {
                        count++;
                    }
                    if (introspectedTable.getRules().generateRecordWithBLOBsClass()) {
                        count++;
                    }

                    if (topLevelClass.getSuperClass() != null || count >= 2) {
                        this.addAnnotation(topLevelClass, "@SuperBuilder");
                    } else {
                        this.addAnnotation(topLevelClass, annotation);
                    }
                }
            } else {
                this.addAnnotation(topLevelClass, annotation);
            }
        }
    }

    /**
     * 添加注解
     *
     * @param topLevelClass
     * @param annotation
     */
    private void addAnnotation(TopLevelClass topLevelClass, String annotation) {
        // 正则提取annotation
        Matcher matcher = LOMBOK_ANNOTATION.matcher(annotation);
        if (matcher.find()) {
            String annotationName = matcher.group(1);
            if (LOMBOK_FEATURES.contains(annotationName)) {
                topLevelClass.addImportedType("lombok." + annotationName);
            } else if (LOMBOK_EXPERIMENTAL_FEATURES.contains(annotationName)) {
                topLevelClass.addImportedType("lombok.experimental." + annotationName);
            } else {
                this.warnings.add("itfsw:插件" + LombokPlugin.class.getTypeName() + "没有找到注解（" + annotation + "）！");
                return;
            }
            topLevelClass.addAnnotation(annotation);
        }
    }
}
