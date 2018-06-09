package com.litepdf.converter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.litepdf.converter.dao.ConverterTrackDao;
import com.litepdf.converter.entity.LitePdfConverterTrack;
import com.litepdf.converter.service.ConverterTrackService;

@Service
public class ConverterTrackServiceImpl implements ConverterTrackService {

	@Autowired
	ConverterTrackDao converterTrackDao ;
	
	public String saveTracker(LitePdfConverterTrack litePdfConverterTrack) {
		return converterTrackDao.saveTracker(litePdfConverterTrack);
	}
}
