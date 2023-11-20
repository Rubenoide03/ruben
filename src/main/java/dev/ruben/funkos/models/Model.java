package dev.ruben.funkos.models;

public enum Model {
    MARVEL(1),ANIME(2),DC(3),OTROS(4);
    private final int value;
    Model(int value){
        this.value=value;
    }
    public int getValue(){
        return value;
    }
}
