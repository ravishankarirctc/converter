package com.litepdf.converter.dao.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.litepdf.converter.dao.ConverterTrackDao;
import com.litepdf.converter.entity.LitePdfConverterTrack;

@Repository
public class ConverterTrackDaoImpl implements ConverterTrackDao {
	
	@Autowired
	HibernateTemplate hibernateTemplate;
	
	@Transactional
	@Qualifier("transactionManager")
	public String saveTracker(LitePdfConverterTrack litePdfConverterTrack) {
		hibernateTemplate.save(litePdfConverterTrack);
		return "success";
	}
}
