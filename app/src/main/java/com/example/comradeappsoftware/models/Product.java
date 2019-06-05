package com.example.comradeappsoftware.models;

public class Product {

    private Integer idproducto;
    private String nombre;
    private Integer idmarca;

    public Product(){

    }

    public Product(Integer idproducto, String nombre, Integer idmarca) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.idmarca = idmarca;
    }

    public Integer getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdmarca() {
        return idmarca;
    }

    public void setIdmarca(Integer idmarca) {
        this.idmarca = idmarca;
    }
}
