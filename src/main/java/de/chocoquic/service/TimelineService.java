package de.chocoquic.service;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.inject.Inject;

import de.chocoquic.dao.TimelineCategoryDao;
import de.chocoquic.dao.TimelineDataDao;
import de.chocoquic.dao.TimelineStoryDao;
import de.chocoquic.entity.DBEntity;
import de.chocoquic.entity.TimelineCategory;
import de.chocoquic.entity.TimelineData;
import de.chocoquic.entity.TimelineStory;

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
