package de.chocoquic.importer;



public class JsonTimlineImportHelper {
/**
 * 
 * @author jay
    
	static Logger logger;
	private TLTimelineData data;
	private String loggingPrefix = "Class JsonTimlineImportHelper"; 
	public boolean importJSON(){
		boolean successful = false;
		
		logger = Logger.getLogger(BasicTimelineView.class);
		try {
		logger.info("post construct called");

		logger.info("models created");
		final Type timelindeDataType = new TypeToken<TLTimelineData>() {
		}.getType();
		logger.info("timelindeDataType created");
		ClassLoader classLoader = getClass().getClassLoader();
		logger.info("classLoader created");
		File file = new File(classLoader.getResource("vdsdates.json").getFile());
		logger.info("JSON fileloader created");
		Gson gson = new Gson();
		logger.info("gson created");
		try {
			JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
			data = gson.fromJson(reader, timelindeDataType);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			logger.error("could not load jsnon file because : "+e.getMessage()+" "+e.getStackTrace());
		}
		
		TimelineData timeline = new  TimelineData();
		
		for (Categories c : data.getCategories()) {
			
		}
		
		int i = 0;
		Random randomGenerator = new Random();
		Calendar cal = Calendar.getInstance();
		for (Stories s : data.getStories()) {
			logger.info("Story added: "+s.getTitle());
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
				logger.info("parse exception in Json: "+e.getMessage());
			} catch (Exception e){
				logger.info(loggingPrefix+" Exception in Method initialize while parsin json: "+e.getMessage()+" "+e.getStackTrace());
			}
			
		return successful;
	}
		
		} finally {
			
		}
	}

public void transcribeTimelineData(TimelineData timeline, TLTimelineData data){
	timeline.setId(Long.valueOf(data.getId()));
	timeline.setAboutText(data.getAboutText());
	timeline.setAltFlickrImageUrl(data.getAltFlickrImageUrl());
	timeline.setAutoPlay(data.getAutoPlay());
	timeline.setBackgroundColour(data.getBackgroundColour());
	timeline.setBackgroundImage(backgroundImage);
	timeline.setBgScale(bgScale);
	timeline.setBgStyle(bgStyle);
	timeline.setCopyable(copyable);
	timeline.setCssFile(cssFile);
	timeline.setDisplayStripes(displayStripes);
	timeline.setDontDisplayIntroPanel(dontDisplayIntroPanel);
	timeline.setDurHeadlineColour(durHeadlineColour);
	timeline.setEmbed(embed);
	timeline.setEmbedHash(embedHash);
	timeline.setEndDate(endDate);
	timeline.setExpander(expander);
	timeline.setFontBase(fontBase);
	timeline.setFontBody(fontBody);
	timeline.setFontHead(fontHead);
	timeline.setHeaderBackgroundColour(headerBackgroundColour);
	timeline.setHeaderTextColour(headerTextColour);
	timeline.setHomepage(homepage);
	timeline.setHost(host);
	timeline.set
}
 */
}
	