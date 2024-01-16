package cc.mikaka.ddd.plugins;

import cc.mikaka.ddd.plugins.utils.BasePlugin;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class BizPrimaryKeyPlugin extends BasePlugin {
    public final static String BIZ_PRIMARY_KEY = "bizPrimaryKey";

    public static String convertCamel(String underline) {
        if (StringUtils.isEmpty(underline)) {
            return "";
        }
        int len = underline.length();
        StringBuilder stringBuilder = new StringBuilder(len);
        for (int i = 0; i < len; i++) {

            char c = underline.charAt(i);
            if (c == '_' && (++i) < len) {
                c = underline.charAt(i);
                stringBuilder.append(Character.toUpperCase(c));
            } else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        generatorParam(method, interfaze, introspectedTable);
        return true;
    }

    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        element.getAttributes().removeIf(e -> e.getName().contains("parameterType"));
        generatorSQLParam(element, introspectedTable);
        return true;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        generatorParam(method, interfaze, introspectedTable);
        return true;
    }

    @Override
    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        element.getAttributes().remove(1);
        generatorSQLParam(element, introspectedTable);
        return true;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        generatorSQLParam(element, introspectedTable);
        return true;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        generatorSQLParam(element, introspectedTable);
        return false;
    }

    /**
     * 生成业务主键ID的参数
     *
     * @param method
     * @param interfaze
     * @param introspectedTable
     */
    private void generatorParam(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        String bizPrimaryKeyTableField = (String) introspectedTable.getTableConfiguration().getProperties().get(BIZ_PRIMARY_KEY);
        if (StringUtils.isEmpty(bizPrimaryKeyTableField)) {
            return;
        }

        String bizPrimaryKeyJavaField = convertCamel(bizPrimaryKeyTableField);

        FullyQualifiedJavaType fullyQualifiedJavaType = new FullyQualifiedJavaType(Param.class.getName());
        interfaze.addImportedType(fullyQualifiedJavaType);

        method.getParameters().remove(0);

        FullyQualifiedJavaType type = introspectedTable.getColumn(bizPrimaryKeyTableField).getFullyQualifiedJavaType();
        Parameter parameter1 = new Parameter(type, bizPrimaryKeyJavaField);
        parameter1.addAnnotation("@Param(\"" + bizPrimaryKeyJavaField + "\")");
        method.addParameter(parameter1);
    }

    /**
     * 生成业务主键ID的sql
     *
     * @param element
     * @param introspectedTable
     */
    private void generatorSQLParam(XmlElement element, IntrospectedTable introspectedTable) {
        String bizPrimaryKeyTableField = (String) introspectedTable.getTableConfiguration().getProperties().get(BIZ_PRIMARY_KEY);
        if (StringUtils.isEmpty(bizPrimaryKeyTableField)) {
            return;
        }

        String bizPrimaryKeyJavaField = convertCamel(bizPrimaryKeyTableField);
        String jdbcType = introspectedTable.getColumn(bizPrimaryKeyTableField).getJdbcTypeName();
        String content = "where " + bizPrimaryKeyTableField + " = #{" + bizPrimaryKeyJavaField + ",jdbcType=" + jdbcType + "}";

        TextElement textElement = new TextElement(content);
        element.getElements().removeIf(element1 -> (element1 instanceof TextElement) && ((TextElement) element1).getContent().contains("where "));
        element.getElements().add(textElement);
    }


}
