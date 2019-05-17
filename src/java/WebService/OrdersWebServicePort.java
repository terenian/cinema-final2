/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import WebService_client.GetTicketsResponse;
import WebService_client.OrdersWebService;
import WebService_client.Ticket;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

/**
 * REST Web Service
 *
 * @author Yitz
 */
@Path("orderswebserviceport")
public class OrdersWebServicePort {
    private OrdersWebService port;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of OrdersWebServicePort
     */
    public OrdersWebServicePort() {
        port = getPort();
    }

    /**
     * Invokes the SOAP method useTickets
     * @param tickets resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("usetickets/")
    public String postUseTickets(JAXBElement<List<Ticket>> tickets) {
        try {
            // Call Web Service Operation
            if (port != null) {
                boolean result = port.useTickets(tickets.getValue());
                return new java.lang.Boolean(result).toString();
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getTickets
     * @param order resource URI parameter
     * @return an instance of javax.xml.bind.JAXBElement<WebService_client.GetTicketsResponse>
     */
    @GET
    @Produces("application/xml")
    @Consumes("text/plain")
    @Path("gettickets/")
    public JAXBElement<GetTicketsResponse> getTickets(@QueryParam("order")
            @DefaultValue("0") int order) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.util.List<WebService_client.Ticket> result = port.getTickets(order);

                class GetTicketsResponse_1 extends WebService_client.GetTicketsResponse {

                    GetTicketsResponse_1(java.util.List<WebService_client.Ticket> _return) {
                        this._return = _return;
                    }
                }
                WebService_client.GetTicketsResponse response = new GetTicketsResponse_1(result);
                return new WebService_client.ObjectFactory().createGetTicketsResponse(response);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method validateUser
     * @param username resource URI parameter
     * @param password resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("validateuser/")
    public String getValidateUser(@QueryParam("username") String username, @QueryParam("password") String password) {
        try {
            // Call Web Service Operation
            if (port != null) {
                boolean result = port.validateUser(username, password);
                return new java.lang.Boolean(result).toString();
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     *
     */
    private OrdersWebService getPort() {
        try {
            // Call Web Service Operation
            WebService_client.OrdersWebService_Service service = new WebService_client.OrdersWebService_Service();
            WebService_client.OrdersWebService p = service.getOrdersWebServicePort();
            return p;
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }
}
