/*
当一个工作人员往自家收容所收容动物时，要先检查房间是否还有余量，如果还有余量，就收容该动物（房间余量减1），否则不收容该动物（抛出异常）。
*/
create or replace TRIGGER tri_shelter_rest
    BEFORE INSERT ON ANIMAL
    for each row
DECLARE
    REST_ NUMBER;
    INSERT_EXCE exception;
BEGIN
    SELECT REST INTO REST_
    FROM SHELTER
    WHERE ID = :NEW.SID;
    IF REST_>0 then
        UPDATE SHELTER
        set REST = REST-1
        where ID = :NEW.SID;
    else
        raise INSERT_EXCE;
    end if;
END;

/*
每当一个新的收容所被登记在系统时，自动生成该收容所的动物信息视图。当这个收容所倒闭时，删除该触发器生成的相应视图。
*/
create or replace trigger shelter_animal
after insert or delete on
shelter for each row
declare
shid varchar2(8);
infor varchar2(50);
begin
  if inserting then
    shid := :new.id;
    infor := 'create a shelter named '+shid;
    execute immediate 'create or raplace view V_'||shid||'_staff(id,ano,aname,type,sex,age,image)
 as select id,ano,aname,type,sex,age,image from staff where sid=:20'
    using shid;
  else
    shid := :old.id;
    infor := 'delete a shelter named '+shid;
    execute immediate 'drop view V_'||shid||'_staff';
  end if;
end shelter_animal;

/*
每当一个新的收容所被登记在系统时，自动生成该收容所的工作人员信息视图。当这个收容所倒闭时，删除该触发器生成的相应视图。
*/

create or replace trigger shelter_staff
after insert or delete on
shelter for each row
declare
shid varchar2(20);
infor varchar2(50);
begin
  if inserting then
    shid := :new.id;
    infor := 'create a shelter named '+shid;
    execute immediate 'create or raplace view V_'||shid||'_animal(id,aname,email,phone)
                        as select id,name,emial,phone from staff where sid=:20'
    using shid;
  else
    shid := :old.id;
    infor := 'delete a shelter named '+shid;
    execute immediate 'drop view V_'||shid||'_staff';
  end if;
end shelter_staff;
