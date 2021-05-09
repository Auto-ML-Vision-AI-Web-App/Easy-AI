package com.eavy;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AI {

    String name;
    String activation;
    String learningRate;
    int hiddenLayerSizes;
    double momentum;
    String solver;

}
