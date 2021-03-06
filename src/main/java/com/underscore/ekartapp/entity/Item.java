/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.entity;

import com.underscore.ekartapp.form.ItemForm;
import com.underscore.ekartapp.form.ItemUpdateForm;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author johnythomas
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i")})
public class Item implements Serializable {

    public static enum Status {
        INACTIVE((short) 0),
        ACTIVE((short) 1),
        DELETED((short) 2);

        public final short value;

        private Status(short value) {
            this.value = value;
        }
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    private BigDecimal price;
    @Basic(optional = false)
    @NotNull
    private int quantity;
    @Size(max = 45)
    private String gender;
    @Size(max = 45)
    private String age;
    @Size(max = 45)
    private String colour;
    private Short deliveryType;
    @Basic(optional = false)
    @NotNull
    private short status;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @JoinColumn(name = "userId", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User userId;
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category categoryId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId", fetch = FetchType.LAZY)
    private List<ItemImage> itemImageList;
    @OneToMany(mappedBy = "itemId", fetch = FetchType.LAZY)
    private List<CartItem> cartItemList;

    public Item() {
    }

    public Item(Integer id) {
        this.id = id;
    }

    public Item(Integer id, String name, String description, BigDecimal price, int quantity, short status, Date createdDate, Date updatedDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Item(ItemForm form, User user) {
        this.name = form.getName();
        this.description = form.getDescription();
        this.price = form.getPrice();
        this.quantity = form.getQuantity();
        this.categoryId = new Category(form.getCategoryId());
        this.colour = form.getColour();
        this.gender = form.getGender();
        this.age = form.getAge();
        this.userId = user;
        this.status = Status.ACTIVE.value;
        Date date = new Date();
        this.createdDate = date;
        this.updatedDate = date;
    }

    public void update(ItemUpdateForm form, User user) {
        this.name = form.getName();
        this.description = form.getDescription();
        this.price = form.getPrice();
        this.quantity = form.getQuantity();
        this.categoryId = new Category(form.getCategoryId());
        this.colour = form.getColour();
        this.gender = form.getGender();
        this.age = form.getAge();
        Date date = new Date();
        this.updatedDate = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Short getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Short deliveryType) {
        this.deliveryType = deliveryType;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public List<ItemImage> getItemImageList() {
        return itemImageList;
    }

    public void setItemImageList(List<ItemImage> itemImageList) {
        this.itemImageList = itemImageList;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.underscore.ekartapp.entity.Item[ id=" + id + " ]";
    }

}
