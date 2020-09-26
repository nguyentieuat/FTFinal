Chạy project:
1. Sửa file application.properties
 thay thế spring.jpa.hibernate.ddl-auto=update bằng spring.jpa.hibernate.ddl-auto=create
2. Chạy project
3. Sửa lại spring.jpa.hibernate.ddl-auto=create bằng spring.jpa.hibernate.ddl-auto=update
4. Run câu lệnh sql thêm roleL: INSERT INTO `role_access` VALUES (1,'owner',NULL),(2,'share',1),(3,'read',2);
5. Register theo link loaclhost:8080/register để đăng kí tài khoản
6. Register theo link loaclhost:8080/login để đăng kí tài khoản
