package com.animal.operate;

import com.animal.conn.dbConnector;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.jdbc.OracleType;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;

@Component
@Path("/table")
public class Table {
    Connection conn = null;
    PreparedStatement call = null;

    public Table(){
        dbConnector dbc = new dbConnector();
        dbc.connect();
        conn = dbc.getConn();
    }

    @GET
    @Path("/shelter")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JSONArray shelter() throws SQLException {
        String sql = "select * from SHELTER";
        JSONArray json_arr = getJSONArray(sql);
        return json_arr;
    }
    @GET
    @Path("/user")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JSONArray user() throws SQLException {
        String sql = "select STAFF.ID STID,STAFF.SNAME SNAME,EMAIL,PHONE,STAFF.SID SID,SHELTER.NAME NAME,TYPE\n" +
                        "from STAFF,SHELTER\n" +
                        "where SHELTER.ID=STAFF.SID";
        JSONArray json_arr = getJSONArray(sql);
        return json_arr;
    }
    @GET
    @Path("/animal")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JSONArray animal() throws SQLException {
        String sql = "select ANIMAL.ID ID,ANO,ANAME,TYPE,SEX,AGE,IMAGE,ANIMAL.SID SID,SHELTER.NAME NAME\n" +
                "FROM ANIMAL,SHELTER\n" +
                "WHERE ANIMAL.SID = SHELTER.ID";
        JSONArray json_arr = getJSONArray(sql);
        return json_arr;
    }

    @GET
    @Path("/health")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JSONArray health(@QueryParam("stid") String stid, @QueryParam("role") String role) throws SQLException {
        String sql = "";
        if(role == "1") {   // 如果是工作人员，根据权限进行相应处理。(工作人员只有对自家收容所健康记录查、增、删、改的权限。
            sql = "{? = call p_login(?)}";   // 查询当前登录用户所在的收容所id
            CallableStatement call = conn.prepareCall(sql);
            call.setObject(2, stid);
            call.registerOutParameter(1, OracleType.VARCHAR2);
            call.execute();
            String sid = call.getNString(1);
            // 查询出当前登录用户所在收容所的健康检查记录。
            sql = "select AID,STID,STATUS,CHECKDATE,REMARK\n" +
                    "from HEALTH, STAFF\n" +
                    "where HEALTH.STID = STAFF.ID and STAFF.SID =" + sid;
        }
        else{   // 如果是管理员，将表种所有数据呈现出来。
            sql = "select AID,STID,STATUS,CHECKDATE,REMARK\n" +
                    "from HEALTH\n";
        }
        JSONArray json_arr = getJSONArray(sql);
        return json_arr;
    }

    @GET
    @Path("/vaccine")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JSONArray vaccine(@QueryParam("stid") String stid, @QueryParam("role") String role) throws SQLException {
        String sql = "";
        if(role == "1") {   // 如果是工作人员，根据权限进行相应处理。(工作人员只有对自家收容所健康记录查、增、删、改的权限。
            sql = "{? = call p_login(?)}";   // 查询当前登录用户所在的收容所id
            CallableStatement call = conn.prepareCall(sql);
            call.setObject(2, stid);
            call.registerOutParameter(1, OracleType.VARCHAR2);
            call.execute();
            String sid = call.getNString(1);
            // 查询出当前登录用户所在收容所的健康检查记录。
            sql = "select AID,STID,VNAME,VTIME,REMARK\n" +
                    "from VACCINE, STAFF\n" +
                    "where VACCINE.STID = STAFF.ID and STAFF.SID =" + sid;
        }
        else{   // 如果是管理员，将表种所有数据呈现出来。
            sql = "select AID,STID,VNAME,VTIME,REMARK\n" +
                    "from VACCINE\n";
        }
        JSONArray json_arr = getJSONArray(sql);
        return json_arr;
    }

    public JSONArray getJSONArray(String sql) throws SQLException {
        call = conn.prepareStatement(sql);
        ResultSet rs = call.executeQuery();
        ResultSetMetaData md = rs.getMetaData();
        JSONArray json_arr = new JSONArray();
        while (rs!=null&rs.next()){
            JSONObject json_obj = new JSONObject();
            int n = md.getColumnCount();
            for(int i = 1;i<=n;i++){
                if(rs.getString(i)==null||rs.getString(i)=="null")
                    json_obj.put(md.getColumnName(i),"");   // 为空值的情况
                else json_obj.put(md.getColumnName(i),rs.getString(i));
            }
            json_arr.add(json_obj);
        }
        System.out.println(json_arr.toString());
        return json_arr;
    }
}
