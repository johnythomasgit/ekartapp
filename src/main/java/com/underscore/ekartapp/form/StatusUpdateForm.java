/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.form;

import javax.validation.constraints.NotNull;

/**
 *
 * @author johny
 */
public class StatusUpdateForm  {
        
    @NotNull(message="id.is.empty")
    private Integer id;
    
    @NotNull(message="status.is.empty")
    private Short status;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    
}
