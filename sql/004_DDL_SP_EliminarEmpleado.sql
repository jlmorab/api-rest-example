CREATE OR REPLACE FUNCTION deleteEmployeeById(emp_id INT)
RETURNS BOOLEAN
LANGUAGE plpgsql
AS $$
DECLARE
    record_count INT;
BEGIN
    DELETE FROM empleado WHERE empleado_id = emp_id;
    
    GET DIAGNOSTICS record_count = ROW_COUNT;
    
    IF record_count > 0 THEN
        RETURN TRUE;
    ELSE
        RETURN FALSE;
    END IF;
END;
$$;