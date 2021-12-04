package si.fri.rso.catalogue;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import si.fri.rso.catalogue.cdi.configuration.KumuluzeeConfig;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@RequestScoped
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConfigResource {

    @Inject
    private KumuluzeeConfig configProperties;

    @GET
    @Path("/config")
    public Response test() {
        String response =
                "{" +
                        "\"base-url\": \"%s\"," +
                        "\"port\": %d," +
                        "}";

        response = String.format(
                response,
                configProperties.getServer().getBaseUrl(),
                configProperties.getServer().getPort()
        );

        return Response.ok(response).build();
    }


     @GET
    @Path("/get")
    public Response get() {
        return Response.ok(ConfigurationUtil.getInstance().get("rest-config.string-property").orElse("nope")).build();
    }

}
