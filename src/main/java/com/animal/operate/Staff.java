// 对STAFF表进行增、删、改
// 可以使用该类的功能，说明此人是超级管理员，所以此处不再在函数体内对身份进行验证。

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
@Path("/staff")
public class Staff {

    Connection conn = null;
    PreparedStatement pcall = null;
    String sql = "";

    public Staff(){
        dbConnector dbc = new dbConnector();
        dbc.connect();
        conn = dbc.getConn();
    }

    @POST
    @Path("/insert")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public void insert(@FormParam("sname") String sname, @FormParam("pwd") String pwd, @FormParam("email") String email, @FormParam("phone") String phone,
                       @FormParam("sid") String sid, @FormParam("type") String type) throws SQLException {
        String id = getId("ST");    // 申请一个以ST为前缀的ID

        sql = "insert into STAFF " +
                "values("+id+","+sname+","+pwd+","+email+","+phone+","+sid+","+type+")";
        pcall = conn.prepareStatement(sql);
        pcall.execute();
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
