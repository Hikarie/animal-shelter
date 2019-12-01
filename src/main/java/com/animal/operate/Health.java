// 对HEALTH表进行增、改
// 权限：工作人员只能对自己所在收容所的动物信息操作。

package com.animal.operate;

import com.animal.conn.dbConnector;
import jdk.nashorn.internal.parser.JSONParser;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;

import static com.animal.user.Id.getId;

@Component
@Path("/health")
public class Health {

    Connection conn = null;
    PreparedStatement call = null;
    String sql = "";

    public Health(){
        dbConnector dbc = new dbConnector();
        dbc.connect();
        conn = dbc.getConn();
    }

    @POST
    @Path("/insert")
    @Produces(MediaType.TEXT_PLAIN)
    public int insert(@FormParam("aid")String aid, @FormParam("stid")String stid, @FormParam("status")String status, @FormParam("date")String date, @FormParam("remark")String remark) throws SQLException {
        String id = getId("HT");    // 申请一个以HT为前缀的ID

        sql = "insert into HEALTH " +
                "values(\'"+id+"\',\'"+aid+"\',\'"+stid+"\',\'"+status+"\',\'"+date+"\',\'"+remark+"\')";
        call = conn.prepareStatement(sql);
        try{
            call.execute();
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }
        return 1;
    }

    @POST
    @Path("/update")
    @Produces(MediaType.TEXT_PLAIN)
    public void update(@FormParam("id") String id, @FormParam("aid") String aid, @FormParam("status") String status) throws SQLException {
        sql = "update HEALTH " +
                "set aid="+aid+
                ",set status="+status+
                ",where id="+id;
        call = conn.prepareStatement(sql);
        try{
            call.execute();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
