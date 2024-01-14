package cc.mikaka.ddd.plugins;


import cc.mikaka.ddd.condition.BaseCondition;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.*;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.internal.util.StringUtility;
import org.springframework.util.Assert;

import java.sql.JDBCType;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class DefaultExtPlugin extends PluginAdapter {
    private static final String TABLE_BIZ_ID = "bizId";
    private static final String BATCH_INSERT_FLAG = "batchInsertFlag";
    private static final String BATCH_UPDATE_FLAG = "batchUpdateFlag";
    private static final String BATCH_UPDATE_STATE_FLAG = "batchUpdateStateFlag";
    private static final String BATCH_DELETE_FLAG = "batchDeleteFlag";
    private static final String LIST_BY_KEYS_FLAG = "listByKeyIdsFlag";

    private static final String LIST_BY_CONDITION_FLAG = "listByConditionFlag";
    private static final String COUNT_BY_CONDITION_FLAG = "countByConditionFlag";
    private static final String PAGE_LIST_BY_CONDITION_FLAG = "pageListByConditionFlag";

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
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * 生成业务主键ID的参数
     *
     * @param method
     * @param interfaze
     * @param introspectedTable
     */
    private void generatorParam(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        String tableBizId = (String) introspectedTable.getTableConfiguration().getProperties().get(TABLE_BIZ_ID);
        if (StringUtils.isEmpty(tableBizId)) {
            return;
        }

        String bizFieldId = convertCamel(tableBizId);

        FullyQualifiedJavaType fullyQualifiedJavaType = new FullyQualifiedJavaType(Param.class.getName());
        interfaze.addImportedType(fullyQualifiedJavaType);

        method.getParameters().remove(0);

        FullyQualifiedJavaType type = introspectedTable.getColumn(tableBizId).getFullyQualifiedJavaType();
        Parameter parameter1 = new Parameter(type, bizFieldId);
        parameter1.addAnnotation("@Param(\"" + bizFieldId + "\")");
        method.addParameter(parameter1);
    }

    /**
     * 生成业务主键ID的sql
     *
     * @param element
     * @param introspectedTable
     */
    private void generatorSQLParam(XmlElement element, IntrospectedTable introspectedTable) {
        String tableBizId = (String) introspectedTable.getTableConfiguration().getProperties().get(TABLE_BIZ_ID);
        if (StringUtils.isEmpty(tableBizId)) {
            return;
        }

        String bizFieldId = convertCamel(tableBizId);
        String jdbcType = introspectedTable.getColumn(bizFieldId).getJdbcTypeName();
        String content = "where " + tableBizId + " = #{" + bizFieldId + ",jdbcType=" + jdbcType + "}";

        TextElement textElement = new TextElement(content);
        element.getElements().removeIf(element1 -> (element1 instanceof TextElement) && ((TextElement) element1).getContent().contains("where "));
        element.getElements().add(textElement);
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
    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
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

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType fullyQualifiedJavaType = new FullyQualifiedJavaType(Mapper.class.getName());
        interfaze.addImportedType(fullyQualifiedJavaType);
        interfaze.addAnnotation("@Mapper");

        StringBuilder sb = new StringBuilder();

        interfaze.addJavaDocLine("/**");

        String remarks = introspectedTable.getRemarks();
        if (StringUtility.stringHasValue(remarks)) {
            interfaze.addJavaDocLine(" * Database Table Remarks:");
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));
            for (String remarkLine : remarkLines) {
                interfaze.addJavaDocLine(" *   " + remarkLine);
            }
            interfaze.addJavaDocLine(" *");
        }
        interfaze.addJavaDocLine(" * This class was generated by MyBatis Generator.");

        sb.append(" * This class corresponds to the database table ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        interfaze.addJavaDocLine(sb.toString());

        addJavadocTagWithoutDateTime(interfaze, true);

        interfaze.addJavaDocLine(" */");

        introspectedTable.getBaseColumns().addAll(introspectedTable.getBLOBColumns());
        introspectedTable.getBLOBColumns().clear();

        if (methodFlag(introspectedTable, BATCH_INSERT_FLAG)) {
            clientGenerateBatchInsert(interfaze, topLevelClass, introspectedTable);
        }
        if (methodFlag(introspectedTable, BATCH_UPDATE_STATE_FLAG)) {
            clientGenerateBatchUpdateState(interfaze, topLevelClass, introspectedTable);
        }
        if (methodFlag(introspectedTable, BATCH_UPDATE_FLAG)) {
            clientGenerateBatchUpdate(interfaze, topLevelClass, introspectedTable);
        }
        if (methodFlag(introspectedTable, BATCH_DELETE_FLAG)) {
            clientGenerateBatchDetele(interfaze, topLevelClass, introspectedTable);
        }
        if (methodFlag(introspectedTable, LIST_BY_KEYS_FLAG)) {
            clientGenerateListByKeyIds(interfaze, topLevelClass, introspectedTable);
        }
        if (methodFlag(introspectedTable, LIST_BY_CONDITION_FLAG)) {
            clientGenerateListByCondition(interfaze, topLevelClass, introspectedTable);
        }
        if (methodFlag(introspectedTable, PAGE_LIST_BY_CONDITION_FLAG)) {
            clientGeneratePageListByCondition(interfaze, topLevelClass, introspectedTable);
        }
        if (methodFlag(introspectedTable, COUNT_BY_CONDITION_FLAG)) {
            clientGenerateCountByCondition(interfaze, topLevelClass, introspectedTable);
        }
        return true;
    }

    private void addJavadocTagWithoutDateTime(JavaElement javaElement, boolean markAsDoNotDelete) {
        javaElement.addJavaDocLine(" *");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        if (markAsDoNotDelete) {
            sb.append(" do_not_delete_during_merge");
        }
        javaElement.addJavaDocLine(sb.toString());
    }

    /**
     * 批量插入Java
     */
    private void clientGenerateBatchInsert(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String targetPackage = introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetPackage();
        String doName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        interfaze.addImportedType(new FullyQualifiedJavaType(List.class.getName()));
        interfaze.addImportedType(new FullyQualifiedJavaType(targetPackage + "." + doName));
        Method method = new Method("batchInsert");
        method.addParameter(new Parameter(new FullyQualifiedJavaType("List<" + doName + ">"), "list"));

        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * This method was generated by MyBatis Generator.");
        addJavadocTagWithoutDateTime(method, true);
        method.addJavaDocLine(" */");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        interfaze.addMethod(method);
    }

    /**
     * 批量更新Java
     */
    private void clientGenerateBatchUpdate(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String targetPackage = introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetPackage();
        String doName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        interfaze.addImportedType(new FullyQualifiedJavaType(List.class.getName()));
        interfaze.addImportedType(new FullyQualifiedJavaType(targetPackage + "." + doName));
        Method method = new Method("batchUpdate");
        method.addParameter(new Parameter(new FullyQualifiedJavaType("List<" + doName + ">"), "list"));

        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * This method was generated by MyBatis Generator.");
        addJavadocTagWithoutDateTime(method, true);
        method.addJavaDocLine(" */");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        interfaze.addMethod(method);
    }

    /**
     * 批量更新状态Java
     */
    private void clientGenerateBatchUpdateState(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        interfaze.addImportedType(new FullyQualifiedJavaType(List.class.getName()));
        Method method = new Method("batchUpdateState");

        String bizTableId = (String) introspectedTable.getTableConfiguration().getProperties().get(TABLE_BIZ_ID);
        String bizFieldId = convertCamel(bizTableId);

        String javaType = introspectedTable.getColumn(bizTableId).getFullyQualifiedJavaType().getShortName();
        Parameter parameter = new Parameter(new FullyQualifiedJavaType("List<" + javaType + ">"), bizFieldId + "List");
        parameter.addAnnotation("@Param(\"" + bizFieldId + "List" + "\")");
        method.addParameter(parameter);

        Parameter parameter2 = new Parameter(FullyQualifiedJavaType.getStringInstance(), "state");
        parameter2.addAnnotation("@Param(\"state\")");
        method.addParameter(parameter2);


        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * This method was generated by MyBatis Generator.");
        addJavadocTagWithoutDateTime(method, true);
        method.addJavaDocLine(" */");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        interfaze.addMethod(method);
    }

    /**
     * 批量删除Java
     */
    private void clientGenerateBatchDetele(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String bizTableId = (String) introspectedTable.getTableConfiguration().getProperties().get(TABLE_BIZ_ID);
        String bizFieldId = convertCamel(bizTableId);

        interfaze.addImportedType(new FullyQualifiedJavaType(List.class.getName()));
        Method method = new Method("batchDelete");

        String javaType = introspectedTable.getColumn(bizTableId).getFullyQualifiedJavaType().getShortName();
        Parameter parameter1 = new Parameter(new FullyQualifiedJavaType("List<" + javaType + ">"), bizFieldId + "List");
        parameter1.addAnnotation("@Param(\"" + bizFieldId + "List\")");
        method.addParameter(parameter1);

        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * This method was generated by MyBatis Generator.");
        addJavadocTagWithoutDateTime(method, true);
        method.addJavaDocLine(" */");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        interfaze.addMethod(method);
    }

    private void clientGenerateListByKeyIds(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String bizTableId = (String) introspectedTable.getTableConfiguration().getProperties().get(TABLE_BIZ_ID);
        String bizFieldId = convertCamel(bizTableId);

        String targetPackage = introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetPackage();
        String doName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        interfaze.addImportedType(new FullyQualifiedJavaType(List.class.getName()));
        interfaze.addImportedType(new FullyQualifiedJavaType(targetPackage + "." + doName));

        interfaze.addImportedType(new FullyQualifiedJavaType(List.class.getName()));
        String mFieldId = bizFieldId.replaceFirst(".", bizFieldId.substring(0, 1).toUpperCase());
        Method method = new Method("listBy" + mFieldId + "s");

        String javaType = introspectedTable.getColumn(bizTableId).getFullyQualifiedJavaType().getShortName();
        Parameter parameter1 = new Parameter(new FullyQualifiedJavaType("List<" + javaType + ">"), bizFieldId + "List");
        parameter1.addAnnotation("@Param(\"" + bizFieldId + "List\")");
        method.addParameter(parameter1);

        method.setReturnType(new FullyQualifiedJavaType("List<" + doName + ">"));

        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * This method was generated by MyBatis Generator.");
        addJavadocTagWithoutDateTime(method, true);
        method.addJavaDocLine(" */");
        interfaze.addMethod(method);
    }

    private void clientGenerateListByCondition(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        String targetPackage = introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetPackage();
        String doName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        interfaze.addImportedType(new FullyQualifiedJavaType(List.class.getName()));
        interfaze.addImportedType(new FullyQualifiedJavaType(targetPackage + "." + doName));

        interfaze.addImportedType(new FullyQualifiedJavaType(Condition.class.getName()));
        Method method = new Method("listByCondition");

        Parameter parameter = new Parameter(new FullyQualifiedJavaType("BaseCondition"), "baseCondition");
        method.addParameter(parameter);

        method.setReturnType(new FullyQualifiedJavaType("List<" + doName + ">"));

        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * This method was generated by MyBatis Generator.");
        method.addJavaDocLine(" * 【merchantId 必输】");
        addJavadocTagWithoutDateTime(method, true);
        method.addJavaDocLine(" */");
        interfaze.addMethod(method);
    }

    private void clientGeneratePageListByCondition(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        String targetPackage = introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetPackage();
        String doName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        interfaze.addImportedType(new FullyQualifiedJavaType(List.class.getName()));
        interfaze.addImportedType(new FullyQualifiedJavaType(targetPackage + "." + doName));

        interfaze.addImportedType(new FullyQualifiedJavaType(BaseCondition.class.getName()));
        Method method = new Method("pageListByCondition");

        Parameter parameter = new Parameter(new FullyQualifiedJavaType("BaseCondition"), "baseCondition");
        method.addParameter(parameter);

        method.setReturnType(new FullyQualifiedJavaType("List<" + doName + ">"));

        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * This method was generated by MyBatis Generator.");
        method.addJavaDocLine(" * 【merchantId 必输】");
        addJavadocTagWithoutDateTime(method, true);
        method.addJavaDocLine(" */");
        interfaze.addMethod(method);
    }

    private void clientGenerateCountByCondition(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        String targetPackage = introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetPackage();
        String doName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        interfaze.addImportedType(new FullyQualifiedJavaType(List.class.getName()));
        interfaze.addImportedType(new FullyQualifiedJavaType(targetPackage + "." + doName));

        interfaze.addImportedType(new FullyQualifiedJavaType(BaseCondition.class.getName()));
        Method method = new Method("countByCondition");

        Parameter parameter = new Parameter(new FullyQualifiedJavaType("BaseCondition"), "baseCondition");
        method.addParameter(parameter);

        method.setReturnType(FullyQualifiedJavaType.getIntInstance());

        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * This method was generated by MyBatis Generator.");
        method.addJavaDocLine(" * 【merchantId 必输】");
        addJavadocTagWithoutDateTime(method, true);
        method.addJavaDocLine(" */");
        interfaze.addMethod(method);
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        if (methodFlag(introspectedTable, BATCH_INSERT_FLAG)) {
            sqlMapGenerateBatchInsert(document, introspectedTable);
        }
        if (methodFlag(introspectedTable, BATCH_UPDATE_STATE_FLAG)) {
            sqlMapGenerateBatchUpdateState(document, introspectedTable);
        }
        if (methodFlag(introspectedTable, BATCH_UPDATE_FLAG)) {
            sqlMapGenerateBatchUpdate(document, introspectedTable);
        }
        if (methodFlag(introspectedTable, BATCH_DELETE_FLAG)) {
            sqlMapGenerateBatchDelete(document, introspectedTable);
        }
        if (methodFlag(introspectedTable, LIST_BY_KEYS_FLAG)) {
            sqlMapGenerateListByKeys(document, introspectedTable);
        }
        if (methodFlag(introspectedTable, LIST_BY_CONDITION_FLAG)) {
            sqlMapGenerateListByCondition(document, introspectedTable);
        }
        if (methodFlag(introspectedTable, PAGE_LIST_BY_CONDITION_FLAG)) {
            sqlMapGeneratePageListByCondition(document, introspectedTable);
        }
        if (methodFlag(introspectedTable, COUNT_BY_CONDITION_FLAG)) {
            sqlMapGenerateCountByCondition(document, introspectedTable);
        }

        if (methodFlag(introspectedTable, LIST_BY_CONDITION_FLAG) || methodFlag(introspectedTable, COUNT_BY_CONDITION_FLAG) || methodFlag(introspectedTable, PAGE_LIST_BY_CONDITION_FLAG)) {
            sqlMapWhereBaseQuery(document, introspectedTable);

//            if (!DocumentParser.findSqlKey(introspectedTable.getContext(), introspectedTable.getMyBatis3XmlMapperFileName(), "Where_Extend_Query")) {
//                sqlMapWhereExtendQuery(document, introspectedTable);
//            }
        }

        return true;
    }

    private void sqlMapGenerateBatchInsert(Document document, IntrospectedTable introspectedTable) {
        XmlElement xmlElement = new XmlElement("insert");
        xmlElement.addAttribute(new Attribute("id", "batchInsert"));
        xmlElement.addAttribute(new Attribute("parameterType", "java.util.List"));

        XmlElement temp = null;
        for (Element e : document.getRootElement().getElements()) {
            if (e instanceof XmlElement) {
                if ("insert".equals(((XmlElement) e).getName())) {
                    temp = (XmlElement) e;
                    break;
                }
            }
        }

        Assert.notNull(temp, "insert cannot be null");

        int indexLoop = 0;
        List<Element> elements = temp.getElements();
        for (int i = 0, elementsSize = elements.size(); i < elementsSize; i++) {
            Element e = elements.get(i);
            TextElement element = (TextElement) e;
            if (element.getContent().trim().startsWith("values")) {
                indexLoop = i;
                TextElement valuesE = new TextElement("values");
                xmlElement.addElement(valuesE);

                TextElement forech = new TextElement("<foreach collection =\"list\" item=\"item\" index= " + "\"index\" separator =\",\">");
                xmlElement.addElement(forech);

                TextElement textElement = new TextElement(element.getContent().split("values")[1].replaceAll("#\\{", "#{item."));
                xmlElement.addElement(textElement);
            } else {
                if (i >= indexLoop) {
                    TextElement valuesE = new TextElement(((TextElement) e).getContent().replaceAll("#\\{", "#{item."));
                    xmlElement.addElement(valuesE);
                } else {
                    xmlElement.addElement(e);
                }
            }
            if (i == elementsSize - 1) {
                TextElement textElement = new TextElement("</foreach >");
                xmlElement.addElement(textElement);
            }
        }
        document.getRootElement().addElement(xmlElement);
    }

    private void sqlMapGenerateBatchUpdate(Document document, IntrospectedTable introspectedTable) {
        XmlElement xmlElement = new XmlElement("update");
        xmlElement.addAttribute(new Attribute("id", "batchUpdate"));
        xmlElement.addAttribute(new Attribute("parameterType", "java.util.List"));

        XmlElement temp = null;
        for (Element e : document.getRootElement().getElements()) {
            if (e instanceof XmlElement) {
                if ("update".equals(((XmlElement) e).getName()) && "updateByPrimaryKeySelective".equals(((XmlElement) e).getAttributes().get(0).getValue())) {
                    temp = (XmlElement) e;
                    break;
                }
            }
        }

        Assert.notNull(temp, "insert cannot be null");

        List<Element> elements = temp.getElements();
        for (int i = 0, elementsSize = elements.size(); i < elementsSize; i++) {
            Element e = elements.get(i);
            if (e instanceof XmlElement) {
                XmlElement element00 = new XmlElement(((XmlElement) e).getName());
                for (int j = 0; j < ((XmlElement) e).getElements().size(); j++) {
                    XmlElement element = (XmlElement) ((XmlElement) e).getElements().get(j);
                    List<Attribute> attributes = Lists.newArrayList();
                    element.getAttributes().forEach(att -> {
                        Attribute attribute = new Attribute(att.getName(), "item." + att.getValue());
                        attributes.add(attribute);
                    });
                    List<Element> eles = Lists.newArrayList();
                    element.getElements().forEach(ele -> {
                        TextElement textEle = new TextElement(((TextElement) ele).getContent().replaceAll("#\\{", "#{item."));
                        eles.add(textEle);
                    });
                    XmlElement subXml = new XmlElement(element.getName());
                    subXml.getAttributes().addAll(attributes);
                    subXml.getElements().addAll(eles);
                    element00.addElement(subXml);
                }
                xmlElement.addElement(element00);
            } else {
                if (((TextElement) e).getContent().trim().startsWith("update")) {
                    TextElement fore = new TextElement("<foreach collection=\"list\" item=\"item\" index=\"index\" " + "open=\"\" close=\"\" separator=\";\">");
                    xmlElement.addElement(fore);
                    xmlElement.addElement(e);
                } else if (((TextElement) e).getContent().trim().startsWith("where")) {
                    TextElement textEle = new TextElement(((TextElement) e).getContent().replaceAll("#\\{", "#{item."));
                    xmlElement.addElement(textEle);
                } else {
                    xmlElement.addElement(e);
                }
            }
            if (i == elementsSize - 1) {
                TextElement textElement = new TextElement("</foreach >");
                xmlElement.addElement(textElement);
            }

        }
        document.getRootElement().addElement(xmlElement);
    }

    private void sqlMapGenerateBatchUpdateState(Document document, IntrospectedTable introspectedTable) {
        XmlElement xmlElement = new XmlElement("update");
        xmlElement.addAttribute(new Attribute("id", "batchUpdateState"));
        xmlElement.addAttribute(new Attribute("parameterType", "java.util.List"));

        xmlElement.addElement(new TextElement("<!--"));

        String sb = "  WARNING - " +
                MergeConstants.NEW_ELEMENT_TAG;
        xmlElement.addElement(new TextElement(sb));
        xmlElement.addElement(new TextElement("  This element is automatically generated by MyBatis Generator, do not modify."));

        xmlElement.addElement(new TextElement("-->"));

        xmlElement.addElement(new TextElement("update " + introspectedTable.getTableConfiguration().getTableName()));

        xmlElement.addElement(new TextElement("set state = #{state,jdbcType=VARCHAR}"));

        String bizTableId = (String) introspectedTable.getTableConfiguration().getProperties().get(TABLE_BIZ_ID);
        Assert.notNull(bizTableId, TABLE_BIZ_ID + " is null");

        String logicFieldId = convertCamel(bizTableId);

        String content = "where ";

        content += bizTableId + " in";
        TextElement textElement = new TextElement(content);

        xmlElement.addElement(textElement);

        String jdbcType = introspectedTable.getColumn(bizTableId).getJdbcTypeName();

        TextElement forx01 = new TextElement("  <foreach collection=\"" + logicFieldId + "List\" item=\"item\"  index=\"index\"\n" + " open=\"(\"  separator=\",\" close=\")\">\n        #{item,jdbcType=" + jdbcType + "}\n" + "      </foreach>");
        xmlElement.addElement(forx01);

        document.getRootElement().addElement(xmlElement);
    }

    private void sqlMapGenerateBatchDelete(Document document, IntrospectedTable introspectedTable) {
        XmlElement xmlElement = new XmlElement("delete");
        xmlElement.addAttribute(new Attribute("id", "batchDelete"));
        xmlElement.addAttribute(new Attribute("parameterType", "java.util.List"));

        XmlElement temp = null;
        for (Element e : document.getRootElement().getElements()) {
            if (e instanceof XmlElement) {
                if ("delete".equals(((XmlElement) e).getName()) && "deleteByPrimaryKey".equals(((XmlElement) e).getAttributes().get(0).getValue())) {
                    temp = (XmlElement) e;
                    break;
                }
            }
        }

        Assert.notNull(temp, "delete cannot be null");

        String bizTableId = (String) introspectedTable.getTableConfiguration().getProperties().get(TABLE_BIZ_ID);
        Assert.notNull(bizTableId, TABLE_BIZ_ID + " is null");
        String logicFieldId = convertCamel(bizTableId);

        String jdbcType = introspectedTable.getColumn(bizTableId).getJdbcTypeName();

        List<Element> elements = temp.getElements();
        for (int i = 0, elementsSize = elements.size(); i < elementsSize; i++) {
            Element e = elements.get(i);
            if (((TextElement) e).getContent().trim().startsWith("where")) {
                String content = "where ";

                content += bizTableId + " in";

                TextElement textElement = new TextElement(content);

                xmlElement.addElement(textElement);

                TextElement forx01 = new TextElement("  <foreach collection=\"" + logicFieldId + "List\" item=\"item\"  index=\"index\"\n" + " open=\"(\"  separator=\",\" close=\")\">\n        #{item,jdbcType=" + jdbcType + "}\n" + "      </foreach>");
                xmlElement.addElement(forx01);
            } else {
                xmlElement.addElement(e);
            }

        }
        document.getRootElement().addElement(xmlElement);
    }

    private void sqlMapGenerateListByKeys(Document document, IntrospectedTable introspectedTable) {

        String bizTableId = (String) introspectedTable.getTableConfiguration().getProperties().get(TABLE_BIZ_ID);
        String bizFieldId = convertCamel(bizTableId);
        String mFieldId = bizFieldId.replaceFirst(".", bizFieldId.substring(0, 1).toUpperCase());

        XmlElement xmlElement = new XmlElement("select");
        xmlElement.addAttribute(new Attribute("id", "listBy" + mFieldId + "s"));
        xmlElement.addAttribute(new Attribute("resultMap", "BaseResultMap"));

        XmlElement temp = null;
        for (Element e : document.getRootElement().getElements()) {
            if (e instanceof XmlElement) {
                if ("select".equals(((XmlElement) e).getName()) && "selectByPrimaryKey".equals(((XmlElement) e).getAttributes().get(0).getValue())) {
                    temp = (XmlElement) e;
                    break;
                }
            }
        }

        Assert.notNull(temp, "select cannot be null");

        String jdbcType = introspectedTable.getColumn(bizTableId).getJdbcTypeName();

        List<Element> elements = temp.getElements();
        for (int i = 0, elementsSize = elements.size(); i < elementsSize; i++) {
            Element e = elements.get(i);
            if (e instanceof TextElement && ((TextElement) e).getContent().trim().startsWith("where")) {
                String content = "where ";

                content += bizTableId + " in";
                TextElement textElement = new TextElement(content);

                xmlElement.addElement(textElement);

                TextElement forx01 = new TextElement("  <foreach collection=\"" + bizFieldId + "List\" item=\"item\"  index=\"index\"\n" + " open=\"(\"  separator=\",\" close=\")\">\n        #{item,jdbcType=" + jdbcType + "}\n" + "      </foreach>");
                xmlElement.addElement(forx01);
            } else {
                xmlElement.addElement(e);
            }

        }
        document.getRootElement().addElement(xmlElement);
    }

    private void sqlMapWhereBaseQuery(Document document, IntrospectedTable introspectedTable) {

        Map<String, IntrospectedColumn> columnMap = introspectedTable.getAllColumns().stream().collect(Collectors.toMap(IntrospectedColumn::getActualColumnName, a -> a, (k1, k2) -> k1));
        XmlElement xmlElement = new XmlElement("sql");
        xmlElement.addAttribute(new Attribute("id", "Where_Base_Query"));

        addComment(xmlElement);

        String content = "where ";

        TextElement textElement = new TextElement(content);
        xmlElement.addElement(textElement);

        String[] columns = {"org_id", "brand_id"};
        for (String column : columns) {
            if (!columnMap.containsKey(column)) {
                continue;
            }
            XmlElement columnIfXml = new XmlElement("if");
            String javaFiled = convertCamel(column);
            if ("org_id".equals(column)) {
                javaFiled = "queryOrgId";
            }
            if ("brand_id".equals(column)) {
                javaFiled = "queryBrandId";
            }
            columnIfXml.addAttribute(new Attribute("test", javaFiled + " != null"));
            columnIfXml.addElement(new TextElement("and " + column + " = #{" + javaFiled + ",jdbcType=" + JDBCType.valueOf(columnMap.get(column).getJdbcType()) + "}"));
            xmlElement.addElement(columnIfXml);
        }

        if (columnMap.containsKey("gmt_modified")) {
            XmlElement columnIfXml = new XmlElement("if");
            columnIfXml.addAttribute(new Attribute("test", convertCamel("startTime") + " != null"));
            columnIfXml.addElement(new TextElement("and gmt_modified " + " > #{startTime,jdbcType=" + JDBCType.valueOf(columnMap.get("gmt_modified").getJdbcType()) + "}"));
            xmlElement.addElement(columnIfXml);
        }
        document.getRootElement().addElement(xmlElement);
    }

    private void sqlMapWhereExtendQuery(Document document, IntrospectedTable introspectedTable) {
        XmlElement xmlElement = new XmlElement("sql");
        xmlElement.addAttribute(new Attribute("id", "Where_Extend_Query"));
        addModifyComment(xmlElement);
        document.getRootElement().addElement(xmlElement);
    }

    private void sqlMapGenerateListByCondition(Document document, IntrospectedTable introspectedTable) {
        //构造title
        XmlElement xmlElement = new XmlElement("select");
        xmlElement.addAttribute(new Attribute("id", "listByCondition"));
        xmlElement.addAttribute(new Attribute("parameterType", "map"));
        if (introspectedTable.getBLOBColumns().size() != 0) {
            xmlElement.addAttribute(new Attribute("resultMap", "ResultMapWithBLOBs"));
        } else {
            xmlElement.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        }
        //增加注释`
        addComment(xmlElement);

        TextElement textElement = new TextElement("select");
        xmlElement.addElement(textElement);

        XmlElement includeSelectXml = new XmlElement("include");
        includeSelectXml.addAttribute(new Attribute("refid", "Base_Column_List"));
        xmlElement.addElement(includeSelectXml);
        if (introspectedTable.getBLOBColumns().size() != 0) {
            TextElement textElement1 = new TextElement(",");
            xmlElement.addElement(textElement1);
            XmlElement includeSelectXml1 = new XmlElement("include");
            includeSelectXml1.addAttribute(new Attribute("refid", "Blob_Column_List"));
            xmlElement.addElement(includeSelectXml1);
        }

        TextElement fromElement = new TextElement("from " + introspectedTable.getTableConfiguration().getTableName());
        xmlElement.addElement(fromElement);

        XmlElement includeWhereXml01 = new XmlElement("include");
        includeWhereXml01.addAttribute(new Attribute("refid", "Where_Base_Query"));
        xmlElement.addElement(includeWhereXml01);

        XmlElement includeWhereXml02 = new XmlElement("include");
        includeWhereXml02.addAttribute(new Attribute("refid", "Where_Extend_Query"));
        xmlElement.addElement(includeWhereXml02);

        document.getRootElement().addElement(xmlElement);
    }

    private void sqlMapGeneratePageListByCondition(Document document, IntrospectedTable introspectedTable) {
        //构造title
        XmlElement xmlElement = new XmlElement("select");
        xmlElement.addAttribute(new Attribute("id", "pageListByCondition"));
        xmlElement.addAttribute(new Attribute("parameterType", "map"));
        if (introspectedTable.getBLOBColumns().size() != 0) {
            xmlElement.addAttribute(new Attribute("resultMap", "ResultMapWithBLOBs"));
        } else {
            xmlElement.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        }
        //增加注释
        addComment(xmlElement);

        TextElement textElement = new TextElement("select");
        xmlElement.addElement(textElement);

        XmlElement includeSelectXml = new XmlElement("include");
        includeSelectXml.addAttribute(new Attribute("refid", "Base_Column_List"));
        xmlElement.addElement(includeSelectXml);
        if (introspectedTable.getBLOBColumns().size() != 0) {
            TextElement textElement1 = new TextElement(",");
            xmlElement.addElement(textElement1);
            XmlElement includeSelectXml1 = new XmlElement("include");
            includeSelectXml1.addAttribute(new Attribute("refid", "Blob_Column_List"));
            xmlElement.addElement(includeSelectXml1);
        }

        TextElement fromElement = new TextElement("from " + introspectedTable.getTableConfiguration().getTableName());
        xmlElement.addElement(fromElement);

        XmlElement includeWhereXml01 = new XmlElement("include");
        includeWhereXml01.addAttribute(new Attribute("refid", "Where_Base_Query"));
        xmlElement.addElement(includeWhereXml01);

        XmlElement includeWhereXml02 = new XmlElement("include");
        includeWhereXml02.addAttribute(new Attribute("refid", "Where_Extend_Query"));
        xmlElement.addElement(includeWhereXml02);

        XmlElement includeWhereXml03 = new XmlElement("include");
        includeWhereXml03.addAttribute(new Attribute("refid", "com.alsc.pos.dish.dao.CommMapper.Global_Pagination_Order"));
        xmlElement.addElement(includeWhereXml03);

        document.getRootElement().addElement(xmlElement);
    }

    private void sqlMapGenerateCountByCondition(Document document, IntrospectedTable introspectedTable) {
        //构造title
        XmlElement xmlElement = new XmlElement("select");
        xmlElement.addAttribute(new Attribute("id", "countByCondition"));
        xmlElement.addAttribute(new Attribute("parameterType", "map"));
        xmlElement.addAttribute(new Attribute("resultType", "java.lang.Integer"));
        //增加注释
        addComment(xmlElement);

        //https://yuque.antfin-inc.com/shulan/exp/10001 count(*)是标准统计行数的语法
        TextElement textElement = new TextElement("select count(*)");
        xmlElement.addElement(textElement);

        TextElement fromElement = new TextElement("from " + introspectedTable.getTableConfiguration().getTableName());
        xmlElement.addElement(fromElement);

        XmlElement includeWhereXml01 = new XmlElement("include");
        includeWhereXml01.addAttribute(new Attribute("refid", "Where_Base_Query"));
        xmlElement.addElement(includeWhereXml01);

        XmlElement includeWhereXml02 = new XmlElement("include");
        includeWhereXml02.addAttribute(new Attribute("refid", "Where_Extend_Query"));
        xmlElement.addElement(includeWhereXml02);

        document.getRootElement().addElement(xmlElement);
    }

    private boolean methodFlag(IntrospectedTable introspectedTable, String prop) {
        String methodFlag = (String) introspectedTable.getTableConfiguration().getProperties().get(prop);
        return !Objects.isNull(methodFlag) && Boolean.parseBoolean(methodFlag);
    }

    private void addComment(XmlElement xmlElement) {
        xmlElement.addElement(new TextElement("<!--"));
        String sb = "  WARNING - " +
                MergeConstants.NEW_ELEMENT_TAG;
        xmlElement.addElement(new TextElement(sb));
        xmlElement.addElement(new TextElement("  This element is automatically generated by MyBatis Generator, do not modify."));
        xmlElement.addElement(new TextElement("-->"));
    }

    private void addModifyComment(XmlElement xmlElement) {
        xmlElement.addElement(new TextElement("<!--"));
        xmlElement.addElement(new TextElement("  Note - This element is automatically generated by MyBatis Generator, but can modify."));
        xmlElement.addElement(new TextElement("-->"));
    }
}