package com.bbloggsbott.billingsystem.service.productservice;

import com.bbloggsbott.billingsystem.integration.dbproductdao.Product;
import com.bbloggsbott.billingsystem.service.connectionservice.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductLookup {
    private static Connection conn = ConnectionFactory.getConnection("products");


    public static ArrayList<Product> lookupByID(int ID){
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from products where id = ?");
            ps.setInt(1,ID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                products.add(new Product(rs.getInt("id"),rs.getString("name"),rs.getBigDecimal("buyprice"),rs.getBigDecimal("sellprice"),rs.getString("type"),rs.getBigDecimal("stock")));
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static ArrayList<Product> lookupByName(String name){
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from products where name like %?%");
            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                products.add(new Product(rs.getInt("id"),rs.getString("name"),rs.getBigDecimal("buyprice"),rs.getBigDecimal("sellprice"),rs.getString("type"),rs.getBigDecimal("stock")));
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Product> lookupAll(){
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from products");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                products.add(new Product(rs.getInt("id"),rs.getString("name"),rs.getBigDecimal("buyprice"),rs.getBigDecimal("sellprice"),rs.getString("type"),rs.getBigDecimal("stock")));
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}