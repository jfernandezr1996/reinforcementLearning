/*
Clase de estados en Q-Learning.
Un estado es definido por un identificador único y el listado de acciones
que se pueden hacer desde este estado hasta otro.
La lista de acciones es vacío si el estado es terminal (o objetivo).

@param id Identificador del estado.
@param actions Lista de acciones
*/

class QLState(
  val id: Int, // identificador del estado
  val actions: List[QLAction] = List.empty) {
  
  @inline
  // Comprobación si estamos en un estado final/terminal
  def isGoal: Boolean = actions.isEmpty 
}
 

/*
Clase de acciones.

@param from Estado origen.
@param to Estado destino.
*/

class QLAction(
    var from: Int, var to: Int)