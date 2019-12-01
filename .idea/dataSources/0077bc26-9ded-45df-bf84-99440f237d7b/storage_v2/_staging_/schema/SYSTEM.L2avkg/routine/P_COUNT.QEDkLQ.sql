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
/

