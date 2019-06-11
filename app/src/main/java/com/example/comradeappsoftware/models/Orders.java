package com.example.comradeappsoftware.models;

import java.util.List;

public class Orders {


    private List<OrderDetails> orderdetail;
    private Sede sedes_idsede;
    private int usuarios_idusuario;
    private String fec_pedido;
    private int idpedido;

    public List<OrderDetails> getOrderdetail() {
        return orderdetail;
    }

    public void setOrderdetail(List<OrderDetails> orderdetail) {
        this.orderdetail = orderdetail;
    }

    public Sede getSedes_idsede() {
        return sedes_idsede;
    }

    public void setSedes_idsede(Sede sedes_idsede) {
        this.sedes_idsede = sedes_idsede;
    }

    public int getUsuarios_idusuario() {
        return usuarios_idusuario;
    }

    public void setUsuarios_idusuario(int usuarios_idusuario) {
        this.usuarios_idusuario = usuarios_idusuario;
    }

    public String getFec_pedido() {
        return fec_pedido;
    }

    public void setFec_pedido(String fec_pedido) {
        this.fec_pedido = fec_pedido;
    }

    public int getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(int idpedido) {
        this.idpedido = idpedido;
    }}


