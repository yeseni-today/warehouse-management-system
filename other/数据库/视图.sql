create view vcyk_iteminbill
as select Item.Item_code,Item.Item_name,Item_in_storage.item_batch,Item_in_operation.storage_time,Item_in_storage.bill_code
from Item,Item_in_storage,Item_in_operation 
where Item.Item_code=Item_in_storage.Item_code and Item_in_storage.storage_ID=Item_in_operation.storage_ID

create view vcyk_itemindate
as select Item.Item_code,Item.item_name,Item_in_storage.item_batch,Item_in_storage.item_indate,item_slot,allow_count
from Item,Item_in_storage
where Item.Item_code=Item_in_storage.Item_code 


create view vcyk_tidtogroup
as select GyxTeacherGroup.TeacherGroupNumber,TeacherGroupName
from GyxTeacherGroup

