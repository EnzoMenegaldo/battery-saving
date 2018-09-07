package com.emenegal.battery_saving.component;

public class AnnotationModel {

    private String name;
    private String value;
    private String type;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

   public AnnotationModel(String name, String type, String value){
        this.name = name;
        this.type = type;
        this.value = value;
   }
}
