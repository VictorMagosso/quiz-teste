# quiz-teste
:iphone: Adicionando um quiz com SQLite

Estou desenvolvendo um app e em parelalo implementei um teste para avaliar o nível da pessoa de acordo com os conteúdo exibidos. Recomendo utilizar o SQLite, mas também é possível utilizar banco de dados externos, como o Firebase. Nesse modelo, a nota máxima é alcançada até que o usuário tire uma nota maior do que a atual. Além disso, quando o usuário acertar mais do que uma quantidade de exercício, um novo nível será desbloqueado (no código não possui tratamentos mais afundo com isso, fica a seu critério o que utilizar após as tratativas passadas aqui)

Basicamente os componentes que terão no display da lista:
1 - Contagem regressiva (CountDown) pra cada exercício
2 - score atual --- conforme o usuário vai acertando, os scores vão aumentando
3 - dinamismo de cores, quando erra não aparece qual a mensagem correta, mas quando acerta a mensagem correta fica verde
4 - ao final, botão finalizar pra armazenar o highscore na tela de início
