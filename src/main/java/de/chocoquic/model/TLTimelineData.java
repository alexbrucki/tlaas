package de.chocoquic.model;

import java.util.ArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
public class TLTimelineData {

    private ArrayList<String> feeds;

    private String lightboxStyle;

    private String zoom;

    private String urlFriendlyTitle;

    private String endDate;

    private String mainColour;

    private String durHeadlineColour;

    private String cssFile;

    private ArrayList<String> spans;

    private String storyDateFormat;

    private String lazyLoading;

    private String sliderBackgroundColour;

    private String introText;

    private String showGroupAuthorNames;

    private String storySpacing;

    private String backgroundColour;

    private String urlHashing;

    private String host;

    private String backgroundImage;

    private String bgScale;

    private String sliderTextColour;

    private String embed;

    private String fontBase;

    private String altFlickrImageUrl;

    private String copyable;

    private ArrayList<Stories> stories;

    private String storyDateStatus;

    private String authorName;

    private String headerBackgroundColour;

    private String displayStripes;

    private String language;

    private String htmlFormatting;

    private String initialFocus;

    private String startDate;

    private String aboutText;

    private String settings3d;

    private String headerTextColour;

    private String fontHead;

    private String id;

    private String title;

    private String showTitleBlock;

    private String fontBody;

    private String expander;

    private String topDateFormat;

    private String embedHash;

    private String bgStyle;

    private String feed;

    private String showAdBlock;

    private String homePage;

    private ArrayList<String> tags;

    private String introImage;

    private String viewType;

    private String backgroundImageCredit;

    private String dontDisplayIntroPanel;

    private String sliderDraggerColour;

    private String showControls;

    private String sliderDateFormat;

    private String accountType;

    private String secret;

    private ArrayList<Categories> categories;

    private String autoPlay;

    private String introImageCredit;

    private String isPublic;

    private String sliderDetailsColour;

    private String openReadMoreLinks;

    @Override
    public String toString() {
        return "ClassPojo [feeds = " + feeds + ", lightboxStyle = " + lightboxStyle + ", zoom = " + zoom
                + ", urlFriendlyTitle = " + urlFriendlyTitle + ", endDate = " + endDate + ", mainColour = " + mainColour
                + ", durHeadlineColour = " + durHeadlineColour + ", cssFile = " + cssFile + ", spans = " + spans
                + ", storyDateFormat = " + storyDateFormat + ", lazyLoading = " + lazyLoading
                + ", sliderBackgroundColour = " + sliderBackgroundColour + ", introText = " + introText
                + ", showGroupAuthorNames = " + showGroupAuthorNames + ", storySpacing = " + storySpacing
                + ", backgroundColour = " + backgroundColour + ", urlHashing = " + urlHashing + ", host = " + host
                + ", backgroundImage = " + backgroundImage + ", bgScale = " + bgScale + ", sliderTextColour = "
                + sliderTextColour + ", embed = " + embed + ", fontBase = " + fontBase + ", altFlickrImageUrl = "
                + altFlickrImageUrl + ", copyable = " + copyable + ", stories = " + stories + ", storyDateStatus = "
                + storyDateStatus + ", authorName = " + authorName + ", headerBackgroundColour = "
                + headerBackgroundColour + ", displayStripes = " + displayStripes + ", language = " + language
                + ", htmlFormatting = " + htmlFormatting + ", initialFocus = " + initialFocus + ", startDate = "
                + startDate + ", aboutText = " + aboutText + ", settings3d = " + settings3d + ", headerTextColour = "
                + headerTextColour + ", fontHead = " + fontHead + ", id = " + id + ", title = " + title
                + ", showTitleBlock = " + showTitleBlock + ", fontBody = " + fontBody + ", expander = " + expander
                + ", topDateFormat = " + topDateFormat + ", embedHash = " + embedHash + ", bgStyle = " + bgStyle
                + ", feed = " + feed + ", showAdBlock = " + showAdBlock + ", homePage = " + homePage + ", tags = "
                + tags + ", introImage = " + introImage + ", viewType = " + viewType + ", backgroundImageCredit = "
                + backgroundImageCredit + ", dontDisplayIntroPanel = " + dontDisplayIntroPanel
                + ", sliderDraggerColour = " + sliderDraggerColour + ", showControls = " + showControls
                + ", sliderDateFormat = " + sliderDateFormat + ", accountType = " + accountType + ", secret = " + secret
                + ", categories = " + categories + ", autoPlay = " + autoPlay + ", introImageCredit = "
                + introImageCredit + ", public = , sliderDetailsColour = " + sliderDetailsColour
                + ", openReadMoreLinks = " + openReadMoreLinks + "]";
    }
}
