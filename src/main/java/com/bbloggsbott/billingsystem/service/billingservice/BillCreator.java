package com.bbloggsbott.billingsystem.service.billingservice;

import com.bbloggsbott.billingsystem.integration.dbusersdao.User;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import com.bbloggsbott.billingsystem.service.billingservice.wagu.components.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;

public class BillCreator {
    private static String company;
    List<String> t1Headers;
    List<List<String>> t1Rows;
    String t2Desc;
    List<String> t2Headers;
    List<List<String>> t2Rows;
    List<Integer> t2ColWidths;
    String t3Desc;
    String summary, summaryVal;
    User user;
    String customerEmail, billNo;

    public BillCreator(User user, String customerName,String customerEmail, String billNo, List<List<String>> items, double total){
        this.user = user;
        this.customerEmail = customerEmail;
        this.billNo = billNo;
        company = "Company Name\nCompany Address\nCompany Phone No.s\n\nCustomer Invoice\n\n";
        t1Headers = Arrays.asList("INFO","CUSTOMER");
        t1Rows = Arrays.asList(Arrays.asList("DATE: "+new SimpleDateFormat("dd/MM/yyyy").format(new Date()),customerName),
                Arrays.asList("TIME: "+new SimpleDateFormat("HH:mm:ss").format(new Date()), ""),
                Arrays.asList("BILL NO: "+billNo, ""));
        t2Desc = "SELLING DETAILS";
        t2Headers = Arrays.asList("ITEM","RATE", "QTY", "PRICE");
        t2Rows = items;
        t2ColWidths = Arrays.asList(17, 9, 5, 12);
        t3Desc = "Summary";
        summary = "TOTAL: \n";
        summaryVal = Double.toString(total)+"\n";

    }

    public String generateBill(){
        Board b = new Board(48);
        b.setInitialBlock(new Block(b, 46, 7, company).allowGrid(false).setBlockAlign(Block.BLOCK_CENTRE).setDataAlign(Block.DATA_CENTER));
        b.appendTableTo(0, Board.APPEND_BELOW, new Table(b, 48, t1Headers, t1Rows));
        b.getBlock(3).setBelowBlock(new Block(b, 46, 1, t2Desc).setDataAlign(Block.DATA_CENTER));
        b.appendTableTo(5, Board.APPEND_BELOW, new Table(b, 48, t2Headers, t2Rows, t2ColWidths));
        b.getBlock(10).setBelowBlock(new Block(b, 46, 1, t3Desc).setDataAlign(Block.DATA_CENTER));
        Block summaryBlock = new Block(b, 35, 9, summary).allowGrid(false).setDataAlign(Block.DATA_MIDDLE_RIGHT);
        b.getBlock(14).setBelowBlock(summaryBlock);
        Block summaryValBlock = new Block(b, 12, 9, summaryVal).allowGrid(false).setDataAlign(Block.DATA_MIDDLE_RIGHT);
        summaryBlock.setRightBlock(summaryValBlock);
        String bill = b.invalidate().build().getPreview();
        bill = bill+"\nUser: "+user.getName()+"("+user.getUserName()+")\n";
        return bill;
    }

    public boolean gernerateAndMailBill(){
        BillMailer bm = new BillMailer();
        if(bm.mailBill(generateBill(), customerEmail, billNo)){
            return true;
        }
        return false;
    }

    public static int getBillNo(){
        JSONParser parser = new JSONParser();
        try{
            FileReader fr = new FileReader("billing.json");
            Object obj = parser.parse(fr);
            JSONObject jsonObject = (JSONObject) obj;
            String billNo = (String) jsonObject.get("billNo");
            fr.close();
            return Integer.parseInt(billNo);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public static boolean incrementBillNo(){
        JSONParser parser = new JSONParser();
        try {
            FileReader fr = new FileReader("billing.json");
            Object obj = parser.parse(fr);
            JSONObject jsonObject = (JSONObject) obj;
            fr.close();
            FileWriter fw = new FileWriter("billing.json");
            int billNo = Integer.parseInt((String) jsonObject.get("billNo"));
            billNo += 1;
            jsonObject.put("billNo",Integer.toString(billNo));
            fw.write(jsonObject.toJSONString());
            fw.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<List<String>> processForPrinting(List<String[]> items){
        List<List<String>> toPrint = Arrays.asList(toList(items.get(0)));
        for(int i = 1;i<items.size();i++){
            toPrint.add(toList(items.get(i)));
        }
        return toPrint;
    }

    public static List<String> toList(String[] toConvert){
        return Arrays.asList(toConvert[0],toConvert[1],toConvert[2],toConvert[3]);
    }
}