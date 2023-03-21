### 10.6. Rodando os testes pelo Maven
- **./mvnw test**: comando maven para rodas testes em linha de comando
- **./mvnw clean package**: comando maven para fazer o build do projeto. 
Esse comando sempre irá executar os testes também. Em caso de falha de algum teste 
o build não é feito.

### 10.7. Configurando Maven Failsafe Plugin no projeto
- Maven Failsafe Plugin é um plugin do maven para rodar testes de integração. Com este plugin o bluid
do projeto não irá executar os testes de integração e não irá falhar o build caso tenha algum problema.
Esse plugin não impede a execução de testes untários.
- Classes de testes de integração devem seguir uma convensão de nomes onde o nome deve terminar sempre
COM IT.
- Com Maven Failsafe Plugin habilitado não será possível executar os testes com os comandos
  **./mvnw test** e **./mvnw clean package**.
- Testes de integração não devem ser executados no build do projeto
  - Gastam mais tempo de execução do que testes unitários, por que promovem uma interação entre todas
as partes do sistema. 
  - Requerem um ambiente muito complexo para execução, que necessitam de configuração de serviços externos, 
bancos de dados e outros componentes.
  - Podem ser menos confiáveis porque dependem de ambientes externos que podem estarou não disponíveis 
ou apresentar um compotamento não esperado. A execulçao de testes de integração junto ao biuld pode falhar
ainda que o código da aplicação esteja correto.
  - Separar testes unitários dos testes de integração e build ajudam a separar as diferentes etapas do
desenvolvimento da aplicação o que facilita a depuração e compreensão do que está sendo feito em cada etapa.
- o comando ***/.mvnw verify*** executa os testes de integração.
- Os problemas acima são evitados com a separação da execução do testes de integração do build utilizando 
o ***maven-failsafe-plugin***, sua função é permitir a execução dos testes de integração para não afetarem 
o projeto de build. [Aqui tem um bom material para revisar.](https://www.baeldung.com/maven-integration-test)