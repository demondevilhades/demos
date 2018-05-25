
ALTER TABLE table_name ADD COLUMN createtime TIMESTAMP;
ALTER TABLE table_name ADD COLUMN updatetime TIMESTAMP;

CREATE TRIGGER trigger_name AFTER INSERT ON table_name FOR EACH ROW NEW.createtime = NOW();
CREATE TRIGGER trigger_name AFTER UPDATE ON table_name FOR EACH ROW NEW.updatetime = NOW();
