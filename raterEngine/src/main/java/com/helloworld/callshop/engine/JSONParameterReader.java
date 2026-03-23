package com.helloworld.callshop.engine;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.helloworld.callshop.model.JSONParametersReader;
import com.helloworld.callshop.model.RateConfig;
import com.helloworld.callshop.model.RatesJson;
import com.helloworld.callshop.rater.rate.factory.ParametersMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

//1. Acceder al fichero JSON.
//2. Parsearlo (Gson/Jackson).
//3. Extraer los parámetros de una tarifa concreta.
//4. Devolver un objeto ParametersMapper que la factory pueda usar.

public class JSONParameterReader implements JSONParametersReader {

    public RatesJson readJSONFile() throws IOException {
        //Evito tener la ruta del json hardcodeada

        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("rates.json")) {

            if (is == null) {
                throw new IOException("No se ha encontrado el archivo rates.json");
            }

            return mapper.readValue(is, RatesJson.class);
        }

    }

    @Override
    public ParametersMapper readParameters(RatesJson parameters) {

        ParametersMapperImpl paramsMapper = new ParametersMapperImpl();

        for (RateConfig rateConfig : parameters.getTarifas()) {
            Map<String, Object> params = rateConfig.getParametros();
            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    paramsMapper.put(key, value);
                }
            }
        }

        return paramsMapper;
    }



}



