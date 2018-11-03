package de.chocoquic.model;

import java.util.ArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
public class Stories {

    private String tags;

    private String id;

    private String startDate;

    private String category;

    private String fullText;

    private String text;

    private String title;

    private String ownerName;

    private String ownerId;

    private String dateFormat;

    private String endDate;

    private ArrayList<String> media;

    private String externalLink;

    @Override
    public String toString() {
        return "ClassPojo [tags = " + tags + ", id = " + id + ", startDate = " + startDate + ", category = " + category
                + ", fullText = " + fullText + ", text = " + text + ", title = " + title + ", ownerName = " + ownerName
                + ", ownerId = " + ownerId + ", dateFormat = " + dateFormat + ", endDate = " + endDate + ", media = "
                + media + ", externalLink = " + externalLink + "]";
    }
}
