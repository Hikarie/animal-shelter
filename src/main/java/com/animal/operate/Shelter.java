package com.animal.operate;

import com.animal.conn.dbConnector;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.animal.user.Id.getId;


@Component
@Path("/shelter")
public class Shelter {

    Connection conn = null;
    PreparedStatement pcall = null;
    String sql = "";

    public Shelter(){
        dbConnector dbc = new dbConnector();
        dbc.connect();
        conn = dbc.getConn();
    }

    @POST
    @Path("/insert")
    @Produces(MediaType.TEXT_PLAIN)
    public int insert(@FormParam("name") String name, @FormParam("addr") String addr, @FormParam("code") String code, @FormParam("total") int total, @FormParam("remark") String remark) throws SQLException {
        String id = getId("SH");    // 申请一个以SH为前缀的ID

        sql = "insert into SHELTER " +
                "values(\'"+id+"\',\'"+name+"\',\'"+addr+"\',\'"+code+"\',"+total+","+total+",\'"+remark+"\')";
        pcall = conn.prepareStatement(sql);
        try {
            pcall.execute();
        }catch (Exception e) {
            System.out.println(e.toString());
            return 0;
        }
        return 1;
    }

    @POST
    @Path("/update")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public void update(@FormParam("id") String id, @FormParam("sname") String sname, @FormParam("pwd") String pwd, @FormParam("email") String email, @FormParam("phone") String phone,
                       @FormParam("sid") String sid, @FormParam("type") String type) throws SQLException {
        sql = "update STAFF " +
                "set SNAME="+sname+
                ",set PWD="+pwd+
                ",set EMAIL="+email+
                ",set PHONE="+phone+
                ",set SID="+sid+
                ",set TYPE="+type+
                ",where id="+id;
        pcall = conn.prepareStatement(sql);
        pcall.execute();
    }

    @POST
    @Path("/delete")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public void update(@FormParam("id") String id) throws SQLException {
        sql = "delete from STAFF " +
                "where id="+id;
        pcall = conn.prepareStatement(sql);
        pcall.execute();
    }
}
