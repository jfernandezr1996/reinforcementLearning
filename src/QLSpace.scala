
/*
Clase que define el espacio de búsqueda + movimientos (estado y acciones)
para el algoritmo Q-Learning.

@param states Estados definidos en el espacio Q-Learning.
@param goalIds Lista de ids de estados teminales.
*/

class QLSpace(states: Array[QLState], 
              goalIds: Array[Int]
             ){
  
  val statesMap: immutable.Map[Int, QLState] =
    states.map(st => (st.id, st)).toMap
  
  val goalStates = new immutable.HashSet[Int]() ++ goalIds
  
  def maxR(st: QLState, policy: QLPolicy): Double = {
      // Estado con mayor recompensa desde estado st
    val best = states.filter(_ != st)
      .maxBy(new_st => policy.R(st.id, new_st.id))
    policy.R(st.id, best.id)
  }
  
  def maxQ(st: QLState, policy: QLPolicy): Double = {
      // Estado con mayor Q(st, accion) (valor estimado) desde estado st
    val best = states.filter(_ != st)
      .maxBy(new_st => policy.EQ(st.id, new_st.id))
    policy.EQ(st.id, best.id) 
  }
  
  def nextStates(st: QLState): List[QLState] =
    st.actions.map(ac => statesMap.get(ac.to).get)
    
    // Inicializar búsqueda con un estado aleatorio
  def init(r: Random): QLState =
    states(r.nextInt(states.size-1)) 
  
    // Verificar si el estado es teminal
  def isGoal(st: QLState): Boolean = goalStates.contains(st.id)
  
}