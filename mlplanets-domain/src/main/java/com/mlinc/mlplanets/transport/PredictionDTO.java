package com.mlinc.mlplanets.transport;

public class PredictionDTO {

    String sistemaSolar;
    long dia;
    String clima;

    public String getSistemaSolar() {
        return sistemaSolar;
    }

    public void setSistemaSolar(String sistemaSolar) {
        this.sistemaSolar = sistemaSolar;
    }

    public long getDia() {
        return dia;
    }

    public void setDia(long dia) {
        this.dia = dia;
    }

    public String getClima() {
        return clima;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }
}
