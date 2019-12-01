// 对VACCINE表进行增、改
// 权限：工作人员只能对自己所在收容所的疫苗接种信息操作。

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
@Path("/vaccine")
public class Vaccine {
    Connection conn = null;
    PreparedStatement call = null;
    String sql = "";

    public Vaccine(){
        dbConnector dbc = new dbConnector();
        dbc.connect();
        conn = dbc.getConn();
    }

    // 新增接种记录
    @POST
    @Path("/insert")
    @Produces(MediaType.TEXT_PLAIN)
    public int insert(@FormParam("aid") String aid, @FormParam("stid") String stid, @FormParam("vname") String vname, @FormParam("date") String date, @FormParam("remark") String remark) throws SQLException {
        String id = getId("VC");    // 申请一个以VC为前缀的ID
        sql = "insert into VACCINE " +
                "values(\'"+id+"\',\'"+aid+"\',\'"+stid+"\',\'"+vname+"\',\'"+date+"\',\'"+remark+"\')";
        call = conn.prepareStatement(sql);
        try{
            call.execute();
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }
        return 1;
    }

    // 对被接种动物或接种疫苗的名称作修改。
    @POST
    @Path("/update")
    @Produces(MediaType.TEXT_PLAIN)
    public void update(@FormParam("id") String id, @FormParam("aid") String aid, @FormParam("vname") String vname) throws SQLException {
        sql = "update HEALTH " +
                "set aid="+aid+
                ",set status="+vname+
                ",where id="+id;
        call = conn.prepareStatement(sql);
        try{
            call.execute();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
