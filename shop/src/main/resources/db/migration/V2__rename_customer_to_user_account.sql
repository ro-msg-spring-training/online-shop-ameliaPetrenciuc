ALTER TABLE orders DROP CONSTRAINT orders_customer_id_fkey;

ALTER TABLE customer RENAME TO user_account;

ALTER TABLE orders RENAME COLUMN customer_id TO user_id;

ALTER TABLE user_account ADD COLUMN user_role VARCHAR(50) NOT NULL;

ALTER TABLE orders ADD CONSTRAINT orders_user_id_fkey FOREIGN KEY (user_id)
                   REFERENCES user_account(id);




