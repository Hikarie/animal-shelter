package com.animal.operate;

import com.animal.conn.dbConnector;
import net.sf.json.JSONObject;
import oracle.jdbc.OracleType;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
@Path("/statistic")
public class Statistics {
    Connection conn = null;
    CallableStatement call = null;

    public Statistics(){
        dbConnector dbc = new dbConnector();
        dbc.connect();
        conn = dbc.getConn();
    }

    @GET
    @Path("/count")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JSONObject Count() throws SQLException {
        String sql = "{call p_count(?,?,?,?,?,?,?)}";
        call = conn.prepareCall(sql);
        call.registerOutParameter(1, OracleType.NUMBER);
        call.registerOutParameter(2, OracleType.NUMBER);
        call.registerOutParameter(3, OracleType.NUMBER);
        call.registerOutParameter(4, OracleType.NUMBER);
        call.registerOutParameter(5, OracleType.NUMBER);
        call.registerOutParameter(6, OracleType.NUMBER);
        call.registerOutParameter(7, OracleType.NUMBER);
        call.execute();
        int count_user = call.getInt(1);
        int count_shelter = call.getInt(2);
        int count_animal = call.getInt(3);
        int count_health = call.getInt(4);
        int count_vaccine = call.getInt(5);
        int count_admin = call.getInt(6);
        int res = call.getInt(7);

        Map map = new HashMap();
        map.put("staff", count_user);
        map.put("shelter", count_shelter);
        map.put("animal", count_animal);
        map.put("health", count_health);
        map.put("vaccine", count_vaccine);
        map.put("admin", count_admin);
        map.put("res", res);
        JSONObject json = JSONObject.fromObject(map);

        return json;
    }
}
