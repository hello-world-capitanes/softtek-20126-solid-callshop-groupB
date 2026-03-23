package com.helloworld.callshop.timeslot;


import com.helloworld.callshop.rater.rate.Rate;
import com.helloworld.callshop.rater.rate.factory.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Predicate;

public class TimeSlotRateFactory implements RateFactory {

    private static final String DESCRIPTION = "Tarifa cambiante por franjas horarias (se aplican otras tarifas diferentes según la hora)";
    private static final String ID = "TIME";
    private static final String PERCENT_NAME = "percent";
    private static final String HORA_INICIO_1="horaInicio1";
    private static final String TARIFA_NOMBRE_1="tarifaNombre1";
    private static final String HORA_INICIO_2="horaInicio2";
    private static final String TARIFA_NOMBRE_2="tarifaNombre2";
    private static final String HORA_INICIO_3="horaInicio3";
    private static final String TARIFA_NOMBRE_3="tarifaNombre3";

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final Predicate<Object> timeParameterValidator = parameter -> {
        try {
            timeFormatter.parse((String) parameter);
            return true;
        } catch (Exception e) {
            return false;
        }
    };

    private final Predicate<Object> rateNameValidator = parameter -> parameter instanceof String value && !value.isBlank();

    private final Parameter horaInicio1Param = new Parameter(HORA_INICIO_1, "Hora de inicio del primer timeslot (formato HH:mm)", timeParameterValidator);
    private final Parameter tarifaNombre1Param = new Parameter(TARIFA_NOMBRE_1, "Nombre de la tarifa para el primer timeslot", rateNameValidator);
    private final Parameter horaInicio2Param = new Parameter(HORA_INICIO_2, "Hora de inicio del segundo timeslot (formato HH:mm)", timeParameterValidator);
    private final Parameter tarifaNombre2Param = new Parameter(TARIFA_NOMBRE_2, "Nombre de la tarifa para el segundo timeslot", rateNameValidator);
    private final Parameter horaInicio3Param = new Parameter(HORA_INICIO_3, "Hora de inicio del tercer timeslot (formato HH:mm)", timeParameterValidator);
    private final Parameter tarifaNombre3Param = new Parameter(TARIFA_NOMBRE_3, "Nombre de la tarifa para el tercer timeslot", rateNameValidator);



    @Override
    public Rate makeRate(ParametersReader parametersReader) throws InvalidParameterValueException, RateBuilderException {
        List<Parameter> parameters = getBasicParameterList();


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
