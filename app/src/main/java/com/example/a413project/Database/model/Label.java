package com.example.a413project.Database.model;

public class Label {
    public String label;
    public float confidence;
    public final static String unknownLabel = "Unknown";
    public Label(){}


    public String getLabel() {
        return (this.label.length()>1)?this.label:unknownLabel;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    public Label(String label, float confidence) {
        this.label = label;
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "Label{" +
                "label='" + label + '\'' +
                ", confidence='" + confidence + '\'' +
                '}';
    }
}

