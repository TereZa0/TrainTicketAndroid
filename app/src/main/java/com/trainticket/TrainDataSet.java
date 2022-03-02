package com.trainticket;

public class TrainDataSet {
    String id;
    String name;
    String tclasss;
    String capacity;
    String images;

    public TrainDataSet(String id, String name, String tclasss, String capacity, String images) {
        this.id = id;
        this.name = name;
        this.tclasss = tclasss;
        this.capacity = capacity;
        this.images = images;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTclasss() {
        return tclasss;
    }

    public void setTclasss(String tclasss) {
        this.tclasss = tclasss;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }
}
