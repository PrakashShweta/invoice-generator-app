# 📄 Invoice Generator App

A *Spring Boot application* for generating PDF invoices.  
This project demonstrates how to create professional invoices using *Spring Boot and PDF libraries*.

---

## 🚀 Features
- Generate invoices in *PDF format*
- REST API to trigger invoice creation
- Download or view generated invoices
- Clean code structure (Controller → Service → Repository → Model)

---

## 📂 Project Structure
invoice-generator/ ├── 📂 src/main/java/com.invoice.app │   
├── InvoiceGeneratorApplication.java   # Main Spring Boot app │   
├── 📂 controller                      # REST APIs │   
├── 📂 model                           # Data models / POJOs │   
├── 📂 repository                      # Data access layer │   
└── 📂 services                        # Business logic │ 
├── 📂 src/main/resources │   ├── application.properties             # Config file │   
├── 📂 static                          # Static assets │   
└── 📂 templates                       # Templates (if used) │ 
├── 📂 target                              # Build output & generated invoices │   
├── invoice_*.pdf                      # Generated invoices │ 
├── 📄 pom.xml                             # Maven dependencies & build config └── 📄 README.md                           # Project documentation

---

## ⚡ Getting Started

### ⿡ Clone the repo
```bash
git clone https://github.com/ParakashShweta/invoice-generator-app.git
cd invoice-generator-app

⿢ Build the project

mvn clean install

⿣ Run the application

mvn spring-boot:run

⿤ Access the API

Generate invoice → POST http://localhost:8080/api/invoices

Download invoice → GET http://localhost:8080/api/invoices/{id}

