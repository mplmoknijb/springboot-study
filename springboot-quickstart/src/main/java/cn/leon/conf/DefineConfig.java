package cn.leon.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class DefineConfig implements Serializable {

    @Value("${cn.leon.name}")
    private String name;

    @Value("${cn.leon.title}")
    private String title;

    @Value("${cn.leon.desc}")
    private String desc;

    @Value("${cn.leon.value}")
    private String value;

    @Value("${cn.leon.number}")
    private Integer number;

    @Value("${cn.leon.bignumber}")
    private Long bignumber;

    @Value("${cn.leon.random1}")
    private Integer random1;

    @Value("${cn.leon.random2}")
    private Integer random2;

    @Override
    public String toString() {
        return "DefinitionConfig [name=" + name + ", title=" + title + ", desc=" + desc + ", value=" + value + ", number=" + number + ", bignumber=" + bignumber + ", random1=" + random1 + ", random2=" + random2 + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getBignumber() {
        return bignumber;
    }

    public void setBignumber(Long bignumber) {
        this.bignumber = bignumber;
    }

    public Integer getRandom1() {
        return random1;
    }

    public void setRandom1(Integer random1) {
        this.random1 = random1;
    }

    public Integer getRandom2() {
        return random2;
    }

    public void setRandom2(Integer random2) {
        this.random2 = random2;
    }
}
