# Reinforcement Learning, Q-Learning - Scala

Proyecto que corresponde al desarrollo de un algortimo Q-Learning usando el lenguaje de programación Scala.

## Algoritmo

Este algoritmo hará que el agente aprenda una serie de normas que le lleve a tomar la decisión óptima (acción) en cada estado en particular.

El objetivo del agente es maximizar su recompensa total. Lo hace sumando la máxima recompensa alcanzable, en estados futuros, a la recompensa por alcanzar su estado actual, influyendo eficazmente en la acción actual por la futura recompensa potencial. Esta recompensa potencial es una suma ponderada de la esperanza matemática de las recompensas de los futuros pasos empezando desde el estado actual.

Hablamos de algunos de los parámetros: Gamma  (el factor de descuento) es un número entre 0 y 1 y evalúa las recompensas recibidas anteriormente con un valor mayor que las recibidas posteriormente. También puede ser interpretada como la probabilidad de tener éxito (o sobrevivir) en cada paso de transición.

El algoritmo, por tanto, tiene una función que calcula la calidad de una combinación estado-acción:

Q'(st,a) <- (1 - alpha) * Q(s,a) + alpha * (r + gamma * max Q(st+1, a))

Donde r es la recompensa recibida al pasar del estado st al st+1, y alpha es el índice de aprendizaje (también entre 0 y 1).

Un episodio del algoritmo termina cuando el estado st+1 es un estado final o terminal.

Para todos los estados finales sf, Q(sf, a) nunca se actualiza, pero se fija al valor de recompensa r observado para el estado sf.

## Pasos

1. Instanciar estados y diagrama de transición entre cada estado con recompensa y probabilidades de transición. El valor Q(s,a) se irá actualizando a medida que el algoritmo va entrenando.
2. Entrenamiento del algoritmo empezando con un estado aleatorio.
3. Tomar la acción desde el estado st que maximice la recompensa y actualizamos el Q-value, Q(s,a).
4. Entrenado el algoritmo, predecimos el próximo estado dado uno cualquiera.
