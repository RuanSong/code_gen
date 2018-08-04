package com.ytx.tools.codegen.config;

import java.io.File;

/**
 * @author Rosan
 * @version 1.0
 * @date 2017/8/28
 */
public class CodeGenConfig {
    /**
     * 默认模板目录
     */
    private String template = "normal";
    /**
     * 扩展domain文件后缀
     */
    public String domainSuffix = "Admin";

    public CodeGenConfig() {
    }

    public CodeGenConfig(String template) {
        this.template = template;
    }

    /**
     * 默认模板目录
     */
    public static final String TEMPLATE_DIRECTORY = "template";
    /**
     * 默认输出文件目录
     */
    public static final String OUTPUT_DIRECTORY = "output";

    /**
     * dao默认模板文件
     */
    public static final String DAO_TEMPLATE = "dao.vm";
    /**
     * 领域模型默认模板文件
     */
    public static final String DOMAIN_TEMPLATE = "domain.vm";
    /**
     * 领域扩展模型默认模板文件
     */
    public static final String DOMAIN_EXT_TEMPLATE = "domainExt.vm";
    /**
     * 映射文件默认模板文件
     */
    public static final String MAPPER_TEMPLATE = "mapper.vm";
    /**
     * 服务实现文件默认模板文件
     */
    public static final String SERVICE_TEMPLATE = "service.vm";
    /**
     * 服务接口默认模板文件
     */
    public static final String SERVICE_INTERFACE_TEMPLATE = "serviceInterface.vm";
    /**
     * Controller层模板文件
     */
    public static final String CONTROLLER_TEMPLATE = "controller.vm";
    /**
     * 渲染页面模板文件
     */
    public static final String PAGE_TEMPLATE = "page.vm";

    public static String getBaseTemplate() {
        StringBuilder stringBuilder = new StringBuilder();
        String userDir = System.getProperty("user.dir");
        stringBuilder.append(userDir.substring(0, userDir.lastIndexOf(File.separator)));
        stringBuilder.append(File.separator);
        stringBuilder.append(TEMPLATE_DIRECTORY);
        return stringBuilder.toString();
    }

    public String getOutputDirectory() {
        StringBuilder stringBuilder = new StringBuilder();
        String userDir = System.getProperty("user.dir");
        stringBuilder.append(userDir.substring(0, userDir.lastIndexOf(File.separator)));
        stringBuilder.append(File.separator);
        stringBuilder.append(TEMPLATE_DIRECTORY);
        stringBuilder.append(File.separator);
        stringBuilder.append(OUTPUT_DIRECTORY);
        stringBuilder.append(File.separator);
        stringBuilder.append(template);
        stringBuilder.append(File.separator);
        return stringBuilder.toString();
    }

    protected StringBuilder getBaseTemplatePath() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(template);
        stringBuilder.append(File.separator);
        return stringBuilder;
    }

    public String getDaoTemplateFilePath() {
        return getBaseTemplatePath().append(DAO_TEMPLATE).toString();
    }


    public String getServiceTemplateFilePath() {
        return getBaseTemplatePath().append(SERVICE_TEMPLATE).toString();
    }

    public String getServiceInterfaceTemplateFilePath() {
        return getBaseTemplatePath().append(SERVICE_INTERFACE_TEMPLATE).toString();
    }

    public String getDomainTemplateFilePath() {
        return getBaseTemplatePath().append(DOMAIN_TEMPLATE).toString();
    }

    public String getDomainExtTemplateFilePath() {
        return getBaseTemplatePath().append(DOMAIN_EXT_TEMPLATE).toString();
    }

    public String getMapperTemplateFilePath() {
        return getBaseTemplatePath().append(MAPPER_TEMPLATE).toString();
    }

    public String getControllerTemplatePath() {
        return getBaseTemplatePath().append(CONTROLLER_TEMPLATE).toString();
    }

    public String getPageTemplatePath() {
        return getBaseTemplatePath().append(PAGE_TEMPLATE).toString();
    }

    public String getTemplate() {
        return template;
    }

    public String getDomainSuffix() {
        return domainSuffix;
    }

    public void setDomainSuffix(String domainSuffix) {
        this.domainSuffix = domainSuffix;
    }
}
