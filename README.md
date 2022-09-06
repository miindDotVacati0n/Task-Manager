*นาย เทพทัต ตั้งสัจจะกุล 6210451195

*Project schedule

*สิ้นเดือน กุมภาพันธ์

*    ทำหน้า view ของ general กับ งานด้านในของ GeneralWorksController คราวๆ

*สัปดาห์ที่ 1 ของเดือน มีนาคม

*    ทำหน้า view ของ general กับ project, กับเพิ่มงานข้างใน GeneralWorksController

*สัปดาห์ที่ 2 ของเดือน มีนาคม

*    ทำหน้า GeneralWorkFileDataSource , ProjectWorkFileDataSource 
     
*    ทำหน้า GeneralWorksController , ProjectController
     
*    ทำโมเดล WeeklyWorks

*สัปดาห์ที่ 3 ของเดือน มีนาคม

*    ตั้งเงื่อนไขหน้า GeneralWorksController (ครบแล้ว)
     
*    ทำโมเดลหน้า ForwardWorks พร้อมกับโยงหน้าทิ้งไว้
     
*    ทำหน้า WeeklyWorksController ไว้คราวๆ

*สัปดาห์ที่ 4 ของเดือน มีนาคม

*    ทำโมเดล category
     
*    ทำ controller AllWorkTableController, CategoryController, Manual
     
*    AllWorkTableController ทำเงือนไขทั้งหมดในการโชว์ตารางของงานทุกประเภท
     
*    CategoryController ทำตารางแสดงหมวดหมู่ที่ได้ทำการ submit เข้าไป และเงื่อนไขดัก
     
*    Manual ทำหน้าโยงแบบกดปุ่มแล้วโยงเข้า pdf ได้เลย
     
*    ทำ service PDFFileDataSource, CategoryFileDataSource เพื่อมาทำการอ่านไฟล์
     
*    ทำการดักเงื่อนไขทุก model และทุก controller


---------------------------------------------------------------------------------------------

*การวางโครงสร้างไฟล์

*    directory data
     
*            - เก็บไฟล์ category.csv เก็บข้อมูลของหมวดหมู่
  
*            - เก็บไฟล์ forwardWorks.csv เก็บข้อมูลของงานประเภทส่งต่อ
  
*            - เก็บไฟล์ generalWorks.csv เก็บข้อมูลของงานประเภททั่วไป
  
*            - เก็บไฟล์ projectWorks.csv เก็บข้อมูลของงานประเภทโปรเจค
  
*            - เก็บไฟล์ weeklyWorks.csv เก็บข้อมูลของงานประเภทรายสัปดาห์
  
*    directory pdfFile
     
*            - เก็บ pdf file
  
*    directory src
     
*                main (แบ่งเป็น 2 ส่วน)
  
*                    java (แบ่งเป็น 4 ส่วน)
  
*                        controller.home
  
*                            - AllWorkTableController
  
*                            - CategoryController
  
*                            - ForwardWorksController  
  
*                            - GeneralWorksController
  
*                            - HomeController
  
*                            - HomePageController
  
*                            - ManualController
  
*                            - ProfileController
  
*                            - ProjectController
  
*                            - WeeklyWorksController
  
*                        model.works
  
*                            - Category
  
*                            - ForwardWorks
  
*                            - GeneralWorks
  
*                            - Project
  
*                            - WeeklyWorks
  
*                        services
  
*                            - CategoryFileDataSource
  
*                            - DataList
  
*                            - DataSource
  
*                            - ForwardWorksFileDataSource
  
*                            - GeneralWorkFileDataSource
  
*                            - PDFFileDataSource
  
*                            - ProjectWorkDataSource
  
*                            - StringConfiguration
  
*                            - WeeklyWorksFileDataSource
  
*                        UDairy
  
*                            - Main
  
*                    resource (แบ่งเป็น 2 ส่วน)
  
*                         homepage  
  
*                            workingSpace
  
*                                - homepage.fxml
  
*                                - manual.fxml
  
*                         Image
  
*                            - รูปภาพ
  
*                         - creator.fxml
  
*                         - home.fxml

--------------------------------------------------------------------------------

*วิธีการติดตั้งโปรแกรม

*ตัวโปรแกรม .jar จะอยู่ใน directory UDairy Application

*กรณีที่ไม่สามารถเปิดโปรแกรมได้จากการ double click ที่ jav file ให้เลือกเปิด terminal หรือ bash หรือ command prompt แล้วใช้คำสั่ง java -jar 6210451195.jar
