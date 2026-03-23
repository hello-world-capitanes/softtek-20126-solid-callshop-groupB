package com.helloworld.callshop.model;

import com.helloworld.callshop.rater.rate.factory.Parameter;
import com.helloworld.callshop.rater.rate.factory.ParametersMapper;

import java.util.List;

public interface JSONParametersReader {

    ParametersMapper readParameters(RatesJson parameters);

}
