echo off 
title 创建表空间和用户 
sqlplus system/system@OraTest @.\spaceanduser\spaceuser.sql >>.\Result.log 
exit 
