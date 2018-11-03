package de.chocoquic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.event.timeline.TimelineSelectEvent;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import de.chocoquic.model.Categories;
import de.chocoquic.model.Stories;
import de.chocoquic.model.TLTimelineData;
import lombok.Data;

@SuppressWarnings("serial")
@ManagedBean(name = "basicTimelineView")
@SessionScoped
@Data
public class BasicTimelineView implements Serializable {

    private TimelineModel model;
    private String loggingPrefix = "Class BasicTimelineView";
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
    private LineChartModel dateModel;

    private String link;
    private String storyTitle;
    private String storyText;
    private String storyDate;
    private Boolean linkRendered;

    DateFormat format;
    HashMap<String, Categories> catMap;
    HashMap<String, LineChartSeries> serMap;
    HashMap<String, Stories> storMap;
    static Logger logger;

    @PostConstruct
    protected void initialize() {
        dateModel = new LineChartModel();
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        model = new TimelineModel();
        logger.info("building Maps");
        catMap = new HashMap<String, Categories>();
        serMap = new HashMap<String, LineChartSeries>();
        storMap = new HashMap<String, Stories>();
        logger.info("Maps built");
        for (Categories c : data.getCategories()) {
            catMap.put(c.getId(), c);
            LineChartSeries series = new LineChartSeries();
            series.setLabel(c.getTitle());
            series.setShowLine(false);
            serMap.put(c.getId(), series);
            logger.info("Category added: " + c.getId());
        }

        int i = 0;
        Random randomGenerator = new Random();
        Calendar cal = Calendar.getInstance();
        for (Stories s : data.getStories()) {
            logger.info("Story added: " + s.getTitle());
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
                        Double d = randomGenerator.nextDouble();
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

            } catch (ParseException e) {
                logger.info("parse exception in Json: " + e.getMessage());
            } catch (Exception e) {
                logger.info(loggingPrefix + " Exception in Method initialize while parsin json: " + e.getMessage() + " " + e.getStackTrace());
            }

        }
        StringBuilder seriesColors = new StringBuilder();
        try {
            for (String key : serMap.keySet()) {
                dateModel.addSeries(serMap.get(key));
                if (seriesColors.length() < 1) {
                    seriesColors.append(catMap.get(key).getColour());
                } else {
                    seriesColors.append(",");
                    seriesColors.append(catMap.get(key).getColour());
                }

            }

            dateModel.setSeriesColors(seriesColors.toString());

            dateModel.setTitle("Events");
            dateModel.setZoom(false);
            dateModel.getAxis(AxisType.Y).setLabel("");
            dateModel.getAxis(AxisType.Y).setMax(2);
            dateModel.getAxis(AxisType.Y).setMin(-1);
            DateAxis axis = new DateAxis("");
            axis.setTickFormat("%#d. %b. %y");
            dateModel.getAxes().put(AxisType.X, axis);
            System.out.println("done");
        } catch (Exception e) {
            logger.error("unexpected exception " + e.getMessage() + " " + e.getMessage());
        }

    }

    private void adjustStartDateAndAddToStoryMap(Calendar cal, Stories s) {
        if (!storMap.containsKey(s.getStartDate())) {
            storMap.put(s.getStartDate(), s);

        } else {

            try {
                cal.setTime(format.parse(s.getStartDate()));
                cal.add(Calendar.SECOND, 1);
                s.setStartDate(format.format(cal.getTime()));
                cal.setTime(format.parse(s.getEndDate()));
                cal.add(Calendar.SECOND, 1);
                s.setEndDate(format.format(cal.getTime()));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            adjustStartDateAndAddToStoryMap(cal, s);
        }
    }

    public void chartItemSelect(ItemSelectEvent event) {
        event.getItemIndex();

        LineChartSeries series = (LineChartSeries) dateModel.getSeries().get(event.getSeriesIndex());
        ArrayList<String> dates = new ArrayList<String>();
        for (Object key : series.getData().keySet()) {
            if (key instanceof String) {
                dates.add((String) key);
            }
        }
        Collections.sort(dates);
        Date d = null;
        try {
            d = format.parse(dates.get(event.getItemIndex()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (null != d) {
            this.start = d;
        }
    }

    public void onSelect(TimelineSelectEvent e) {
        TimelineEvent timelineEvent = e.getTimelineEvent();

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected event:",
                timelineEvent.getData().toString());
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
        Map<String, String> parameterMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap();

        this.storyDate = parameterMap.get("date");
        Stories story = storMap.get(this.storyDate);
        this.storyTitle = story.getTitle();
        this.storyText = wrapStringAfterWhitespaceWithIndex(story.getText(), 50);
        this.link = story.getExternalLink();
        if (this.link == null || this.link.isEmpty()) {
            this.linkRendered = false;
        } else {
            this.linkRendered = true;
        }

    }

    public void test() throws Exception {
        throw new RuntimeException();

    }
}
