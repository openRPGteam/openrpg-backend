package info.openrpg.rest.world;

import info.openrpg.ejb.GameServerBean;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class WorldRest {
    @Inject
    private GameServerBean world;

    public WorldRest() {
    }

    @GET
    @Path("world/players")
    public int getPlayersCount() {
        return world.getPlayersCount();
    }
}
