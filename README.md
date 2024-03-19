Para a inicialização correta do projeto....
1° Execute o arquivo .sql -> Criação da tabela no banco
2° Entre no cmd, ou prompt de comando desajavel, e na pasta do projeto execute o comando: docker-compose up -d -> dessa forma, containers tanto do banco MySQL, como o server para subir o front sera baixados e executados
3° Para executar o back pela IDE, poderá subir o Tomcat do Spring Boot apos executar o docker-compose.
4° Para acessar a pagina, em seu browser acesse a url http://localhost. O serviço do Ngix ficará responsavel em subir a aplicação front nessa url.
