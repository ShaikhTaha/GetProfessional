package com.getprofessional.models;

public class OrderHistory {

    private int order_id;
    private int cust_id;
    private String emp_image;
    private String emp_name;
    private String service_name;
    private String order_date;
    private double order_amt;

    public OrderHistory(int order_id, int cust_id, String emp_image, String emp_name, String service_name, String order_date, double order_amt) {
        this.order_id = order_id;
        this.cust_id = cust_id;
        this.emp_image = emp_image;
        this.emp_name = emp_name;
        this.service_name = service_name;
        this.order_date = order_date;
        this.order_amt = order_amt;
    }

    public int getOrder_id() {
        return order_id;
    }

    public int getCust_id() {
        return cust_id;
    }

    public String getEmp_image() {
        return emp_image;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public String getService_name() {
        return service_name;
    }

    public String getOrder_date() {
        return order_date;
    }

    public double getOrder_amt() {
        return order_amt;
    }
}
