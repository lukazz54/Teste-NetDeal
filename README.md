Para a inicialização correta do projeto....
1° Entre no cmd, ou prompt de comando desajavel, e na pasta do projeto execute o comando: docker-compose up -d -> dessa forma, containers tanto do banco MySQL, como o server para subir o front sera baixados e executados.
  OBS: Caso tenha problema com a conexão com o banco envolver Public Key, no DBeaver vá em Edit Connection > Driver Properties > allowPublicKeyRetrieval e deixe marcado TRUE
2° Crie manualmente a database => register-users (CREATE DATABASE register-users)
3° Execute o arquivo .sql -> Criação da tabela no banco
4° Para executar o back pela IDE, poderá subir o Tomcat do Spring Boot apos executar o docker-compose.
5° Para acessar a pagina, em seu browser acesse a url http://localhost. O serviço do Ngix ficará responsavel em subir a aplicação front nessa url.
