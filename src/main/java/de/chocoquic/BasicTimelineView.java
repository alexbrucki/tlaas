package de.chocoquic;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.ItemSelectEvent;
import org.primefaces.event.timeline.TimelineSelectEvent;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

import de.chocoquic.model.Categories;
import de.chocoquic.model.Stories;
import de.chocoquic.model.TLTimelineData;

import java.util.Arrays;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
@Named("basicTimelineView")
@SessionScoped
@Data
public class BasicTimelineView implements Serializable {

    static final Logger L = LoggerFactory.getLogger(BasicTimelineView.class);

    private boolean selectable = false;
    private boolean zoomable = true;
    private boolean moveable = true;
    private boolean stackEvents = true;
    private boolean snapEvents = false;
    private String eventStyle = "dot";

    private boolean axisOnTop;
    private boolean showCurrentTime = true;
    private boolean showNavigation = true;

    private Long zoomMax = 15552000000L;
    private Long zoomMin = 1296000000L;

    private Integer eventMargin = 1;
    private Integer eventMarginAxis = 1;

    private Date min;
    private Date max;
    private Date start;
    private Date end;

    private TLTimelineData data;
    private TimelineModel model = new TimelineModel();

    private LineChartModel dateModel = new LineChartModel();

    private String link;
    private String storyTitle;
    private String storyText;
    private String storyDate;

    private Boolean linkRendered;

    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private HashMap<String, Categories> catMap = new HashMap<>();
    private HashMap<String, LineChartSeries> serMap = new HashMap<>();
    private HashMap<String, Stories> storMap = new HashMap<>();

    @PostConstruct
    protected void initialize() {
        if (data == null) {
            L.error("TLTimelineData is null, please set it");
            return;
        }
        L.info("Maps built");
        data.getCategories().stream().map((c) -> {
            catMap.put(c.getId(), c);
            return c;
        }).forEach((c) -> {
            LineChartSeries series = new LineChartSeries();
            series.setLabel(c.getTitle());
            series.setShowLine(false);
            serMap.put(c.getId(), series);
            L.info("Category added: " + c.getId());
        });

        Random random = new Random();
        Calendar cal = Calendar.getInstance();
        data.getStories().stream().forEach((s) -> {
            L.info("Story added: " + s.getTitle());
            try {
                adjustStartDateAndAddToStoryMap(cal, s);

                Date startDate = format.parse(s.getStartDate());
                Date endDate = format.parse(s.getEndDate());
                Categories categories = catMap.get(s.getCategory());
                String categoryTitle;
                String categoryId;
                if (null != categories && null != categories.getTitle() && null != categories.getId()) {
                    categoryTitle = categories.getTitle();
                    categoryId = categories.getId();
                    if (null != startDate && null != endDate) {
                        TimelineEvent event = new TimelineEvent(s, startDate);
                        Double d = random.nextDouble();
                        model.add(event);
                        serMap.get(s.getCategory()).set(s.getStartDate(), d);
                    }
                }
                if (start == null) {
                    start = startDate;
                } else {
                    if (start.after(startDate)) {
                        start = startDate;
                        cal.setTime(startDate);
                        cal.add(Calendar.DATE, 30);
                        end = cal.getTime();
                    }
                }
                if (min == null) {
                    min = startDate;
                } else {
                    if (min.after(startDate)) {
                        min = startDate;
                    }
                }
                if (max == null) {
                    max = endDate;
                } else {
                    if (max.before(endDate)) {
                        max = startDate;
                    }
                }

            } catch (ParseException parseEx) {
                L.error("parse exception in Json: {}", parseEx.getMessage());
            } catch (Exception ex) {
                L.error(" Exception in Method initialize while parsin json: {}, {}", ex.getMessage(), Arrays.toString(ex.getStackTrace()));
            }
        });

        StringBuilder seriesColors = new StringBuilder();

        serMap.keySet().stream().map((key) -> {
            dateModel.addSeries(serMap.get(key));
            return key;
        }).forEach((key) -> {
            if (seriesColors.length() < 1) {
                seriesColors.append(catMap.get(key).getColour());
            } else {
                seriesColors.append(",");
                seriesColors.append(catMap.get(key).getColour());
            }
        });

        //build up the dataModel
        dateModel.setSeriesColors(seriesColors.toString());
        dateModel.setTitle("Events");
        dateModel.setZoom(false);
        dateModel.getAxis(AxisType.Y).setLabel("");
        dateModel.getAxis(AxisType.Y).setMax(2);
        dateModel.getAxis(AxisType.Y).setMin(-1);
        DateAxis axis = new DateAxis("");
        axis.setTickFormat("%#d. %b. %y");
        dateModel.getAxes().put(AxisType.X, axis);

        L.info("done");
    }

    private void adjustStartDateAndAddToStoryMap(Calendar calendar, Stories stories) {
        if (!storMap.containsKey(stories.getStartDate())) {
            storMap.put(stories.getStartDate(), stories);
        } else {
            try {
                calendar.setTime(format.parse(stories.getStartDate()));
                calendar.add(Calendar.SECOND, 1);
                calendar.setTime(format.parse(stories.getEndDate()));
                calendar.add(Calendar.SECOND, 1);

                stories.setStartDate(format.format(calendar.getTime()));
                stories.setEndDate(format.format(calendar.getTime()));
            } catch (ParseException ex) {
                L.error("ParseException {}", ex.getMessage());
            }
            adjustStartDateAndAddToStoryMap(calendar, stories);
        }
    }

    public void chartItemSelect(ItemSelectEvent event) {
        event.getItemIndex();

        LineChartSeries series = (LineChartSeries) dateModel.getSeries().get(event.getSeriesIndex());
        ArrayList<String> dates = new ArrayList<>();
        series.getData().keySet().stream()
                .filter((key) -> (key instanceof String))
                .forEach((key) -> {
                    dates.add((String) key);
                });
        Collections.sort(dates);

        Date d = null;
        try {
            d = format.parse(dates.get(event.getItemIndex()));
        } catch (ParseException ex) {
            L.error("ParseException {}", ex.getMessage());
        }

        if (null != d) {
            this.start = d;
        }
    }

    public void onSelect(TimelineSelectEvent e) {
        TimelineEvent timelineEvent = e.getTimelineEvent();

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected event:", timelineEvent.getData().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String wrapStringAfterWhitespaceWithIndex(String text, int index) {
        StringBuilder sb = new StringBuilder(text);
        int i = 0;
        while ((i = sb.indexOf(" ", i + index)) != -1) {
            sb.replace(i, i + 1, "\r\n");
        }
        return sb.toString();
    }

    public String trimString(String text, int index) {
        if (text.length() > index) {
            return text.substring(0, index) + "...";
        }
        return text;
    }

    public String getCategoryTitle(String cat) {
        return catMap.get(cat).getTitle();
    }

    public String getCategoryColor(String cat) {
        return "background: #" + catMap.get(cat).getColour() + ";";
    }

    public void setDialogValues() {
        Map<String, String> parameterMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        this.storyDate = parameterMap.get("date");
        Stories story = storMap.get(this.storyDate);
        this.storyTitle = story.getTitle();
        this.storyText = wrapStringAfterWhitespaceWithIndex(story.getText(), 50);
        this.link = story.getExternalLink();
        this.linkRendered = !(this.link == null || this.link.isEmpty());
    }

    public void test() throws Exception {
        throw new RuntimeException();
    }
}
