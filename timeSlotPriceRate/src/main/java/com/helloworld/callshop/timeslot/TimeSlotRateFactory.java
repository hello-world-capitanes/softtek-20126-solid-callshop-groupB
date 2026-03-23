package com.helloworld.callshop.timeslot;


import com.helloworld.callshop.rater.rate.Rate;
import com.helloworld.callshop.rater.rate.factory.*;

import java.util.List;
import java.util.function.Predicate;

public class TimeSlotRateFactory implements RateFactory {

    // TODO todos los que devuelva NULL o ""
    @Override
    public Rate makeRate(ParametersReader parametersReader) throws InvalidParameterValueException, RateBuilderException {
        return null;
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getId() {
        return "";
    }

    @Override
    public List<Parameter> getBasicParameterList() {
        return RateFactory.super.getBasicParameterList();
    }

    @Override
    public Predicate<Object> getNameValidator() {
        return RateFactory.super.getNameValidator();
    }
}
