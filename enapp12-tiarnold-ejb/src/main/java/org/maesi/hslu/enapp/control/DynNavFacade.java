package org.maesi.hslu.enapp.control;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.ProxySelector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.ProxySelectorRoutePlanner;
import org.maesi.hslu.enapp.ext.dynnav.Item;
import org.maesi.hslu.enapp.ext.dynnav.ItemFields;
import org.maesi.hslu.enapp.ext.dynnav.ItemFilter;
import org.maesi.hslu.enapp.ext.dynnav.ItemPort;
import org.maesi.hslu.enapp.ext.dynnav.ItemService;
import org.maesi.hslu.enapp.ext.enappdeamon.SalesOrder;

@Stateless
@LocalBean
public class DynNavFacade {

	private static final String STATUS_URL = "http://10.29.3.152/ENAPPDaemon-war/resources/salesorder/corr/";
    
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

    public Item find(String aProductId) {
        Item item = null;
        for (Item _item : findAll()) {
            if (_item.getNo().equals(aProductId)) {
                item = _item;
                break;
            }
        }
        return item;
    }

    public boolean exists(String productId) {
        return find(productId) != null;
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
    
    public SalesOrder getOrderState(String aCorrelationId) throws Exception {
    	String _url = STATUS_URL + aCorrelationId;
        HttpGet getRequest = new HttpGet(_url);
        // TODO: entfernen
        DefaultHttpClient client = new DefaultHttpClient();
        client.setRoutePlanner(new ProxySelectorRoutePlanner(
                client.getConnectionManager().getSchemeRegistry(),
                ProxySelector.getDefault()));
        HttpResponse httpResponse = null;
        try {
            httpResponse = client.execute(getRequest);
        } catch (Exception e) {
            throw new Exception("Fehler bei der Statusueberpruefung: " + e.getMessage());
        }
        return unmarshalResponse(httpResponse);
    }
    
    private SalesOrder unmarshalResponse(HttpResponse httpResponse) throws Exception {
        try {
            Unmarshaller unmarshaller = JAXBContext.newInstance(SalesOrder.class).createUnmarshaller();
            return (SalesOrder) unmarshaller.unmarshal(httpResponse.getEntity().getContent());
        } catch (Exception e) {
            throw new Exception(String.format("Could not unmarshal response from enapp deamon service: %s", e.getMessage()));
        }
    }
}
