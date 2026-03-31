# demo-dao-jdbc

## Descrição

Este projeto Java demonstra a implementação do padrão DAO (Data Access Object) utilizando JDBC (Java Database Connectivity) para interagir com um banco de dados relacional. Ele gerencia operações CRUD (Create, Read, Update, Delete) para entidades `Seller` (Vendedor) e `Department` (Departamento), fornecendo um exemplo claro de como organizar a camada de acesso a dados em uma aplicação Java.

## Tecnologias Utilizadas

*   **Java**: Linguagem de programação principal.
*   **JDBC**: API para conectividade com banco de dados.
*   **MySQL**: Banco de dados relacional (configurável via `db.properties`).

## Estrutura do Projeto

A estrutura do projeto segue uma organização lógica para separar as responsabilidades:

```
.gitignore
db.properties
demo-dao-jdbc.iml
src
├── application
│   ├── Main.java
│   └── Main2.java
├── db
│   ├── DB.java
│   ├── DbException.java
│   └── DbIntegrityException.java
├── mapper
│   ├── DepartmentMapper.java
│   └── SellerMapper.java
└── models
    ├── dao
    │   ├── DaoFactory.java
    │   ├── DepartmentDao.java
    │   ├── SellerDao.java
    │   └── impl
    │       ├── DepartmentDaoJDBC.java
    │       └── SellerDaoJDBC.java
    └── entities
        ├── Department.java
        └── Seller.java
```

*   `application/`: Contém as classes principais de execução (`Main.java`, `Main2.java`) que demonstram o uso dos DAOs.
*   `db/`: Classes utilitárias para conexão e tratamento de exceções do banco de dados.
*   `mapper/`: Classes responsáveis por mapear resultados de consultas SQL para objetos Java.
*   `models/dao/`: Interfaces DAO que definem os contratos para as operações de acesso a dados.
*   `models/dao/impl/`: Implementações concretas das interfaces DAO usando JDBC.
*   `models/entities/`: Classes de entidade (`Seller`, `Department`) que representam as tabelas do banco de dados.

## Configuração do Banco de Dados

O projeto utiliza um arquivo `db.properties` para configurar a conexão com o banco de dados. Certifique-se de que seu banco de dados MySQL esteja em execução e que as credenciais estejam corretas.

Exemplo de `db.properties`:

```properties
user=root
password=1234
dburl=jdbc:mysql://localhost:3306/coursejdbc
useSSL=false
```

**Observação**: Altere `user`, `password` e `dburl` conforme a configuração do seu ambiente.

## Como Executar

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/Konkvon/demo-dao-jdbc.git
    cd demo-dao-jdbc
    ```
2.  **Configure o banco de dados:**
    *   Crie um banco de dados chamado `coursejdbc` (ou o nome configurado em `dburl`).
    *   Crie as tabelas `Department` e `Seller` com os campos apropriados (o esquema não está incluído no repositório, mas pode ser inferido pelas entidades).
    *   Atualize o arquivo `db.properties` com suas credenciais de acesso ao banco de dados.
3.  **Compile e execute:**
    *   Abra o projeto em sua IDE Java preferida (IntelliJ IDEA, Eclipse, etc.).
    *   Certifique-se de que as dependências JDBC para MySQL estejam configuradas no seu `classpath`.
    *   Execute a classe `Main.java` ou `Main2.java` localizada em `src/application/` para ver os exemplos de operações DAO em ação.

## Exemplos de Uso (Baseado em `Main.java`)

A classe `Main.java` demonstra as seguintes operações:

*   **`findById`**: Busca um vendedor pelo ID.
*   **`findByDepartment`**: Busca vendedores por departamento.
*   **`findAll`**: Lista todos os vendedores.
*   **`insert`**: Insere um novo vendedor.
*   **`update`**: Atualiza os dados de um vendedor existente.
*   **`deleteById`**: Deleta um vendedor pelo ID.

Cada operação é impressa no console para fácil visualização dos resultados.
