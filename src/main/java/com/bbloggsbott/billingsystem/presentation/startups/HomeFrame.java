package com.bbloggsbott.billingsystem.presentation.startups;

import com.bbloggsbott.billingsystem.integration.dbusersdao.User;
import com.bbloggsbott.billingsystem.presentation.producthandling.AddProductFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeFrame extends JFrame implements ActionListener {

    private JLabel title;
    private JButton addProduct, deleteProduct, updateProduct, billing, searchProduct, stockStatement,userManagement;
    private JPanel top,bottom;
    private GridBagConstraints gbc;
    User user;

    public HomeFrame(User user){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new SpringLayout());
        ImageIcon img = new ImageIcon("resources/billLogo.png");
        this.user = user;
        top = new JPanel();
        top.setLayout(new FlowLayout());
        bottom = new JPanel();
        bottom.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        title = new JLabel("Shop Management");
        addProduct = new JButton("Add Product");
        deleteProduct = new JButton("Delete Product");
        updateProduct = new JButton("Update Product");
        billing = new JButton("Billing");
        searchProduct = new JButton("Search");
        stockStatement = new JButton("Stock Statement");


        top.add(title);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        bottom.add(addProduct,gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        bottom.add(deleteProduct,gbc);
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        bottom.add(updateProduct,gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        bottom.add(billing,gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        bottom.add(searchProduct,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        bottom.add(stockStatement,gbc);

        if(user.getAdmin()){
            userManagement = new JButton("Users");
            gbc.gridx = 0;
            gbc.gridy = 4;
            bottom.add(userManagement,gbc);
            userManagement.addActionListener(this);
        }

        setVisible(true);
        add(top,BorderLayout.NORTH);
        add(bottom,BorderLayout.CENTER);
        setSize(300,300);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addProduct){
            AddProductFrame apf = new AddProductFrame();
        }
    }
}
