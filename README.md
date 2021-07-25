# gerenciaConf | Repositório para a aula de Gerência de Configuração

## Descrição da priveira atualização realizada (v1.0)

> Realizamos a alteração do software com uma modificação no layout da tela de reservas onde passamos de um layout horizontal para um vertical
e também alteramos a cor do funda da página e a organização dos elementos na tela.

> Na pasta **[Atualizacao_v1.1](./Atualizacao_v1.1)** são armazenados os documentos que foram gerados no processo de atualização do software
como o documento de teste, protótipo e os requisitos levantados.

> Na pasta **[Reserva_De_Salas_v1.1](./Reserva_De_Salas_v1.1)** é armazenado o código que foi atualizado em desenvolvimento e posteriormente testedo.

## Descrição dos passos para usar o software

- Primeiro clone o repositório a partir da branch mais recente (a estrutura será stable-v*.*)
- Após clonar o repositório, de posse do MySQL faça o import do arquivo reservas.sql que está na pasta Reserva_De_Salas_v*.* para restaurar o banco
- Com o banco resturado abra o nosso projeto que está na mesma pasta, recomendamos o uso do Apache NetBeans IDE
- De posse do projeto e com ele aberto acesse a estrutura do mesmo indo por Reserva_De_Salas > Source Packges > Connection > ConnectionFactory.java
- Abra o arquivo ConnectionFactory.java e altere a linha 13 
- ``` return DriverManager.getConnection("jdbc:mysql://localhost:3306/reservas?useTimezone=true&serverTimezone=UTC","root","0704"); ``` trocando a segunda aspas que tem root para seu usuário do banco e a terceira aspas para a senha do mesmo.
- Após essa alteração acesse no projeto Reserva_De_Salas > Source Packeges > View > Execute a TelaLogin.java > coloque o usuário: adm | senha: 123
- Clique no botão REALIZAR RESERVA > Clique em CRIAR na parte inferior da tela
- Essa é a tela que foi alterada no processo

## ATENÇÃO PARA ERROS !!!

- Se o programa apresentar erros em algumas telas, tente importar os jars da pasta lib do projeto.
