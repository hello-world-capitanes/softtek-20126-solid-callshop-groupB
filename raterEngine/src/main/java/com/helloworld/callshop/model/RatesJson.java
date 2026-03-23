package com.helloworld.callshop.model;

import java.util.List;

public class RatesJson {
    private String autor;
    private String fecha;
    private List<RateConfig> tarifas;

    public RatesJson(String autor, String fecha, List<RateConfig> tarifas) {
        this.autor = autor;
        this.fecha = fecha;
        this.tarifas = tarifas;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<RateConfig> getTarifas() {
        return tarifas;
    }

    public void setTarifas(List<RateConfig> tarifas) {
        this.tarifas = tarifas;
    }
}
