Este projeto foi desenvolvido no âmbito da Unidade Curricular 'Inteligência Artificial', durante o ano letivo 2022/2023, na Faculdade de Ciências da Universidade do Porto, pelos alunos [André Sousa](https://github.com/anfisou)), [Isabel Brito](https://github.com/Nia3324) and [Paulo Silva](https://github.com/Panda-Hacks).

O projeto tem como objetivo a implementação do algoritmo ID3 para a criação de Decision Trees, assim como a utilização do modelo criado a partir do dataset Connect4 na fase de rollout do algoritmo MCTS no jogo Quatro em linha.



### Instruções de Compilação e Execução

Para poder executar cada problema é primeiro necessário compilar todos os ficheiros java. Para tal, utilize a seguinte instrução:

```javac *.java```

Após a compilação de todos os ficheiros, é possível, finalmente, executá-los. Foram utilizados os seguintes datasets:
- Iris dataset
- Restaurant dataset
- Weather dataset
- Connect 4 dataset

Para a utilização de cada um, utilizar o seguinte formato de entrada `java DecisionTree <dataset>`, onde `<dataset>` deve ser substituido pelo nome do dataset desejado:
- ```java DecisionTree iris```
- ```java DecisionTree restaurant```
- ```java DecisionTree weather```
- ```java DecisionTree connect4```

Nos 3 primeiros casos, imprime a árvore de decisão correspondente ao dataset escolhido o programa, bem como prever os casos fornecidos num ficheiro **test.csv**. Caso não seja pretendido fazer previsão de novos input, o ficheiro **test.csv** deve ficar vazio. (ver Exemplo 1)

Quanto ao último caso, dataset Connect4, o algoritmo irá gerar a árvore de decisão, embora não a imprima dado o seu tamanho. Para além disto, um jogo de connect 4 será iniciado onde é possível ver o algoritmo em ação contra o jogador. (ver Exemplo 2)

As árvores para cada ums dos datasets estão disponíveis na pasta ```trees```.

#### Exemplo 1

**Input:**

    java DecisionTree restaurant

**Ficheiro test.csv:**

    Yes,No,Yes,Yes,Full,$,No,No,Burger,10-30
    Yes,No,Yes,No,None,$,No,No,French,>60
    No,Yes,Yes,Yes,Some,$$$,Yes,Yes,Italian,0-10

**Output final:**

    <Pat>
      Some: Yes (4)         
        Full: 
          <Hun>
            Yes: 
              <Type>        
                Thai:
                  <Fri>
                    No: No (1)         
                    Yes: Yes (1)         
                Burger: Yes (1)         
                Italian: No (1)         
            No: No (2)         
        None: No (2)

    Yes 
    No 
    Yes

#### Exemplo 2

**Input:**

    java DecisionTree connect4

**Output:**

    -------------------------------
    Player : 2
    | - | - | - | - | - | - | - |
    | - | - | - | - | - | - | - |
    | - | - | - | - | - | - | - |
    | - | - | - | - | - | - | - |
    | - | - | - | - | - | - | - |
    | - | - | - | - | - | - | - |
      1   2   3   4   5   6   7
    -------------------------------
          
