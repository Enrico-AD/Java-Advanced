# 🚀 Java Advanced - FIAP (4º Sprint)

Este projeto foi desenvolvido como entrega da disciplina **Java Advanced**, atendendo aos requisitos de utilização de:

- ⚡ **Spring Boot**
- 🎨 **Thymeleaf**
- 🗂️ **Flyway**
- 🔒 **Spring Security**
- 🐳 **Docker Compose** (MySQL)

---

## 📂 Estrutura do Projeto

```bash
src/
├─ main/
│  ├─ java/com/fiap/enrico_andrade/
│  │  ├─ controller/     # Controllers da aplicação
│  │  ├─ dto/            # Data Transfer Objects
│  │  ├─ entity/         # Entidades JPA
│  │  ├─ repository/     # Interfaces de acesso ao banco
│  │  ├─ security/       # Configurações de autenticação/autorização
│  │  ├─ service/        # Regras de negócio
│  │  ├─ util/           # Utilitários auxiliares
│  │  └─ MainApplication # Classe principal (Spring Boot)
│  │
│  └─ resources/
│     ├─ db/migration/   # Scripts Flyway
│     ├─ static/         # Arquivos estáticos (CSS / JS)
│     │  ├─ css/
│     │  └─ js/
│     └─ templates/      # Páginas Thymeleaf
│        ├─ auth/        # Login / Logout
│        ├─ contract/    # Fluxo de contratos
│        ├─ fragments/   # Layouts reutilizáveis (header, menu, footer)
│        ├─ motorcycle/  # Páginas de motocicletas
│        └─ user/        # Gestão de usuários
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

senha: 123

Usuário comum

## 🚀 Deploy no Railway (Spring Boot + MySQL)

Este projeto pode ser implantado facilmente na [Railway](https://railway.app/) utilizando um serviço **Spring Boot** conectado a um **MySQL**.  
Abaixo estão as etapas completas para configurar o deploy.

---

### 🧩 1. Criar o projeto no Railway

1. Acesse [railway.app](https://railway.app/).
2. Clique em **New Project → Deploy from GitHub repo**.
3. Selecione este repositório.
4. Aguarde o Railway criar o ambiente automaticamente.

---

### 🗃️ 2. Adicionar o serviço MySQL

1. No painel do projeto, clique em **+ New → Database → MySQL**.
2. Após a criação, o Railway disponibiliza automaticamente as seguintes variáveis de ambiente:

MYSQLHOST
MYSQLPORT
MYSQLUSER
MYSQLPASSWORD
MYSQLDATABASE

yaml
Copiar código

3. Você pode visualizá-las na aba **Variables** do serviço MySQL.

---

### ⚙️ 3. Configurar o serviço Spring Boot

1. Acesse o serviço da aplicação (ex: `spring-app`).
2. Abra a aba **Variables** e adicione as seguintes variáveis de ambiente:

| Variável | Valor |
|-----------|-------|
| `SPRING_DATASOURCE_DRIVER_CLASS_NAME` | `com.mysql.cj.jdbc.Driver` |
| `SPRING_DATASOURCE_URL` | `jdbc:mysql://${MYSQLHOST}:${MYSQLPORT}/${MYSQLDATABASE}` |
| `SPRING_DATASOURCE_USERNAME` | `${MYSQLUSER}` |
| `SPRING_DATASOURCE_PASSWORD` | `${MYSQLPASSWORD}` |
| `SPRING_PROFILES_ACTIVE` | `prod` |

💡 **Dica:** o Railway permite usar as variáveis do serviço MySQL diretamente nas configurações da aplicação.

---

### 🧱 4. Ajustar o arquivo `application.properties`

Garanta que seu arquivo `src/main/resources/application.properties`use as variáveis de ambiente, por exemplo:

```properties
    spring.datasource.driver-class-name=${SPRING_DATASOURCE_DRIVER_CLASS_NAME}
    spring.datasource.url=${SPRING_DATASOURCE_URL}
    spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
    spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
    
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=false

🔄 5. Fazer o deploy
Após salvar as variáveis:

Vá até o serviço Spring Boot.

Clique em Deploy → Redeploy from GitHub.

Aguarde o Railway detectar o projeto (Maven ou Gradle).

Após a conclusão do build, clique em Open App para acessar sua aplicação online.

✅ 6. Verificar logs e status
Acompanhe a inicialização da aplicação na aba Logs.

Confirme se a conexão com o banco de dados foi estabelecida com sucesso.

📎 Exemplo prático
.env
    Copiar código
    SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
    SPRING_DATASOURCE_URL=jdbc:mysql://mysql.railway.internal:3306/railway
    SPRING_DATASOURCE_USERNAME=root
    SPRING_DATASOURCE_PASSWORD=dHlygLgOnddAavxEEpokDrowdqnYrOAu
    SPRING_PROFILES_ACTIVE=prod
