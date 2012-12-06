package org.maesi.hslu.enapp.control;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.maesi.hslu.enapp.ext.dynnav.Item;
import org.maesi.hslu.enapp.ext.dynnav.ItemFields;
import org.maesi.hslu.enapp.ext.dynnav.ItemFilter;
import org.maesi.hslu.enapp.ext.dynnav.ItemPort;
import org.maesi.hslu.enapp.ext.dynnav.ItemService;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Stateless
@LocalBean
public class DynNavFacade {

    private static final String AUTHENTICATION_DOMAIN = "ICOMPANY";
    private static final String AUTHENTICATION_USER = "dynNavWsSvc";
    private static final String AUTHENTICATION_PASSWORD = "icompany";
    
    private ItemPort servicePort;
    private Collection<Item> items;

    public DynNavFacade() {
    	setServiceAuthenticator();
        ItemService service = new ItemService();
        servicePort = service.getItemPort();
    }

    public Collection<Item> findAll() {
        if (items == null) {
            items = getItemsFromService();
        }
        return items;
    }

    public Item find(String productId) {
        Item item = null;
        for (Item candidate : findAll()) {
            if (candidate.getNo().equals(productId)) {
                item = candidate;
                break;
            }
        }
        return item;
    }

    public boolean exists(String productId) {
        return (find(productId) != null);
    }

    private Collection<Item> getItemsFromService() {
        setServiceAuthenticator();
        return servicePort.readMultiple(getFilters(), null, 0).getItem();
    }

    private void setServiceAuthenticator() {
    	Authenticator.setDefault(new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(AUTHENTICATION_DOMAIN + "\\" + AUTHENTICATION_USER, AUTHENTICATION_PASSWORD.toCharArray());
            }
        });
    }

    private List<ItemFilter> getFilters() {
        List<ItemFilter> list = new ArrayList<ItemFilter>();
        ItemFilter filter = new ItemFilter();
        filter.setField(ItemFields.PRODUCT_GROUP_CODE);
        filter.setCriteria("MP3");
        list.add(filter);
        return list;
    }
}
