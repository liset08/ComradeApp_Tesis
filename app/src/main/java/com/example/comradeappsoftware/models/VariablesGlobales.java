package com.example.comradeappsoftware.models;

import java.util.List;

public class VariablesGlobales {

    private static int id_sede;
    public static int getId_sede() {
        return id_sede;
    }

    public static void setId_sede(int id_sede) {
        VariablesGlobales.id_sede = id_sede;
    }


    private static int id_pedido;
    public static int getId_pedido() {
        return id_pedido;
    }

    public static void setId_pedido(int id_pedido) {
        VariablesGlobales.id_pedido = id_pedido;
    }
    private static int id_producto_predict;

    public static int getId_producto_predict() {
        return id_producto_predict;
    }

    public static void setId_producto_predict(int id_producto_predict) {
        VariablesGlobales.id_producto_predict = id_producto_predict;
    }

    public static List<Orders> getOrders() {
        return orders;
    }

    public static void setOrders(List<Orders> orders) {
        VariablesGlobales.orders = orders;
    }

    private static List<Orders> orders;

    public static int getId_order() {
        return id_order;
    }

    public static void setId_order(int id_order) {
        VariablesGlobales.id_order = id_order;
    }

    private static int id_order;







}
