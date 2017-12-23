package net.mithra.familly.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import net.mithra.familly.db.bo.DBUserService;
import net.mithra.familly.service.FileService;
import net.mithra.familly.ws.controller.util.ServiceMapper;
import net.mithra.familly.ws.manager.ParametersCollector;

@PropertySource("classpath:familly.properties")
public abstract class  ControllerHelper {

	@Autowired
	protected ServiceMapper serviceMapper;

	
	@Autowired
	protected DBUserService dbUserService;
	
	@Autowired
	protected FileService fileService;
	
	@Autowired
	protected ParametersCollector parametersCollector;
	
	
	@Value("#{propertiesFile['folder.temp']}")
	protected String folderTemp;
	
	@Value("#{propertiesFile['folder.base']}")
	protected String folderBase;


}
