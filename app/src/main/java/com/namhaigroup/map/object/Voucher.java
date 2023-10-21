package com.namhaigroup.map.object;

public class Voucher {
    private int id;
    private String voucher_code;
    private double sale;

    public Voucher() {
    }

    public Voucher(int id, String voucher_code, double sale) {
        this.id = id;
        this.voucher_code = voucher_code;
        this.sale = sale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoucher_code() {
        return voucher_code;
    }

    public void setVoucher_code(String voucher_code) {
        this.voucher_code = voucher_code;
    }

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }
}
