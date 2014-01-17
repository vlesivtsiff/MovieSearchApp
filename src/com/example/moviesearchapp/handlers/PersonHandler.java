package com.example.moviesearchapp.handlers;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.example.moviesearchapp.model.Image;
import com.example.moviesearchapp.model.Person;

public class PersonHandler extends DefaultHandler {
	
	private StringBuffer buffer = new StringBuffer();
	
	private ArrayList<Person> personList;
	private Person person;
	private ArrayList<Image> personImageList;
	private Image personImage;
	
	@Override
	public void startElement(
			String namespaceURI,
			String localName,
			String qName,
			Attributes atts
			) throws SAXException {
		buffer.setLength(0);
		
		if(localName.equals("people")) {
			personList = new ArrayList<Person>();
		}
		else if(localName.equals("person")) {
			person = new Person();
		}
		else if(localName.equals("images")) {
			personImageList = new ArrayList<Image>();
		}
		else if(localName.equals("image")) {
			personImage = new Image();
			personImage.type = atts.getValue("type");
			personImage.url = atts.getValue("url");
			personImage.size = atts.getValue("size");
			personImage.width = Integer.parseInt(atts.getValue("width"));
			personImage.height = Integer.parseInt(atts.getValue("height"));
		}
	}
	
	@Override
	public void endElement(
			String namespaceURI,
			String localName,
			String qName
			) throws SAXException {
		if(localName.equals("person")) {
			personList.add(person);
		}
		else if(localName.equals("score")) {
			person.score = buffer.toString();
		} 
		else if(localName.equals("popularity")) {
			person.popularity = buffer.toString();
		} 
		else if(localName.equals("name")) {
			person.name = buffer.toString();
		} 
		else if(localName.equals("id")) {
			person.id = buffer.toString();
		} 
		else if(localName.equals("bibliography")) {
			person.bibliography = buffer.toString();
		} 
		else if(localName.equals("url")) {
			person.url = buffer.toString();
		} 
		else if(localName.equals("version")) {
			person.version = buffer.toString();
		} 
		else if(localName.equals("last_modified_at")) {
			person.lastModifiedAt = buffer.toString();
		} 
		else if(localName.equals("image")) {
			person.imageList.add(personImage);
		} 
		else if(localName.equals("images")) {
			person.imageList = personImageList;
		} 
	}

	public ArrayList<Person> retrievePersonList() {
		return personList;
	}
	
	@Override
	public void characters(char[] ch, int start, int length) {
		buffer.append(ch, start, length);
	}

}
