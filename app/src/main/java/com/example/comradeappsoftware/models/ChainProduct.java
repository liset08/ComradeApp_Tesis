package com.example.comradeappsoftware.models;

public class ChainProduct {

    private Integer idproductosede;
    private Double precio;
    private Integer idproducto;
    private Integer idsede;

    public ChainProduct(){}

    public ChainProduct(Integer idproductosede, Double precio, Integer idproducto, Integer idsede) {
        this.idproductosede = idproductosede;
        this.precio = precio;
        this.idproducto = idproducto;
        this.idsede = idsede;
    }

    public Integer getIdproductosede() {
        return idproductosede;
    }

    public void setIdproductosede(Integer idproductosede) {
        this.idproductosede = idproductosede;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public Integer getIdsede() {
        return idsede;
    }

    public void setIdsede(Integer idsede) {
        this.idsede = idsede;
    }
}
