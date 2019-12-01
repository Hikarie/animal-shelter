create procedure p_signup
    (userid varchar2,
     username varchar2,
     userpwd varchar2,
     useremail varchar2,
     userphone varchar2,
     usersid varchar2,
     usertype varchar2)
as
begin
  insert into staff
  values(userid,username,userpwd,useremail,userphone,usersid,usertype);
end p_signup;
/

