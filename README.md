# Blog-Backend
Blog Backend (Spring Boot)

A complete REST API backend for a Blog platform.
Supports user authentication (JWT), CRUD for Posts, Categories, Comments and Users.
Blog post images are stored locally in uploads/.
Tech Stack
Component	Technology
Language	Java
Framework	Spring Boot
Security	Spring Security + JWT
ORM	Spring Data JPA (Hibernate)
Database	MySQL (default)
Build	Maven
Validation	Bean Validation
File Upload	Multipart + local storage (/uploads)

**Project Structure**

Blog-Backend/
 ├─ src/
 │   ├─ main/
 │   │   ├─ java/
 │   │   │   └─ TheChhetriGroup/
 │   │   │        └─ Blog/
 │   │   │             ├─ controller/
 │   │   │             ├─ service/
 │   │   │             ├─ repository/
 │   │   │             ├─ entity/
 │   │   │             ├─ payload/   (DTOs)
 │   │   │             ├─ config/    (Security, JWT)
 │   │   │             └─ BlogApplication.java/
 │   │   └─ resources/
 │   │        └─ application.yml
 ├─ uploads/           # images stored here
 ├─ pom.xml
 ├─ mvnw / mvnw.cmd
 └─ README.md

 
**Features**
Register / login (JWT token)
Create / update / delete blog posts
Add comments to posts
Create / update / delete categories
Pagination on post listing
Search posts
Handle image uploads for posts
Layered architecture (Controller → Service → Repo)


**Setup**
clone
git clone https://github.com/Abi-chhetri/Blog-Backend.git
cd Blog-Backend

Uploads
Uploaded image files for posts are stored under:
/uploads

License
Free to use.
