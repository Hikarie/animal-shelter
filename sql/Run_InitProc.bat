ECHO OFF
ECHO 正在执行SQL脚本(创建视图,函数,存储过程)......
sqlplus hikari/hikari@hikarinew @.\Batch\Batch_InitPro.sql  >>.\Result.log
EXIT


