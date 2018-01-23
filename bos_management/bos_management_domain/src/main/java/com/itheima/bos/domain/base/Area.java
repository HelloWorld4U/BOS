package com.itheima.bos.domain.base;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @description:地域信息实体类，主要包含 省市区(县)
 */
@Entity
@Table(name = "T_AREA")
public class Area {
    public Area() {
    }

    public Area(String province, String city, String district, String postcode, String citycode,
            String shortcode) {
        super();
        this.province = province;
        this.city = city;
        this.district = district;
        this.postcode = postcode;
        this.citycode = citycode;
        this.shortcode = shortcode;
    }

    @Id
    @Column(name = "C_ID")
    @GeneratedValue
    private Long id;
    @Column(name = "C_PROVINCE")
    private String province; // 省
    @Column(name = "C_CITY")
    private String city; // 城市
    @Column(name = "C_DISTRICT")
    private String district; // 区域
    @Column(name = "C_POSTCODE")
    private String postcode; // 邮编
    @Column(name = "C_CITYCODE")
    private String citycode; // 城市编码
    @Column(name = "C_SHORTCODE")
    private String shortcode; // 简码

    @OneToMany(mappedBy = "area")
    private Set<SubArea> subareas = new HashSet<SubArea>();

    //net.sf.json将对象转化为json的原理是调用bean对象的get方法
    //getA() 转化成的json的key为A:""
    //前端需要返回的jason中含有name的key，所以构造一个getName方法返回含有name为key的json字符串
    
    public String getName(){
        return province+city+district;
    }
    
    public Long getId() {
        return id;     
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    public Set<SubArea> getSubareas() {
        return subareas;
    }

    public void setSubareas(Set<SubArea> subareas) {
        this.subareas = subareas;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Area [id=" + id + ", province=" + province + ", city=" + city
                + ", district=" + district + ", postcode=" + postcode
                + ", citycode=" + citycode + ", shortcode=" + shortcode + "]";
    }

}
