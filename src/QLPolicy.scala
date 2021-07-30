/*
Clase que implementa la recompensa y probabilidad de ir de un estado a otro.

@param from Estado origen.
@param to Estado destino.
@param reward Recompensa.
@param prob Probabilidad de transición desde estado "from" hasta estado "to"
*/

class QLInput(
    var from: Int, 
    var to: Int, 
    var reward: Double, 
    var prob: Double = 1.0)


/*
Clase que crea la matriz de recompensas.

@param reward Recompensa.
@param prob Probabilidad de transición.
@param value Valor Q(s,a): tomar la acción "a" desde estado "s". 

Q-Value se irá actualizando a medida que va aprendiendo Q-Learning.
*/

class QLData(
    var reward: Double = 1.0, 
    var prob: Double = 1.0, 
    var value: Double = 0.0){
  def estimate = value * probability
}
 
 


// Estrategia - Policy

/*
Clase que define la política/estrategia dado un set de inputs y estados.

@param numStates Número de estados totales.
@param input Array de transiciones entre estados con recompensa.
*/
 
class QLPolicy(numStates:Int, input: Array[QLInput]){
  
  
  val qlData = {
      // Creación de matriz qlData recompensas+probabilidad
    val data = Array.tabulate(numStates)(_ => Array.fill(numStates)(new QLData))
    input.foreach(in => {
      data(in.from)(in.to).reward = in.reward
      data(in.from)(in.to).prob = in.prob
    })
    data
  }
  
  def setQ(from: Int, to: Int, value: Double): Unit = 
    qlData(from)(to).value = value
  
  def Q(from: Int, to: Int): Double =
    qlData(from)(to).value
  
  def EQ(from: Int, to: Int): Double =
    qlData(from)(to).estimate
  
  def R(from: Int, to:Int): Double =
    qlData(from)(to).reward
  
}