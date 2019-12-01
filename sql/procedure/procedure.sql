/*
用户登录身份验证
param: userid  用户输入ID
param: userpwd 用户输入密码
return: o_result 状态码
*/
create or replace procedure p_login
    (userid varchar2,
     userpwd varchar2,
     o_result out integer)
as
id_ varchar2(20);
pwd_ varchar2(20);
count_ integer;
begin
    select count(1) into count_
    from admin
    where id = userid and pwd = userpwd;
    if count_ > 0 then
        select id,pwd
        into id_,pwd_
        from admin
        where id = userid and pwd = userpwd;
    end if;
    if id_ is not null then
        o_result:=2;
    else
        select count(1) into count_
        from staff
        where id = userid and pwd = userpwd;
        if count_ > 0 then
            select id,pwd
            into id_,pwd_
            from staff
            where id = userid and pwd = userpwd;
        end if;
--  id_1:=id_;
        if id_ is not null then
            o_result:=1;
        else
            o_result:=0;
        end if;
    end if;
end p_login;

/*
用户注册
param: userid  用户ID（业务逻辑层生成）
param: username    用户名
param: userpwd 密码
param: Userphone   手机号码
param: Useremail   邮箱地址
param: Usersid 所在收容所
param: Usertype    类型（分为工作人员和志愿者）
return: o_result 状态码
*/
create procedure p_signup
    (userid varchar2,
     username varchar2,
     userpwd varchar2,
     useremail varchar2,
     userphone varchar2,
     usersid varchar2,
     usertype varchar2,
     o_result out integer)
as
begin
  insert into staff
  values(userid,username,userpwd,useremail,userphone,usersid,usertype);
  o_result := 1;
exception
  when others then
    o_result := -163;
end p_signup;


/*
系统主页上对各表的统计
param: Count_admin 超级管理员总数
param: Count_shelter   收容所总数
param: Count_animal    动物总数
param: Count_staff 工作人员总数
param: Count_health    健康检查记录总数
param: Count_vaccine   疫苗接种记录总数
return: o_result 状态码
*/

create or replace procedure p_count(
    count_user out integer,
    count_shelter out integer,
    count_animal out integer,
    count_health out integer,
    count_vaccine out integer,
    count_admin out integer,
    o_result out integer)
is
begin
    select count(1)
    into count_user
    from STAFF;
    select count(1)
    into count_shelter
    from SHELTER;
    select count(1)
    into count_animal
    from ANIMAL;
    select count(1)
    into count_health
    from HEALTH;
    select count(1)
    into count_vaccine
    from VACCINE;
    select count(1)
    into count_admin
    from ADMIN;
    o_result := 1;
exception
    when others then
        o_result := -163;
end p_count;

/*
查询用户所在收容所
（这里主要为权限控制作辅助用，前端浏览器在用户登录时将用户id保存在localstorage，
每次用户使用各项查询功能时都要根据保存的id来请求后台资源）
param: sid 用户ID
return: stid 收容所ID
*/
create function p_sid(stid in varchar2)
    return varchar2
as
    x varchar2(20);
begin
    select SID
    into x
    from
        (select * from staff
         where staff.ID = stid
        )
    where rownum<2;   -- 要加；
    return x;
end p_sid;

/*
用户记录对收容动物的记录
param: Takein _id  记录ID（业务逻辑层生成）
param: Takein _ano 动物编号
param: Takein _aname   动物名字
param: Takein _type    动物品种
param: Takein _sex 动物性别
param: Takein _image   动物照片
param: Takein_sid  收容所ID
return: o_result 状态码
*/
create procedure p_takein
    (takin_id varchar2,
     takein_ano varchar2,
     takein_aname varchar2,
     takein_type varchar2,
     takein_sex varchar2,
     takein_image varchar2,
     takein_sid varchar2,
     o_result out integer)
as
begin
  insert into animal
  values(takin_id, takin_ano, takin_aname, takin_type, takin_sex, takin_image, takin_sid);
  o_result := 1;
exception
  when others then
    o_result := -163;
end p_takein;

/*
用户记录对收容动物检查的记录
param: Check_id    记录ID（业务逻辑层生成）
param: Check_aid   动物ID
param: Check_stid  检查人ID
param: Check_status    健康状态（‘health’或症状）
param: Check_date  检查日期
param: Check_remark    备注
return: o_result 状态码
*/
create procedure p_check
    (check_id varchar2,
     check_aid varchar2,
     check_stid varchar2,
     check_status varchar2,
     check_date varchar2,
     check_remark varchar2,
     o_result out integer)
as
begin
  insert into health
  values(check_id, check_aid, check_stid, check_status, check_date, check_remark);
  o_result := 1;
exception
  when others then
    o_result := -163;
end p_check;

/*
用户记录对收容动物接种疫苗的记录
param: inject_id   记录ID（业务逻辑层生成）
param: inject _aid 动物ID
param: inject _stid    接种人ID
param: inject_vname    疫苗名称
param: inject _vdate   接种日期
param: inject _remark  备注
return: o_result 状态码
*/

create procedure p_inject
    (inject_id varchar2,
     inject_aid varchar2,
     inject_stid varchar2,
     inject_vname varchar2,
     inject_vdate varchar2,
     inject_remark varchar2,
     o_result out integer)
as
begin
  insert into vaccine
values(inject_id,inject_aid,inject_stid,inject_vname,inject_vdate,inject_remark);
  o_result := 1;
exception
  when others then
    o_result := -163;
end p_inject;


/*
超级管理员对收容所的登记
param: Register_id 收容所ID（业务逻辑层生成）
param: Register_name   名称
param: Register_addr   地址
param: Register_code   邮编
param: Register_total  房间总数
param: Register_remark 备注
return: o_result 状态码
*/
create procedure p_register
    (register_id varchar2,
     register_name varchar2,
     register _addr varchar2,
     register _code varchar2,
     register _total varchar2,
     register _remark varchar2,
     o_result out integer)
as
begin
  insert into shelter
values(register_id,register_name,register_addr,register_code,register_total,register_remark);
  o_result := 1;
exception
  when others then

