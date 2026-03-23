package com.helloworld.callshop.timeslot;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class Timeslot {

LocalTime startTime;
LocalTime endTime;

    public Timeslot(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;

        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("El tiempo de entrada o salida no puedeb ser nulos");
        }

        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("El tiempo de entrada no puede ser después del tiempo de salida");
        }

        long duracion = duracionvalida(startTime,endTime);

        if(duracion < 60){
            throw new IllegalArgumentException("La duración del timeslot no puede ser menor a 60 minutos" +
                    "Duracion actual: " + duracion);
        }


    }

    private long duracionvalida(LocalTime startTime, LocalTime endTime) {



        if (startTime.isBefore(endTime)) {
            return Duration.between(startTime, endTime).toMinutes();
        }


        Duration toMidnight  = Duration.between(startTime, LocalTime.MIDNIGHT);
        Duration fromMidnight = Duration.between(LocalTime.MIN, endTime);

        return toMidnight.plus(fromMidnight).toMinutes();


    }


    public boolean isInSlot(LocalTime time) {
        if (time == null) return false;


        if (startTime.isBefore(endTime)) {
            return !time.isBefore(startTime) && time.isBefore(endTime);
        }


        return !time.isBefore(startTime) || time.isBefore(endTime);
    }



}
