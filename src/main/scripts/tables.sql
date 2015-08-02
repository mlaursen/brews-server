CREATE TABLE brew
( id SERIAL
, name TEXT
, original_gravity DECIMAL
, final_gravity DECIMAL
, date_brewed DATE
, primary_fermentation_end DATE
, secondary_fermentation_end DATE
, date_drinkable DATE
, number_of_bottles INTEGER
, CONSTRAINT pk_Brew_Id PRIMARY KEY(id)
);
