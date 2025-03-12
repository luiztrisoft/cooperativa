## Relatório Técnico: Projeto Cooperativa

#### 1. **Simplicidade no Design da Solução**
Optei por uma abordagem direta e eficiente, evitando over engineering. Utilizei ferramentas como **Liquibase** para gerenciar migrações de banco de dados, **Docker** para garantir um ambiente consistente e **Swagger** para documentar os endpoints da API de forma clara e acessível. Essas escolhas simplificaram o desenvolvimento e a manutenção do projeto.

#### 2. **Organização do Código**
O código foi organizado em camadas bem definidas (controllers, services, repositories e DTOs), seguindo as boas práticas do Spring Boot. Utilizei o **MapStruct** para mapeamento de entidades e DTOs, o que melhorou a legibilidade e a manutenção do código.

#### 3. **Arquitetura do Projeto**
A arquitetura segue o padrão em camadas, com controllers responsáveis pelas requisições HTTP, services contendo a lógica de negócio e repositories para acesso ao banco de dados. A injeção de dependências foi utilizada para desacoplar as classes.

#### 4. **Boas Práticas de Programação**
Adotei nomes descritivos para classes, métodos e variáveis, além de seguir o padrão RESTful para os endpoints. Implementei testes automatizados (unitários e de integração) para garantir a qualidade do código e a cobertura de funcionalidades.

#### 5. **Possíveis Bugs**
Os testes automatizados ajudam a identificar e prevenir bugs. Para cenários de borda, como pautas sem votos ou votos inválidos, adicionei verificações específicas.

#### 6. **Tratamento de Erros e Exceções**
Criei exceções personalizadas (por exemplo, `CooperativaException`) e utilizei logs com **SLF4J** para registrar eventos importantes e facilitar a depuração. Isso permite monitorar o comportamento da aplicação em produção.

#### 7. **Explicação das Escolhas**
Escolhi o **Liquibase** para versionar mudanças no banco de dados, o **Docker** para garantir consistência entre ambientes e o **Swagger** para documentar a API de forma interativa. Essas ferramentas foram selecionadas por sua eficiência e facilidade de uso.

#### 8. **Uso de Testes Automatizados e Ferramentas de Qualidade**
Implementei testes unitários e de integração utilizando **JUnit**. Para melhorar a qualidade do código, utilizei o **MapStruct** para mapeamentos e o **Caffeine** para cache em memória, além de **@Async** para execução de tarefas em segundo plano.

#### 9. **Limpeza do Código**
O código foi mantido limpo e organizado, com imports não utilizados removidos e padrões de nomenclatura consistentes.

#### 10. **Documentação do Código e da API**
A API foi documentada com **Swagger**, disponível em `http://localhost:8080/swagger-ui/index.html`.

#### 11. **Logs da Aplicação**
Utilizei **SLF4J** para registrar logs em pontos críticos da aplicação, como ao salvar uma pauta ou abrir votação. Isso facilita a monitoração e a depuração em produção.

#### 12. **Mensagens e Organização dos Commits**
Os commits foram feitos com mensagens descritivas, seguindo boas práticas de versionamento. Não utilizei branches separados para funcionalidades específicas.

---

### Extras

1. **Validação de CPF**:
    - Não consegui encontrar uma API externa para validar CPFs, então deixei essa funcionalidade comentada no código. Uma solução futura poderia incluir a integração com um serviço externo, microserviço ou a implementação de uma validação local.

2. **Melhorias de Performance**:
    - Implementei cache em memória com **Caffeine** para pautas e associados, além de usar **@Async** para executar tarefas em segundo plano, como o fechamento automático de votações expiradas. Para melhorar o desempenho nas votações, uma evolução possível seria salvar votos em lote, reduzindo o número de operações no banco de dados.

3. **Versionamento da API**:
    - Optei por uma abordagem simples de versionamento por URL, em vez de usar headers ou parâmetros de consulta. Isso facilita a compreensão e o uso da API.

---

### Considerações Finais

Pela descrição das instruções, o foco estava no cadastro de pautas, abertura de sessões de votação, recebimento de votos e contabilização dos resultados. No entanto, decidi persistir os associados no banco de dados para garantir a integridade e a consistência das informações. Acredito que essa decisão adiciona valor ao projeto, mesmo não sendo explicitamente solicitada.

---

### Execução do projeto

#### 1. Banco de dados
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)  ![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)


O banco de dados escolhido para este projeto foi o PostgreSQL e está configurado em um container Docker, conforme detalhado abaixo:

```bash
docker run --name db_cooperativa -e POSTGRES_PASSWORD=123456 -p 3333:5432 -v c:\docker\cwi:/var/lib/postgresql/data -d postgres
```

Informações de conexão (também podem ser encontradas no application.yml na API)
```
User: postgres
Pass: 123456
Port: 3333
Base: cooperativa
```
##### Importante: É necessário criar a base (cooperativa) na mão antes de executar  a API.


#### 2. API
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Liquibase](https://img.shields.io/badge/Liquibase-2962FF?style=for-the-badge&logo=liquibase&logoColor=white)


O projeto foi desenvolvido com Java 23, mas sem uso de recursos tão modernos, devendo ser compatível com versões anteriores. Também foi usado o Spring-boot na versão 3.4.3. Depois de baixar, descompactar e abrir o projeto com a IDE de sua preferência, execute o projeto com Maven:
```
spring-boot:run
```

#### 3. MapStruct
![MapStruct](https://img.shields.io/badge/MapStruct-FF6F00?style=for-the-badge&logo=mapstruct&logoColor=white)

Executar o **maven clean** e o **maven install** para gerar as implementações do MapStruct. A falta deles pode acarretar em não trazer visualização nas consultas e outras situações inesperadas.


#### 4. Documentação e testes
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)


Com a aplicação em execução, a documentação da API pode ser acessada em:

`http://localhost:8080/swagger-ui/index.html`.

---

### Conclusão

Acredito que o projeto atende aos requisitos propostos, com uma arquitetura bem estruturada, boas práticas de programação e testes automatizados. As escolhas tecnológicas, como Liquibase, Docker, MapStruct e Swagger, foram fundamentais para garantir a qualidade e a manutenibilidade do código. As melhorias de performance e a persistência de associados no banco de dados são diferenciais que agregam valor à solução.

