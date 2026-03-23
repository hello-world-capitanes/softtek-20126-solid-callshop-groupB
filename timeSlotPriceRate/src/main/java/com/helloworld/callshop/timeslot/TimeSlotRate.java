package com.helloworld.callshop.timeslot;

import com.helloworld.callshop.rater.rate.Rate;
import com.helloworld.callshop.rater.rate.RateableCall;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

public class TimeSlotRate implements Rate {
    private final List<Timeslot> slots;
    private final List<Rate> rates;
    private final String name;

    public TimeSlotRate(String name, List<Timeslot> slots, List<Rate> rates) {
        if (slots == null || rates == null || slots.size() != 3 || rates.size() != 3) {
            throw new IllegalArgumentException("Debe haber exactamente 3 timeslots y 3 tarifas");
        }
        this.slots = slots;
        this.rates = rates;
        this.name = name;
    }

    @Override
    public BigDecimal calculatePrice(RateableCall call) {
        LocalTime callStart = call.getCallStart();

        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).isInSlot(callStart)) {
                return rates.get(i).calculatePrice(call);
            }
        }

        throw new IllegalStateException("No se encontró un timeslot para la hora: " + callStart);
    }

    @Override
    public String getName() {
        return name;
    }
}
