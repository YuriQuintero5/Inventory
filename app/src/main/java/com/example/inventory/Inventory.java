package com.example.inventory;

public class Inventory {
    private String code;
    private String description;
    private String quantity;
    private String weight;
    private String size;
    private int Foto;
    private String id;


    public Inventory(String code, String description, String quantity, String weight, String size, int Foto){
        this.code = code;
        this.description = description;
        this.quantity = quantity;
        this.weight = weight;
        this.size = size;
        this.Foto = Foto;
    }
    public String getId() {
        return id;
    }

    public Inventory(){

    }

    public Inventory(String code, String description, String quantity, String weight, String size, int Foto, String id){
        this.code = code;
        this.description = description;
        this.quantity = quantity;
        this.weight = weight;
        this.size = size;
        this.Foto = Foto;
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getFoto() {
        return Foto;
    }

    public void setFoto(int foto) {
        Foto = foto;
    }

    public void guardar(){
        Datos.guardar(this);
    }

    public void eliminar(){
        Datos.eliminar(this);
    }

}
