package com.caunk94.mycrud2;

/**
 * Created by caunk94 on 4/25/2016.
 */
public class Payment {
    private long _id;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getIdpayment() {
        return idpayment;
    }

    public void setIdpayment(String idpayment) {
        this.idpayment = idpayment;
    }

    public String getTanggalpayment() {
        return tanggalpayment;
    }

    public void setTanggalpayment(String tanggalpayment) {
        this.tanggalpayment = tanggalpayment;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Double getTotalbayar() {
        return totalbayar;
    }

    public void setTotalbayar(Double totalbayar) {
        this.totalbayar = totalbayar;
    }

    private String idpayment;
    private String  tanggalpayment;
    private String supplier;
    private Double totalbayar;
}
