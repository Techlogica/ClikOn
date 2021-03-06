package com.tids.clikonservice.model;

public class ScannedProductModel {

    private String productDocId;
    private String productScannedId;
    private String productName;
    private String productSerialNumber;
    private String productBatchNumber;
    private String productComplaint;
    private String pageFlag;
    private String productReferId;
    private String productCode;
    private String customerName;
    private String customerCode;

    public ScannedProductModel(String productDocId, String productScannedId, String productName,
                               String productSerialNumber, String productBatchNumber,
                               String productComplaint, String productReferId, String productCode,
                               String customerName, String customerCode) {
        this.productDocId = productDocId;
        this.productScannedId = productScannedId;
        this.productName = productName;
        this.productSerialNumber = productSerialNumber;
        this.productBatchNumber = productBatchNumber;
        this.productComplaint = productComplaint;
        this.productReferId = productReferId;
        this.productCode = productCode;
        this.customerName = customerName;
        this.customerCode = customerCode;
    }

    public ScannedProductModel(String productDocId, String productScannedId, String productName,
                               String productSerialNumber, String productBatchNumber, String productComplaint,
                               String pageFlag, String productReferId, String productCode,
                               String customerName, String customerCode) {
        this.productDocId = productDocId;
        this.productScannedId = productScannedId;
        this.productName = productName;
        this.productSerialNumber = productSerialNumber;
        this.productBatchNumber = productBatchNumber;
        this.productComplaint = productComplaint;
        this.pageFlag = pageFlag;
        this.productReferId = productReferId;
        this.productCode = productCode;
        this.customerName = customerName;
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductReferId() {
        return productReferId;
    }

    public void setProductReferId(String productReferId) {
        this.productReferId = productReferId;
    }

    public String getProductDocId() {
        return productDocId;
    }

    public void setProductDocId(String productDocId) {
        this.productDocId = productDocId;
    }

    public String getProductScannedId() {
        return productScannedId;
    }

    public void setProductScannedId(String productScannedId) {
        this.productScannedId = productScannedId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSerialNumber() {
        return productSerialNumber;
    }

    public void setProductSerialNumber(String productSerialNumber) {
        this.productSerialNumber = productSerialNumber;
    }

    public String getProductBatchNumber() {
        return productBatchNumber;
    }

    public void setProductBatchNumber(String productBatchNumber) {
        this.productBatchNumber = productBatchNumber;
    }

    public String getProductComplaint() {
        return productComplaint;
    }

    public void setProductComplaint(String productComplaint) {
        this.productComplaint = productComplaint;
    }

    public String getPageFlag() {
        return pageFlag;
    }

    public void setPageFlag(String pageFlag) {
        this.pageFlag = pageFlag;
    }
}
