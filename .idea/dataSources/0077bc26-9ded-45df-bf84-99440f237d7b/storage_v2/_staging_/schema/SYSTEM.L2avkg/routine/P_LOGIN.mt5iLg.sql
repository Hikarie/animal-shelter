create procedure p_login
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
/

