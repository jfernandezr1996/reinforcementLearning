/*
Ejemplo de ejecución de un proceso Q-Learning
para los estados y transiciones definidas
*/

// Definimos las acciones entre cada estado
val action1 = new QLAction(1,2)
val action2 = new QLAction(1,3)
val action3 = new QLAction(2,3)
val action4 = new QLAction(2,1)
val action5 = new QLAction(3,1)
val action6 = new QLAction(3,2)
val action7 = new QLAction(1,4)
val action8 = new QLAction(2,4)
val action9 = new QLAction(3,4)
//val action10 = new QLAction(4,2) // sin considerar este -> terminal
 
// Definimos los diferentes estados
val estado1 = new QLState(1, List(action1, action2, action7))
val estado2 = new QLState(2, List(action3, action4, action8))
val estado3 = new QLState(3, List(action5, action6, action9))
val estado4 = new QLState(4) // no terminal
estado4.isGoal // False
 
// Definimos la recompensa de cada paso de estado
val firstStep = new QLInput(1, 2, 5, 1)
val secondStep = new QLInput(1, 3, 4, 1)
val thirdStep = new QLInput(2, 3, 3, 1)
val fourStep = new QLInput(2, 1, 1, 1)
val fiveStep = new QLInput(3, 1, 7, 1)
val sixStep = new QLInput(3, 2, 5, 1)
val sevenStep = new QLInput(1, 4, 5, 1)
val eightStep = new QLInput(2, 4, 10, 1)
val nineStep = new QLInput(3, 4, 1, 1)
//val tenStep = new QLInput(4, 2, 15, 1)
// Si consideramos la accion 10 y tenStep veremos como el agente
// aprende en la transición 2->4 / 4->2 : sin estado terminal
println("Recompensa del tercer paso: " + thirdStep.reward.toString) // 3
 
// Definimos la estrategia
val strategy = new QLPolicy(5, Array(firstStep, secondStep, thirdStep, fourStep, fiveStep, 
                                     sixStep, sevenStep, eightStep, nineStep))//, tenStep))
println("Reward 1->2: " + strategy.R(1, 2)) // 5 -> obtain valor R, reward
 
// Definimos el espacio de estados y calculamos un ejemplo de encontrar la máxima recompensa en un estado
val space = new QLSpace(Array(estado1, estado2, estado3, estado4), Array(4))
// Ejemplo máximo reward y próximos estados
val random_state = space.init(Random)
println("Identificador del estado: " + random_state.id.toString) // identificador del estado aleatorio
println("Máximo Reward desde el estado actual: " + space.maxR(random_state, strategy).toString) // Máximo reward desde el estado aleatorio
space.nextStates(random_state) // Próximos estados posibles del estado aleatorio
 
 
// Ejemplo ENTRENAMIENTO MODELO Q-LEARNING
val config = new QLConfig(numEpisodes = 20, minCoverage = 0.5, gamma = 0.5, alpha = 0.5, episodeLength = 100)
val qlearning = new QLearning(config, space, strategy)
 
 
// Predicción usando el modelo Q-Learning entrenado!!
println("Comenzamos la predicción usando el modelo Q-learning")
val prediction = qlearning.predict(estado1)
print("Predicción comenzando en S1: " + prediction.id.toString + "\n")