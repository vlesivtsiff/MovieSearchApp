package com.example.moviesearchapp.services;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.example.moviesearchapp.handlers.MovieHandler;
import com.example.moviesearchapp.handlers.PersonHandler;
import com.example.moviesearchapp.model.Movie;
import com.example.moviesearchapp.model.Person;

public class XMLParser {
	private XMLReader initializeReader() throws ParserConfigurationException, SAXException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		XMLReader xmlReader = parser.getXMLReader();
		return xmlReader;
	}
	
	public ArrayList<Person> parsePeopleResponse(String xml) {
		try {
			XMLReader xmlReader = initializeReader();
			PersonHandler personHandler = new PersonHandler();
			
			xmlReader.setContentHandler(personHandler);
			xmlReader.parse(new InputSource(new StringReader(xml)));
			
			return personHandler.retrievePersonList();
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Movie> parseMovieResponse(String xml) {
		try {
			XMLReader xmlReader = initializeReader();
			MovieHandler movieHandler = new MovieHandler();
			
			xmlReader.setContentHandler(movieHandler);
			xmlReader.parse(new InputSource(new StringReader(xml)));
			
			return movieHandler.retrieveMovieList();
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
