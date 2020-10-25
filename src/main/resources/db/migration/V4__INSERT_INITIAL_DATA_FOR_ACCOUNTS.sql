insert into accounts (account_number, account_name,account_type,balance_date,currency,opening_avail_balance) values
('585309209','SGSavings726', 'Savings','2018-11-08', 'SGD', 84327.51),
('791066619','AUSavings933', 'Savings','2018-11-08', 'AUD', 98055.53),
('321143048','AUCurrent433', 'Current','2018-11-08', 'AUD', 38001.62),
('347786244','SGCurrent166', 'Current','2018-11-08', 'SGD', 50664.65),
('680168913','AUCurrent374', 'Current','2018-11-08', 'AUD', 48574.43),
('136056165','AUSavings938', 'Savings','2018-11-08', 'AUD', 34298.76),
('453963528','SGSavings842', 'Savings','2018-11-08', 'SGD', 33884.32),
('111111111','SGSavings833', 'Savings','2018-11-08', 'SGD', 37654.32);

insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 954.98, 'Credit', id from accounts where accounts.account_number='585309209';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 745.98, 'Credit', id from accounts where accounts.account_number='585309209';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 342.22, 'Credit', id from accounts where accounts.account_number='585309209';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 34.11, 'Credit', id from accounts where accounts.account_number='585309209';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 665.44, 'Credit', id from accounts where accounts.account_number='585309209';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 88.98, 'Credit', id from accounts where accounts.account_number='585309209';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 22.12, 'Credit', id from accounts where accounts.account_number='585309209';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 234.43, 'Credit', id from accounts where accounts.account_number='585309209';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 3456.23, 'Credit', id from accounts where accounts.account_number='585309209';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 65.33, 'Credit', id from accounts where accounts.account_number='585309209';

insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 533.2, 'Credit', id from accounts where accounts.account_number='791066619';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 2343.22, 'Credit', id from accounts where accounts.account_number='791066619';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 5456.88, 'Credit', id from accounts where accounts.account_number='791066619';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 211.11, 'Credit', id from accounts where accounts.account_number='791066619';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 432.66, 'Credit', id from accounts where accounts.account_number='791066619';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 876.12, 'Credit', id from accounts where accounts.account_number='791066619';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 1223.65, 'Credit', id from accounts where accounts.account_number='791066619';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 34.76, 'Credit', id from accounts where accounts.account_number='791066619';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 765.33, 'Credit', id from accounts where accounts.account_number='791066619';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 23.76, 'Credit', id from accounts where accounts.account_number='791066619';

insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 232.2, 'Credit', id from accounts where accounts.account_number='321143048';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 222.22, 'Credit', id from accounts where accounts.account_number='321143048';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 333.88, 'Credit', id from accounts where accounts.account_number='321143048';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 444.11, 'Credit', id from accounts where accounts.account_number='321143048';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 555.66, 'Credit', id from accounts where accounts.account_number='321143048';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 666.12, 'Credit', id from accounts where accounts.account_number='321143048';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 777.65, 'Credit', id from accounts where accounts.account_number='321143048';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 123.76, 'Credit', id from accounts where accounts.account_number='321143048';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 7655.33, 'Credit', id from accounts where accounts.account_number='321143048';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 345.76, 'Credit', id from accounts where accounts.account_number='321143048';

insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 999.21, 'Credit', id from accounts where accounts.account_number='347786244';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 888.22, 'Credit', id from accounts where accounts.account_number='347786244';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 777.88, 'Credit', id from accounts where accounts.account_number='347786244';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 666.11, 'Credit', id from accounts where accounts.account_number='347786244';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 566.66, 'Credit', id from accounts where accounts.account_number='347786244';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 555.12, 'Credit', id from accounts where accounts.account_number='347786244';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 444.65, 'Credit', id from accounts where accounts.account_number='347786244';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 333.76, 'Credit', id from accounts where accounts.account_number='347786244';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 222.33, 'Credit', id from accounts where accounts.account_number='347786244';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 111.76, 'Credit', id from accounts where accounts.account_number='347786244';


insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 111.21, 'Credit', id from accounts where accounts.account_number='680168913';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 222.22, 'Credit', id from accounts where accounts.account_number='680168913';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 333.88, 'Credit', id from accounts where accounts.account_number='680168913';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 444.11, 'Credit', id from accounts where accounts.account_number='680168913';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 566.66, 'Credit', id from accounts where accounts.account_number='680168913';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 666.12, 'Credit', id from accounts where accounts.account_number='680168913';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 6777.65, 'Credit', id from accounts where accounts.account_number='680168913';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 8888.76, 'Credit', id from accounts where accounts.account_number='680168913';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 7765.33, 'Credit', id from accounts where accounts.account_number='680168913';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 889.76, 'Credit', id from accounts where accounts.account_number='680168913';


insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 999.21, 'Credit', id from accounts where accounts.account_number='136056165';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 2344.22, 'Credit', id from accounts where accounts.account_number='136056165';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 5433.88, 'Credit', id from accounts where accounts.account_number='136056165';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 234.11, 'Credit', id from accounts where accounts.account_number='136056165';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 765.66, 'Credit', id from accounts where accounts.account_number='136056165';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 433.12, 'Credit', id from accounts where accounts.account_number='136056165';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 9876.65, 'Credit', id from accounts where accounts.account_number='136056165';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 233.76, 'Credit', id from accounts where accounts.account_number='136056165';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 222.33, 'Credit', id from accounts where accounts.account_number='136056165';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 2345.76, 'Credit', id from accounts where accounts.account_number='136056165';


insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 6533.21, 'Credit', id from accounts where accounts.account_number='453963528';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 888.22, 'Credit', id from accounts where accounts.account_number='453963528';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 777.88, 'Credit', id from accounts where accounts.account_number='453963528';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 12343.11, 'Credit', id from accounts where accounts.account_number='453963528';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 566.66, 'Credit', id from accounts where accounts.account_number='453963528';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 555.12, 'Credit', id from accounts where accounts.account_number='453963528';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 43353.65, 'Credit', id from accounts where accounts.account_number='453963528';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 333.76, 'Credit', id from accounts where accounts.account_number='453963528';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 22333.33, 'Credit', id from accounts where accounts.account_number='453963528';
insert into transactions (value_date, currency, debit_amount, credit_amount, debit_or_credit, account_id)
select '2012-01-12', 'SGD', 0, 888.76, 'Credit', id from accounts where accounts.account_number='453963528';