# 🚀 Java Advanced - FIAP (3º Sprint)

Este projeto foi desenvolvido como entrega da disciplina **Java Advanced**, atendendo aos requisitos de utilização de **Spring Boot**, **Thymeleaf**, **Flyway**, **Spring Security** e **Docker Compose** para banco de dados MySQL.

---

## 📂 Estrutura do Projeto

src/
├─ main/
│ ├─ java/com/fiap/enrico_andrade/
│ │ ├─ controller/ # Controllers da aplicação
│ │ ├─ dto/ # Data Transfer Objects
│ │ ├─ entity/ # Entidades JPA
│ │ ├─ repository/ # Interfaces de acesso ao banco
│ │ ├─ security/ # Configurações de autenticação/autorização
│ │ ├─ service/ # Regras de negócio
│ │ ├─ util/ # Utilitários auxiliares
│ │ └─ MainApplication # Classe principal (Spring Boot)
│ │
│ └─ resources/
│ ├─ db/migration/ # Scripts Flyway
│ ├─ static/ # Arquivos estáticos (CSS / JS)
│ │ ├─ css/
│ │ └─ js/
│ └─ templates/ # Páginas Thymeleaf
│ ├─ auth/ # Login / Logout
│ ├─ contract/ # Fluxo de contratos
│ ├─ fragments/ # Layouts reutilizáveis (header, menu, footer)
│ ├─ motorcycle/ # Páginas de motocicletas
│ └─ user/ # Gestão de usuários
│
└─ application.properties # Configurações da aplicação


---

## 🛠️ Tecnologias Utilizadas

- **Java 25**
- **Spring Boot**
    - Spring Web (MVC + Thymeleaf)
    - Spring Data JPA (Hibernate)
    - Spring Security
- **Flyway** (migrações de banco de dados)
- **Thymeleaf** (camada de visualização)
- **MySQL** rodando via **Docker Compose**
- **Bootstrap 5** (UI responsiva)

---

## ⚙️ Setup do Projeto

### 1. Clonar o repositório
```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio

2. Subir o banco de dados com Docker

O projeto já possui um docker-compose.yml configurado para o MySQL.
Execute:

docker-compose up -d


Isso iniciará o MySQL na porta 3306, com as credenciais definidas no application.properties:

Database: bike_shed

User: root

Password: admin123

3. Rodar as migrações Flyway

Assim que a aplicação iniciar, o Flyway aplicará automaticamente as migrações presentes em src/main/resources/db/migration.

▶️ Executando a Aplicação
Pré-requisitos

Java 25 instalado

Maven configurado

Rodando localmente
./mvnw spring-boot:run


A aplicação estará disponível em:
👉 http://localhost:8080

🔑 Usuários de Teste

O sistema já cria usuários via script Flyway:

Admin

login: admin

senha: admin123

Usuário comum

login: user

senha: user123

📹 Entrega

A entrega deve conter:

Repositório público no GitHub (este README incluso).

Scripts e instruções para rodar a aplicação (este passo a passo).

Vídeo de até 10 minutos demonstrando:

Login/logout

Fluxo completo de contrato (criação, edição, finalização)

Validações funcionando

Diferentes perfis de usuário e permissões

✅ Critérios Atendidos

 Thymeleaf para frontend com fragments reutilizáveis

 Flyway para versionamento do banco

 Spring Security para autenticação e autorização

 Validações (datas, CPF, CEP, obrigatoriedade de motocicleta)

 Fluxos completos de negócio além do CRUD simples

---
