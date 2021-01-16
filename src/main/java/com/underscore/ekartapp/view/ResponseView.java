/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.view;

/**
 * @author johny
 */
public class ResponseView {

    private Object result;
    private String errorCode;
    private String errorMessage;

    public ResponseView(Object result, String errorCode, String errorMessage) {
        this.result = result;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ResponseView(Object result) {
        this.result = result;
        this.errorCode = "0";
        this.errorMessage = "";
    }

    public ResponseView() {
        this.result = "";
        this.errorCode = "0";
        this.errorMessage = "";
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
