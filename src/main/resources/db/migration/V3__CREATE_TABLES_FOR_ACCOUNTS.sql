
create table accounts (
    id serial primary key,
    account_number varchar(20),
    account_name varchar(20),
    account_type varchar(10),
    balance_date date,
    currency char(3),
    opening_avail_balance numeric(15,2)
);

create table transactions (
    id serial primary key,
    value_date date,
    currency char(3),
    debit_amount numeric(15,2),
    credit_amount numeric(15,2),
    debit_or_credit char(7),
    transaction_narrative varchar(20),
    account_id int references accounts (id)
);