
# Advanced Dungeon and Dragons (AD&D)
Bem-vindo ao Advanced Dungeon and Dragons (AD&D), uma API baseada em Spring Boot que simula um emocionante jogo de RPG. Neste projeto, você encontrará uma estrutura baseada em DDD (Domain-Driven Design) com três Aggregate Roots principais: Characters, Battle, e Actions. Siga este guia para entender como jogar e interagir com a API.

## Visão Geral
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