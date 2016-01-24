INSERT INTO
  province (province_code, name)
VALUES
  ("12", "นนทบุรี");

INSERT INTO
  district (district_code, name, province_code)
VALUES
  ("1202", "บางกรวย", "12"),
  ("1206", "ปากเกร็ด", "12"),
  ("1204", "บางบัวทอง", "12"),
  ("1205", "ไทรน้อย", "12"),
  ("1203", "บางใหญ่", "12"),
  ("1201", "เมืองนนทบุรี", "12");

INSERT INTO
  subdistrict (subdistrict_code, name, district_code)
VALUES
  ("120404", "บางคูรัด", "1204"),
  ("120405", "ละหาร", "1204"),
  ("120602", "บางตลาด", "1206"),
  ("120206", "บางคูเวียง", "1202"),
  ("120104", "บางกระสอ", "1201"),
  ("120601", "ปากเกร็ด", "1206"),
  ("120209", "ศาลากลาง", "1202"),
  ("120202", "บางกรวย", "1202"),
  ("120401", "โสนลอย", "1204"),
  ("120303", "บางเลน", "1203"),
  ("120507", "ทวีวัฒนา", "1205"),
  ("120106", "บางไผ่", "1201"),
  ("120305", "บางใหญ่", "1203"),
  ("120109", "ไทรม้า", "1201"),
  ("120611", "บางพลับ", "1206"),
  ("120201", "วัดชลอ", "1202"),
  ("120501", "ไทรน้อย", "1205"),
  ("120304", "เสาธงหิน", "1203"),
  ("120107", "บางศรีเมือง", "1201"),
  ("120503", "หนองเพรางาย", "1205"),
  ("120402", "บางบัวทอง", "1204"),
  ("120105", "ท่าทราย", "1201"),
  ("120604", "บางพูด", "1206"),
  ("120203", "บางสีทอง", "1202"),
  ("120103", "บางเขน", "1201"),
  ("120608", "เกาะเกร็ด", "1206"),
  ("120603", "บ้านใหม่", "1206"),
  ("120306", "บ้านใหม่", "1203"),
  ("120606", "คลองพระอุดม", "1206"),
  ("120504", "ไทรใหญ่", "1205"),
  ("120610", "คลองข่อย", "1206"),
  ("120101", "สวนใหญ่", "1201"),
  ("120102", "ตลาดขวัญ", "1201"),
  ("120612", "คลองเกลือ", "1206"),
  ("120301", "บางม่วง", "1203"),
  ("120207", "มหาสวัสดิ์", "1202"),
  ("120407", "พิมลราช", "1204"),
  ("120208", "ปลายบาง", "1202"),
  ("120403", "บางรักใหญ่", "1204"),
  ("120505", "ขุนศรี", "1205"),
  ("120607", "ท่าอิฐ", "1206"),
  ("120108", "บางกร่าง", "1201"),
  ("120406", "ลำโพ", "1204"),
  ("120502", "ราษฎร์นิยม", "1205"),
  ("120110", "บางรักน้อย", "1201"),
  ("120609", "อ้อมเกร็ด", "1206"),
  ("120506", "คลองขวาง", "1205"),
  ("120302", "บางแม่นาง", "1203"),
  ("120605", "บางตะไนย์", "1206"),
  ("120408", "บางรักพัฒนา", "1204"),
  ("120204", "บางขนุน", "1202"),
  ("120205", "บางขุนกอง", "1202");

INSERT INTO
  organization (org_id, name, subdistrict_code, health_region_code)
VALUES
  ("100", "กรมควบคุมโรค", "120105", "1");

INSERT INTO
  user_profile (username, org_id)
VALUES
  ("dpc-user", "100");

INSERT INTO
  container_type (container_type_id, name)
VALUES
  (1, "น้ำใช้"),
  (2, "น้ำดื่ม"),
  (3, "แจกัน"),
  (4, "ที่รองกันมด"),
  (5, "จานรองกระถาง"),
  (6, "อ่างบัว/ไม้น้ำ"),
  (7, "ยางรถยนต์เก่า"),
  (8, "กากใบพืช"),
  (9, "ภาชนะที่ไม่ใช้"),
  (10, "อื่นๆ (ที่ใช้ประโยชน์)");

INSERT INTO
  container_location (container_location_id, name)
VALUES
  (1, "ภายในอาคาร"),
  (2, "ภายนอกอาคาร");

INSERT INTO
  place_type (type_id, name)
VALUES
  ("1", "หมู่บ้าน/ชุมชน"),
  ("2", "ศาสนสถาน"),
  ("3", "สถานศึกษา"),
  ("4", "โรงพยาบาล"),
  ("5", "โรงงาน"),
  ("6", "ที่พักชั่วคราว");

INSERT INTO
  place_subtype (subtype_id, name, type_id)
VALUES
  ("1", "สำนักงานสาธารณสุขจังหวัด", "4"),
  ("2", "สำนักงานสาธารณสุขอำเภอ", "4"),
  ("3", "รพ.สต./สาธารณสุขชุมชน", "4"),
  ("4", "โรงพยาบาลศูนย์/โรงพยาบาลทั่วไป", "4"),
  ("5", "โรงพยาบาลชุมชน", "4"),
  ("6", "โรงพยาบาลอื่นสังกัด สธ./โรงพยาบาลสังกัดกระทรวงอื่น", "4"),
  ("7", "โรงพยาบาลเอกชน", "4"),
  ("8", "ศูนย์สุขภาพชุมชน/บริการสาธารณสุข", "4"),
  ("9", "ศูนย์วิชาการ", "4"),
  ("10", "ชุมชนแออัด", "1"),
  ("11", "ชุมชนพักอาศัย", "1"),
  ("12", "ชุมชนพาณิชย์", "1"),
  ("13", "วัด", "2"),
  ("14", "โบสถ์", "2"),
  ("15", "มัสยิด/สุเหร่า", "2"),
  ("16", "โรงเรียน", "3"),
  ("17", "โรงงาน", "5");

INSERT INTO
  place (place_id, subtype_id, name, subdistrict_code, update_by, update_time)
VALUES
  ("abc01db8-7207-8a65-152f-ad208cb99b5e", "10", "หมู่บ้านทดสอบ", "120202", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),
  ("935b9aeb-6522-461e-994f-f9e9006c4a33", "11", "หมู่บ้านพาลาซเซตโต้", "120202", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),
  ("e5ce769e-f397-4409-bec2-818f7bd02464", "11", "ชุมชนกอล์ฟวิว", "120202", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),
  ("e1088db2-1670-4a0b-a907-af12df6bb258", "7", "โรงพยาบาลกรุงเทพ", "120202", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),
  ("37ebde70-aa49-4c06-b102-53a022d46370", "4", "ธรรมศาสตร์", "120202", "dpc-user", "2015-12-24T12:05:19.626+07:00"),
  ("9b27df17-4234-4b9b-b444-0dc3d637d1fe", "13", "วัดป่าภูก้อน", "120202", "dpc-user", "2015-12-24T12:05:19.626+07:00"),
  ("febb0058-3007-41ae-91d8-de2c3160c935", "14", "โบสถ์เซนต์เมรี่", "120202", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),
  ("9a2840cc-e28c-4939-8c69-67620ccd3946", "16", "โรงเรียนเซนต์เมรี่", "120202", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),
  ("343962a4-b488-4032-8aa9-549051e31a00", "16", "โรงเรียนดอนบอสโก", "120202", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),
  ("a173b278-cfa7-4e6d-9871-a582764df342", "16", "โรงเรียนอนุบาล", "120202", "dpc-user",
   "2015-12-24T12:05:19.626+07:00");

INSERT INTO
  building (building_id, place_id, name, update_by, update_time)
VALUES
  ("00001db8-7207-8a65-152f-ad208cb99b01", "abc01db8-7207-8a65-152f-ad208cb99b5e", "23/2", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),
  ("f5bfd399-8fb2-4a69-874a-b40495f7786f", "935b9aeb-6522-461e-994f-f9e9006c4a33", "214/43", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),
  ("eb7b15f7-31bb-4abd-875f-a43c20025134", "935b9aeb-6522-461e-994f-f9e9006c4a33", "214/44", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),
  ("edce1e8e-b4b6-480d-8223-ddb1e839f556", "935b9aeb-6522-461e-994f-f9e9006c4a33", "214/45", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),
  ("f373e434-48cd-484a-92a6-0e3ff8aa4810", "935b9aeb-6522-461e-994f-f9e9006c4a33", "214/46", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),
  ("520f2998-53e9-430d-b3aa-38d153eda783", "935b9aeb-6522-461e-994f-f9e9006c4a33", "214/47", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),
  ("73eaead6-b62d-4b36-b80c-aebaff0a0340", "935b9aeb-6522-461e-994f-f9e9006c4a33", "214/48", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),
  ("442145b0-0aa6-493d-bc50-ea95c8335c22", "935b9aeb-6522-461e-994f-f9e9006c4a33", "214/49", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),
  ("1e08a727-04d7-40bc-a2b6-e630be312744", "935b9aeb-6522-461e-994f-f9e9006c4a33", "214/50", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),
  ("f79d6788-4a44-4837-a37a-2ee068991b31", "935b9aeb-6522-461e-994f-f9e9006c4a33", "214/51", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),
  ("24d67216-42ad-45f1-ad94-00d318b73671", "935b9aeb-6522-461e-994f-f9e9006c4a33", "214/52", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),
  ("17defaa3-17de-4235-9a81-cdc91345642d", "935b9aeb-6522-461e-994f-f9e9006c4a33", "214/53", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),
  ("cde91b3f-67b5-4ffd-8d0c-695e2890b2a2", "935b9aeb-6522-461e-994f-f9e9006c4a33", "214/54", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),
  ("4d843012-9696-4824-b52e-87398a589f40", "935b9aeb-6522-461e-994f-f9e9006c4a33", "214/55", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),

  ("bc4d3340-6480-4348-af7c-642d0849d322", "e1088db2-1670-4a0b-a907-af12df6bb258", "ตึก1", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),
  ("7e6de397-33cb-4958-9192-ed9d762f6337", "e1088db2-1670-4a0b-a907-af12df6bb258", "ตึกพักญาติ", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),
  ("2048d7af-aa53-42d7-86dc-03ea3f961a57", "e1088db2-1670-4a0b-a907-af12df6bb258", "โรงอาหาร", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),

  ("5a57720e-11b3-4d4f-9f80-a3ca80dc8563", "9b27df17-4234-4b9b-b444-0dc3d637d1fe", "ศาลาใหญ่", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),
  ("5174ed8d-9d51-4994-86fe-ea1e8636998b", "9b27df17-4234-4b9b-b444-0dc3d637d1fe", "เมรุ", "dpc-user",
   "2015-12-24T12:05:19.626+07:00"),
  ("6a2fc9f1-3489-4a73-a425-b3d4e5b0e34b", "9b27df17-4234-4b9b-b444-0dc3d637d1fe", "ลานหน้าศาลากลาง", "dpc-user",
   "2015-12-24T12:05:19.626+07:00");