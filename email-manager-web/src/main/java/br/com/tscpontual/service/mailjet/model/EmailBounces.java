package br.com.tscpontual.service.mailjet.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bounces")
public class EmailBounces {

	@XmlElement(name = "item", required = true)
    protected List<EmailBounceItem> items;

	public List<EmailBounceItem> getItems() {
		if (items == null) {
			items = new ArrayList<EmailBounceItem>();
        }
        return this.items;
	}
	
}
