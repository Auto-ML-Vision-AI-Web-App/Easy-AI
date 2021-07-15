package com.eavy.ai;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AI {

    @Id @GeneratedValue
    Long id;

    String name;
    String activation;
    String learningRate;
    int hiddenLayerSizes;
    double momentum;
    String solver;

    public AI() {
    }

    public AI(String name, String activation, String learningRate, int hiddenLayerSizes, double momentum, String solver) {
        this.name = name;
        this.activation = activation;
        this.learningRate = learningRate;
        this.hiddenLayerSizes = hiddenLayerSizes;
        this.momentum = momentum;
        this.solver = solver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivation() {
        return activation;
    }

    public void setActivation(String activation) {
        this.activation = activation;
    }

    public String getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(String learningRate) {
        this.learningRate = learningRate;
    }

    public int getHiddenLayerSizes() {
        return hiddenLayerSizes;
    }

    public void setHiddenLayerSizes(int hiddenLayerSizes) {
        this.hiddenLayerSizes = hiddenLayerSizes;
    }

    public double getMomentum() {
        return momentum;
    }

    public void setMomentum(double momentum) {
        this.momentum = momentum;
    }

    public String getSolver() {
        return solver;
    }

    public void setSolver(String solver) {
        this.solver = solver;
    }

    @Override
    public String toString() {
        return "AI{" +
                "name='" + name + '\'' +
                ", activation='" + activation + '\'' +
                ", learningRate='" + learningRate + '\'' +
                ", hiddenLayerSizes=" + hiddenLayerSizes +
                ", momentum=" + momentum +
                ", solver='" + solver + '\'' +
                '}';
    }
}
