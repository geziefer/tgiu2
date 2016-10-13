package geziefer.tgiu2.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import geziefer.tgiu2.LocalEntityManagerFactory;
import geziefer.tgiu2.entity.Round;

@Path("/")
public class TGIUService {
	private static final ObjectMapper jsonMapper = new ObjectMapper().findAndRegisterModules();
	
	@GET
	@Path("/rounds")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Round> getRounds() {
		jsonMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		EntityManager em = LocalEntityManagerFactory.createEntityManager();
		TypedQuery<Round> query = em.createNamedQuery("Round.findAll", Round.class);
		return query.getResultList();
	}
}
