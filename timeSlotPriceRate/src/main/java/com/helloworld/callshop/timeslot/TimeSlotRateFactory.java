package com.helloworld.callshop.timeslot;


import com.helloworld.callshop.rater.rate.Rate;
import com.helloworld.callshop.rater.rate.factory.*;

import java.util.List;
import java.util.function.Predicate;

public class TimeSlotRateFactory implements RateFactory {

    private static final String DESCRIPTION = "Tarifa cambiante por franjas horarias (se aplican otras tarifas diferentes según la hora)";
    private static final String ID = "TIME";
    private static final String PERCENT_NAME = "percent";

    // TODO todos los que devuelva NULL o ""
    @Override
    public Rate makeRate(ParametersReader parametersReader) throws InvalidParameterValueException, RateBuilderException {
        String name = (String) parametersReader.getValue(RATE_NAME_NAME);
        return new TimeSlotRate(name);
    }

    @Override
    public String getDescription() { return DESCRIPTION; }

    public String getId() { return ID; }

    @Override
    public List<Parameter> getBasicParameterList() {
        return RateFactory.super.getBasicParameterList();
    }

    @Override
    public Predicate<Object> getNameValidator() {
        return RateFactory.super.getNameValidator();
    }
}
