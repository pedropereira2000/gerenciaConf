# gerenciaConf | Repositório para a aula de Gerência de Configuração

## Descrição da disposição do repositório

### Branchs

> O repositório está organizado em branchs para o que a equipe precisa desempenhar como: 
* [*validação*](https://github.com/pedropereira2000/gerenciaConf/tree/validacao) para fazer um protótipo do que o cliente vier a solicitar em uma atualização, gerênciado pela Jaine
* [*requisitos*](https://github.com/pedropereira2000/gerenciaConf/tree/requisitos) para fazer o levantamento do que precisa ser alterado e quais são os requisitos desta alteração, gerênciado pelo Philippe
* [*desenvolvimento*](https://github.com/pedropereira2000/gerenciaConf/tree/desenvolvimento) para concretizar o que foi solicitado para o cliente sempre fazendo separado da última versão estável do programa, gerênciado pelo Marcos
* [*testes*](https://github.com/pedropereira2000/gerenciaConf/tree/testes) para verificar e validar junto com os requisitos e protótipos gerados se o que foi realizado em desenvolvimento está funcionando e coerente, gerênciado pelo Pedro Pereira.

> E branchs para o controle de versões (sempre fazendo uso do v*.* no nome da branch para demonstrar a versão) do código como:
* [*stable*](https://github.com/pedropereira2000/gerenciaConf/tree/stable) para a primeira versão estável do software antes do começo das atualizações
* [*stable_v1.1*](https://github.com/pedropereira2000/gerenciaConf/tree/stable-v1.1) para a primeira atualização gerada para o software

## Descrição das tags

> Foram utilizadas tags para fazer o controle e demonstração das versões estáveis do software. Sempre seguindo o padrão de v[o número da versão].[o número da atualização]

## Descrição dos passos para usar o software
- Primeiro escolha a ultima versão do software pelas branchs ou tags geradas para as atualizações
- Clone o repositório pela branch escolhida
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
