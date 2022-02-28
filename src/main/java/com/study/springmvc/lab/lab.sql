-- 基金
create table if not exists fund (
	fid integer not null auto_increment,
	fname varchar(50),
	createtime datetime default current_timestamp,
	primary key(fid)
);
-- 股票
create table if not exists fundstock (
	sid integer not null auto_increment,
	fid integer,
	symbol varchar(50),
	-- cost integer not null, --買進成本(上課時間不夠，暫時先不加)
	share integer not null,
	foreign key(fid) references fund(fid), -- 外鍵關聯
	primary key(sid)
);

-- 基金資料
insert into fund (fname) values ('A');
insert into fund (fname) values ('B');
insert into fund (fname) values ('C');
insert into fund (fname) values ('D');
insert into fund (fname) values ('E');
insert into fund (fname) values ('F');
insert into fund (fname) values ('G');
-- 股票資料
insert into fundstock (fid, symbol, share) values (1, "2330.TW", 50000);
insert into fundstock (fid, symbol, share) values (2, "2330.TW", 60000);
insert into fundstock (fid, symbol, share) values (2, "2303.TW", 70000);
insert into fundstock (fid, symbol, share) values (3, "2376.TW", 80000);
insert into fundstock (fid, symbol, share) values (3, "1101.TW", 90000);
insert into fundstock (fid, symbol, share) values (3, "2317.TW", 80000);
insert into fundstock (fid, symbol, share) values (4, "2331.TW", 70000);
insert into fundstock (fid, symbol, share) values (4, "2356.TW", 60000);
insert into fundstock (fid, symbol, share) values (4, "2880.TW", 50000);
insert into fundstock (fid, symbol, share) values (4, "2891.TW", 40000);
insert into fundstock (fid, symbol, share) values (5, "2330.TW", 30000);
insert into fundstock (fid, symbol, share) values (5, "1101.TW", 20000);
insert into fundstock (fid, symbol, share) values (5, "2317.TW", 10000);
insert into fundstock (fid, symbol, share) values (5, "2886.TW", 20000);
insert into fundstock (fid, symbol, share) values (5, "2002.TW", 30000);
insert into fundstock (fid, symbol, share) values (6, "1201.TW", 30000);

insert into fundstock (symbol) values ("2480.TW"); -- 資訊服務
insert into fundstock (symbol) values ("2201.TW"); -- 食品 統一
insert into fundstock (symbol) values ("2216.TW"); -- 食品 味全

-- 向左關聯 (所有基金，包含尚未配置股票的基金)
select f.fid , f.fname , f.createtime , 
	   s.sid , s.fid , s.symbol , s.share 
from fund f left join fundstock s 
on f.fid = s.fid;
-- 交集 (只查詢，有配置股票的基金)
select f.fid , f.fname , f.createtime , 
	   s.sid , s.fid , s.symbol , s.share 
from fund f join join fundstock s 
on f.fid = s.fid;

-- 向左關聯差集 (查詢尚未配置股票的基金)
select f.fid , f.fname , f.createtime , 
	   s.sid , s.fid , s.symbol , s.share 
from fund f left join fundstock s 
on f.fid = s.fid 
where f.fid is null;

--  向右關聯差集 (查詢尚未配置基金的股票)
select f.fid , f.fname , f.createtime , 
	   s.sid , s.fid , s.symbol , s.share 
from fund f right join fundstock s 
on f.fid = s.fid 
where s.fid is null;






