# ESIG - Sistema de Cálculo de Salários

Aplicação Web Java desenvolvida como teste técnico, utilizando **JSF (PrimeFaces + Jakarta Faces)**, **Hibernate/JPA**, **PostgreSQL** e **WildFly**, empacotada em Docker.

---

## 🚀 Funcionalidades
- **CRUD de Pessoas** com seleção de Cargo  
- **Cálculo/Recalculo de Salários** consolidados por cargo e vencimentos  
- Processamento **assíncrono** do cálculo com feedback em tela   
- **CRUD de Usuários + Login/Autenticação**  

---

## 🛠️ Tecnologias
- Java 17
- Jakarta EE (Faces, CDI, JPA)
- PrimeFaces 15 (Jakarta)
- Hibernate 6
- PostgreSQL 15 (Docker)
- WildFly 26 (Docker)
- Maven

---

## 📦 Como rodar localmente

### 1. Clonar o projeto
git clone https://github.com/saymonandres/esig-salarios.git
cd esig-salarios

### 2. Subir infraestrutura (banco e servidor)
docker-compose up --build

- Postgres: localhost:5432
- WildFly: localhost:8080

### 3. Empacotar aplicação 

### 4. Deploy no WildFly
Copiar o WAR para a pasta de deploy do container:
target/esig-salarios.war

O WildFly detectará automaticamente e fará o deploy.

### 5. Acessar aplicação
http://localhost:8080/esig-salarios/
