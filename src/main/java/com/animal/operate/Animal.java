// 对ANIMAL表进行增、删、改
// 权限：工作人员只能对自己所在收容所的动物信息操作。

package com.animal.operate;

import com.animal.conn.dbConnector;
import oracle.jdbc.OracleType;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.animal.user.Id.getId;

@Component
@Path("/animal")
public class Animal {

    Connection conn = null;
    PreparedStatement pcall = null;
    String sql = "";

    public Animal(){
        dbConnector dbc = new dbConnector();
        dbc.connect();
        conn = dbc.getConn();
    }

    @POST
    @Path("/insert")
    @Produces(MediaType.TEXT_PLAIN)
    public int insert(@FormParam("ano") String stid, @FormParam("ano") String ano, @FormParam("aname") String aname, @FormParam("type") String type,
                       @FormParam("sex") String sex, @FormParam("age") String age, @FormParam("image") String image) throws SQLException {
        String id = getId("AN");    // 申请一个以HT为前缀的ID

        sql = "{? = call p_login(?)}";   // 查询当前登录用户所在的收容所id
        CallableStatement call = conn.prepareCall(sql);
        call.setObject(2, stid);
        call.registerOutParameter(1, OracleType.VARCHAR2);
        call.execute();
        String sid = call.getNString(1);

        sql = "insert into ANIMAL " +
                "values(\'"+id+"\',\'"+ano+"\',\'"+aname+"\',\'"+type+"\',\'"+sex+"\',"+age+",\'"+image+"\',\'"+sid+"\')";
        System.out.println(sql);
        pcall = conn.prepareStatement(sql);
        try{
            pcall.execute();
        }catch (Exception e){
            System.out.println(e.toString());
            return 0;
        }
        return 1;
    }

    @POST
    @Path("/update")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public void update(@FormParam("id") String id, @FormParam("ano") String ano, @FormParam("aname") String aname, @FormParam("type") String type,
                       @FormParam("sex") String sex, @FormParam("age") String age) throws SQLException {
        sql = "update ANIMAL " +
                "set ANO="+ano+
                ",set ANAME="+aname+
                ",set TYPE="+type+
                ",set SEX="+sex+
                ",set AGE="+age+
                ",where id="+id;
        pcall = conn.prepareStatement(sql);
        pcall.execute();
    }

    @POST
    @Path("/delete")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public void update(@FormParam("id") String id) throws SQLException {
        sql = "delete from ANIMAL " +
                "where id="+id;
        pcall = conn.prepareStatement(sql);
        pcall.execute();
    }
}
