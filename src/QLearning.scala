import scala.collection.immutable
import scala.util.Random
import scala.util.{Try,Success,Failure}

// Modelo y entrenamiento

/*
Clase que define un modelo para el algoritmo Q-Learning

@param bestPolicy Mejor "policy" conseguida o estimada durante entrenamiento.
@param coverage Número de iteraciones hasta alcanzar el objetivo
*/
class QLModel(
    val bestPolicy: QLPolicy, val coverage: Double)
 


// Configuración

/*
Clase de configuración para la ejecución del algoritmo.

@param numEpisodes Número de iteraciones para el algoritmo. (epochs)
@param minCoverage Mínimo coverage permitido durante el entrenamiento.
@param gamma Discount rate para el algoritmo Q-Learning.
@param alpha Tasa de aprendizaje para el algoritmo Q-Learning.
@param episodeLength Máximo número de estados por epoch/iteración.
*/
class QLConfig(
  val numEpisodes: Int, val minCoverage: Double, 
  val gamma: Double, val alpha: Double, val episodeLength: Int)
 


// Modelo Q-Learning!!

/*
Clase que implementa el algoritmo Q-Learning.

Q'(t) = Q(t) + alpha.[r(t+1) + gamma.max{Q(t+1)} - Q(t)

@param config Configuración del algoritmo Q-Learning.
@param qlSpace Espacio de estados.
@param qlPolicy Inicial policy para la búsqueda/algoritmo.
*/
 
class QLearning[T](
  config: QLConfig, 
  qlSpace: QLSpace, 
  qlPolicy: QLPolicy){
  
  def train: Option[QLModel] =     Try {
    // Entrenamos para cada iteración con un estado aleatorio
    val r = new Random(System.currentTimeMillis)
    val completions = Range(0, config.numEpisodes).map(epoch => if(training(r)) 1 else 0).sum
            
    // Cálculo del coverage: número de iteraciones para cada estado final alcanzado
    completions.toDouble/config.numEpisodes
        
    // Accedemos al modelo si se cumple que supera el minCoverage determinadao en config
    }.map(coverage => {
        if(coverage > config.minCoverage) Some(new QLModel(qlPolicy, coverage)) else None
    }).get
  
  
  def training(r: Random): Boolean = {
    
    def search(st: (QLState, Int)): (QLState, Int) = {
      /*print("Step " + st._2.toString + "\n")*/
        // Búsqueda de los próximos estados dado el ID del estado actual
      val states = qlSpace.nextStates(st._1) 
      if (states.isEmpty || st._2 >= config.episodeLength)
        (st._1, -1) // nos quedamos en el mismo estado y acaba el proceso recursivo
      else {
        val state = states.maxBy(s => qlPolicy.R(st._1.id, s.id)) // Accedemos al estado con mayor recompensa (otra opción es acceder al máximo Q(s,a) -> EQ)
        if(qlSpace.isGoal(state)) // Comprobamos si el próximo estado es un estado final
          (state, st._2)
        else { //  En otro caso, calculamos el Q(s,a) "policy" de acuerdo a la transición realizada usando la matriz de recompensas
          /*println("- Transición " +  st._1.id.toString +" -> " + state.id.toString)*/
          val r = qlPolicy.R(st._1.id, state.id)
          val q = qlPolicy.Q(st._1.id, state.id)
          val deltaQ = r + config.gamma*qlSpace.maxR(state, qlPolicy) - q
          val nq =  q + config.alpha*deltaQ
          
          qlPolicy.setQ(st._1.id, state.id, nq)
          search((state, st._2+1))
        }
      }
    }
       
    r.setSeed(System.currentTimeMillis*Random.nextInt)
    // Devolvemos TRUE si es un estado terminal
    val finalState = search((qlSpace.init(r), 0))
    if( finalState._2 == -1) 
      false 
    else 
      qlSpace.isGoal(finalState._1)  // Devuelve True si es estado final
  }
  
  
  // Modelo Q-Learning
  val model: Option[QLModel] = train
  
  def isModel: Boolean = model.isDefined
  
  // Predicción próximo estado usando el modelo entrenado Q-Learning.
  // El modelo es automáticamente creado (o entrenado) al instanciarse la clase.
  def predict: PartialFunction[QLState, QLState] = {
    case state: QLState if isModel => 
    if( state.isGoal) state else nextState((state, 0))._1
  }
  
  
  def nextState(st: (QLState, Int)): (QLState, Int) =  {
    val states = qlSpace.nextStates(st._1)
    if (states.isEmpty || st._2 >= config.episodeLength) 
      st
    else { 
      // Seleccionar el estado con la mayor recompensa dada la "policy" BESTPOLICY 
      val qState = states.maxBy(
        s => model.map(_.bestPolicy.R(st._1.id, s.id)).getOrElse(-1.0)
      )
      println("- Transición: " + st._1.id.toString + " -> " + qState.id)
      nextState((qState, st._2+1))
    }
  }
}