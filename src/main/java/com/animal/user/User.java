package com.animal.user;

import oracle.jdbc.OracleType;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.animal.conn.dbConnector;
import org.springframework.web.bind.annotation.RequestParam;

import static com.animal.user.Id.getId;

@Component
@Path("/user")
public class User {
    Connection conn = null;
    CallableStatement call = null;
    public User() {
        dbConnector dbc = new dbConnector();
        dbc.connect();
        conn = dbc.getConn();
    }
    @POST
    @Path("/login")
    @Produces(MediaType.TEXT_PLAIN)
    public int login(@FormParam("id")String id, @FormParam("pwd")String pwd) throws SQLException {
        String sql = "{call p_login(?,?,?)}";
        call = conn.prepareCall(sql);
        call.setObject(1, id);
        call.setObject(2, pwd);
        call.registerOutParameter(3, OracleType.NUMBER);
        call.execute();
        int res = call.getInt(3);
        return res;     // 1为工作人员；2为超级管理员；0为找不到用户
    }

    @POST
    @Path("/register")
    @Produces(MediaType.TEXT_PLAIN)
    public int register(@FormParam("name")String name, @FormParam("pwd")String pwd, @FormParam("email")String email, @FormParam("phone")String phone, @FormParam("sid")String sid, @FormParam("type")String type) throws SQLException {
        CallableStatement call = conn.prepareCall("{call p_signup(?,?,?,?,?,?,?)}");
        String id = getId("ST");
        System.out.println(id);
        call.setObject(1, id);
        call.setObject(2, name);
        call.setObject(3, pwd);
        call.setObject(4, email);
        call.setObject(5, phone);
        call.setObject(6, sid);
        call.setObject(7, type);
        try {
            call.execute();
        }catch(Exception e){
            System.out.println(e.toString());
            return 0;
        }
        return 1;     // 1为工作人员；2为超级管理员；0为找不到用户
    }

}

