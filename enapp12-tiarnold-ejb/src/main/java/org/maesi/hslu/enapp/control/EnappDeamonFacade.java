package org.maesi.hslu.enapp.control;

import java.io.StringWriter;
import java.net.ProxySelector;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.ProxySelectorRoutePlanner;
import org.maesi.hslu.enapp.entity.Purchase;
import org.maesi.hslu.enapp.entity.Purchaseitem;
import org.maesi.hslu.enapp.ext.enappdeamon.Customer;
import org.maesi.hslu.enapp.ext.enappdeamon.Line;
import org.maesi.hslu.enapp.ext.enappdeamon.PurchaseMessage;
import org.maesi.hslu.enapp.ext.enappdeamon.SalesOrder;



@Stateless
public class EnappDeamonFacade {

    private static final String URL = "http://10.29.3.152/ENAPPDaemon-war/resources/salesorder/corr/%s";
    
    @Resource(mappedName = "jms/EnappQueueFactory")
    private QueueConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/enappqueue")
    private Queue queue;

    @Inject
    private PurchaseItemFacade purchaseItemFacade;
    @Inject
    private CustomerFacade customerFacade;

    public String sendPurchase(Purchase purchase) {
        String correlationId = getCorrelationId();
        String content = marshalMessage(getPurchaseMessage(purchase));
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            TextMessage message = session.createTextMessage(content);
            message.setStringProperty("MessageFormat", "Version 1.5");
            message.setJMSCorrelationID(correlationId);
            session.createProducer(queue).send(message);
        } catch (Exception e) {
        	System.out.println("Fehler beim EnappDaeom" + e.getMessage());
        }
        return correlationId;
    }

    private String getCorrelationId() {
    	return String.valueOf((new Random().nextInt(8999) +  1000) 
                * 10000000000000l
                + Calendar.getInstance().getTimeInMillis());
    }

    private PurchaseMessage getPurchaseMessage(Purchase aPurchase) {
        PurchaseMessage message = new PurchaseMessage();
        message.setPurchaseId(aPurchase.getId());
        message.setPaymentId(aPurchase.getPaymentid());
        message.setStudent("tiarnold");
        message.setTotalAmount(aPurchase.getTotalamount().longValue()*100);
        message.setDate(aPurchase.getDatetime());
        message.setCustomer(getCustomer(aPurchase.getCustomerid()));
        message.setLines(getLines(aPurchase.getId()));
        return message;
    }

    private Customer getCustomer(int customerId) {
        org.maesi.hslu.enapp.entity.Customer entity = customerFacade.find(customerId);
        Customer customer = new Customer();
        customer.setExternalCustomerId(entity.getNavisionid());
        customer.setFullName(entity.getName());
        customer.setAddress(entity.getAddress());
        customer.setPostCode("6214");
        customer.setCity("Schenkon");
        customer.setUsername(entity.getUsername());
        return customer;
    }

    private Collection<Line> getLines(int purchaseId) {
        Collection<Line> lines = new ArrayList<Line>();
        for (Purchaseitem entity : purchaseItemFacade.findAllByPurchaseId(purchaseId)) {
            Line line = new Line();
            line.setProductId(entity.getProductid());
            line.setDescription("");
            line.setQuantity(entity.getQuantity().longValue());
            line.setAmount(entity.getLineamount().longValue());
            lines.add(line);
        }
        return lines;
    }

    private String marshalMessage(PurchaseMessage message) {
        String textMessage = null;
        try {
            JAXBContext context = JAXBContext.newInstance(PurchaseMessage.class);
            StringWriter writer = new StringWriter();
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(message, writer);
            textMessage = writer.toString();
        } catch (Exception e) {
        	System.out.println("Fehler beim EnappDaeom-Marhalling" + e.getMessage());
        }
        return textMessage;
    }

    public SalesOrder getOrderState(String correlationId) throws Exception {
        HttpGet getRequest = new HttpGet(String.format(URL, correlationId));
        DefaultHttpClient client = new DefaultHttpClient();
        client.setRoutePlanner(new ProxySelectorRoutePlanner(
                client.getConnectionManager().getSchemeRegistry(),
                ProxySelector.getDefault()));
        HttpResponse httpResponse = null;
        try {
            httpResponse = client.execute(getRequest);
        } catch (Exception e) {
        	System.out.println("Fehler beim EnappDaeom-Status" + e.getMessage());
        }
        return unmarshalResponse(httpResponse);
    }

    private SalesOrder unmarshalResponse(HttpResponse httpResponse){
        try {
            Unmarshaller unmarshaller = JAXBContext.newInstance(SalesOrder.class).createUnmarshaller();
            return (SalesOrder) unmarshaller.unmarshal(httpResponse.getEntity().getContent());
        } catch (Exception e) {
        	System.out.println("Fehler beim EnappDaeom-Unmarshalling" + e.getMessage());
        	return null;
        }
    }
}
