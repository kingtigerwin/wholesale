-- char(7) pads 'Credit' to 'Credit ', which leaked the trailing space into the API response.
alter table transactions alter column debit_or_credit set data type varchar(7);
update transactions set debit_or_credit = trim(debit_or_credit);
