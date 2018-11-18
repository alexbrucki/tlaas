package de.chocoquic.service;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.inject.Inject;

import de.chocoquic.fasade.TimelineCategoryFasade;
import de.chocoquic.fasade.TimelineDataFasade;
import de.chocoquic.fasade.TimelineStoryFasade;
import de.chocoquic.entity.DBEntity;
import de.chocoquic.entity.TimelineCategory;
import de.chocoquic.entity.TimelineData;
import de.chocoquic.entity.TimelineStory;
import java.io.Serializable;
import javax.inject.Named;

@Named
@Stateless
public class TimelineService  implements Serializable{

    @Inject
    private TimelineDataFasade timelineDataDao;

    @Inject
    private TimelineStoryFasade timelineStoryDao;

    @Inject
    private TimelineCategoryFasade timelineCategoryDao;

    public TimelineCategory findTimelineCategoryById(Long id) {
        return timelineCategoryDao.findById(TimelineCategory.class, id);
    }

    public ArrayList<TimelineCategory> findTimelineCategoryByTimelineData(TimelineData timelineData) {
        return timelineCategoryDao.findByTimelineData(timelineData);
    }

    public TimelineStory findTimelineStoryById(Long id) {
        return timelineStoryDao.findById(TimelineStory.class, id);

    }

    public ArrayList<TimelineStory> findTimelineStoryByTimelineData(TimelineData timelineData) {
        return timelineStoryDao.findByTimelineData(timelineData);
    }

    public TimelineData findById(Long id) {
        return timelineDataDao.findById(TimelineData.class,id);
    }

    public DBEntity saveOrUpdateTimeLineData(TimelineData timelinedata) {
        if (timelinedata != null) {
            if (timelinedata.getId() == null | timelinedata.getId() < 0) {
                timelineDataDao.merge(timelinedata);
            } else {
                timelineDataDao.persist(timelinedata);
            }
        }
        return null;
    }

    public DBEntity saveOrUpdateTimelineStory(TimelineStory timelineStory) {
        if (timelineStory != null) {
            if (timelineStory.getId() == null | timelineStory.getId() < 0) {
                timelineStoryDao.merge(timelineStory);
            } else {
                timelineStoryDao.persist(timelineStory);
            }
        }
        return null;
    }
}
