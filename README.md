
# Advanced Dungeon and Dragons (AD&D)
Bem-vindo ao Advanced Dungeon and Dragons (AD&D), uma API baseada em Spring Boot que simula um emocionante jogo de RPG. Neste projeto, você encontrará uma estrutura baseada em DDD (Domain-Driven Design) com três Aggregate Roots principais: Characters, Battle, e Actions. Siga este guia para entender como jogar e interagir com a API.

- [Tecnologias](#tecnologias-)
- [Como instalar/iniciar](#como-instalariniciar)
- [Documentação e endpoints](#documentação-e-endpoints)
- [Visão Geral e Regras de jogo](#visão-geral-e-regras-de-jogo)
- [Disclaimer e Autocrítica](#disclaimer-e-autocrítica)

## Tecnologias 
- Java 17
- Spring Boot 3.1.3
- Postgres SQL
- JPA/Hibernate
- Springdoc
- Flyway
- Lombok
- Vavr
- JUnit
- Docker (opcional);

## Como instalar/iniciar;

### Por docker 
- A forma mais simples de inicializar a aplicação é utilizando o docker.

- Você deve adicionar um arquivo .env com as configurações. Existe um exemplo, o arquivo ".env.example" que pode ser copiado ou renomeado para .env, e já fará a aplicação funcionar corretamente. 

- Com esse arquivo configurado, basta executar o comando: 
```bash
docker compose up -d
```

- A aplicação vai rodar na porta que foi definida na variável "API_PORT" do .env, no caso, por padrão 8070.
- Após rodar a aplicação, espere um pouco que ela vai gerar o Open API detalhado no seguinte url:  http://localhost:8070/docs
- Lembre-se que a porta será aquela que definiu no .env

## Outros meios 
- Pode rodar a aplicação por outros meios também sem problema, 
- 
## Documentação e endpoints.
- Uma vez que a aplicação estiver rodando, você pode acessar sua documentação pela url: http://localhost:8080/swagger-ui/index.html/
<br><br>
- O open api do spring docs está bem explicativo sobre o cada endpoint faz, além de ter definições de schema e exemplos prontos de payload (body json); 

## Visão Geral e Regras de jogo
### Characters
Os Characters são os personagens/classes do jogo. Eles podem ser heróis ou monstros, cada um com seus atributos específicos. A API oferece um CRUD completo, permitindo que você crie e customize suas classes de personagens.

### Battle
As Battles representam as partidas em batalha. Você pode jogar contra o "ambiente" ao iniciar uma partida no endpoint correspondente. Ao começar uma batalha, você escolherá seu nome (nickname) e classe. Você também tem a opção de escolher o nome e a classe do seu adversário, ou deixar esses campos vazios para enfrentar um adversário aleatório (que sempre será do arquétipo "monstro").

Ao iniciar uma batalha, você receberá os dados do combate em formato JSON, incluindo um código ou ID que pode ser usado para continuar jogando em sessões futuras.

### Actions
As Actions representam as ações realizadas durante uma batalha. Elas incluem:

- **Iniciativa:** Lançamento de 1d20 para determinar quem começa o ataque.
- **Atacar**: Tentativa de um ataque contra o oponente.
- **Defender**: Tentativa de se defender do oponente.
- **Dano**: Cálculo do dano final quando um ataque é realizado.
- **Cada** ação tem seu próprio endpoint específico. Para agir durante uma batalha, você deve fornecer o código ou o ID do combate correto e executar a ação apropriada no momento certo.

O jogador cujo HP (Pontos de Vida) for reduzido a dez ou menos será considerado o perdedor da batalha.

Você também pode acessar os históricos de combates e ações para revisar suas partidas anteriores.

## Como Jogar
Comece criando seu próprio Character personalizado. Você pode escolher ser um herói ou um monstro e definir seus atributos específicos.

Inicie uma nova Battle através do endpoint correspondente. Escolha seu nome (nickname) e classe, e se desejar, defina o nome e a classe do seu oponente.

Ao iniciar uma batalha, você receberá os dados do combate em formato JSON, incluindo um código ou ID exclusivo.

Execute as Actions no momento certo para lutar contra seu adversário. Use a Iniciativa para determinar quem ataca primeiro, Ataque para atacar, Defesa para se defender e Dano para calcular o dano infligido.

Continue agindo até que um dos jogadores tenha seu HP reduzido a dez ou menos. O jogador nessa condição perde a batalha.

Acesse os históricos de combates e ações para revisar suas partidas anteriores.

Endpoints Disponíveis
A API oferece os seguintes endpoints para interagir com o jogo:

- v1/characters: _Gerenciamento de personagens._
- v1/battles: _Iniciar e gerenciar batalhas._
- v1/history/battles: _Acessar o histórico de combates._
- v1/history/actions: _Acessar o histórico de ações._

## Comece a Jogar
Agora que você conhece a estrutura do projeto AD&D e como jogar, comece a explorar a API e crie suas próprias aventuras épicas. Divirta-se e boa sorte nas batalhas!

## Disclaimer e Autocrítica

- Devido ao tempo apertado, houveram algumas dificuldades em conciliar o tempo e cuidar do projeto, por isso, essa primeira versão é desleixada.
- Muito dela foi feita em GOHORSE, então existe coisas que quebram princípios de um código limpo.
- Por exemplo: queria muito fazer um vídeo, containerzar a aplicação, fazer testes. Nessas últimas horas que eu tive para fazer isso, surgiram imprevistos (incluindo bug da aplicação que não queria rodar em cli, mas rodava no intellij) que me atrapalhou.

### Coisas a melhorar:
- Melhorar responsabilidade das classes, existem classes muito grandes.
- Fazer testes unitários. Só deu para fazer um que não era tão relevante assim.
- Melhorar os commits, porque foram todos empacotados.
- Perdi muito tempo em coisas que não agregavam tanto valor (como fugir da obsessão do primitivos optando por sempre usar VO do que tipos primitivos)
- Algumas funções confusas porque não tive tempo de reorganizá-las depois.
- Nomeclaturas não seguem a linguagem de domínio, tendo nomeações estranhas.
- Alguns code smells como números mágicos.
- Do mais, o projeto foi muito bacana, eu tive muitas ideias no meio do caminho que foram me animando e tornando um pouco megalomaniaco, queria implementar todas. E por isso, as vezes foquei em outras coisas.

### Pontos positivos:
- Estrutura de pastas e desaclopamento permite a facilidade de refatoração, seguindo o DDD.
- Códigos rebuscados usando linguagem funcional com VAVR, deixando o código mais elegante e conciso (quando usado bem);
- Bom funcionamento e documentação com swagger. Funciona para o usuário final. 
- Recursos interessantes como as buscas por paginação de forma dinâmica.

Dito isso, vou estar fazendo uma branch para incluir um vídeo exemplo da aplicação e fazer as correções do que estiver acima, mas não precisará ser considerado na avaliação.

Obrigado!