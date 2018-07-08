package de.chocoquic.ltass.service;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.inject.Inject;

import de.chocoquic.ltass.dao.TimelineCategoryDao;
import de.chocoquic.ltass.dao.TimelineDataDao;
import de.chocoquic.ltass.dao.TimelineStoryDao;
import de.chocoquic.ltass.entity.*;

@Stateless
public class TimelineService {
	@Inject
	private TimelineDataDao timelineDataDao;
	
	@Inject
	private TimelineStoryDao timelineStoryDao;
	
	@Inject
	private TimelineCategoryDao timelineCategoryDao;
	
	public TimelineCategory findTimelineCategoryById(Long id) {
		return timelineCategoryDao.findById(id);
	}
	
	public ArrayList<TimelineCategory> findTimelineCategoryByTimelineData(TimelineData timelineData) {
		return timelineCategoryDao.findByTimelineData(timelineData);
	}

	public TimelineStory findTimelineStoryById(Long id) {
		return timelineStoryDao.findById(id);
	
	}
	
	public ArrayList<TimelineStory> findTimelineStoryByTimelineData(TimelineData timelineData) {
		return timelineStoryDao.findByTimelineData(timelineData);
	}

	public TimelineData findById(Long id) {
		return timelineDataDao.findById(id);
	}
	
	public DBEntity saveOrUpdateTimeLineData(TimelineData timelinedata){
		return timelineDataDao.saveOrUpdate(timelinedata);
	}
	
	public DBEntity saveOrUpdateTimelineStory(TimelineStory timelineStory){
		return timelineStoryDao.saveOrUpdate(timelineStory);
	}
}
