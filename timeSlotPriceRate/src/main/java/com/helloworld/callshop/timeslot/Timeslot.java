package com.helloworld.callshop.timeslot;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class Timeslot {

LocalTime startTime;
LocalTime endTime;

    public Timeslot(LocalTime startTime, LocalTime endTime) {


        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("El tiempo de entrada o salida no puedeb ser nulos");
        }

        long duracion = duracionvalida(startTime,endTime);

        if(duracion < 60){
            throw new IllegalArgumentException("La duración del timeslot no puede ser menor a 60 minutos" +
                    "Duracion actual: " + duracion);
        }

        this.startTime = startTime;
        this.endTime = endTime;

    }

    private long duracionvalida(LocalTime startTime, LocalTime endTime) {


        int startMin = startTime.toSecondOfDay() / 60;
        int endMin = endTime.toSecondOfDay() / 60;

      
        if (endMin <= startMin) {
            endMin += 24 * 60;
        }

        return endMin - startMin;

    }


    public boolean isInSlot(LocalTime time) {
        if (time == null) return false;


        if (startTime.isBefore(endTime)) {
            return !time.isBefore(startTime) && time.isBefore(endTime);
        }


        return !time.isBefore(startTime) || time.isBefore(endTime);
    }



}
