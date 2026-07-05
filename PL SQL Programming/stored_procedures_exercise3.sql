-- ============================================================
-- Exercise 3: Stored Procedures
-- ============================================================
-- Assumed table structures (adjust column/table names to match
-- your actual schema):
--
-- ACCOUNTS (
--     account_id      NUMBER PRIMARY KEY,
--     account_type    VARCHAR2(20),   -- e.g. 'SAVINGS', 'CURRENT'
--     balance         NUMBER
-- );
--
-- EMPLOYEES (
--     employee_id     NUMBER PRIMARY KEY,
--     department_id   NUMBER,
--     salary          NUMBER
-- );
-- ============================================================

SET SERVEROUTPUT ON;


-- ============================================================
-- Scenario 1:
-- ProcessMonthlyInterest — applies 1% interest to the balance
-- of all savings accounts.
-- ============================================================

CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest
IS
    CURSOR savings_cursor IS
        SELECT account_id, balance
        FROM accounts
        WHERE account_type = 'SAVINGS';

    v_account_id accounts.account_id%TYPE;
    v_balance    accounts.balance%TYPE;
    v_interest   accounts.balance%TYPE;

BEGIN
    FOR acc_rec IN savings_cursor LOOP
        v_account_id := acc_rec.account_id;
        v_balance    := acc_rec.balance;
        v_interest   := v_balance * 0.01;

        UPDATE accounts
        SET balance = balance + v_interest
        WHERE account_id = v_account_id;

        DBMS_OUTPUT.PUT_LINE(
            'Interest applied to Account ID: ' || v_account_id ||
            ' | Interest: ' || v_interest ||
            ' | New Balance: ' || (v_balance + v_interest)
        );
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('ProcessMonthlyInterest completed successfully.');

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error in ProcessMonthlyInterest: ' || SQLERRM);
        RAISE;
END ProcessMonthlyInterest;
/

-- Sample invocation:
-- BEGIN
--     ProcessMonthlyInterest;
-- END;
-- /


-- ============================================================
-- Scenario 2:
-- UpdateEmployeeBonus — adds a bonus percentage to the salary
-- of all employees in a given department.
-- ============================================================

CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus (
    p_department_id   IN NUMBER,
    p_bonus_percent   IN NUMBER
)
IS
    CURSOR emp_cursor IS
        SELECT employee_id, salary
        FROM employees
        WHERE department_id = p_department_id;

    v_employee_id employees.employee_id%TYPE;
    v_salary      employees.salary%TYPE;
    v_bonus       employees.salary%TYPE;

BEGIN
    IF p_bonus_percent IS NULL OR p_bonus_percent < 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Bonus percentage must be a non-negative number.');
    END IF;

    FOR emp_rec IN emp_cursor LOOP
        v_employee_id := emp_rec.employee_id;
        v_salary      := emp_rec.salary;
        v_bonus       := v_salary * (p_bonus_percent / 100);

        UPDATE employees
        SET salary = salary + v_bonus
        WHERE employee_id = v_employee_id;

        DBMS_OUTPUT.PUT_LINE(
            'Bonus applied to Employee ID: ' || v_employee_id ||
            ' | Bonus: ' || v_bonus ||
            ' | New Salary: ' || (v_salary + v_bonus)
        );
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('UpdateEmployeeBonus completed successfully for Department ID: ' || p_department_id);

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error in UpdateEmployeeBonus: ' || SQLERRM);
        RAISE;
END UpdateEmployeeBonus;
/

-- Sample invocation (10% bonus for Department 100):
-- BEGIN
--     UpdateEmployeeBonus(100, 10);
-- END;
-- /


-- ============================================================
-- Scenario 3:
-- TransferFunds — transfers a specified amount from one
-- account to another, checking sufficient balance first.
-- ============================================================

CREATE OR REPLACE PROCEDURE TransferFunds (
    p_from_account_id   IN NUMBER,
    p_to_account_id     IN NUMBER,
    p_transfer_amount   IN NUMBER
)
IS
    v_from_balance   accounts.balance%TYPE;
    v_to_exists      NUMBER;

    insufficient_funds EXCEPTION;
    invalid_account    EXCEPTION;

BEGIN
    IF p_transfer_amount IS NULL OR p_transfer_amount <= 0 THEN
        RAISE_APPLICATION_ERROR(-20002, 'Transfer amount must be greater than zero.');
    END IF;

    IF p_from_account_id = p_to_account_id THEN
        RAISE_APPLICATION_ERROR(-20003, 'Source and destination accounts must be different.');
    END IF;

    -- Verify destination account exists
    SELECT COUNT(*) INTO v_to_exists
    FROM accounts
    WHERE account_id = p_to_account_id;

    IF v_to_exists = 0 THEN
        RAISE invalid_account;
    END IF;

    -- Lock and fetch the source account balance
    SELECT balance INTO v_from_balance
    FROM accounts
    WHERE account_id = p_from_account_id
    FOR UPDATE;

    IF v_from_balance < p_transfer_amount THEN
        RAISE insufficient_funds;
    END IF;

    -- Debit source account
    UPDATE accounts
    SET balance = balance - p_transfer_amount
    WHERE account_id = p_from_account_id;

    -- Credit destination account
    UPDATE accounts
    SET balance = balance + p_transfer_amount
    WHERE account_id = p_to_account_id;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE(
        'Transferred ' || p_transfer_amount ||
        ' from Account ' || p_from_account_id ||
        ' to Account ' || p_to_account_id || ' successfully.'
    );

EXCEPTION
    WHEN insufficient_funds THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE(
            'Transfer failed: Insufficient balance in Account ' || p_from_account_id
        );
        RAISE_APPLICATION_ERROR(-20004, 'Insufficient balance in source account.');

    WHEN invalid_account THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE(
            'Transfer failed: Destination Account ' || p_to_account_id || ' does not exist.'
        );
        RAISE_APPLICATION_ERROR(-20005, 'Destination account does not exist.');

    WHEN NO_DATA_FOUND THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE(
            'Transfer failed: Source Account ' || p_from_account_id || ' does not exist.'
        );
        RAISE_APPLICATION_ERROR(-20006, 'Source account does not exist.');

    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error in TransferFunds: ' || SQLERRM);
        RAISE;
END TransferFunds;
/

-- Sample invocation (transfer 500 from Account 101 to Account 102):
-- BEGIN
--     TransferFunds(101, 102, 500);
-- END;
-- /
