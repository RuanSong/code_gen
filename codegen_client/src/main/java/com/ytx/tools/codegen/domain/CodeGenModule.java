package com.ytx.tools.codegen.domain;

/**
 * @author Rosan
 * @version 1.0
 * @date 2017/8/28
 */
public class CodeGenModule {
    private String tableName;
    private String moduleName;
    private String module;

    public CodeGenModule(String tableName, String module, String moduleName) {
        this.tableName = tableName;
        this.module = module;
        this.moduleName = moduleName;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
