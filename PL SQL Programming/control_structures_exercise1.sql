-- ============================================================
-- Exercise 1: Control Structures
-- PL/SQL blocks for bank customer/loan scenarios
-- ============================================================
-- Assumed table structures (adjust column/table names to match
-- your actual schema):
--
-- CUSTOMERS (
--     customer_id   NUMBER PRIMARY KEY,
--     name          VARCHAR2(100),
--     age           NUMBER,
--     balance       NUMBER,
--     is_vip        VARCHAR2(1)   -- 'Y' / 'N' (or use a NUMBER/BOOLEAN as per your schema)
-- );
--
-- LOANS (
--     loan_id         NUMBER PRIMARY KEY,
--     customer_id     NUMBER,
--     interest_rate   NUMBER,
--     due_date        DATE
-- );
-- ============================================================


-- ============================================================
-- Scenario 1:
-- Apply a 1% discount to loan interest rates for customers
-- above 60 years old.
-- ============================================================

SET SERVEROUTPUT ON;

DECLARE
    CURSOR cust_cursor IS
        SELECT customer_id, age
        FROM customers;

    v_customer_id customers.customer_id%TYPE;
    v_age         customers.age%TYPE;

BEGIN
    FOR cust_rec IN cust_cursor LOOP
        v_customer_id := cust_rec.customer_id;
        v_age         := cust_rec.age;

        IF v_age > 60 THEN
            UPDATE loans
            SET interest_rate = interest_rate - (interest_rate * 0.01)
            WHERE customer_id = v_customer_id;

            DBMS_OUTPUT.PUT_LINE(
                'Discount applied for Customer ID: ' || v_customer_id ||
                ' (Age: ' || v_age || ')'
            );
        END IF;
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Scenario 1 completed: Interest rate discounts applied.');
END;
/


-- ============================================================
-- Scenario 2:
-- Set IsVIP flag to TRUE ('Y') for customers with a balance
-- over $10,000.
-- ============================================================

DECLARE
    CURSOR cust_cursor IS
        SELECT customer_id, balance
        FROM customers;

    v_customer_id customers.customer_id%TYPE;
    v_balance     customers.balance%TYPE;

BEGIN
    FOR cust_rec IN cust_cursor LOOP
        v_customer_id := cust_rec.customer_id;
        v_balance     := cust_rec.balance;

        IF v_balance > 10000 THEN
            UPDATE customers
            SET is_vip = 'Y'
            WHERE customer_id = v_customer_id;

            DBMS_OUTPUT.PUT_LINE(
                'Customer ID: ' || v_customer_id ||
                ' promoted to VIP (Balance: ' || v_balance || ')'
            );
        END IF;
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Scenario 2 completed: VIP status updated.');
END;
/


-- ============================================================
-- Scenario 3:
-- Fetch all loans due in the next 30 days and print a
-- reminder message for each customer.
-- ============================================================

DECLARE
    CURSOR loan_due_cursor IS
        SELECT l.loan_id, l.customer_id, l.due_date, c.name
        FROM loans l
        JOIN customers c ON c.customer_id = l.customer_id
        WHERE l.due_date BETWEEN SYSDATE AND (SYSDATE + 30);

    v_loan_id      loans.loan_id%TYPE;
    v_customer_id  loans.customer_id%TYPE;
    v_due_date     loans.due_date%TYPE;
    v_name         customers.name%TYPE;

BEGIN
    FOR loan_rec IN loan_due_cursor LOOP
        v_loan_id     := loan_rec.loan_id;
        v_customer_id := loan_rec.customer_id;
        v_due_date    := loan_rec.due_date;
        v_name        := loan_rec.name;

        DBMS_OUTPUT.PUT_LINE(
            'Reminder: Loan ID ' || v_loan_id ||
            ' for Customer ' || v_name || ' (ID: ' || v_customer_id || ')' ||
            ' is due on ' || TO_CHAR(v_due_date, 'DD-MON-YYYY')
        );
    END LOOP;

    DBMS_OUTPUT.PUT_LINE('Scenario 3 completed: Loan due reminders printed.');
END;
/
