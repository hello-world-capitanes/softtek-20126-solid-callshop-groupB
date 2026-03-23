package com.helloworld.callshop.timeslot;


import com.helloworld.callshop.rater.rate.Rate;
import com.helloworld.callshop.rater.rate.RatesRepository;
import com.helloworld.callshop.rater.rate.factory.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.function.Predicate;

public class TimeSlotRateFactory implements RateFactory {

    private static final String DESCRIPTION = "Tarifa cambiante por franjas horarias (se aplican otras tarifas diferentes según la hora)";
    private static final String ID = "TIME";
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
        ParametersMapper parametersMapper= parametersReader.readParameters(parameters);
        Object name= parametersMapper.getValue(RATE_NAME_NAME);
        if (!getNameValidator().test(name)) {
            throw new InvalidParameterValueException("Nombre no válido");
        }
        String nombre=(String) name;

        LocalTime hora1=parseTime(parametersMapper.getValue(HORA_INICIO_1));
        LocalTime hora2=parseTime(parametersMapper.getValue(HORA_INICIO_2));
        LocalTime hora3=parseTime(parametersMapper.getValue(HORA_INICIO_3));
        if (!hora1.isBefore(hora2) || !hora2.isBefore(hora3)) {
            throw new InvalidParameterValueException("Las horas de inicio deben estar en orden ascendente");
        }

        Rate rate1=getRateFromRepository(parametersMapper.getValue(TARIFA_NOMBRE_1));
        Rate rate2=getRateFromRepository(parametersMapper.getValue(TARIFA_NOMBRE_2));
        Rate rate3=getRateFromRepository(parametersMapper.getValue(TARIFA_NOMBRE_3));

        List<Timeslot> slots= List.of(new Timeslot(hora1, hora2), new Timeslot(hora2, hora3), new Timeslot(hora3, hora1));
        List<Rate> rates= List.of(rate1, rate2, rate3);
        return new TimeSlotRate(slots, rates);
    }

    private LocalTime parseTime(Object value) throws InvalidParameterValueException {
        if (!(value instanceof String)) {
            throw new InvalidParameterValueException("Hora debe ser un string en formato HH:mm");
        }
        try {
            return LocalTime.parse((String) value, timeFormatter);
        } catch (DateTimeParseException e) {
            throw new InvalidParameterValueException("Formato de hora inválido: " + value);
        }
    }

    private Rate getRateFromRepository(Object value) throws InvalidParameterValueException {
        if (!(value instanceof String)) {
            throw new InvalidParameterValueException("Nombre de tarifa debe ser un string");
        }
        String name = (String) value;
        Rate rate = RatesRepository.INSTANCE.getRate(name);
        if (rate == null) {
            throw new InvalidParameterValueException("Tarifa no encontrada: " + name);
        }
        return rate;
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
