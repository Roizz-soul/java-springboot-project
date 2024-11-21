-- Create Box table
CREATE TABLE IF NOT EXISTS box (
    txref VARCHAR(20) PRIMARY KEY,  -- Unique identifier for the box
    weight_limit INT NOT NULL,  -- Maximum weight limit
    battery_capacity INT NOT NULL,
    state ENUM('IDLE', 'LOADING', 'LOADED', 'DELIVERING', 'DELIVERED', 'RETURNING') NOT NULL  -- State of the box
);

-- Create Item table
CREATE TABLE IF NOT EXISTS item (
    name VARCHAR(255) NOT NULL,  -- Name of the item
    weight INT NOT NULL,  -- Weight of the item
    code VARCHAR(20) PRIMARY KEY  -- Unique identifier for the item
);

-- Create Box_Item table (junction table to link Box and Item)
CREATE TABLE IF NOT EXISTS box_item (
    box_txref VARCHAR(20),  -- Foreign key to Box table
    item_code VARCHAR(20),  -- Foreign key to Item table
    FOREIGN KEY (box_txref) REFERENCES box(txref),
    FOREIGN KEY (item_code) REFERENCES item(code),
    PRIMARY KEY (box_txref, item_code)  -- Composite primary key for unique pairing of box and item
);

-- Insert Boxes (IDLE, LOADING, LOADED, DELIVERING, and RETURNING states)
INSERT INTO box (txref, weight_limit, battery_capacity, state) VALUES
    ('BOX001', 500, 100, 'IDLE'),  -- Valid Box, IDLE state, enough battery
    ('BOX002', 500, 30, 'IDLE'),   -- Valid Box, IDLE state, battery low
    ('BOX003', 300, 100, 'LOADING'), -- Valid Box, LOADING state, enough battery
    ('BOX004', 500, 50, 'LOADED'),   -- Valid Box, LOADED state, battery okay
    ('BOX005', 500, 90, 'DELIVERING'), -- Valid Box, DELIVERING state, enough battery
    ('BOX006', 500, 15, 'IDLE'),   -- Valid Box, IDLE state, battery very low
    ('BOX007', 700, 100, 'IDLE');  -- Invalid Box, over weight limit

-- Insert Items (Valid and Invalid items based on constraints)
INSERT INTO item (name, weight, code) VALUES
    ('Item-1', 100, 'ITEM1'),           -- Valid item, 100g weight, valid code
    ('Item_2', 200, 'ITEM2'),          -- Valid item, 200g weight, valid code
    ('Heavy-Item_3', 400, 'ITEM3'),    -- Valid item, 400g weight, valid code
    ('Item_4', 500, 'ITEM4'),          -- Valid item, 500g weight, valid code
    ('ExtraHeavy-Item_5', 700, 'ITEM5'),-- Item that exceeds weight limit
    ('Invalid Item 6', 200, 'ITEM6'),  -- Invalid item, contains space in name
    ('Item-7', 300, 'ITEM7');          -- Valid item, 300g weight, valid code

-- Insert items into boxes (loading items into specific boxes)
-- Loading 'Item-1' and 'Item_2' into 'BOX001'
INSERT INTO box_item (box_txref, item_code) VALUES ('BOX001', 'ITEM1');
INSERT INTO box_item (box_txref, item_code) VALUES ('BOX001', 'ITEM2');

-- Loading 'Heavy-Item_3' and 'Item_4' into 'BOX002' (this box can handle it)
INSERT INTO box_item (box_txref, item_code) VALUES ('BOX002', 'ITEM3');
INSERT INTO box_item (box_txref, item_code) VALUES ('BOX002', 'ITEM4');

-- Loading 'Item-1' and 'Item_4' into 'BOX003' (ensure it's in LOADING state)
INSERT INTO box_item (box_txref, item_code) VALUES ('BOX003', 'ITEM1');
INSERT INTO box_item (box_txref, item_code) VALUES ('BOX003', 'ITEM4');

-- Loading 'Item-7' into 'BOX004' (testing loaded state)
INSERT INTO box_item (box_txref, item_code) VALUES ('BOX004', 'ITEM7');

-- Loading items into 'BOX005' while it's in the DELIVERING state
INSERT INTO box_item (box_txref, item_code) VALUES ('BOX005', 'ITEM2');
INSERT INTO box_item (box_txref, item_code) VALUES ('BOX005', 'ITEM3');

-- No loading for BOX006 as the battery is very low (battery < 25%)

-- Load items into 'BOX007' (this box exceeds weight limit)
INSERT INTO box_item (box_txref, item_code) VALUES ('BOX007', 'ITEM1');
INSERT INTO box_item (box_txref, item_code) VALUES ('BOX007', 'ITEM2');
