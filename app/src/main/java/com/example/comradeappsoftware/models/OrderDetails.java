package com.example.comradeappsoftware.models;

public class OrderDetails {


    private Product idproducto;
    private String descuento;
    private String precioxunidad;
    private int cantidad;
    private int idpedido;
    private int idpedidodeta;

    public Product getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Product idproducto) {
        this.idproducto = idproducto;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getPrecioxunidad() {
        return precioxunidad;
    }

    public void setPrecioxunidad(String precioxunidad) {
        this.precioxunidad = precioxunidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(int idpedido) {
        this.idpedido = idpedido;
    }

    public int getIdpedidodeta() {
        return idpedidodeta;
    }

    public void setIdpedidodeta(int idpedidodeta) {
        this.idpedidodeta = idpedidodeta;
    }

    public static class Idproducto {
        private int idmarca;
        private String nombre;
        private int idproducto;

        public int getIdmarca() {
            return idmarca;
        }

        public void setIdmarca(int idmarca) {
            this.idmarca = idmarca;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public int getIdproducto() {
            return idproducto;
        }

        public void setIdproducto(int idproducto) {
            this.idproducto = idproducto;
        }
    }
}
