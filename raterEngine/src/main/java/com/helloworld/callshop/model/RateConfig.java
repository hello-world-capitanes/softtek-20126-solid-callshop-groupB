package com.helloworld.callshop.model;

import java.util.Map;

public class RateConfig {
    private String tipoTarifa;
    private Map<String, Object> parametros;  // O una clase específica si sabes los tipos

    public RateConfig(Map<String, Object> parametros, String tipoTarifa) {
        this.parametros = parametros;
        this.tipoTarifa = tipoTarifa;
    }

    public String getTipoTarifa() {
        return tipoTarifa;
    }

    public void setTipoTarifa(String tipoTarifa) {
        this.tipoTarifa = tipoTarifa;
    }

    public Map<String, Object> getParametros() {
        return parametros;
    }

    public void setParametros(Map<String, Object> parametros) {
        this.parametros = parametros;
    }
}
