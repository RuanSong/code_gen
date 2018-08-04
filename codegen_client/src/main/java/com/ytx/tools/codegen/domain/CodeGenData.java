package com.ytx.tools.codegen.domain;

import java.util.List;
import java.util.Set;

/**
 * @author Rosan
 * @version 1.0
 * @date 2017/8/28
 */
public class CodeGenData {
    /**
     * 模块
     */
    private String module;
    /**
     * 模块名称
     */
    private String moduleName;
    /**
     * 表名领域名称
     */
    private String domain;
    /**
     * 拓展领域名称
     */
    private String extDomain;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 表名转化实体名称
     */
    private String domainEntity;
    /**
     * 扩展实体名称
     */
    private String extDomainEntity;
    /**
     * 开发者
     */
    private String auth;
    /**
     * 日期
     */
    private String date;
    /**
     * 拓展后缀
     */
    private String suffix;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    /**
     * 模块字段列表
     */
    private List<CodeGenField> codeGenFieldList;
    /**
     * 扩展模块字段列表
     */
    private List<CodeGenField> extCodeGenFieldList;
    /**
     * 模块字段列表(包括扩展字段)
     */
    private List<CodeGenField> showCodeGenFieldList;
    /**
     * 字段类型列表
     */
    private Set<String> importTypeSet;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDomainEntity() {
        return domainEntity;
    }

    public void setDomainEntity(String domainEntity) {
        this.domainEntity = domainEntity;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<CodeGenField> getCodeGenFieldList() {
        return codeGenFieldList;
    }

    public void setCodeGenFieldList(List<CodeGenField> codeGenFieldList) {
        this.codeGenFieldList = codeGenFieldList;
    }

    public List<CodeGenField> getExtCodeGenFieldList() {
        return extCodeGenFieldList;
    }

    public void setExtCodeGenFieldList(List<CodeGenField> extCodeGenFieldList) {
        this.extCodeGenFieldList = extCodeGenFieldList;
    }

    public List<CodeGenField> getShowCodeGenFieldList() {
        return showCodeGenFieldList;
    }

    public void setShowCodeGenFieldList(List<CodeGenField> showCodeGenFieldList) {
        this.showCodeGenFieldList = showCodeGenFieldList;
    }

    public Set<String> getImportTypeSet() {
        return importTypeSet;
    }

    public void setImportTypeSet(Set<String> importTypeSet) {
        this.importTypeSet = importTypeSet;
    }

    public String getExtDomainEntity() {
        return extDomainEntity;
    }

    public void setExtDomainEntity(String extDomainEntity) {
        this.extDomainEntity = extDomainEntity;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getExtDomain() {
        return extDomain;
    }

    public void setExtDomain(String extDomain) {
        this.extDomain = extDomain;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
