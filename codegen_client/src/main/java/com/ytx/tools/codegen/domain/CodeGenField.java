package com.ytx.tools.codegen.domain;

/**
 * @author Rosan
 * @version 1.0
 * @date 2017/8/28
 */
public class CodeGenField implements Cloneable {
    /**
     * 数据表字段名称
     */
    private String tableField;
    /**
     * 是否是主键
     */
    private boolean isPrimary;
    /**
     * 构建之后的字段名称
     */
    private String exchangeField;
    /**
     * 领域字段
     */
    private String domainField;
    /**
     * 字段注释
     */
    private String comment;
    /**
     * 元数据数据类型
     */
    private String metaType;
    /**
     * 领域类型
     */
    private String domainType;

    /**
     * 字段的顺序号
     */
    private Integer orderNumber;

    public String getTableField() {
        return tableField;
    }

    public void setTableField(String tableField) {
        this.tableField = tableField;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public String getExchangeField() {
        return exchangeField;
    }

    public void setExchangeField(String exchangeField) {
        this.exchangeField = exchangeField;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMetaType() {
        return metaType;
    }

    public void setMetaType(String metaType) {
        this.metaType = metaType;
    }

    public String getDomainType() {
        return domainType;
    }

    public void setDomainType(String domainType) {
        this.domainType = domainType;
    }

    public String getDomainField() {
        return domainField;
    }

    public void setDomainField(String domainField) {
        this.domainField = domainField;
    }

    @Override
    public CodeGenField clone() throws CloneNotSupportedException {
        CodeGenField codeGenField = new CodeGenField();
        codeGenField.setDomainField(this.getDomainField());
        codeGenField.setPrimary(this.isPrimary());
        codeGenField.setComment(this.getComment());
        codeGenField.setDomainType(this.getDomainType());
        codeGenField.setExchangeField(this.getExchangeField());
        codeGenField.setMetaType(this.getMetaType());
        codeGenField.setTableField(this.getTableField());
        return codeGenField;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}
