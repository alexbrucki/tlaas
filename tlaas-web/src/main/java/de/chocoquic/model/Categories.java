package de.chocoquic.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
public class Categories {

    private String colour;

    private String id;

    private String title;

    private String order;

    private String layout;

    private String size;

    private String rows;

  
    @Override
    public String toString() {
        return "ClassPojo [colour = " + colour + ", id = " + id + ", title = " + title + ", order = " + order + ", layout = " + layout + ", size = " + size + ", rows = " + rows + "]";
    }
}
