package Model;

public class model {
    double inr;
    double usd;
    String cn;

    public model(double inr, double usd, String cn) {
        this.inr = inr;
        this.usd = usd;
        this.cn = cn;
    }

    public double getInr() {
        return inr;
    }

    public void setInr(double inr) {
        this.inr = inr;
    }

    public double getUsd() {
        return usd;
    }

    public void setUsd(double usd) {
        this.usd = usd;
    }
    public void setCn(String cn){
        this.cn = cn;
    }
    public String getCn(){
        return cn;
    }
}
