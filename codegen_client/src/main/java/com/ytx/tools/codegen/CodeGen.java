package com.ytx.tools.codegen;



import com.ytx.common.util.StringUtil;
import com.ytx.tools.codegen.config.CodeGenConfig;
import com.ytx.tools.codegen.domain.CodeGenData;
import com.ytx.tools.codegen.domain.CodeGenField;
import com.ytx.tools.codegen.domain.CodeGenModule;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 代码自动生成工具类
 *
 * @author Rosan
 * @version 1.0
 * @date 2017/8/28
 */
public class CodeGen {
    /**
     * 数据库链接
     */
    private Connection connection;
    /**
     * 处理的表结构
     */
    private CodeGenModule[] codeGenModules;
    /**
     * 元数据
     */
    private DatabaseMetaData metaData;
    /**
     * 处理完的结果数据
     */
    private Map<String, CodeGenData> codeGenDataMap = new HashMap<>();
    /**
     * 代码生成配置
     */
    private CodeGenConfig codeGenConfig;

    private static VelocityEngine velocityEngine = new VelocityEngine();

    static {
        Properties properties = new Properties();
        properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, CodeGenConfig.getBaseTemplate());
        properties.setProperty(VelocityEngine.INPUT_ENCODING, "utf-8");
        properties.setProperty(VelocityEngine.OUTPUT_ENCODING, "utf-8");
        velocityEngine.init(properties);
    }

    public CodeGen(Connection connection, CodeGenModule[] codeGenModules) {
        this.connection = connection;
        this.codeGenModules = codeGenModules;
        codeGenConfig = new CodeGenConfig();
    }

    public CodeGen(Connection connection, CodeGenModule[] codeGenModules, String template) {
        this.connection = connection;
        this.codeGenModules = codeGenModules;
        codeGenConfig = new CodeGenConfig(template);
    }

    public void init() throws SQLException, CloneNotSupportedException {
        metaData = connection.getMetaData();
        validate();
        for (CodeGenModule module : codeGenModules) {
            initTableColumn(module);
        }
    }

    private void validate() throws SQLException, CloneNotSupportedException {
        if (codeGenModules == null || codeGenModules.length == 0) {
            throw new IllegalArgumentException("Illegal table length ");
        }
        for (CodeGenModule module : codeGenModules) {
            validateTableColumn(module);
        }
    }

    public void gen() throws IOException {
        genMapper();
        genDomainExt();
        genDomain();
        genDao();
        genServiceInterface();
        genService();
        genController();
        genPage();
    }

    private void genPage() throws IOException {
        Map<String, CodeGenData> codeGenDataMap = getCodeGenDataMap();
        for (Map.Entry<String, CodeGenData> entry : codeGenDataMap.entrySet()) {
            CodeGenData codeGenData = entry.getValue();
            String filePath = codeGenConfig.getPageTemplatePath();
            Template template = velocityEngine.getTemplate(filePath);
            VelocityContext context = new VelocityContext();
            String outDirectory = codeGenConfig.getOutputDirectory().concat("page").concat(File.separator).concat(File.separator).concat(codeGenData.getModule()).concat(File.separator);
            File file = new File(outDirectory);
            if (!file.exists()) {
                file.mkdirs();
            }
            String outPutDirectory = outDirectory.concat(codeGenData.getDomainEntity()).concat("Index.vm");
            FileWriter fileWriter = new FileWriter(new File(outPutDirectory));
            context.put("codeGen", codeGenData);
            template.merge(context, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }
    }

    private void genController() throws IOException {
        Map<String, CodeGenData> codeGenDataMap = getCodeGenDataMap();
        for (Map.Entry<String, CodeGenData> entry : codeGenDataMap.entrySet()) {
            CodeGenData codeGenData = entry.getValue();
            String filePath = codeGenConfig.getControllerTemplatePath();
            Template template = velocityEngine.getTemplate(filePath);
            VelocityContext context = new VelocityContext();
            String outDirectory = codeGenConfig.getOutputDirectory().concat(codeGenData.getModule()).concat(File.separator).concat("controller").concat(File.separator);
            File file = new File(outDirectory);
            if (!file.exists()) {
                file.mkdirs();
            }
            String outPutDirectory = outDirectory.concat(File.separator).concat(codeGenData.getDomain().concat("Controller.java"));
            FileWriter fileWriter = new FileWriter(new File(outPutDirectory));
            context.put("codeGen", codeGenData);
            template.merge(context, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }
    }

    private void genMapper() throws IOException {
        Map<String, CodeGenData> codeGenDataMap = getCodeGenDataMap();
        for (Map.Entry<String, CodeGenData> entry : codeGenDataMap.entrySet()) {
            CodeGenData codeGenData = entry.getValue();
            String filePath = codeGenConfig.getMapperTemplateFilePath();
            Template template = velocityEngine.getTemplate(filePath);
            VelocityContext context = new VelocityContext();
            String outDirectory = codeGenConfig.getOutputDirectory().concat("mapper").concat(File.separator);
            File file = new File(outDirectory);
            if (!file.exists()) {
                file.mkdirs();
            }
            String outPutDirectory = outDirectory.concat(codeGenData.getDomain().concat(codeGenData.getSuffix()).concat(".xml"));
            FileWriter fileWriter = new FileWriter(new File(outPutDirectory));
            context.put("codeGen", codeGenData);
            template.merge(context, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }
    }

    private void genDomainExt() throws IOException {
        Map<String, CodeGenData> codeGenDataMap = getCodeGenDataMap();
        for (Map.Entry<String, CodeGenData> entry : codeGenDataMap.entrySet()) {
            CodeGenData codeGenData = entry.getValue();
            String filePath = codeGenConfig.getDomainExtTemplateFilePath();
            Template template = velocityEngine.getTemplate(filePath);
            VelocityContext context = new VelocityContext();
            String outDirectory = codeGenConfig.getOutputDirectory().concat(codeGenData.getModule()).concat(File.separator).concat("domain").concat(File.separator).concat(codeGenData.getModule().toLowerCase()).concat(File.separator).concat("ext").concat(File.separator);
            File file = new File(outDirectory);
            if (!file.exists()) {
                file.mkdirs();
            }
            String outPutDirectory = outDirectory.concat(codeGenData.getDomain().concat(codeGenData.getSuffix()).concat(".java"));
            FileWriter fileWriter = new FileWriter(new File(outPutDirectory));
            context.put("codeGen", codeGenData);
            template.merge(context, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }
    }

    private void genDomain() throws IOException {
        Map<String, CodeGenData> codeGenDataMap = getCodeGenDataMap();
        for (Map.Entry<String, CodeGenData> entry : codeGenDataMap.entrySet()) {
            CodeGenData codeGenData = entry.getValue();
            String filePath = codeGenConfig.getDomainTemplateFilePath();
            Template template = velocityEngine.getTemplate(filePath);
            VelocityContext context = new VelocityContext();
            String outDirectory = codeGenConfig.getOutputDirectory().concat(codeGenData.getModule()).concat(File.separator).concat("domain").concat(File.separator).concat(codeGenData.getModule().toLowerCase()).concat(File.separator);
            File file = new File(outDirectory);
            if (!file.exists()) {
                file.mkdirs();
            }
            String outPutDirectory = outDirectory.concat(codeGenData.getDomain().concat(".java"));
            FileWriter fileWriter = new FileWriter(new File(outPutDirectory));
            context.put("codeGen", codeGenData);
            template.merge(context, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }
    }

    private void genDao() throws IOException {
        Map<String, CodeGenData> codeGenDataMap = getCodeGenDataMap();
        for (Map.Entry<String, CodeGenData> entry : codeGenDataMap.entrySet()) {
            CodeGenData codeGenData = entry.getValue();
            String filePath = codeGenConfig.getDaoTemplateFilePath();
            Template template = velocityEngine.getTemplate(filePath);
            VelocityContext context = new VelocityContext();
            String outDirectory = codeGenConfig.getOutputDirectory().concat(codeGenData.getModule()).concat(File.separator).concat("dao").concat(File.separator).concat(codeGenData.getModule().toLowerCase()).concat(File.separator);
            File file = new File(outDirectory);
            if (!file.exists()) {
                file.mkdirs();
            }
            String outPutDirectory = outDirectory.concat(codeGenData.getExtDomain().concat("Dao.java"));
            FileWriter fileWriter = new FileWriter(new File(outPutDirectory));
            context.put("codeGen", codeGenData);
            template.merge(context, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }
    }

    private void genServiceInterface() throws IOException {
        Map<String, CodeGenData> codeGenDataMap = getCodeGenDataMap();
        for (Map.Entry<String, CodeGenData> entry : codeGenDataMap.entrySet()) {
            CodeGenData codeGenData = entry.getValue();
            String filePath = codeGenConfig.getServiceInterfaceTemplateFilePath();
            Template template = velocityEngine.getTemplate(filePath);
            VelocityContext context = new VelocityContext();
            String outDirectory = codeGenConfig.getOutputDirectory().concat(codeGenData.getModule()).concat(File.separator).concat("service").concat(File.separator).concat(codeGenData.getModule().toLowerCase()).concat(File.separator);
            File file = new File(outDirectory);
            if (!file.exists()) {
                file.mkdirs();
            }
            String outPutDirectory = outDirectory.concat("I").concat(codeGenData.getExtDomain().concat("Service.java"));
            FileWriter fileWriter = new FileWriter(new File(outPutDirectory));
            context.put("codeGen", codeGenData);
            template.merge(context, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }
    }

    private void genService() throws IOException {
        Map<String, CodeGenData> codeGenDataMap = getCodeGenDataMap();
        for (Map.Entry<String, CodeGenData> entry : codeGenDataMap.entrySet()) {
            CodeGenData codeGenData = entry.getValue();
            String filePath = codeGenConfig.getServiceTemplateFilePath();
            Template template = velocityEngine.getTemplate(filePath);
            VelocityContext context = new VelocityContext();
            String outDirectory = codeGenConfig.getOutputDirectory().concat(codeGenData.getModule().toLowerCase()).concat(File.separator).concat("service").concat(File.separator).concat(codeGenData.getModule().toLowerCase()).concat(File.separator).concat("impl").concat(File.separator);
            File file = new File(outDirectory);
            if (!file.exists()) {
                file.mkdirs();
            }
            String outPutDirectory = outDirectory.concat(codeGenData.getExtDomain().concat("Service.java"));
            FileWriter fileWriter = new FileWriter(new File(outPutDirectory));
            context.put("codeGen", codeGenData);
            template.merge(context, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }
    }

    private CodeGenField toCodeGen(ResultSet resultSet, String columnName) throws SQLException {
        CodeGenField codeGenField = new CodeGenField();
        String cName = getPropertiesValue(resultSet.getString("COLUMN_NAME").toLowerCase());
        String type = resultSet.getString("TYPE_NAME").toLowerCase();
        codeGenField.setTableField(columnName);
        codeGenField.setExchangeField(cName);
        codeGenField.setDomainField(cName.substring(0, 1).toUpperCase().concat(cName.substring(1)));
        codeGenField.setMetaType(resultSet.getString("TYPE_NAME"));
        codeGenField.setDomainType(typeDif(type));
        codeGenField.setComment(resultSet.getString("REMARKS"));
        return codeGenField;
    }

    private void initTableColumn(CodeGenModule module) throws SQLException, CloneNotSupportedException {
        List<String> columnList = new ArrayList<>();
        List<String> primaryFieldList = new ArrayList<>();
        List<CodeGenField> codeGenList = new ArrayList<>();
        List<CodeGenField> extCodeGenList = new ArrayList<>();
        List<CodeGenField> showCodeGenList = new ArrayList<>();

        Set<String> typeFieldSet = new HashSet<>();
        ResultSet primaryKeySet = metaData.getPrimaryKeys(null, null, module.getTableName());
        while (primaryKeySet.next()) {
            String columnName = primaryKeySet.getString("COLUMN_NAME").toLowerCase();
            primaryFieldList.add(columnName);
        }
        ResultSet resultSet = metaData.getColumns(null, "%", module.getTableName(), "%");
        while (resultSet.next()) {
            String columnName = resultSet.getString("COLUMN_NAME").toLowerCase();
            if (!columnList.contains(columnName)) {
                columnList.add(columnName);
                CodeGenField codeGenField = toCodeGen(resultSet, columnName);
                if (primaryFieldList.contains(columnName)) {
                    codeGenField.setPrimary(true);
                }
                if (("BigDecimal").equals(codeGenField.getDomainType())) {
                    typeFieldSet.add("import java.math.BigDecimal;");
                } else if (("Date").equals(codeGenField.getDomainType())) {
                    typeFieldSet.add("import java.util.Date;");
                    CodeGenField dateStartField = codeGenField.clone();
                    dateStartField.setDomainField(dateStartField.getDomainField().concat("Start"));
                    dateStartField.setMetaType("VARCHAR");
                    dateStartField.setExchangeField(dateStartField.getExchangeField().concat("Start"));
                    dateStartField.setDomainType("String");
                    dateStartField.setComment(dateStartField.getComment().concat("(开始)"));
                    CodeGenField dateEndField = codeGenField.clone();
                    dateEndField.setDomainField(dateEndField.getDomainField().concat("End"));
                    dateEndField.setMetaType("VARCHAR");
                    dateEndField.setExchangeField(dateEndField.getExchangeField().concat("End"));
                    dateEndField.setDomainType("String");
                    dateEndField.setComment(dateEndField.getComment().concat("(结束)"));
                    extCodeGenList.add(dateStartField);
                    extCodeGenList.add(dateEndField);
                }
                codeGenList.add(codeGenField);
            }
        }
        String[] tableName = module.getTableName().split("_");
        if (tableName == null || tableName.length == 0) {
            return;
        }
        String domain = "";
        if (tableName.length > 1) {
            for (int i = 1; i < tableName.length; i++) {
                domain = domain + tableName[i].substring(0, 1).toUpperCase() + tableName[i].substring(1);
            }
        }
        showCodeGenList.addAll(codeGenList);
        showCodeGenList.addAll(extCodeGenList);

        CodeGenData codeGenData = new CodeGenData();
        codeGenData.setCodeGenFieldList(codeGenList);
        codeGenData.setExtCodeGenFieldList(extCodeGenList);
        codeGenData.setShowCodeGenFieldList(showCodeGenList);
        codeGenData.setImportTypeSet(typeFieldSet);
        codeGenData.setAuth(System.getProperty("user.name"));
        codeGenData.setDomain(domain);
        codeGenData.setTableName(module.getTableName());
        codeGenData.setModule(module.getModule());
        codeGenData.setModuleName(module.getModuleName());
        codeGenData.setSuffix(codeGenConfig.getDomainSuffix());
        codeGenData.setDomainEntity(domain.substring(0, 1).toLowerCase() + domain.substring(1));
        codeGenData.setExtDomainEntity(codeGenData.getDomainEntity().concat(codeGenConfig.getDomainSuffix()));
        codeGenData.setExtDomain(codeGenData.getExtDomainEntity().substring(0, 1).toUpperCase() + codeGenData.getExtDomainEntity().substring(1));
        codeGenData.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        codeGenDataMap.put(codeGenData.getDomainEntity(), codeGenData);
    }

    private String getPropertiesValue(String value) {
        if (StringUtil.isEmpty(value)) {
            return "";
        }
        String[] valueArray = value.split("_");
        if (valueArray.length == 1) {
            return valueArray[0];
        }
        StringBuilder sb = new StringBuilder();
        sb.append(valueArray[0]);
        for (int i = 1; i < valueArray.length; i++) {
            sb.append(valueArray[i].substring(0, 1).toUpperCase() + valueArray[i].substring(1));
        }
        return sb.toString();
    }

    private String typeDif(String sqlType) {
        sqlType = sqlType.toUpperCase();
        if (("FLOAT").equals(sqlType) || ("DOUBLE").equals(sqlType)) {
            return "double";
        } else if (("DECIMAL").equals(sqlType)) {
            return "BigDecimal";
        } else if (("BIGINT").equals(sqlType) || ("bigint").equals(sqlType)) {
            return "Long";
        } else if (("TINYINT").equals(sqlType) || ("SMALLINT").equals(sqlType) || ("MEDIUMINT").equals(sqlType)
                || ("INT").equals(sqlType) || ("INTEGER").equals(sqlType) || ("BIT").equals(sqlType)) {
            return "Integer";
        } else if (("DATE").equals(sqlType) || ("TIME").equals(sqlType) || ("YEAR").equals(sqlType) || ("DATETIME").equals(sqlType) ||
                ("TIMESTAMP").equals(sqlType)) {
            return "Date";
        }
        return "String";
    }

    public void validateTableColumn(CodeGenModule module) throws SQLException {
        List<String> columnList = new ArrayList<>();
        if (module == null) {
            throw new IllegalArgumentException("Illegal module ");
        }
        if (StringUtil.isEmpty(module.getModuleName()) || StringUtil.isEmpty(module.getTableName()) || StringUtil.isEmpty(module.getModule())) {
            throw new IllegalArgumentException("Illegal module,table_name or module necessory is null  ");
        }
        ResultSet resultSet = metaData.getColumns(null, "%", module.getTableName(), "%");
        while (resultSet.next()) {
            columnList.add(resultSet.getString("COLUMN_NAME").toLowerCase());
        }
        if (columnList.size() == 0) {
            throw new IllegalArgumentException("Illegal table [" + module.getTableName() + "] column size is zero");
        }
    }

    public Map<String, CodeGenData> getCodeGenDataMap() {
        return codeGenDataMap;
    }
}
