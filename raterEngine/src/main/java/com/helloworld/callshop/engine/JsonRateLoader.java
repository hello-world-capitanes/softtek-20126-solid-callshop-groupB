package com.helloworld.callshop.engine;

import com.helloworld.callshop.model.RateConfig;
import com.helloworld.callshop.model.RatesJson;
import com.helloworld.callshop.rater.rate.Rate;
import com.helloworld.callshop.rater.rate.RatesRepository;
import com.helloworld.callshop.rater.rate.factory.ParametersReader;
import com.helloworld.callshop.rater.rate.factory.RateFactoriesContainer;
import com.helloworld.callshop.rater.rate.factory.RateFactory;
import com.helloworld.callshop.rater.rate.factory.Parameter;
import com.helloworld.callshop.rater.rate.factory.ParametersMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonRateLoader {
    
    // Localiza la RateFactory correspondiente y construye la tarifa.
    // Registra cada tarifa en RatesRepository para que el motor pueda usarla.

    private RateFactoriesContainer factoriesContainer;
    private JSONParameterReader jsonParameterReader;

    public JsonRateLoader(RateFactoriesContainer factoriesContainer, JSONParameterReader jsonParameterReader) {
        this.factoriesContainer = factoriesContainer;
        this.jsonParameterReader = jsonParameterReader;
    }
   // Se ha utilizado una lamda para implementar la interfaz ParametersReader que es la que necesita la factoria que es la que necesita la factoria la instancia de la clase que previamente
   // ya la tiene implementada que es ParametersMapperImpl
   // No se ha hecho en una clase aparte porque solo se va a utilizar en este unico contexto y es muy breve
    public void loadRates() throws IOException {
        RatesJson ratesJson = jsonParameterReader.readJSONFile();
        for (RateConfig rateConfig : ratesJson.getTarifas()) {
            String tipoTarifa = rateConfig.getTipoTarifa();
            RateFactory factory = factoriesContainer.getFactories().get(tipoTarifa);

            if (factory != null) {
                try {
                    ParametersReader reader = parameters -> {
                        ParametersMapperImpl mapper = new ParametersMapperImpl();
                        Map<String, Object> params = rateConfig.getParametros();
                        if (params != null) {
                            mapper.putAll(params);
                        }
                        return mapper;
                    };

                    Rate rate = factory.makeRate(reader);
                    RatesRepository.INSTANCE.addRate(rate);
                } catch (Exception e) {
                    System.out.println("Error creando la tarifa para " + tipoTarifa + ": " + e.getMessage());
                }
            } else {
                System.out.println("Factory no encontrada para tipoTarifa: " + tipoTarifa + ". Mis compañeros la implementarán.");
            }
        }
    }
}
