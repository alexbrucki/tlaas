package de.chocoquic.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The persistent class for the timeline_data database table.
 *
 */
@Entity
@EqualsAndHashCode(callSuper=true, of = "aboutText")
@Data
@Table(name = "timeline_data")
@NamedQueries({
    @NamedQuery(name = "TimelineData.findAll", query = "SELECT t FROM TimelineData t")
    ,
@NamedQuery(name = "TimelineData.findByTitle", query = "SELECT t FROM TimelineData t where t.title = :title")
})
public class TimelineData extends DBEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "about_text", length = 1000)
    private String aboutText;

    @Column(name = "alt_flickr_image_url", length = 255)
    private String altFlickrImageUrl;

    @Column(name = "auto_play", length = 255)
    private String autoPlay;

    @Column(name = "background_colour", length = 65)
    private String backgroundColour;

    @Column(name = "background_image", length = 1000)
    private String backgroundImage;

    @Column(name = "background_image_credits", length = 1000)
    private String backgroundImageCredits;

    @Column(name = "bg_scale")
    private int bgScale;

    @Column(name = "bg_style")
    private int bgStyle;

    private byte copyable;

    @Column(name = "css_file", length = 65)
    private String cssFile;

    @Column(name = "display_stripes")
    private byte displayStripes;

    @Column(name = "dont_display_intro_panel")
    private byte dontDisplayIntroPanel;

    @Column(name = "dur_headline_colour", length = 65)
    private String durHeadlineColour;

    private byte embed;

    @Column(name = "embed_hash", length = 65)
    private String embedHash;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;

    private int expander;

    @Column(name = "font_base", length = 255)
    private String fontBase;

    @Column(name = "font_body", length = 255)
    private String fontBody;

    @Column(name = "font_head", length = 255)
    private String fontHead;

    @Column(name = "header_background_colour", length = 65)
    private String headerBackgroundColour;

    @Column(name = "header_text_colour", length = 65)
    private String headerTextColour;

    private byte homepage;

    @Column(length = 255)
    private String host;

    @Column(name = "html_formatting")
    private byte htmlFormatting;

    @Column(name = "initial_focus", length = 65)
    private String initialFocus;

    @Column(name = "intro_image", length = 1000)
    private String introImage;

    @Column(name = "intro_image_credits", length = 1000)
    private String introImageCredits;

    @Column(name = "intro_text", length = 1000)
    private String introText;

    @Column(name = "is_public")
    private byte isPublic;

    @Column(length = 65)
    private String language;

    @Column(name = "lazy_loading")
    private byte lazyLoading;

    @Column(name = "light_box_style")
    private byte lightBoxStyle;

    @Column(name = "main_colour", length = 65)
    private String mainColour;

    @Column(name = "open_read_more_links")
    private byte openReadMoreLinks;

    private byte secret;

    @Column(length = 1000)
    private String settings3d;

    @Column(name = "show_ad_block")
    private byte showAdBlock;

    @Column(name = "show_controls")
    private byte showControls;

    @Column(name = "show_group_author_names")
    private byte showGroupAuthorNames;

    @Column(name = "show_title_block")
    private byte showTitleBlock;

    @Column(name = "slider_background_colour", length = 65)
    private String sliderBackgroundColour;

    @Column(name = "slider_date_format", length = 65)
    private String sliderDateFormat;

    @Column(name = "slider_details_colour", length = 65)
    private String sliderDetailsColour;

    @Column(name = "slider_dragger_colour", length = 65)
    private String sliderDraggerColour;

    @Column(name = "slider_text_colour", length = 65)
    private String sliderTextColour;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "story_date_format", length = 65)
    private String storyDateFormat;

    @Column(name = "story_date_status")
    private int storyDateStatus;

    @Column(name = "story_spacing")
    private int storySpacing;

    @Column(length = 255)
    private String title;

    @Column(name = "top_date_format", length = 65)
    private String topDateFormat;

    @Column(name = "url_friendly_title", length = 255)
    private String urlFriendlyTitle;

    @Column(name = "url_hashing")
    private byte urlHashing;

    @Column(name = "view_type")
    private int viewType;

    @Column(length = 65)
    private String zoom;

    //bi-directional many-to-one association to TimelineCategory
    @OneToMany(mappedBy = "timelineData")
    private Set<TimelineCategory> timelineCategories;

    //bi-directional many-to-one association to TimelineFeed
    @OneToMany(mappedBy = "timelineData")
    private Set<TimelineFeed> timelineFeeds;

    //bi-directional many-to-one association to TimelineSpan
    @OneToMany(mappedBy = "timelineData")
    private Set<TimelineSpan> timelineSpans;

    //bi-directional many-to-one association to TimelineStory
    @OneToMany(mappedBy = "timelineData")
    private Set<TimelineStory> timelineStories;

    //bi-directional many-to-many association to TimelineUser
    @ManyToMany(mappedBy = "timelineData")
    private Set<TimelineUser> timelineUsers;

    //bi-directional many-to-one association to TimelineUserRole
    @OneToMany(mappedBy = "timelineData")
    private Set<TimelineUserRole> timelineUserRoles;

    public TimelineData() {
    }

    public TimelineCategory addTimelineCategory(TimelineCategory timelineCategory) {
        getTimelineCategories().add(timelineCategory);
        timelineCategory.setTimelineData(this);

        return timelineCategory;
    }

    public TimelineCategory removeTimelineCategory(TimelineCategory timelineCategory) {
        getTimelineCategories().remove(timelineCategory);
        timelineCategory.setTimelineData(null);

        return timelineCategory;
    }

    public TimelineFeed addTimelineFeed(TimelineFeed timelineFeed) {
        getTimelineFeeds().add(timelineFeed);
        timelineFeed.setTimelineData(this);

        return timelineFeed;
    }

    public TimelineFeed removeTimelineFeed(TimelineFeed timelineFeed) {
        getTimelineFeeds().remove(timelineFeed);
        timelineFeed.setTimelineData(null);

        return timelineFeed;
    }

    public TimelineSpan addTimelineSpan(TimelineSpan timelineSpan) {
        getTimelineSpans().add(timelineSpan);
        timelineSpan.setTimelineData(this);

        return timelineSpan;
    }

    public TimelineSpan removeTimelineSpan(TimelineSpan timelineSpan) {
        getTimelineSpans().remove(timelineSpan);
        timelineSpan.setTimelineData(null);

        return timelineSpan;
    }

    public TimelineUserRole addTimelineUserRole(TimelineUserRole timelineUserRole) {
        getTimelineUserRoles().add(timelineUserRole);
        timelineUserRole.setTimelineData(this);

        return timelineUserRole;
    }

    public TimelineUserRole removeTimelineUserRole(TimelineUserRole timelineUserRole) {
        getTimelineUserRoles().remove(timelineUserRole);
        timelineUserRole.setTimelineData(null);

        return timelineUserRole;
    }

}
