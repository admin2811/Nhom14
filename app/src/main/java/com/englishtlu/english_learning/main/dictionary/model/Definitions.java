package com.englishtlu.english_learning.main.dictionary.model;

import java.util.List;

public class Definitions {
    String definition = "";
    String example = "";


    List<String> synonyns = null;
    List<String> antonyns = null;

    public List<String> getSynonyns() {
        return synonyns;
    }

    public void setSynonyns(List<String> synonyns) {
        this.synonyns = synonyns;
    }

    public List<String> getAntonyns() {
        return antonyns;
    }

    public void setAntonyns(List<String> antonyns) {
        this.antonyns = antonyns;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
